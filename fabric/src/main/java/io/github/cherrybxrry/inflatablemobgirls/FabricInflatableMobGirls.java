package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.worldgen.FabricWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;

public class FabricInflatableMobGirls implements ModInitializer {

    @Override
    public void onInitialize() {
        InflatableMobGirls.init();
        Services.SPAWN_PLACEMENTS.applySpawnPlacements(SpawnPlacements::register);
        FabricWorldGen.load();

        Services.ATTRIBUTES.applyEntityAttributeRegistrations(FabricDefaultAttributeRegistry::register);
    }
}
