package io.github.cherrybxrry.inflatablemobgirls.datagen.lootsubprovider;

import io.github.cherrybxrry.inflatablemobgirls.blocks.CreepSporeBlock;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.NeoForgeRegistryHelper;
import net.minecraft.advancements.predicates.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jspecify.annotations.NonNull;

import java.util.Set;

public class ModBlockLootSubProvider extends BlockLootSubProvider {
    public ModBlockLootSubProvider(HolderLookup.Provider registries) {
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
