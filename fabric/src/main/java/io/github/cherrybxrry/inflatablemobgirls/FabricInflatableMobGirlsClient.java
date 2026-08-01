package io.github.cherrybxrry.inflatablemobgirls;

import io.github.cherrybxrry.inflatablemobgirls.events.client.ModClientTickEvent;
import io.github.cherrybxrry.inflatablemobgirls.platform.ServicesClient;
import io.github.cherrybxrry.inflatablemobgirls.platform.services.client.FabricClientRegistryHelper;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.client.renderer.special.SpecialModelRenderers;

public class FabricInflatableMobGirlsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        InflatableMobGirlsClient.init();

        ServicesClient.CLIENT_REGISTRY.applyBlockEntityRendererRegistrations(BlockEntityRenderers::register);
        ServicesClient.CLIENT_REGISTRY.applyEntityRendererRegistrations(EntityRenderers::register);
        ServicesClient.CLIENT_REGISTRY.applyKeyMappingRegistrations(KeyMappingHelper::registerKeyMapping);
        ServicesClient.CLIENT_REGISTRY.applyModelLayerRegistrations(((location, supplier) -> ModelLayerRegistry.registerModelLayer(location, supplier::get)));
        ServicesClient.CLIENT_REGISTRY.applyParticleProviderRegistrations(ParticleProviderRegistry.getInstance()::register);
        ServicesClient.CLIENT_REGISTRY.applySpriteParticleProviderRegistrations(((FabricClientRegistryHelper) ServicesClient.CLIENT_REGISTRY).createRegistrarForEvent(ParticleProviderRegistry.getInstance()));
        ServicesClient.CLIENT_REGISTRY.applySpecialModelRendererRegistrations(SpecialModelRenderers.ID_MAPPER::put);

        ClientTickEvents.END_CLIENT_TICK.register((client) -> {
            ModClientTickEvent.playerKeyPressed(client);
        });
    }
}
