package io.github.cherrybxrry.inflatablemobgirls.entity;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerRideableMeleeAttacking extends PlayerRideable {
    boolean canMeleeAttack();

    void handleStartMeleeAttack();

    void handleStopMeleeAttack();

    default int getMeleeAttackCooldown() {
        return 0;
    }
}
