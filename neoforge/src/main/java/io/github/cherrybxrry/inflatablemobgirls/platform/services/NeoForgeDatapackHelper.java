package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

import java.util.ArrayList;
import java.util.List;

public class NeoForgeDatapackHelper implements IDatapackRegistryHelper {
    private static final List<Registration<?>> REGISTRATIONS = new ArrayList<>();

    @Override
    public <T> void register(ResourceKey<Registry<T>> key, Codec<T> directCodec) {
        REGISTRATIONS.add(new Registration<>(key, directCodec, null));
    }

    @Override
    public <T> void registerSynced(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec) {
        REGISTRATIONS.add(new Registration<>(key, directCodec, networkCodec));
    }

    public static void registerAll(DataPackRegistryEvent.NewRegistry event) {
        for (Registration<?> registration : REGISTRATIONS) {
            registration.apply(event);
        }
    }

    private record Registration<T>(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec) {
        void apply(DataPackRegistryEvent.NewRegistry event) {
            if (networkCodec != null) event.dataPackRegistry(key, directCodec, networkCodec);
            else event.dataPackRegistry(key, directCodec);
        }
    }
}
