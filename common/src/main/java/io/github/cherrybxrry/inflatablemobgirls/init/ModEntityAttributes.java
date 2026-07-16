package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirl;
import io.github.cherrybxrry.inflatablemobgirls.entities.ghastgirl.GhastGirl;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;

public final class ModEntityAttributes {
    private ModEntityAttributes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Entities Attributes");

        Services.ATTRIBUTES.registerEntityAttributes(ModEntityTypes.CREEPER_GIRL, CreeperGirl::createAttributes);
        Services.ATTRIBUTES.registerEntityAttributes(ModEntityTypes.GHAST_GIRL, GhastGirl::createAttributes);
    }
}
