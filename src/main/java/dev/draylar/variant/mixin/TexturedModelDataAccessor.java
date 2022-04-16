package dev.draylar.variant.mixin;

import net.minecraft.client.model.ModelData;
import net.minecraft.client.model.TextureDimensions;
import net.minecraft.client.model.TexturedModelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TexturedModelData.class)
public interface TexturedModelDataAccessor {
    @Accessor
    ModelData getData();

    @Accessor
    TextureDimensions getDimensions();
}
