package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.particles.SimpleParticleType;

public final class ModParticleTypes {
    private ModParticleTypes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Particles");
    }

    public static final RegistryHandle<SimpleParticleType> CHARGED_SPARK = Services.REGISTRY.registerParticle(
            "charged_spark",
            false
    );
}
