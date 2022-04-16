package dev.draylar.variant.api.variant;

import dev.draylar.variant.api.EntityVariant;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FlameVariant extends EntityVariant {

    public FlameVariant(String id) {
        super(id);
    }

    @Override
    public void tick(World world, MobEntity mob) {
        super.tick(world, mob);
        if(world.isClient && world.random.nextDouble() <= 0.3) {
            world.addParticle(ParticleTypes.FLAME, false, mob.getParticleX(1.0f), mob.getY() + world.random.nextFloat() * mob.getHeight() * 1.25f, mob.getParticleZ(1.0f), 0.0, 0.0, 0.0);
            world.addParticle(ParticleTypes.SMOKE, false, mob.getParticleX(1.0f), mob.getY() + world.random.nextFloat() * mob.getHeight() * 1.25f, mob.getParticleZ(1.0f), 0.0, 0.0, 0.0);
        }
    }

    @Override
    public void attack(World world, MobEntity source, Entity target) {
        super.attack(world, source, target);
        target.setOnFireFor(20 * 3);
    }
}
