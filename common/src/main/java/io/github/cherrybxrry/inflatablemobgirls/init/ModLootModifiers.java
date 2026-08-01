package io.github.cherrybxrry.inflatablemobgirls.init;

import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.entries.NestedLootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemEntityPropertyCondition;

import java.util.ArrayList;
import java.util.List;

public final class ModLootModifiers {
    private ModLootModifiers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final List<Entry> CHARGED_CREEPER_ENTRIES = List.of(new Entry(ModLootTables.CHARGED_CREEPER_CREEPER_GIRL, ModEntityTypes.CREEPER_GIRL.get(), ModItems.CREEPER_GIRL_HEAD.get()));
    public static final List<LootPoolEntryContainer.Builder<?>> CHARGED_CREEPER_ALT = new ArrayList<>(CHARGED_CREEPER_ENTRIES.size());

    static {
        HolderGetter<EntityType<?>> entityTypes = BuiltInRegistries.ENTITY_TYPE;

        for(ModLootModifiers.Entry entry : ModLootModifiers.CHARGED_CREEPER_ENTRIES) {
            LootItemCondition.Builder predicate = LootItemEntityPropertyCondition.hasProperties(LootContext.EntityTarget.THIS, EntityPredicate.Builder.entity().entityType(EntityTypePredicate.of(entityTypes, entry.entityType())));
            ModLootModifiers.CHARGED_CREEPER_ALT.add(NestedLootTable.lootTableReference(entry.lootTable()).when(predicate));
        }
    }

    public record Entry(ResourceKey<LootTable> lootTable, EntityType<?> entityType, Item item) {}
}