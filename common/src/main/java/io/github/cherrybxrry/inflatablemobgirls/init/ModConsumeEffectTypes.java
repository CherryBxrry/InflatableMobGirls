package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.consume_effect.InflateConsumeEffect;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.world.item.consume_effects.ConsumeEffect;

public final class ModConsumeEffectTypes {
    private ModConsumeEffectTypes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Consume Effects");
    }

    public static final RegistryHandle<ConsumeEffect.Type<InflateConsumeEffect>> INFLATE = Services.REGISTRY.registerConsumeEffect(
            "inflate", InflateConsumeEffect.CODEC, InflateConsumeEffect.STREAM_CODEC
    );
}
