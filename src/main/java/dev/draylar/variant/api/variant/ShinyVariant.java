package dev.draylar.variant.api.variant;

import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class ShinyVariant extends EntityVariant {

    public ShinyVariant(String id) {
        super(id);
    }

    @Override
    public void tick(World world, MobEntity mob) {
        super.tick(world, mob);
        if(world.isClient && world.random.nextDouble() <= 0.3) {
            world.addParticle(ParticleTypes.END_ROD, false, mob.getParticleX(1.0f), mob.getY() + world.random.nextFloat() * mob.getHeight() * 1.25f, mob.getParticleZ(1.0f), 0.0, 0.0, 0.0);
        }
    }
}
