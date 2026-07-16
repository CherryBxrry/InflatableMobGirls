package io.github.cherrybxrry.inflatablemobgirls;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Constants {
    public static final String MOD_ID = "inflatablemobgirls";
    public static final String MOD_NAME = "Inflatable Mob Girls";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Marker ENTRY_MARKER = MarkerFactory.getMarker("Entry");
    public static final Marker REGISTRY_MARKER = MarkerFactory.getMarker("Registries");

    public static Identifier id(String name) {
        return Identifier.fromNamespaceAndPath(MOD_ID, name);
    }
}