package dev.draylar.variant.api.variant;

import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

import java.util.Optional;

public class ThunderingVariant extends EntityVariant {

    public ThunderingVariant(String id) {
        super(id);
    }

    @Override
    public void initialize(MobEntity mob) {
        super.initialize(mob);

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED)).ifPresent(speed -> {
           speed.addPersistentModifier(new EntityAttributeModifier("VariantMovementSpeed", 0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        });

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_ATTACK_SPEED)).ifPresent(speed -> {
            speed.addPersistentModifier(new EntityAttributeModifier("VariantAttackSpeed", 0.15f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        });
    }
}
