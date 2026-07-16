package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        // Vanilla Tags
        tag(BlockTags.CROPS)
                .add(ModBlocks.CREEPSPORE_CROP.key());
        tag(BlockTags.ENDERMAN_HOLDABLE)
                .add(ModBlocks.CREEPSHROOM.block().key());
        tag(BlockTags.FLOWER_POTS)
                .add(ModBlocks.POTTED_CREEPSHROOM.key());
        tag(BlockTags.MAINTAINS_FARMLAND)
                .add(ModBlocks.CREEPSPORE_CROP.key());
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(ModBlocks.CREEPSHROOM_BLOCK.block().key())
                .add(ModBlocks.HUGE_CREEPSHROOM_STEM.block().key());
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.NETHER_GEYSER.block().key());
        tag(BlockTags.REPLACEABLE_BY_MUSHROOMS)
                .add(ModBlocks.CREEPSHROOM.block().key())
                .add(ModBlocks.CREEPSHROOM_BLOCK.block().key());
    }
}
