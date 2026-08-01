package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.special.MobGirlSkullSpecialRenderer;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;

public class ModSpecialModelRenderers {
    private ModSpecialModelRenderers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Special Model Renderers");

        ServicesClient.CLIENT_REGISTRY.registerSpecialModelRenderer(Constants.id("mob_girl_skull"), MobGirlSkullSpecialRenderer.Unbaked.MAP_CODEC);
    }
}
