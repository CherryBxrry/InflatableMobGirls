package io.github.cherrybxrry.inflatablemobgirls.datagen.bootstrap;

import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModFeatures;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModWorldGen;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class ModWorldGenBootstrapper {
    private ModWorldGenBootstrapper() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void bootstrapConfiguredFeatures(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        context.register(ModWorldGen.CREEPSHROOM, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.CREEPSHROOM.block().get()))
        ));

        context.register(ModWorldGen.HUGE_CREEPSHROOM, new ConfiguredFeature<>(
                ModFeatures.HUGE_CREEPSHROOM.get(),
                new HugeMushroomFeatureConfiguration(
                        BlockStateProvider.simple(
                                ModBlocks.CREEPSHROOM_BLOCK.block().get().defaultBlockState().setValue(HugeMushroomBlock.DOWN, false)
                        ),
                        BlockStateProvider.simple(
                                ModBlocks.HUGE_CREEPSHROOM_STEM.block().get().defaultBlockState()
                        ),
                        2,
                        BlockPredicate.matchesTag(BlockTags.HUGE_BROWN_MUSHROOM_CAN_PLACE_ON)
                )
        ));

        context.register(ModWorldGen.NETHER_GEYSER, new ConfiguredFeature<>(
                Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.simple(ModBlocks.NETHER_GEYSER.block().get()))
        ));
    }

    public static void bootstrapPlacedFeatures(BootstrapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        context.register(ModWorldGen.CREEPSHROOM_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModWorldGen.CREEPSHROOM),
                List.of(
                        RarityFilter.onAverageOnceEvery(32), // Tried {chance} times per chunk
                        InSquarePlacement.spread(), // Spreads it throughout the chunk
                        PlacementUtils.HEIGHTMAP,
                        BiomeFilter.biome(), // Normal biome placement
                        CountPlacement.of(64), // Number of block placement attempts
                        RandomOffsetPlacement.ofTriangle(6, 2), // Creates the patch
                        BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE) // Only replaces air
                )
        ));

        context.register(ModWorldGen.NETHER_GEYSER_PLACED, new PlacedFeature(
                configuredFeatures.getOrThrow(ModWorldGen.NETHER_GEYSER),
                List.of(
                        RarityFilter.onAverageOnceEvery(16), // Tried {chance} times per chunk
                        InSquarePlacement.spread(), // Spreads it throughout the chunk
                        PlacementUtils.RANGE_4_4,
                        BiomeFilter.biome(), // Normal biome placement
                        CountPlacement.of(64), // Number of block placement attempts
                        RandomOffsetPlacement.ofTriangle(7, 3), // Creates the patch
                        BlockPredicateFilter.forPredicate(BlockPredicate.allOf(
                                BlockPredicate.ONLY_IN_AIR_OR_WATER_PREDICATE, // Only replaces air
                                BlockPredicate.matchesBlocks(
                                        new Vec3i(0, -1, 0),
                                        List.of(
                                                Blocks.NETHERRACK,
                                                Blocks.MAGMA_BLOCK
                                        ))) // Only spawns on
                        ))
        ));
    }
}
