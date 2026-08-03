package io.github.cherrybxrry.inflatablemobgirls.platform.util;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import org.jspecify.annotations.NonNull;

import java.util.function.Supplier;

public interface RegistryHandle<T> extends Supplier<T> {
    Identifier id();

    ResourceKey<?> key();

    @NonNull
    @SuppressWarnings("unchecked")
    default <K> ResourceKey<K> typedKey() {
        if (key() == null) throw new IllegalStateException("No ResourceKey available for " + id());
        return (ResourceKey<K>) key();
    }
}
