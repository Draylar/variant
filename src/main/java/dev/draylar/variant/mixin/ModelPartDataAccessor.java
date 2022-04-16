package dev.draylar.variant.mixin;

import net.minecraft.client.model.ModelCuboidData;
import net.minecraft.client.model.ModelPartData;
import net.minecraft.client.model.ModelTransform;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;
import java.util.Map;

@Mixin(ModelPartData.class)
public interface ModelPartDataAccessor {
    @Accessor
    List<ModelCuboidData> getCuboidData();

    @Accessor
    Map<String, ModelPartData> getChildren();

    @Accessor
    ModelTransform getRotationData();

    @Mutable
    @Accessor
    void setRotationData(ModelTransform rotationData);

    @Mutable
    @Accessor
    void setCuboidData(List<ModelCuboidData> cuboidData);
}
