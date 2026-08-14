package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import io.github.cherrybxrry.inflatablemobgirls.platform.loot.FabricLootTableModifiers;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.FabricServerNetworkingHelper;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.client.FabricClientNetworkingHelper;
import io.github.cherrybxrry.inflatablemobgirls.platform.worldgen.FabricWorldGen;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.fabricmc.fabric.api.loot.v3.LootTableEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.SpawnPlacements;

public class FabricInflatableMobGirls implements ModInitializer {

    @Override
    public void onInitialize() {
        InflatableMobGirls.init();
        Services.ATTRIBUTES.applyEntityAttributeRegistrations(FabricDefaultAttributeRegistry::register);
        Services.DATAPACKS.applyDatapackRegistrations(DynamicRegistries::register);
        Services.DATAPACKS.applySyncedDatapackRegistrations(DynamicRegistries::registerSynced);
        Services.SPAWN_PLACEMENTS.applySpawnPlacements(SpawnPlacements::register);

        ServicesClient.CLIENT_NETWORKING.applyServerboundPacketRegistrations((((FabricClientNetworkingHelper) ServicesClient.CLIENT_NETWORKING).createServerboundRegistrar()));
        Services.SERVER_NETWORKING.applyClientboundPacketRegistrations((((FabricServerNetworkingHelper) Services.SERVER_NETWORKING).createClientboundRegistrar()));

        FabricWorldGen.load();
        LootTableEvents.MODIFY.register(FabricLootTableModifiers::modifyLootTables);
    }
}
