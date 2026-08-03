package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.blocks.CreepSporeBlock;
import io.github.cherrybxrry.inflatablemobgirls.init.*;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.NeoForgeRegistryHelper;
import net.minecraft.advancements.predicates.DamageSourcePredicate;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.advancements.predicates.TagPredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.entity.EntityTypePredicate;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.functions.EnchantedCountIncreaseFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.stream.Stream;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(
                output,
                Set.of(),
                List.of(
                        new SubProviderEntry(ModBlockLootSubProvider::new, LootContextParamSets.BLOCK),
                        new SubProviderEntry(ModEntityLootSubProvider::new, LootContextParamSets.ENTITY),
                        new SubProviderEntry(CreeperGirlExplosionLoot::new, LootContextParamSets.ENTITY)
                ),
                registries
        );
    }

    private static final class ModBlockLootSubProvider extends BlockLootSubProvider {
        ModBlockLootSubProvider(HolderLookup.Provider registries) {
            super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        protected void generate() {
            add(ModBlocks.CREEPER_GIRL_HEAD.get(), this::createMobSkullDrop);
            dropSelf(ModBlocks.CREEPSHROOM.block().get());
            LootItemCondition.Builder isCreepSporeMaxAge = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.CREEPSPORE_CROP.get())
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(CreepSporeBlock.AGE, CreepSporeBlock.MAX_AGE));
            add(ModBlocks.CREEPSPORE_CROP.get(), block -> createCropDrops(block, ModItems.CREEPSPORE.get(), ModItems.CREEPSPORE.get(), isCreepSporeMaxAge));
            add(ModBlocks.CREEPSHROOM_BLOCK.block().get(), block -> createMushroomBlockDrop(block, ModBlocks.CREEPSHROOM.item().get()));
            add(ModBlocks.HUGE_CREEPSHROOM_STEM.block().get(), block -> createMushroomBlockDrop(block, ModBlocks.CREEPSHROOM.item().get()));
            dropSelf(ModBlocks.NETHER_GEYSER.block().get());
            dropPottedContents(ModBlocks.POTTED_CREEPSHROOM.get());
        }

        private LootTable.Builder createMobSkullDrop(Block block) {
            return LootTable.lootTable()
                    .withPool(
                            this.applyExplosionCondition(
                                    block,
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(block)
                                                            .apply(CopyComponentsFunction.copyComponentsFromBlockEntity(LootContextParams.BLOCK_ENTITY).include(DataComponents.CUSTOM_NAME))
                                            )
                            )
                    );
        }

        @Override
        protected @NonNull Iterable<Block> getKnownBlocks() {
            return NeoForgeRegistryHelper.BLOCKS.getEntries()
                    .stream()
                    .map(entry -> (Block) entry.value())
                    .toList();
        }
    }

    private static final class ModEntityLootSubProvider extends EntityLootSubProvider {
        ModEntityLootSubProvider(HolderLookup.Provider registries) {
            super(FeatureFlags.DEFAULT_FLAGS, registries);
        }

        @Override
        public void generate() {
            HolderGetter<EntityType<?>> entityTypes = this.registries.lookupOrThrow(Registries.ENTITY_TYPE);

            // Overworld
            add(ModEntityTypes.CREEPER_GIRL.get(),
                    LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(Items.GUNPOWDER)
                                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                            .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                            )
                            )
                            .withPool(
                                    LootPool.lootPool()
                                            .add(TagEntry.expandTag(ItemTags.CREEPER_DROP_MUSIC_DISCS))
                                            .when(
                                                    LootItemEntityPropertyCondition.hasProperties(
                                                            LootContext.EntityTarget.ATTACKER, EntityPredicate.Builder.entity().of(entityTypes, EntityTypeTags.SKELETONS)
                                                    )
                                            )
                            ));

            // Nether
            add(ModEntityTypes.GHAST_GIRL.get(),
                    LootTable.lootTable()
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(Items.GHAST_TEAR)
                                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 1.0F)))
                                                            .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                            )
                            )
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(
                                                    LootItem.lootTableItem(Items.GUNPOWDER)
                                                            .apply(SetItemCountFunction.setCount(UniformGenerator.between(0.0F, 2.0F)))
                                                            .apply(EnchantedCountIncreaseFunction.lootingMultiplier(this.registries, UniformGenerator.between(0.0F, 1.0F)))
                                            )
                            )
                            .withPool(
                                    LootPool.lootPool()
                                            .setRolls(ConstantValue.exactly(1.0F))
                                            .add(LootItem.lootTableItem(Items.MUSIC_DISC_TEARS))
                                            .apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0F)))
                                            .when(
                                                    DamageSourceCondition.hasDamageSource(
                                                            DamageSourcePredicate.Builder.damageType()
                                                                    .tag(TagPredicate.is(DamageTypeTags.IS_PROJECTILE))
                                                                    .direct(EntityPredicate.Builder.entity().of(entityTypes, EntityTypes.FIREBALL))
                                                    )
                                            )
                                            .when(LootItemKilledByPlayerCondition.killedByPlayer())
                            )
            );
        }

        @Override
        protected @NonNull Stream<EntityType<?>> getKnownEntityTypes() {
            return NeoForgeRegistryHelper.ENTITIES.getEntries()
                    .stream()
                    .map(DeferredHolder::value);
        }
    }

    private record CreeperGirlExplosionLoot(HolderLookup.Provider registries) implements LootTableSubProvider {
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
}
