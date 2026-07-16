package io.github.cherrybxrry.inflatablemobgirls.platform.services.client;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public interface IClientRegistryHelper {
    <T extends BlockEntity, S extends BlockEntityRenderState> void registerBlockEntityRenderer(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, S> provider);

    <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, EntityRendererProvider<T> provider);

    void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> supplier);

    <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> particleType, ParticleProvider<T> provider);

    <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> particleType, SpriteParticleProvider<T> provider);

    void applyBlockEntityRendererRegistrations(BlockEntityRendererRegistrar registrar);

    void applyEntityRendererRegistrations(EntityRendererRegistrar registrar);

    void applyModelLayerRegistrations(ModelLayerRegistrar registrar);

    void applyParticleProviderRegistrations(ParticleProviderRegistrar registrar);

    void applySpriteParticleProviderRegistrations(SpriteParticleProviderRegistrar registrar);

    interface BlockEntityRendererRegistrar {
        <T extends BlockEntity, S extends BlockEntityRenderState> void register(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, S> provider);
    }

    interface EntityRendererRegistrar {
        <T extends Entity> void register(EntityType<T> entityType, EntityRendererProvider<T> provider);
    }

    interface ModelLayerRegistrar {
        void register(ModelLayerLocation location, Supplier<LayerDefinition> supplier);
    }

    interface ParticleProviderRegistrar {
        <T extends ParticleOptions> void register(ParticleType<T> type, ParticleProvider<T> provider);
    }

    interface SpriteParticleProviderRegistrar {
        <T extends ParticleOptions> void register(ParticleType<T> type, SpriteParticleProvider<T> provider);
    }

    @FunctionalInterface
    interface SpriteParticleProvider<T extends ParticleOptions> {
        ParticleProvider<T> create(SpriteSet spriteSet);
    }
}
