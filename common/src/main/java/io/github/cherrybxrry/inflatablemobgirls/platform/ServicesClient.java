package io.github.cherrybxrry.inflatablemobgirls.platform;

import io.github.cherrybxrry.inflatablemobgirls.platform.services.client.IClientRegistryHelper;

public final class ServicesClient {
    public static final IClientRegistryHelper CLIENT_REGISTRY = Services.load(IClientRegistryHelper.class);

    private ServicesClient() {
    }
}
