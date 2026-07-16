package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.event.registry.DynamicRegistries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

public class FabricDatapackHelper implements IDatapackRegistryHelper {
    @Override
    public <T> void register(ResourceKey<Registry<T>> key, Codec<T> directCodec) {
        DynamicRegistries.register(key, directCodec);
    }

    @Override
    public <T> void registerSynced(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec) {
        DynamicRegistries.registerSynced(key, directCodec, networkCodec);
    }
}
