package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.CreepSporeCropRenderer;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.MobGirlSkullBlockRenderer;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public final class ModBlockEntityRenderers {
    private ModBlockEntityRenderers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Block Entity Renderers");

        ServicesClient.CLIENT_REGISTRY.registerBlockEntityRenderer(ModBlockEntityTypes.MOB_GIRL_SKULL.get(), MobGirlSkullBlockRenderer::new);
        ServicesClient.CLIENT_REGISTRY.registerBlockEntityRenderer(ModBlockEntityTypes.CREEPSPORE_CROP.get(), CreepSporeCropRenderer::new);
    }
}
