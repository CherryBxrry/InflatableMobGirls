package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.particle.ChargedSparkParticle;
import io.github.cherrybxrry.inflatablemobgirls.init.ModParticleTypes;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public final class ModParticleProviders {
    private ModParticleProviders() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Particle Providers");

        ServicesClient.CLIENT_REGISTRY.registerParticleProvider(ModParticleTypes.CHARGED_SPARK.get(), ChargedSparkParticle.Provider::new);
    }
}
