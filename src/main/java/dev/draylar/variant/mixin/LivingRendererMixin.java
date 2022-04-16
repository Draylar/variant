package dev.draylar.variant.mixin;

import dev.draylar.variant.client.VariantOverlayFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow
    protected abstract boolean addFeature(FeatureRenderer<T, M> feature);

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addLayerFeatures(EntityRendererFactory.Context context, EntityModel model, float shadowRadius, CallbackInfo ci) {
        if((Object) this instanceof MobEntityRenderer) {
            FeatureRendererContext<T, M> c = (FeatureRendererContext<T, M>) this;
            addFeature((FeatureRenderer<T, M>) new VariantOverlayFeatureRenderer(c));
        }
    }
}
