package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.networking.ServerboundPackets;
import io.github.cherrybxrry.inflatablemobgirls.networking.packet.ServerboundRiddenInputPacket;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public final class ModPackets {
    private ModPackets() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Packets");

        registerClientbound();
        registerServerbound();
    }

    private static void registerClientbound() {

    }

    private static void registerServerbound() {
        ServicesClient.CLIENT_NETWORKING.registerServerboundPacket(
                ServerboundRiddenInputPacket.TYPE,
                ServerboundRiddenInputPacket.STREAM_CODEC,
                ServerboundPackets::handleRiddenInput
        );
    }
}
