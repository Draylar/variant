package dev.draylar.variant.mixin;

import com.mojang.datafixers.util.Pair;
import dev.draylar.variant.api.ShaderRegistry;
import net.minecraft.client.gl.Program;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Shader;
import net.minecraft.resource.ResourceManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;
import java.util.function.Consumer;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

    @Inject(method = "loadShaders", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void loadCustomShaders(ResourceManager manager, CallbackInfo ci, List<Program> list, List<Pair<Shader, Consumer<Shader>>> shaderPairs) {
        // Clear cached shaders
        ShaderRegistry.loadedShaders.forEach(Shader::close);
        ShaderRegistry.loadedShaders.clear();

        // load new shaders
        ShaderRegistry.SHADERS.forEach(function -> function.apply(manager).register(ShaderRegistry.INSTANCE));
        ShaderRegistry.consumers.forEach((shader, callback) -> shaderPairs.add(Pair.of(shader, callback)));
    }
}
