package io.github.cherrybxrry.inflatablemobgirls.entities;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerRideableRangedAttacking extends PlayerRideable {
    boolean canRangedAttack();

    void handleStartRangedAttack();

    void handleStopRangedAttack();

    default int getRangedAttackCooldown() {
        return 0;
    }
}
