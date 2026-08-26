package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirlVariant;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataSerializer;

public final class ModEntityDataSerializers {
    private ModEntityDataSerializers() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Entity Data Serializers");
    }

    public static final RegistryHandle<EntityDataSerializer<Holder<CreeperGirlVariant>>> CREEPER_GIRL_VARIANT =
            Services.REGISTRY.registerEntityDataSerializer("creeper_girl_variant", CreeperGirlVariant.STREAM_CODEC);
}
