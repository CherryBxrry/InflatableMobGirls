package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.CreeperGirlRenderer;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.GhastGirlRenderer;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public final class ModEntityRenderers {
    private ModEntityRenderers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Entity Renderers");

        ServicesClient.CLIENT_REGISTRY.registerEntityRenderer(ModEntityTypes.CREEPER_GIRL.get(), CreeperGirlRenderer::new);
        ServicesClient.CLIENT_REGISTRY.registerEntityRenderer(ModEntityTypes.GHAST_GIRL.get(), GhastGirlRenderer::new);
    }
}
