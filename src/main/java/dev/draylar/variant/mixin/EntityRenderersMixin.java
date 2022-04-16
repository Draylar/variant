package dev.draylar.variant.mixin;

import dev.draylar.variant.VariantClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Set;

@Mixin(EntityRenderers.class)
public class EntityRenderersMixin {

    @Shadow @Final private static Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES;

    static {
        RENDERER_FACTORIES.forEach((type, factory) -> {
            Identifier id = Registry.ENTITY_TYPE.getId(type);
            Set<EntityModelLayer> layers = EntityModelLayersAccessor.getLAYERS();
            layers.forEach(layer -> {
                String layerId = layer.getId().toString();
                if(layerId.equals(id.toString())) {
                    EntityRendererFactory.Context cpmtext = new EntityRendererFactory.Context(
                            MinecraftClient.getInstance().getEntityRenderDispatcher(),
                            MinecraftClient.getInstance().getItemRenderer(),
                            MinecraftClient.getInstance().getResourceManager(),
                            MinecraftClient.getInstance().getEntityModelLoader(),
                            MinecraftClient.getInstance().textRenderer
                    );

                    EntityRenderer<?> renderer = factory.create(cpmtext);
                    VariantClient.LAYERS_BY_CLASS.put(renderer.getClass(), layer);
                }
            });
        });
    }
}
