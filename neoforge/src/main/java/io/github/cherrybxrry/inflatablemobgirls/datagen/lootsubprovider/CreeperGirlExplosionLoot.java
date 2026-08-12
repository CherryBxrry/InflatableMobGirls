package io.github.cherrybxrry.inflatablemobgirls.datagen.lootsubprovider;

import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import io.github.cherrybxrry.inflatablemobgirls.init.ModLootTables;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.AlternativesEntry;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public record CreeperGirlExplosionLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
    private static final List<Entry> ENTRIES = List.of(
            new Entry(
                    ModLootTables.CHARGED_CREEPER_GIRL_CREEPER_GIRL,
                    ModEntityTypes.CREEPER_GIRL.get(),
                    ModItems.CREEPER_GIRL_HEAD.get()
            )
    );

    @Override
    public void generate(@NonNull BiConsumer<ResourceKey<LootTable>, LootTable.Builder> output) {
        HolderGetter<EntityType<?>> entityTypes = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);
        List<LootPoolEntryContainer.Builder<?>> alternatives = new ArrayList<>(ENTRIES.size());

        output.accept(ModLootTables.EXPLODE_CREEPER_GIRL, createChanceDrop(ModItems.CREEPSPORE.get(), 0.25F));
        for (Entry entry : ENTRIES) {
            output.accept(
                    entry.lootTable,
                    LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(entry.item)))
            );
            LootItemCondition.Builder predicate = LootItemEntityPropertyCondition.hasProperties(
                    LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entry.entityType))
            );
            alternatives.add(NestedLootTable.lootTableReference(entry.lootTable).when(predicate));
        }

        output.accept(
                ModLootTables.CHARGED_CREEPER_GIRL,
                LootTable.lootTable()
                        .withPool(
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(AlternativesEntry.alternatives(alternatives.toArray(LootPoolEntryContainer.Builder[]::new)))
                        )
        );
    }

    private static LootTable.Builder createChanceDrop(ItemLike item, float chance) {
        return LootTable.lootTable()
                .withPool(
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(
                                        LootItem.lootTableItem(item)
                                                .when(LootItemRandomChanceCondition.randomChance(chance))
                                )
                );
    }

    private static LootTable.Builder createChanceDrop(Block block, float chance) {
        return createChanceDrop(block.asItem(), chance);
    }

    private record Entry(ResourceKey<LootTable> lootTable, EntityType<?> entityType, Item item) { }
}
