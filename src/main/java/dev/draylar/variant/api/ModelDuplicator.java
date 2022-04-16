package dev.draylar.variant.api;

import dev.draylar.variant.mixin.ModelCuboidDataAccessor;
import dev.draylar.variant.mixin.ModelPartDataAccessor;
import dev.draylar.variant.mixin.TextureDimensionsAccessor;
import dev.draylar.variant.mixin.TexturedModelDataAccessor;
import net.minecraft.client.model.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModelDuplicator {

    public static TexturedModelData dialate(TexturedModelData data, float amount) {
        ModelData modelData = ((TexturedModelDataAccessor) data).getData();
        ModelPartData root = modelData.getRoot();
        processRecursively(root, amount);
        return data;
    }

    private static void processRecursively(ModelPartData data, float amount) {
        process(data, amount);
        ((ModelPartDataAccessor) data).getChildren().forEach((id, child) -> processRecursively(child, amount));
    }

    private static void process(ModelPartData data, float amount) {
        ((ModelPartDataAccessor) data).getCuboidData().forEach(modelCuboidData -> {
            ModelCuboidDataAccessor cuboidAccessor = (ModelCuboidDataAccessor) (Object) modelCuboidData;
            cuboidAccessor.setExtraSize(cuboidAccessor.getExtraSize().add(amount));
        });
    }

    public static TexturedModelData clone(TexturedModelData data) {
        TexturedModelDataAccessor dataAccessor = (TexturedModelDataAccessor) data;
        TextureDimensionsAccessor dimensionsAccessor = (TextureDimensionsAccessor) dataAccessor.getDimensions();
        ModelData modelData = dataAccessor.getData();
        return TexturedModelData.of(clone(modelData), dimensionsAccessor.getWidth(), dimensionsAccessor.getHeight());
    }

    private static ModelData clone(ModelData data) {
        // Clone cuboidData, rotationData, children to clone
        ModelPartData original = data.getRoot();
        cloneModelPartData(original);

        return cloneModelPartData(original);
    }

    public static ModelData cloneModelPartData(ModelPartData original) {
        ModelData c = new ModelData();
        ModelPartData clone = c.getRoot();
        ModelPartDataAccessor originalPartDataAccessor = (ModelPartDataAccessor) original;
        ModelPartDataAccessor clonePartDataAccessor = (ModelPartDataAccessor) clone;
        List<ModelCuboidData> clonedCuboidData = new ArrayList<>();

        // Clone cuboidData
        originalPartDataAccessor.getCuboidData().forEach(modelCuboidData -> {
            @NotNull ModelCuboidDataAccessor cuboidDataAccessor = (ModelCuboidDataAccessor) (Object) modelCuboidData;
            clonedCuboidData.add(ModelCuboidDataAccessor.createModelCuboidData(
                    cuboidDataAccessor.getName(),
                    cuboidDataAccessor.getTextureUV().getX(),
                    cuboidDataAccessor.getTextureUV().getY(),
                    cuboidDataAccessor.getOffset().getX(),
                    cuboidDataAccessor.getOffset().getY(),
                    cuboidDataAccessor.getOffset().getZ(),
                    cuboidDataAccessor.getDimensions().getX(),
                    cuboidDataAccessor.getDimensions().getY(),
                    cuboidDataAccessor.getDimensions().getZ(),
                    cuboidDataAccessor.getExtraSize(),
                    cuboidDataAccessor.isMirror(),
                    cuboidDataAccessor.getTextureScale().getX(),
                    cuboidDataAccessor.getTextureScale().getY()
            ));
        });
        clonePartDataAccessor.setCuboidData(clonedCuboidData);

        // Copy rotationData
        ModelTransform originalRotation = originalPartDataAccessor.getRotationData();
        clonePartDataAccessor.setRotationData(cloneTransform(originalRotation));

        // Clone children
        originalPartDataAccessor.getChildren().forEach((id, childData) -> {
            clonePartDataAccessor.getChildren().put(id, cloneModelPartData(childData).getRoot());
        });

        return c;
    }

    public static ModelTransform cloneTransform(ModelTransform transform) {
        return ModelTransform.of(transform.pivotX, transform.pivotY, transform.pivotZ, transform.pitch, transform.yaw, transform.roll);
    }
}
