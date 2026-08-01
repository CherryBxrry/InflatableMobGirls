package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItemTags;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.references.ItemIds;
import net.minecraft.tags.ItemTags;
import net.neoforged.neoforge.common.data.ItemTagsProvider;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        // Mod Tags
        this.tag(ModItemTags.CREEPER_GIRL_FOOD)
                .add(ItemIds.BONE_MEAL);
        this.tag(ModItemTags.GHAST_GIRL_FOOD)
                .add(ModItems.SOUL_CHOCOLATE.key());
        this.tag(ModItemTags.INFLATES_CREEPER_GIRL);
        this.tag(ModItemTags.INFLATES_GHAST_GIRL)
                .add(ModItems.BELLOWS.key());
        this.tag(ModItemTags.TAMES_CREEPER_GIRL)
                .add(ModBlocks.CREEPSHROOM.itemKey());
        this.tag(ModItemTags.TAMES_GHAST_GIRL)
                .add(ModItems.SOUL_CHOCOLATE.key());

        // Vanilla Tags
        this.tag(ItemTags.NOTE_BLOCK_TOP_INSTRUMENTS)
                .add(ModItems.CREEPER_GIRL_HEAD.key());
        this.tag(ItemTags.PARROT_POISONOUS_FOOD)
                .add(ModItems.SOUL_CHOCOLATE.key());
        this.tag(ItemTags.SULFUR_CUBE_ARCHETYPE_SLOW_SLIDING)
                .add(ModBlocks.CREEPSHROOM_BLOCK.itemKey())
                .add(ModBlocks.HUGE_CREEPSHROOM_STEM.itemKey());
        this.tag(ItemTags.SKULLS)
                .add(ModItems.CREEPER_GIRL_HEAD.key());
        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(ModItems.CREEPSPORE.key());
    }
}
