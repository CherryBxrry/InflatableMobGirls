package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class ModLootTables {
    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();

    private ModLootTables() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final ResourceKey<LootTable> EXPLODE_CREEPER_GIRL = register("explode/creeper_girl");

    private static ResourceKey<LootTable> register(final String location) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, Constants.id(location)));
    }

    private static ResourceKey<LootTable> register(final ResourceKey<LootTable> location) {
        if (LOCATIONS.add(location)) {
            return location;
        } else {
            throw new IllegalArgumentException(location.identifier() + " is already a registered built-in loot table");
        }
    }

    public static Set<ResourceKey<LootTable>> getLocations() {
        return Collections.unmodifiableSet(LOCATIONS);
    }
}