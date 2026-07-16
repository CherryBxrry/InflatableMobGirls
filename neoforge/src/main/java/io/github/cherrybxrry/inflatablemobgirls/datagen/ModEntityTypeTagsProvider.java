package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypeTags;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.tags.EntityTypeTags;
import org.jspecify.annotations.NonNull;

import java.util.concurrent.CompletableFuture;

public class ModEntityTypeTagsProvider extends EntityTypeTagsProvider {
    public ModEntityTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, Constants.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.@NonNull Provider provider) {
        // Mod Tags
        tag(ModEntityTypeTags.INFLATABLE_MOB_GIRL)
                .add(ModEntityTypes.getRK(ModEntityTypes.CREEPER_GIRL.get()))
                .add(ModEntityTypes.getRK(ModEntityTypes.GHAST_GIRL.get()));

        // Vanilla Tags
        tag(EntityTypeTags.CAN_EQUIP_HARNESS)
                .add(ModEntityTypes.getRK(ModEntityTypes.GHAST_GIRL.get()));
        tag(EntityTypeTags.DISMOUNTS_UNDERWATER)
                .add(ModEntityTypes.getRK(ModEntityTypes.GHAST_GIRL.get()));
        tag(EntityTypeTags.FALL_DAMAGE_IMMUNE)
                .add(ModEntityTypes.getRK(ModEntityTypes.GHAST_GIRL.get()));
        tag(EntityTypeTags.FOLLOWABLE_FRIENDLY_MOBS)
                .add(ModEntityTypes.getRK(ModEntityTypes.GHAST_GIRL.get()));
    }
}
