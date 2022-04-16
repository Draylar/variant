package dev.draylar.variant.api.variant;

import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.MobEntity;

import java.util.Optional;

public class AbyssalVariant extends EntityVariant {

    public AbyssalVariant(String id) {
        super(id);
    }

    @Override
    public void initialize(MobEntity mob) {
        super.initialize(mob);

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_ARMOR)).ifPresent(speed -> {
            speed.addPersistentModifier(new EntityAttributeModifier("VariantArmor", 5.0f, EntityAttributeModifier.Operation.ADDITION));
        });

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)).ifPresent(speed -> {
            speed.addPersistentModifier(new EntityAttributeModifier("VariantKBResistance", 2.0f, EntityAttributeModifier.Operation.ADDITION));
        });

        Optional.ofNullable(mob.getAttributes().getCustomInstance(EntityAttributes.GENERIC_ATTACK_SPEED)).ifPresent(speed -> {
            speed.addPersistentModifier(new EntityAttributeModifier("VariantAttackSpeed", -0.1f, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        });
    }
}
