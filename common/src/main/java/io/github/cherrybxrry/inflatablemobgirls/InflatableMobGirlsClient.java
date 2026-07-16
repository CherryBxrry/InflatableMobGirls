package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.init.client.ModBlockEntityRenderers;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModEntityRenderers;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModLayerDefinitions;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModParticleProviders;

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
        ModParticleProviders.load();
    }
}
