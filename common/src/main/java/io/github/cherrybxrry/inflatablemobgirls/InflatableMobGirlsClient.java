package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.init.client.*;

public final class InflatableMobGirlsClient {
    private static boolean initialized;

    private InflatableMobGirlsClient() {
    }

    public static void init() {
        if (initialized) return;
        initialized = true;

        ModBlockEntityRenderers.load();
        ModLayerDefinitions.load();
        ModEntityRenderers.load();
        ModKeyMappings.load();
        ModParticleProviders.load();
        ModSpecialModelRenderers.load();
    }
}
