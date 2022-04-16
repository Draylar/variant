package dev.draylar.variant;

import dev.draylar.variant.client.VariantShaders;
import dev.draylar.variant.mixin.RenderLayerAccessor;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class VariantClient implements ClientModInitializer {


    public static final Map<Class<?>, EntityModelLayer> LAYERS_BY_CLASS = new HashMap<>();

    public static final Function<Identifier, RenderLayer> GHOST = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .shader(VariantShaders.GHOST_SHADER_PHASE)
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                .build(true);

        return RenderLayerAccessor.callOf("entity_ghost", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    public static final Function<Identifier, RenderLayer> SHINY = Util.memoize(texture -> {
        RenderLayer.MultiPhaseParameters multiPhaseParameters = RenderLayer.MultiPhaseParameters
                .builder()
                .shader(VariantShaders.SHINY_SHADER_PHASE)
                .texture(new RenderPhase.Texture(texture, false, false))
                .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                .lightmap(RenderLayer.ENABLE_LIGHTMAP)
                .overlay(RenderLayer.ENABLE_OVERLAY_COLOR)
                .build(true);

        return RenderLayerAccessor.callOf("entity_shiny", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, 256, true, true, multiPhaseParameters);
    });

    @Override
    public void onInitializeClient() {
        VariantShaders.initialize();
    }
}
