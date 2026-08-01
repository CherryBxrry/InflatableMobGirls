package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public interface IDatapackRegistryHelper {
    <T> void registerDataPack(ResourceKey<Registry<T>> key, Codec<T> directCodec);

    <T> void registerDataPack(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec);

    void applyDatapackRegistrations(DatapackRegistrar registrar);

    void applySyncedDatapackRegistrations(SyncedDatapackRegistrar registrar);

    interface DatapackRegistrar {
        <T> void register(ResourceKey<Registry<T>> key, Codec<T> directCodec);
    }

    interface SyncedDatapackRegistrar {
        <T> void register(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec);
    }
}
