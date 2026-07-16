package io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModRegistries;
import net.minecraft.core.ClientAsset;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.variant.BiomeCheck;
import net.minecraft.world.entity.variant.SpawnPrioritySelectors;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;

public class CreeperGirlVariants {
    public static final ResourceKey<CreeperGirlVariant> MOSSY = createKey("mossy");
    public static final ResourceKey<CreeperGirlVariant> PALE = createKey("pale");
    public static final ResourceKey<CreeperGirlVariant> SNOWY = createKey("snowy");
    public static final ResourceKey<CreeperGirlVariant> DEFAULT = MOSSY;

    private static ResourceKey<CreeperGirlVariant> createKey(String name) {
        return ResourceKey.create(ModRegistries.CREEPER_GIRL_VARIANT, Constants.id(name));
    }

    private static void register(BootstrapContext<CreeperGirlVariant> context, ResourceKey<CreeperGirlVariant> name, String fileName, ResourceKey<Biome> spawnBiome) {
        register(context, name, fileName, highPrioBiome(HolderSet.direct(context.lookup(Registries.BIOME).getOrThrow(spawnBiome))));
    }

    private static void register(BootstrapContext<CreeperGirlVariant> context, ResourceKey<CreeperGirlVariant> name, String fileName, TagKey<Biome> spawnBiome) {
        register(context, name, fileName, highPrioBiome(context.lookup(Registries.BIOME).getOrThrow(spawnBiome)));
    }

    private static SpawnPrioritySelectors highPrioBiome(HolderSet<Biome> biomes) {
        return SpawnPrioritySelectors.single(new BiomeCheck(biomes), 1);
    }

    private static void register(BootstrapContext<CreeperGirlVariant> context, ResourceKey<CreeperGirlVariant> name, String fileName, SpawnPrioritySelectors selectors) {
        Identifier normalTexture = Constants.id("entity/creeper_girl/" + fileName);
        Identifier chargedTexture = Constants.id("entity/creeper_girl/" + fileName + "_charged");
        Identifier babyTexture = Constants.id("entity/creeper_girl/" + fileName + "_baby");
        Identifier chargedBabyTexture = Constants.id("entity/creeper_girl/" + fileName + "_charged_baby");

        context.register(
                name,
                new CreeperGirlVariant(
                        new CreeperGirlVariant.AssetInfo(
                                new ClientAsset.ResourceTexture(normalTexture),
                                new ClientAsset.ResourceTexture(chargedTexture)
                        ),
                        new CreeperGirlVariant.AssetInfo(
                                new ClientAsset.ResourceTexture(babyTexture),
                                new ClientAsset.ResourceTexture(chargedBabyTexture)
                        ),
                        selectors
                )
        );
    }

    public static void bootstrap(BootstrapContext<CreeperGirlVariant> context) {
        register(context, MOSSY, "creeper_girl", SpawnPrioritySelectors.fallback(0));
        register(context, PALE, "creeper_girl_pale", Biomes.SUNFLOWER_PLAINS);
        register(context, SNOWY, "creeper_girl_snowy", Biomes.SNOWY_PLAINS);
    }
}
