package dev.draylar.variant.api.variant;

import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class GhostlyVariant extends EntityVariant {

    public GhostlyVariant(String id) {
        super(id);
    }

    @Override
    public @Nullable SoundEvent getHurtSound() {
        return SoundEvents.PARTICLE_SOUL_ESCAPE;
    }

    @Override
    public void initialize(MobEntity mob) {
        super.initialize(mob);

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).ifPresent(speed -> {
            speed.addPersistentModifier(new EntityAttributeModifier("VariantMovementSpeed", -0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        });
    }
}
