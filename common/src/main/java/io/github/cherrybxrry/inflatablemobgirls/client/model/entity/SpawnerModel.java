package io.github.cherrybxrry.inflatablemobgirls.client.model.entity;

import net.minecraft.client.animation.KeyframeAnimation;

public interface SpawnerModel {
    default void applySpawnerAnimation(KeyframeAnimation spawnerAnimation, boolean isSpawner) {
        if (isSpawner) spawnerAnimation.applyStatic();
    }
}
