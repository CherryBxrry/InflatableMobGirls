package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.waypoints.Waypoint;

import java.util.function.Function;
import java.util.function.Supplier;

public final class ModItems {
    private ModItems() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Items");
    }

    public static final RegistryHandle<Item> CREEPER_GIRL_HEAD = Services.REGISTRY.registerItem(
            "creeper_girl_head",
            properties -> new StandingAndWallBlockItem(
                    ModBlocks.CREEPER_GIRL_HEAD.get(),
                    ModBlocks.CREEPER_GIRL_WALL_HEAD.get(),
                    Direction.DOWN,
                    Waypoint.addHideAttribute(properties)
                            .rarity(Rarity.UNCOMMON)
                            .equippableUnswappable(EquipmentSlot.HEAD)
            )
    );

    public static final RegistryHandle<Item> CREEPSPORE = Services.REGISTRY.registerItem(
            "creepspore",
            createBlockItemWithCustomItemName(ModBlocks.CREEPSPORE_CROP));

    public static final RegistryHandle<Item> BELLOWS = Services.REGISTRY.registerItem(
            "bellows",
            properties -> new Item(properties.stacksTo(1).useCooldown(2.5F))
    );

    public static final RegistryHandle<Item> SOUL_CHOCOLATE = Services.REGISTRY.registerItem(
            "soul_chocolate",
            properties -> new Item(properties.food(ModFoods.SOUL_CHOCOLATE, ModConsumables.SOUL_CHOCOLATE))
    );

    public static final RegistryHandle<Item> CREEPER_GIRL_SPAWN_EGG = registerSpawnEgg(
            "creeper_girl_spawn_egg",
            ModEntityTypes.CREEPER_GIRL::get
    );

    public static final RegistryHandle<Item> GHAST_GIRL_SPAWN_EGG = registerSpawnEgg(
            "ghast_girl_spawn_egg",
            ModEntityTypes.GHAST_GIRL::get
    );

    private static Function<Item.Properties, Item> createBlockItemWithCustomItemName(Supplier<Block> block) {
        return properties -> new BlockItem(block.get(), properties.useItemDescriptionPrefix());
    }

    private static RegistryHandle<Item> registerSpawnEgg(String name, Supplier<EntityType<?>> entity) {
        return Services.REGISTRY.registerItem(
                name,
                properties -> new SpawnEggItem(properties.spawnEgg(entity.get()))
        );
    }
}
