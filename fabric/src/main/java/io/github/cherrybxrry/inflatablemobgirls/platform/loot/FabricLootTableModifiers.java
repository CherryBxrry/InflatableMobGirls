package io.github.cherrybxrry.inflatablemobgirls.platform.loot;

import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import io.github.cherrybxrry.inflatablemobgirls.init.ModLootTables;
import net.fabricmc.fabric.api.loot.v3.FabricLootTableBuilder;
import net.fabricmc.fabric.api.loot.v3.LootTableSource;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.ArrayList;
import java.util.List;

public class FabricLootTableModifiers {
    private static final List<Entry> ENTRIES = List.of(
            new Entry(
                    ModLootTables.CHARGED_CREEPER_GIRL_CREEPER_GIRL,
                    ModEntityTypes.CREEPER_GIRL.get(),
                    ModItems.CREEPER_GIRL_HEAD.get()
            )
    );

    public static void modifyLootTables(ResourceKey<LootTable> key, FabricLootTableBuilder builder, LootTableSource source, HolderLookup.Provider provider) {
        if (key.identifier().equals(BuiltInLootTables.CHARGED_CREEPER.identifier())) {
            HolderGetter<EntityType<?>> entityTypes = provider.lookupOrThrow(Registries.ENTITY_TYPE);
            List<LootPoolEntryContainer.Builder<?>> alternatives = new ArrayList<>(ENTRIES.size());

            for (Entry entry : ENTRIES) {
                LootItemCondition.Builder predicate = LootItemEntityPropertyCondition.hasProperties(
                        LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entry.entityType))
                );
                alternatives.add(NestedLootTable.lootTableReference(entry.lootTable).when(predicate));
            }

            builder.pool(LootPool.lootPool()
                    .setRolls(ConstantValue.exactly(1.0F))
                    .add(AlternativesEntry.alternatives(alternatives.toArray(LootPoolEntryContainer.Builder[]::new)))
                    .build());
        }
    }

    private record Entry(ResourceKey<LootTable> lootTable, EntityType<?> entityType, Item item) { }
}
