package dev.draylar.variant.mixin;

import dev.draylar.variant.VariantClient;
import dev.draylar.variant.api.EntityVariant;
import dev.draylar.variant.api.VariantRegistry;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin<T extends LivingEntity> extends EntityRenderer<T> {

    private LivingEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "getRenderLayer", at = @At("HEAD"), cancellable = true)
    private void changeRenderLayer(T entity, boolean showBody, boolean translucent, boolean showOutline, CallbackInfoReturnable<@Nullable RenderLayer> cir) {
        if(entity instanceof MobEntity mob) {
            EntityVariant variant = VariantRegistry.getVariant(mob);
            if(variant == VariantRegistry.GHOSTLY) {
                cir.setReturnValue(VariantClient.GHOST.apply(getTexture(entity)));
            } else if(variant == VariantRegistry.SHINY) {
                cir.setReturnValue(VariantClient.SHINY.apply(getTexture(entity)));
            } else if (variant == VariantRegistry.ABYSSAL) {
                cir.setReturnValue(RenderLayer.getEndPortal());
            }
        }
    }
}
