package io.github.cherrybxrry.inflatablemobgirls.platform.util;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;

import java.util.function.Supplier;

public interface RegistryHandle<T> extends Supplier<T> {
    Identifier id();

    default ResourceKey<T> key() {
        return null;
    }
}
