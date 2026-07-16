package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.datagen.bootstrap.ModWorldGenBootstrapper;
import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirlVariants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntitySpawns;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModRegistries;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModWorldGen;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public final class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {
    private static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, ModWorldGenBootstrapper::bootstrapConfiguredFeatures)
            .add(Registries.PLACED_FEATURE, ModWorldGenBootstrapper::bootstrapPlacedFeatures)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ModWorldGenProvider::bootstrapBiomeModifiers)
            .add(ModRegistries.CREEPER_GIRL_VARIANT, CreeperGirlVariants::bootstrap);

    // Features
    private static final ResourceKey<BiomeModifier> CREEPSHROOM_MODIFIER = biomeModifierKey("creepshroom");
    private static final ResourceKey<BiomeModifier> NETHER_GEYSER_MODIFIER = biomeModifierKey("nether_geyser");

    // Entity Spawning
    private static final ResourceKey<BiomeModifier> IS_PLAINS = biomeModifierKey("is_plains_spawns");
    private static final ResourceKey<BiomeModifier> BASALT_DELTAS = biomeModifierKey("basalt_deltas_spawns");
    private static final ResourceKey<BiomeModifier> NETHER_WASTES = biomeModifierKey("nether_wastes_spawns");
    private static final ResourceKey<BiomeModifier> SOUL_SAND_VALLEY = biomeModifierKey("soul_sand_valley_spawns");

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Constants.MOD_ID));
    }

    private static void bootstrapBiomeModifiers(BootstrapContext<BiomeModifier> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<PlacedFeature> placedFeatures = context.lookup(Registries.PLACED_FEATURE);

        // Overworld
        context.register(CREEPSHROOM_MODIFIER, new BiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(ModWorldGen.CREEPSHROOM_PLACED)),
                GenerationStep.Decoration.VEGETAL_DECORATION
        ));

        context.register(IS_PLAINS, new BiomeModifiers.AddSpawnsBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                WeightedList.of(List.of(
                        new Weighted<>(
                                new MobSpawnSettings.SpawnerData(
                                        ModEntityTypes.CREEPER_GIRL.get(),
                                        ModEntitySpawns.CREEPER_GIRL.minGroupSize(),
                                        ModEntitySpawns.CREEPER_GIRL.maxGroupSize()
                                ),
                                ModEntitySpawns.CREEPER_GIRL.weight()
                        )
                ))
        ));

        // Nether
        context.register(NETHER_GEYSER_MODIFIER, new BiomeModifiers.AddFeaturesBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES)),
                HolderSet.direct(placedFeatures.getOrThrow(ModWorldGen.NETHER_GEYSER_PLACED)),
                GenerationStep.Decoration.UNDERGROUND_DECORATION
        ));

        context.register(BASALT_DELTAS, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.BASALT_DELTAS)),

                WeightedList.of(List.of(
                        new Weighted<>(
                                new MobSpawnSettings.SpawnerData(
                                        ModEntityTypes.GHAST_GIRL.get(),
                                        ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.minGroupSize(),
                                        ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.maxGroupSize()
                                ),
                                ModEntitySpawns.GHAST_GIRL_BASALT_DELTAS.weight()
                        )
                ))
        ));

        context.register(NETHER_WASTES, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.NETHER_WASTES)),
                WeightedList.of(List.of(
                        new Weighted<>(
                                new MobSpawnSettings.SpawnerData(
                                        ModEntityTypes.GHAST_GIRL.get(),
                                        ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.minGroupSize(),
                                        ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.maxGroupSize()
                                ),
                                ModEntitySpawns.GHAST_GIRL_NETHER_WASTES.weight()
                        )
                ))
        ));

        context.register(SOUL_SAND_VALLEY, new BiomeModifiers.AddSpawnsBiomeModifier(
                HolderSet.direct(biomes.getOrThrow(Biomes.SOUL_SAND_VALLEY)),
                WeightedList.of(List.of(
                        new Weighted<>(
                                new MobSpawnSettings.SpawnerData(
                                        ModEntityTypes.GHAST_GIRL.get(),
                                        ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.minGroupSize(),
                                        ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.maxGroupSize()
                                ),
                                ModEntitySpawns.GHAST_GIRL_SOUL_SAND_VALLEY.weight()
                        )
                ))
        ));
    }

    private static ResourceKey<BiomeModifier> biomeModifierKey(String name) {
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, Constants.id(name));
    }
}
