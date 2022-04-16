package dev.draylar.variant.mixin;

import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void adjustDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        if((Entity) (Object) this instanceof MobEntity mob) {
            EntityVariant variant = VariantRegistry.getVariant(mob);
            if(variant == VariantRegistry.MEGA) {
                EntityDimensions dimensions = cir.getReturnValue();
                cir.setReturnValue(dimensions.scaled(2));
            }
        }
    }
}
