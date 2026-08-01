package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.events.client.ModClientTickEvent;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.client.NeoForgeClientRegistryHelper;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public final class NeoForgeInflatableMobGirlsClient {
    private NeoForgeInflatableMobGirlsClient() {
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        InflatableMobGirlsClient.init();
        ServicesClient.CLIENT_REGISTRY.applyBlockEntityRendererRegistrations(event::registerBlockEntityRenderer);
        ServicesClient.CLIENT_REGISTRY.applyEntityRendererRegistrations(event::registerEntityRenderer);
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        InflatableMobGirlsClient.init();
        ServicesClient.CLIENT_REGISTRY.applyKeyMappingRegistrations(event::register);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        InflatableMobGirlsClient.init();
        ServicesClient.CLIENT_REGISTRY.applyModelLayerRegistrations((event::registerLayerDefinition));
    }

    @SubscribeEvent
    public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
        InflatableMobGirlsClient.init();
        ServicesClient.CLIENT_REGISTRY.applyParticleProviderRegistrations(event::registerSpecial);
        ServicesClient.CLIENT_REGISTRY.applySpriteParticleProviderRegistrations((((NeoForgeClientRegistryHelper) ServicesClient.CLIENT_REGISTRY).createRegistrarForEvent(event)));
    }

    @SubscribeEvent
    public static void registerSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
        InflatableMobGirlsClient.init();
        ServicesClient.CLIENT_REGISTRY.applySpecialModelRendererRegistrations(event::register);
    }

    @SubscribeEvent
    private static void onClientTick(ClientTickEvent.Post event) {
        Minecraft client = Minecraft.getInstance();

        ModClientTickEvent.playerKeyPressed(client);
    }
}
