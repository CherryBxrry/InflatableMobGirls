package io.github.cherrybxrry.inflatablemobgirls.worldgen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.feature.HugeCreepshroomFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;

public final class ModFeatures {
    private ModFeatures() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Features");
    }

    public static final RegistryHandle<Feature<HugeMushroomFeatureConfiguration>> HUGE_CREEPSHROOM = Services.REGISTRY.registerFeature(
            "huge_creepshroom",
            new HugeCreepshroomFeature(HugeMushroomFeatureConfiguration.CODEC)
    );
}
