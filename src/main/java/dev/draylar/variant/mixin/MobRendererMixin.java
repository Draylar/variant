package dev.draylar.variant.mixin;

import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntityRenderer.class)
public class MobRendererMixin {

    @Inject(method = "render(Lnet/minecraft/entity/mob/MobEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"))
    private void beforeRender(MobEntity mob, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        EntityVariant variant = VariantRegistry.getVariant(mob);
        if(variant == VariantRegistry.MEGA) {
            matrices.push();
            matrices.scale(2.0f, 2.0f, 2.0f);
        }
    }

    @Inject(method = "render(Lnet/minecraft/entity/mob/MobEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("RETURN"))
    private void afterRender(MobEntity mob, float f, float g, MatrixStack matrices, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        EntityVariant variant = VariantRegistry.getVariant(mob);
        if(variant == VariantRegistry.MEGA) {
            matrices.pop();
        }
    }
}
