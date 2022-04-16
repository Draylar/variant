package dev.draylar.variant.mixin;

import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.ModelCuboidData;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.util.math.Vec3f;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ModelCuboidData.class)
public interface ModelCuboidDataAccessor {
    @Accessor
    Dilation getExtraSize();

    @Accessor
    boolean isMirror();

    @Accessor
    Vector2f getTextureUV();

    @Accessor
    Vector2f getTextureScale();

    @Invoker("<init>")
    static ModelCuboidData createModelCuboidData(@Nullable String name, float textureX, float textureY, float offsetX, float offsetY, float offsetZ, float sizeX, float sizeY, float sizeZ, Dilation extra, boolean mirror, float textureScaleX, float textureScaleY) {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor
    void setExtraSize(Dilation extraSize);

    @Accessor
    String getName();

    @Accessor
    Vec3f getOffset();

    @Accessor
    Vec3f getDimensions();
}
