package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.init.*;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModFeatures;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModWorldGen;

public class InflatableMobGirls {
    public static void init() {
        if (Services.PLATFORM.isModLoaded(Constants.MOD_ID)) {
            Constants.LOG.info(Constants.ENTRY_MARKER, "Loading {} for {}", Constants.MOD_ID, Services.PLATFORM.getPlatformName());
        }

        ModDatapackRegistries.load();

        ModSounds.load();
        ModParticleTypes.load();
        ModBlocks.load();
        ModBlockEntityTypes.load();
        ModDataComponents.load();
        ModConsumeEffectTypes.load();
        ModItems.load();
        ModEntityAttributes.load();
        ModEntityDataSerializers.load();
        ModEntitySpawns.load();
        ModEntityTypes.load();
        ModCreativeTabs.load();
        ModFeatures.load();
        ModPackets.load();
        ModWorldGen.load();
    }
}