package dev.draylar.variant.mixin;

import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityMixin extends LivingEntity {

    private MobEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void onMobTick(CallbackInfo ci) {
        EntityVariant variant = VariantRegistry.getVariant((MobEntity) (Object) this);
        if(variant != VariantRegistry.NONE) {
            variant.tick(world, (MobEntity) (Object) this);
        }
    }

    @Inject(method = "tryAttack", at = @At("HEAD"))
    private void inflictVariantStatus(Entity target, CallbackInfoReturnable<Boolean> cir) {
        EntityVariant variant = VariantRegistry.getVariant((MobEntity) (Object) this);
        if(variant != VariantRegistry.NONE) {
            variant.attack(world, (MobEntity) (Object) this, target);
        }
    }
}
