package dev.draylar.variant.client;

import dev.draylar.variant.VariantClient;
import dev.draylar.variant.api.VariantRegistry;
import dev.draylar.variant.api.EntityVariant;
import dev.draylar.variant.api.ModelUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

public class VariantOverlayFeatureRenderer<T extends Entity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private static final Identifier SKIN = new Identifier("textures/entity/lightning_overlay.png");
    private static final Identifier FLAME_SKIN = new Identifier("textures/entity/flame_overlay.png");

    private M model;

    public VariantOverlayFeatureRenderer(FeatureRendererContext<T, M> featureRendererContext) {
        super(featureRendererContext);

        M model = featureRendererContext.getModel();
        EntityModelLayer layer = VariantClient.LAYERS_BY_CLASS.get(featureRendererContext.getClass());
        if(layer != null) {
            ModelPart expandedModelPat = MinecraftClient.getInstance().getEntityModelLoader().getModelPart(
                    new EntityModelLayer(new Identifier(layer.getId().getNamespace(), layer.getId().getPath() + "_variant_layer"), "main"));
            EntityModel<T> e = ModelUtils.duplicate(model, expandedModelPat);
            if(e != null) {
                this.model = (M) e;
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!(entity instanceof MobEntity)) {
            return;
        }

        EntityVariant variant = VariantRegistry.getVariant((MobEntity) entity);
        if(VariantRegistry.getVariant((MobEntity) entity) != VariantRegistry.THUNDERING && VariantRegistry.getVariant((MobEntity) entity) != VariantRegistry.FLAME) {
            return;
        }

        float f = (float) (entity).age + tickDelta;
        EntityModel<T> entityModel = getEnergySwirlModel();
        entityModel.animateModel(entity, limbAngle, limbDistance, tickDelta);
        getContextModel().copyStateTo(entityModel);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEnergySwirl(this.getEnergySwirlTexture(variant), this.getEnergySwirlX(f) % 1.0f, f * 0.01f % 1.0f));
        entityModel.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);

        if(variant == VariantRegistry.FLAME) {
            entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        } else {
            entityModel.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 0.5f, 0.5f, 0.5f, 1.0f);
        }
    }

    public float getEnergySwirlX(float partialAge) {
        return partialAge * 0.01f;
    }

    public Identifier getEnergySwirlTexture(EntityVariant variant) {
        return variant == VariantRegistry.FLAME ? FLAME_SKIN : SKIN;
    }

    public EntityModel<T> getEnergySwirlModel() {
        return model;
    }
}
