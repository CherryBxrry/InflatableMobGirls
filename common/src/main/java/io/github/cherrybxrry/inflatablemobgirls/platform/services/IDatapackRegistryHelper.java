package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface IDatapackRegistryHelper {
    <T> void register(ResourceKey<Registry<T>> key, Codec<T> directCodec);

    <T> void registerSynced(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec);
}
