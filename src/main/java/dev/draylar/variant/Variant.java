package dev.draylar.variant;

import dev.draylar.variant.api.VariantRegistry;
import net.fabricmc.api.ModInitializer;

public class Variant implements ModInitializer {

    @Override
    public void onInitialize() {
        VariantRegistry.load();
    }
}
