package io.github.cherrybxrry.inflatablemobgirls.init.client;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import net.minecraft.client.KeyMapping;
import org.apache.logging.log4j.util.Lazy;
import org.lwjgl.glfw.GLFW;

public final class ModKeyMappings {
    private ModKeyMappings() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Key Mappings");
    }

    public static final KeyMapping.Category INFLATABLE_MOB_GIRLS = KeyMapping.Category.register(Constants.id("inflatable_mob_girls"));

    public static final Lazy<KeyMapping> MELEE_ATTACK = register(
            "melee_attack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_H,
            INFLATABLE_MOB_GIRLS
    );

    public static final Lazy<KeyMapping> RANGED_ATTACK = register(
        "ranged_attack",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_G,
            INFLATABLE_MOB_GIRLS
    );

    public static Lazy<KeyMapping> register(String name, InputConstants.Type inputType, int key, KeyMapping.Category category) {
        return ServicesClient.CLIENT_REGISTRY.registerKeyMapping(new KeyMapping("key." + Constants.MOD_ID + "." + name, inputType, key, category));
    }
}
