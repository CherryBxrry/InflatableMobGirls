package io.github.cherrybxrry.inflatablemobgirls.platform;

import io.github.cherrybxrry.inflatablemobgirls.datagen.*;
import io.github.cherrybxrry.inflatablemobgirls.datagen.lang.ModEnglishLangProvider;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class ModDataGen {
    private ModDataGen() {
        throw new UnsupportedOperationException("This class is a data gen class");
    }

    public static void onGatherClientData(GatherDataEvent.Client event) {
        event.createProvider(ModAdvancementProvider::new);
        event.createProvider(ModBlockTagsProvider::new);
        event.createProvider(ModEnglishLangProvider::new);
        event.createProvider(ModEntityTypeTagsProvider::new);
        event.createProvider(ModEquipmentAssetProvider::new);
        event.createProvider(ModItemTagsProvider::new);
        event.createProvider(ModLootTableProvider::new);
        event.createProvider(ModModelProvider::new);
        event.createProvider(ModParticleDescriptionProvider::new);
        event.createProvider(ModRecipeProvider.Runner::new);
        event.createProvider(ModWorldGenProvider::new);
    }
}
