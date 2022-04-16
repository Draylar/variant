package dev.draylar.variant.mixin;

import com.google.common.collect.ImmutableMap;
import dev.draylar.variant.api.ModelDuplicator;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(EntityModelLoader.class)
public class EntityModelsMixin {

    @Shadow private Map<EntityModelLayer, TexturedModelData> modelParts;

    @Inject(method = "reload", at = @At("RETURN"))
    private void injectDuplicatedLayers(ResourceManager manager, CallbackInfo ci) {
        Map<EntityModelLayer, TexturedModelData> expandedModelData = new HashMap<>();

        modelParts.forEach((layer, data) -> {
            if(layer.getName().equals("main")) {
                TexturedModelData clone = ModelDuplicator.clone(data);
                ModelDuplicator.dialate(clone, 1.0f);
                expandedModelData.put(new EntityModelLayer(new Identifier(layer.getId().getNamespace(), layer.getId().getPath() + "_variant_layer"), "main"), clone);
            }
        });

        expandedModelData.putAll(modelParts);
        modelParts = new ImmutableMap.Builder<EntityModelLayer, TexturedModelData>().putAll(expandedModelData).build();
    }
}
