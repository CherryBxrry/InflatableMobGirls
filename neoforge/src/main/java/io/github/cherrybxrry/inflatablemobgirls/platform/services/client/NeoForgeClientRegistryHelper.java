package io.github.cherrybxrry.inflatablemobgirls.platform.services.client;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import org.apache.logging.log4j.util.Lazy;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class NeoForgeClientRegistryHelper implements IClientRegistryHelper {
    private final List<BlockEntityRendererEntry<?, ?>> blockEntityRenderers = new ArrayList<>();
    private final List<EntityRendererEntry<?>> entityRenderers = new ArrayList<>();
    private final List<KeyMappingEntry> keyMappings = new ArrayList<>();
    private final List<ModelLayerEntry> modelLayers = new ArrayList<>();
    private final List<ParticleProviderEntry<?>> particleProviders = new ArrayList<>();
    private final List<SpriteParticleProviderEntry<?>> spriteParticleProviders = new ArrayList<>();
    private final List<SpecialModelRendererEntry> specialModelRenderers = new ArrayList<>();

    @Override
    public <T extends BlockEntity, S extends BlockEntityRenderState> void registerBlockEntityRenderer(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, S> provider) {
        this.blockEntityRenderers.add(new BlockEntityRendererEntry<>(blockEntityType, provider));
    }

    @Override
    public <T extends Entity> void registerEntityRenderer(EntityType<T> entityType, EntityRendererProvider<T> provider) {
        this.entityRenderers.add(new EntityRendererEntry<>(entityType, provider));
    }

    @Override
    public Lazy<KeyMapping> registerKeyMapping(KeyMapping keyMapping) {
        this.keyMappings.add(new KeyMappingEntry(keyMapping));
        return Lazy.value(keyMapping);
    }

    @Override
    public void registerModelLayer(ModelLayerLocation location, Supplier<LayerDefinition> supplier) {
        this.modelLayers.add(new ModelLayerEntry(location, supplier));
    }

    @Override
    public <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> particleType, ParticleProvider<T> provider) {
        this.particleProviders.add(new ParticleProviderEntry<>(particleType, provider));
    }

    @Override
    public <T extends ParticleOptions> void registerParticleProvider(ParticleType<T> particleType, SpriteParticleProvider<T> provider) {
        this.spriteParticleProviders.add(new SpriteParticleProviderEntry<>(particleType, provider));
    }

    @Override
    public void registerSpecialModelRenderer(Identifier location, MapCodec<? extends SpecialModelRenderer.Unbaked<?>> source) {
        this.specialModelRenderers.add(new SpecialModelRendererEntry(location, source));
    }

    public IClientRegistryHelper.SpriteParticleProviderRegistrar createRegistrarForEvent(RegisterParticleProvidersEvent event) {
        return new IClientRegistryHelper.SpriteParticleProviderRegistrar() {
            @Override
            public <T extends ParticleOptions> void register(ParticleType<T> type, IClientRegistryHelper.SpriteParticleProvider<T> provider) {
                event.registerSpriteSet(type, provider::create);
            }
        };
    }

    @Override
    public void applyBlockEntityRendererRegistrations(BlockEntityRendererRegistrar registrar) {
        for (BlockEntityRendererEntry<?, ?> entry : this.blockEntityRenderers) {
            entry.register(registrar);
        }
    }

    @Override
    public void applyEntityRendererRegistrations(EntityRendererRegistrar registrar) {
        for (EntityRendererEntry<?> entry : this.entityRenderers) {
            entry.register(registrar);
        }
    }

    @Override
    public void applyKeyMappingRegistrations(KeyMappingRegistrar registrar) {
        for (KeyMappingEntry entry : this.keyMappings) {
            entry.register(registrar);
        }
    }

    @Override
    public void applyModelLayerRegistrations(ModelLayerRegistrar registrar) {
        for (ModelLayerEntry entry : this.modelLayers) {
            entry.register(registrar);
        }
    }

    @Override
    public void applyParticleProviderRegistrations(ParticleProviderRegistrar registrar) {
        for (ParticleProviderEntry<?> entry : this.particleProviders) {
            entry.register(registrar);
        }
    }

    @Override
    public void applySpriteParticleProviderRegistrations(SpriteParticleProviderRegistrar registrar) {
        for (SpriteParticleProviderEntry<?> entry : this.spriteParticleProviders) {
            entry.register(registrar);
        }
    }

    @Override
    public void applySpecialModelRendererRegistrations(SpecialModelRendererRegistrar registrar) {
        for (SpecialModelRendererEntry entry : this.specialModelRenderers) {
            entry.register(registrar);
        }
    }

    private record BlockEntityRendererEntry<T extends BlockEntity, S extends BlockEntityRenderState>(BlockEntityType<T> blockEntityType, BlockEntityRendererProvider<T, S> provider) {
        private void register(BlockEntityRendererRegistrar registrar) {
            registrar.register(this.blockEntityType, this.provider);
        }
    }

    private record EntityRendererEntry<T extends Entity>(EntityType<T> entityType, EntityRendererProvider<T> provider) {
        private void register(EntityRendererRegistrar registrar) {
            registrar.register(this.entityType, this.provider);
        }
    }

    private record KeyMappingEntry(KeyMapping keyMapping) {
        private void register(KeyMappingRegistrar registrar) {
            registrar.register(this.keyMapping);
        }
    }

    private record ModelLayerEntry(ModelLayerLocation location, Supplier<LayerDefinition> supplier) {
        private void register(ModelLayerRegistrar registrar) {
            registrar.register(this.location, this.supplier);
        }
    }

    private record ParticleProviderEntry<T extends ParticleOptions>(ParticleType<T> type,
                                                                    ParticleProvider<T> provider) {
        private void register(ParticleProviderRegistrar registrar) {
            registrar.register(this.type, this.provider);
        }
    }

    private record SpriteParticleProviderEntry<T extends ParticleOptions>(ParticleType<T> type, SpriteParticleProvider<T> provider) {
        private void register(SpriteParticleProviderRegistrar registrar) {
            registrar.register(this.type, this.provider);
        }
    }

    private record SpecialModelRendererEntry(Identifier location, MapCodec<? extends SpecialModelRenderer.Unbaked<?>> source) {
        private void register(SpecialModelRendererRegistrar registrar) {
            registrar.register(this.location, this.source);
        }
    }
}
