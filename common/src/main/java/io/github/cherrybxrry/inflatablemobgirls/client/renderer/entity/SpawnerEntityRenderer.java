package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity;

import net.minecraft.world.entity.LivingEntity;

public interface SpawnerEntityRenderer {
    // Probably a good way to tell if it's a spawner block
    default boolean isSpawner(LivingEntity entity) {
        return entity.level().getEntity(entity.getUUID()) == null && entity.isAlive() && entity.tickCount <= 0;
    }
}
