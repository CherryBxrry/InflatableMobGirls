package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirlVariant;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;

public final class ModDataComponents {
    private ModDataComponents() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Data Components");
    }

    public static final RegistryHandle<DataComponentType<Holder<CreeperGirlVariant>>> CREEPER_GIRL_VARIANT = Services.REGISTRY.registerDataComponentType(
            "creeper_girl/variant",
            b -> b.persistent(CreeperGirlVariant.CODEC).networkSynchronized(CreeperGirlVariant.STREAM_CODEC)
    );
}
