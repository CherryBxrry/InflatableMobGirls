package io.github.cherrybxrry.inflatablemobgirls.platform.worldgen;

import io.github.cherrybxrry.inflatablemobgirls.init.ModEntitySpawns;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModWorldGen;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalBiomeTags;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.GenerationStep;

public final class FabricWorldGen {
    private FabricWorldGen() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        // Overworld
        BiomeModifications.addFeature(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_PLAINS),
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModWorldGen.CREEPSHROOM_PLACED
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.tag(ConventionalBiomeTags.IS_PLAINS),
                ModEntityTypes.CREEPER_GIRL.get().getCategory(),
                ModEntityTypes.CREEPER_GIRL.get(),
                ModEntitySpawns.CREEPER_GIRL.weight(),
                ModEntitySpawns.CREEPER_GIRL.minGroupSize(),
                ModEntitySpawns.CREEPER_GIRL.maxGroupSize()
        );

        // Nether
        BiomeModifications.addFeature(
                BiomeSelectors.includeByKey(Biomes.NETHER_WASTES),
                GenerationStep.Decoration.UNDERGROUND_DECORATION,
                ModWorldGen.NETHER_GEYSER_PLACED
        );

        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.BASALT_DELTAS),
                ModEntityTypes.GHAST_GIRL.get().getCategory(),
                ModEntityTypes.GHAST_GIRL.get(),
                ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.weight(),
                ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.minGroupSize(),
                ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.maxGroupSize()
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.NETHER_WASTES),
                ModEntityTypes.GHAST_GIRL.get().getCategory(),
                ModEntityTypes.GHAST_GIRL.get(),
                ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.weight(),
                ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.minGroupSize(),
                ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.maxGroupSize()
        );
        BiomeModifications.addSpawn(
                BiomeSelectors.includeByKey(Biomes.SOUL_SAND_VALLEY),
                ModEntityTypes.GHAST_GIRL.get().getCategory(),
                ModEntityTypes.GHAST_GIRL.get(),
                ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.weight(),
                ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.minGroupSize(),
                ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.maxGroupSize()
        );
    }
}
