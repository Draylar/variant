package dev.draylar.variant.mixin;

import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow protected abstract float getSoundVolume();
    @Shadow public abstract float getSoundPitch();
    @Shadow protected abstract void dropLoot(DamageSource source, boolean causedByPlayer);
    @Shadow protected int playerHitTimer;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getMaxHealth", at = @At("HEAD"), cancellable = true)
    private void inflictVariantStatus(CallbackInfoReturnable<Float> cir) {
        if((LivingEntity) (Object) this instanceof MobEntity mob) {
            EntityVariant variant = VariantRegistry.getVariant(mob);
            if(variant == VariantRegistry.MEGA) {
                cir.setReturnValue(cir.getReturnValueF() * 3);
            }
        }
    }

    @Inject(method = "playHurtSound", at = @At("HEAD"), cancellable = true)
    private void modifyHurtSound(DamageSource source, CallbackInfo ci) {
        if((LivingEntity) (Object) this instanceof MobEntity mob) {
            EntityVariant variant = VariantRegistry.getVariant(mob);
            @Nullable SoundEvent sound = variant.getHurtSound();
            if(sound != null) {
                playSound(sound, getSoundVolume(), getSoundPitch());
                ci.cancel();
            }
        }
    }

    @Inject(method = "drop", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;dropLoot(Lnet/minecraft/entity/damage/DamageSource;Z)V"))
    private void dropShinyLoot(DamageSource source, CallbackInfo ci) {
        if((LivingEntity) (Object) this instanceof MobEntity mob) {
            EntityVariant variant = VariantRegistry.getVariant(mob);
            if(variant == VariantRegistry.SHINY) {
                boolean player = playerHitTimer > 0;
                dropLoot(source, player);
                dropLoot(source, player);
                dropLoot(source, player);
                dropLoot(source, player);
            }
        }
    }
}