package io.github.cherrybxrry.inflatablemobgirls.platform;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.*;

import java.util.ServiceLoader;

public class Services {
    public static final IAttributeRegistryHelper ATTRIBUTES = load(IAttributeRegistryHelper.class);
    public static final IDatapackRegistryHelper DATAPACKS = load(IDatapackRegistryHelper.class);
    public static final IPlatformHelper PLATFORM = load(IPlatformHelper.class);
    public static final IRegistryHelper REGISTRY = load(IRegistryHelper.class);
    public static final ISpawnPlacementRegistryHelper SPAWN_PLACEMENTS = load(ISpawnPlacementRegistryHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz, Services.class.getClassLoader())
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Constants.LOG.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}