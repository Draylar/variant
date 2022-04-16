package dev.draylar.variant.api;

import net.minecraft.client.render.Shader;
import net.minecraft.resource.ResourceManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class ShaderRegistry {

    public static final ShaderRegistry INSTANCE = new ShaderRegistry();
    public static List<Function<ResourceManager, I>> SHADERS = new ArrayList<>();
    public static List<Shader> loadedShaders = new ArrayList<>();
    public static final Map<Shader, Consumer<Shader>> consumers = new HashMap<>();

    public static void register(Function<ResourceManager, I> consumer) {
        SHADERS.add(consumer);
    }

    public interface I {
        public void register(ShaderRegistry manager);
    }

    public void register(Shader shader, Consumer<Shader> consumer) throws IOException {
        consumers.put(shader, consumer);
        loadedShaders.add(shader);
    }

    public void close() {
        loadedShaders.forEach(Shader::close);
    }
}
