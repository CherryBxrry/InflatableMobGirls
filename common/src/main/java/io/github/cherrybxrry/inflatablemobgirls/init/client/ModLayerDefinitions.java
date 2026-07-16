package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.model.blockentity.CreepSporeCropModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.*;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import net.minecraft.client.model.geom.builders.MeshTransformer;

public final class ModLayerDefinitions {
    private ModLayerDefinitions() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Model Layers");

        // Block Entities
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.CREEPSPORE_CROP, CreepSporeCropModel::createBodyLayer);

        // Entities
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.CREEPER_GIRL, AdultCreeperGirlModel::createBodyLayer);
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.CREEPER_GIRL_BABY, BabyCreeperGirlModel::createBodyLayer);
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.GHAST_GIRL, GhastGirlModel::createBodyLayer);
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.GHAST_GIRL_HARNESS, GhastGirlHarnessModel::createBodyLayer);
        ServicesClient.CLIENT_REGISTRY.registerModelLayer(ModModelLayers.GHAST_GIRL_ROPES, GhastGirlRopesModel::createBodyLayer);
    }
}
