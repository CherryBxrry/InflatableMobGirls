package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirlVariant;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;

public final class ModDatapackRegistries {
    private ModDatapackRegistries() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Services.DATAPACKS.registerDataPack(
                ModRegistries.CREEPER_GIRL_VARIANT,
                CreeperGirlVariant.DIRECT_CODEC,
                CreeperGirlVariant.NETWORK_CODEC
        );
    }
}
