package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.networking.ModServerPayloadHandlers;
import io.github.cherrybxrry.inflatablemobgirls.networking.packets.ServerboundRiddenInputPacket;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public final class ModPackets {
    private ModPackets() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Packets");

        ServicesClient.CLIENT_NETWORKING.registerServerboundPacket(
                ServerboundRiddenInputPacket.TYPE,
                ServerboundRiddenInputPacket.CODEC,
                (packet, context) -> ModServerPayloadHandlers.handleRiddenInput(packet, context.player())
        );
    }
}
