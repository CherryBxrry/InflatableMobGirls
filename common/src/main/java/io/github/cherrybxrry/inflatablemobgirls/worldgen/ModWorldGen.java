package io.github.cherrybxrry.inflatablemobgirls.worldgen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.core.registries.Registries;import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class ModWorldGen {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CREEPSHROOM = configuredFeatureKey("creepshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HUGE_CREEPSHROOM = configuredFeatureKey("huge_creepshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> NETHER_GEYSER = configuredFeatureKey("nether_geyser");

    public static final ResourceKey<PlacedFeature> CREEPSHROOM_PLACED = placedFeatureKey("creepshroom_placed");
    public static final ResourceKey<PlacedFeature> NETHER_GEYSER_PLACED = placedFeatureKey("nether_geyser_placed");

    private ModWorldGen() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod World Gen");
    }

    private static ResourceKey<ConfiguredFeature<?, ?>> configuredFeatureKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, Constants.id(name));
    }

    private static ResourceKey<PlacedFeature> placedFeatureKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, Constants.id(name));
    }
}
