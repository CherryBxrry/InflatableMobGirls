package io.github.cherrybxrry.inflatablemobgirls.entities;

import net.minecraft.world.entity.PlayerRideable;

public interface PlayerRideableMeleeAttacking extends PlayerRideable {
    boolean canMeleeAttack();

    void handleStartMeleeAttack();

    void handleStopMeleeAttack();

    default int getMeleeAttackCooldown() {
        return 0;
    }
}
