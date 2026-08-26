package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirlVariant;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

public final class ModRegistries {
    private ModRegistries() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final ResourceKey<Registry<CreeperGirlVariant>> CREEPER_GIRL_VARIANT = createRegistryKey("creeper_girl");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(String name) {
        return ResourceKey.createRegistryKey(Identifier.withDefaultNamespace(name));
    }
}
