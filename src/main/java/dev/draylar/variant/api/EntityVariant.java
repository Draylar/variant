package dev.draylar.variant.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EntityVariant {

    private final String id;

    public EntityVariant(String id) {

        this.id = id;
    }

    public void tick(World world, MobEntity mob) {

    }

    public void attack(World world, MobEntity source, Entity target) {

    }

    public void initialize(MobEntity mob) {

    }

    public String getId() {
        return id;
    }

    @Nullable
    public SoundEvent getHurtSound() {
        return null;
    }
}
