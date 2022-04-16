package dev.draylar.variant.client;

import dev.draylar.variant.api.ShaderRegistry;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.Shader;
import net.minecraft.client.render.VertexFormats;

import java.io.IOException;

public class VariantShaders {

    private static Shader GHOST_SHADER;
    private static Shader SHINY_SHADER;
    public static final RenderPhase.Shader GHOST_SHADER_PHASE = new RenderPhase.Shader(() -> GHOST_SHADER);
    public static final RenderPhase.Shader SHINY_SHADER_PHASE = new RenderPhase.Shader(() -> SHINY_SHADER);

    public static void initialize() {
        ShaderRegistry.register(manager -> registry -> {
            try {
                registry.register(new Shader(manager, "entity_ghost", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL), shader -> GHOST_SHADER = shader);
                registry.register(new Shader(manager, "entity_shiny", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL), shader -> SHINY_SHADER = shader);
            } catch (IOException exception) {
                registry.close();
                throw new RuntimeException("Failed to reload shaders", exception);
            }
        });
    }
}
