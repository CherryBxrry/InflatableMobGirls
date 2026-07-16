package io.github.cherrybxrry.inflatablemobgirls.init.client;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.client.model.geom.ModelLayerLocation;

public final class ModModelLayers {
    private ModModelLayers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final ModelLayerLocation CREEPER_GIRL = createLayer("creeper_girl");
    public static final ModelLayerLocation CREEPER_GIRL_BABY = createLayer("creeper_girl_baby");
    public static final ModelLayerLocation CREEPSPORE_CROP = createLayer("creepspore_crop");
    public static final ModelLayerLocation GHAST_GIRL = createLayer("ghast_girl");
    public static final ModelLayerLocation GHAST_GIRL_HARNESS = createLayer("ghast_girl_harness");
    public static final ModelLayerLocation GHAST_GIRL_ROPES = createLayer("ghast_girl_ropes");

    public static ModelLayerLocation createLayer(String name) {
        return new ModelLayerLocation(Constants.id(name), "main");
    }
}
