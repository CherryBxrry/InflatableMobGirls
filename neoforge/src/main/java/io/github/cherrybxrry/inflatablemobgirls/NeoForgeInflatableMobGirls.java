package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.platform.ModDataGen;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.IAttributeRegistryHelper;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.ISpawnPlacementRegistryHelper;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.NeoForgeDatapackHelper;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.NeoForgeRegistryHelper;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.level.levelgen.Heightmap;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

@Mod(Constants.MOD_ID)
public class NeoForgeInflatableMobGirls {

    public NeoForgeInflatableMobGirls(IEventBus eventBus) {
        InflatableMobGirls.init();

        eventBus.addListener(NeoForgeInflatableMobGirls::onEntityAttributeCreation);
        eventBus.addListener(NeoForgeInflatableMobGirls::onNewRegistry);
        eventBus.addListener(NeoForgeInflatableMobGirls::onRegisterSpawnPlacements);
        eventBus.addListener(ModDataGen::onGatherClientData);
        NeoForgeRegistryHelper.register(eventBus);
    }

    private static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        Services.ATTRIBUTES.applyEntityAttributeRegistrations(new IAttributeRegistryHelper.EntityAttributeRegistrar() {
            @Override
            public <T extends LivingEntity> void register(EntityType<T> entityType, AttributeSupplier.Builder builder) {
                event.put(entityType, builder.build());
            }
        });
    }

    private static void onNewRegistry(DataPackRegistryEvent.NewRegistry event) {
        NeoForgeDatapackHelper.registerAll(event);
    }

    private static void onRegisterSpawnPlacements(RegisterSpawnPlacementsEvent event) {
        Services.SPAWN_PLACEMENTS.applySpawnPlacements(new ISpawnPlacementRegistryHelper.SpawnPlacementsRegistrar() {
            @Override
            public <T extends Mob> void register(EntityType<T> entityType, SpawnPlacementType spawnPlacementType, Heightmap.Types heightmap, SpawnPlacements.SpawnPredicate<T> predicate) {
                event.register(entityType, spawnPlacementType, heightmap, predicate, RegisterSpawnPlacementsEvent.Operation.REPLACE);
            }
        });
    }
}