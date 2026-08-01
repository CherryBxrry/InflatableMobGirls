package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.ArrayList;
import java.util.List;

public class FabricDatapackRegistryHelper implements IDatapackRegistryHelper {
    private static final List<DataPackEntry<?>> datapacks = new ArrayList<>();
    private static final List<SyncedDataPackEntry<?>> datapacksSynced = new ArrayList<>();

    @Override
    public <T> void registerDataPack(ResourceKey<Registry<T>> key, Codec<T> directCodec) {
        datapacks.add(new DataPackEntry<>(key, directCodec));
    }

    @Override
    public <T> void registerDataPack(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec) {
        datapacksSynced.add(new SyncedDataPackEntry<>(key, directCodec, networkCodec));
    }

    @Override
    public void applyDatapackRegistrations(DatapackRegistrar registrar) {
        for (DataPackEntry<?> entry : datapacks) {
            entry.register(registrar);
        }
    }

    @Override
    public void applySyncedDatapackRegistrations(SyncedDatapackRegistrar registrar) {
        for (SyncedDataPackEntry<?> entry : datapacksSynced) {
            entry.register(registrar);
        }
    }

    private record DataPackEntry<T>(ResourceKey<Registry<T>> key, Codec<T> directCodec) {
        private void register(DatapackRegistrar registrar) {
            registrar.register(this.key, this.directCodec);
        }
    }

    private record SyncedDataPackEntry<T>(ResourceKey<Registry<T>> key, Codec<T> directCodec, Codec<T> networkCodec) {
        private void register(SyncedDatapackRegistrar registrar) {
            registrar.register(this.key, this.directCodec, this.networkCodec);
        }
    }
}
