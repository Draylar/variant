package dev.draylar.variant.api;

import dev.draylar.variant.api.variant.*;
import dev.draylar.variant.impl.MobVariantExtensions;
import net.minecraft.entity.mob.MobEntity;

import java.util.*;

public class VariantRegistry {

    private static final Map<String, EntityVariant> VARIANT_BY_ID = new HashMap<>();
    private static final List<EntityVariant> VARIANT_POOL = new ArrayList<>();

    public static final EntityVariant NONE = new EntityVariant("none");
    public static final EntityVariant GHOSTLY = register(new GhostlyVariant("ghostly"), 3);
    public static final EntityVariant THUNDERING = register(new ThunderingVariant("thundering"), 6);
    public static final EntityVariant SHINY = register(new ShinyVariant("shiny"), 1);
    public static final EntityVariant FLAME = register(new FlameVariant("flame"), 6);
    public static final EntityVariant MEGA = register(new EntityVariant("mega"), 5);
    public static final EntityVariant ABYSSAL = register(new AbyssalVariant("abyssal"), 1);

    public static void load() {

    }

    public static EntityVariant register(EntityVariant variant, int weight) {
        VARIANT_BY_ID.put(variant.getId(), variant);
        for (int i = 0; i < weight; i++) {
            VARIANT_POOL.add(variant);
        }

        return variant;
    }

    public static EntityVariant getRandom(Random random) {
        if(random.nextDouble() <= 0.1) {
            return VARIANT_POOL.get(random.nextInt(VARIANT_POOL.size()));
        }

        return NONE;
    }

    public static EntityVariant getVariant(MobEntity mob) {
        return VARIANT_BY_ID.getOrDefault(((MobVariantExtensions) mob).getVariant(), NONE);
    }
}
