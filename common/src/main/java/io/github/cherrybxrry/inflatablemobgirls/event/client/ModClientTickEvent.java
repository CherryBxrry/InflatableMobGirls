package io.github.cherrybxrry.inflatablemobgirls.event.client;

import io.github.cherrybxrry.inflatablemobgirls.init.client.ModKeyMappings;
import io.github.cherrybxrry.inflatablemobgirls.networking.packet.ServerboundRiddenInputPacket;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ModClientTickEvent {
    public static void playerKeyPressed(Minecraft client) {
        Player player = client.player;
        if (player == null) return;

        while (ModKeyMappings.MELEE_ATTACK.get().consumeClick()) {
            ServicesClient.CLIENT_NETWORKING.sendToServer(new ServerboundRiddenInputPacket(ServerboundRiddenInputPacket.Action.START_MELEE_ATTACK));
        }

        while (ModKeyMappings.RANGED_ATTACK.get().consumeClick()) {
            ServicesClient.CLIENT_NETWORKING.sendToServer(new ServerboundRiddenInputPacket(ServerboundRiddenInputPacket.Action.START_RANGED_ATTACK));
        }
    }
}
