package io.github.cherrybxrry.inflatablemobgirls.networking;

import io.github.cherrybxrry.inflatablemobgirls.entity.PlayerRideableMeleeAttacking;
import io.github.cherrybxrry.inflatablemobgirls.entity.PlayerRideableRangedAttacking;
import io.github.cherrybxrry.inflatablemobgirls.networking.packet.ServerboundRiddenInputPacket;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.client.IClientNetworkingHelper;
import net.minecraft.world.entity.player.Player;

public class ServerboundPackets {
    public static void handleRiddenInput(final ServerboundRiddenInputPacket packet, IClientNetworkingHelper.Context context) {
        Player player = context.player();

        switch (packet.action()) {
            case START_MELEE_ATTACK -> {
                if (player.getVehicle() instanceof PlayerRideableMeleeAttacking vehicle && vehicle.canMeleeAttack() && vehicle.getMeleeAttackCooldown() == 0) {
                    vehicle.handleStartMeleeAttack();
                }
            }
            case STOP_MELEE_ATTACK -> {
                if (player.getVehicle() instanceof PlayerRideableMeleeAttacking vehicle) vehicle.handleStopMeleeAttack();
            }
            case START_RANGED_ATTACK -> {
                if (player.getVehicle() instanceof PlayerRideableRangedAttacking vehicle && vehicle.canRangedAttack() && vehicle.getRangedAttackCooldown() == 0) {
                    vehicle.handleStartRangedAttack();
                }
            }
            case STOP_RANGED_ATTACK -> {
                if (player.getVehicle() instanceof PlayerRideableRangedAttacking vehicle) vehicle.handleStopRangedAttack();
            }
            default -> throw new IllegalArgumentException("Invalid action for ServerboundRiddenInputPacket");
        }
    }
}
