package dev.draylar.variant.mixin;

import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import dev.draylar.variant.impl.MobVariantExtensions;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
public abstract class MobEntityVariantMixin extends LivingEntity implements MobVariantExtensions {

    @Unique
    private static final TrackedData<String> VARIANT = DataTracker.registerData(MobEntity.class, TrackedDataHandlerRegistry.STRING);

    private MobEntityVariantMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initialize", at = @At("RETURN"))
    private void initializeVariantData(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        EntityVariant variant = VariantRegistry.getRandom(world.getRandom());
        dataTracker.set(VARIANT, variant.getId());
        variant.initialize((MobEntity) (Object) this);
    }

    @Inject(method = "initDataTracker", at = @At("RETURN"))
    private void addVariantDataTracker(CallbackInfo ci) {
        dataTracker.startTracking(VARIANT, VariantRegistry.NONE.getId());
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
    private void writeVariantData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putString("MobVariant", dataTracker.get(VARIANT));
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
    private void readVariantData(NbtCompound nbt, CallbackInfo ci) {
        dataTracker.set(VARIANT, nbt.getString("MobVariant"));
    }

    @Override
    public String getVariant() {
        return dataTracker.get(VARIANT);
    }
}
