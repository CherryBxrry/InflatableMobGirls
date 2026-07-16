package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityDataRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class FabricRegistryHelper implements IRegistryHelper {
    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        ResourceKey<Block> key = IRegistryHelper.blockKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(
                BuiltInRegistries.BLOCK,
                id,
                block.apply(BlockBehaviour.Properties.of().setId(key))
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public final <T extends BlockEntity> RegistryHandle<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Set<Supplier<? extends Block>> validBlocks) {
        Identifier id = Constants.id(name);

        if (validBlocks.isEmpty()) {
            Constants.LOG.warn("Block entity type {} requires at least one valid block to be defined!", id);
        }

        Block[] blocks = validBlocks.stream().map(Supplier::get).toArray(Block[]::new);
        BlockEntityType<T> registered = Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.<T>create(factory::create, blocks).build());

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public BlockEntityType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name, properties -> item.apply(block.get(), properties));
    }

    @Override
    public RegistryHandle<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Consumer<CreativeTabOutput> entries) {
        Identifier id = Constants.id(name);
        CreativeModeTab registered = Registry.register(
                BuiltInRegistries.CREATIVE_MODE_TAB,
                id,
                FabricCreativeModeTab.builder()
                        .title(Component.translatable("itemGroup." + Constants.MOD_ID + "." + name))
                        .icon(icon)
                        .displayItems((_, output) -> entries.accept(output::accept))
                        .build()
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public CreativeModeTab get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends ConsumeEffect> RegistryHandle<ConsumeEffect.Type<T>> registerConsumeEffect(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        Identifier id = Constants.id(name);
        ConsumeEffect.Type<T> registered = Registry.register(
                BuiltInRegistries.CONSUME_EFFECT_TYPE,
                id,
                new ConsumeEffect.Type<>(codec, streamCodec)
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ConsumeEffect.Type<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T> RegistryHandle<DataComponentType<T>> registerDataComponentType(String name, Function<DataComponentType.Builder<T>, DataComponentType.Builder<T>> builder) {
        Identifier id = Constants.id(name);
        DataComponentType<T> registered = Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                id,
                builder.apply(DataComponentType.builder()).build()
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public DataComponentType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        Identifier id = key.identifier();
        T registered = Registry.register(
                BuiltInRegistries.ITEM,
                id,
                item.apply(new Item.Properties().setId(key))
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public T get() {
                return registered;
            }
        };
    }

    @Override
    public <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, EntityDataSerializer<T> serializer) {
        Identifier id = Constants.id(name);
        FabricEntityDataRegistry.register(id, serializer);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public EntityDataSerializer<T> get() {
                return serializer;
            }
        };
    }

    @Override
    public <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        Identifier id = Constants.id(name);
        EntityDataSerializer<T> serializer = EntityDataSerializer.forValueType(streamCodec);
        FabricEntityDataRegistry.register(id, serializer);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public EntityDataSerializer<T> get() {
                return serializer;
            }
        };
    }

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.entityTypeKey(name);
        Identifier id = key.identifier();
        EntityType<T> registered = Registry.register(
                BuiltInRegistries.ENTITY_TYPE,
                id,
                builder.build(key)
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public EntityType<T> get() {
                return registered;
            }
        };
    }

    @Override
    public <C extends FeatureConfiguration, F extends Feature<C>> RegistryHandle<F> registerFeature(String name, F feature) {
        ResourceKey<Feature<?>> key = IRegistryHelper.featureKey(name);
        Identifier id = key.identifier();

        F registered = Registry.register(
                BuiltInRegistries.FEATURE,
                id,
                feature
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public F get() {
                return registered;
            }
        };
    }

    @Override
    public RegistryHandle<SimpleParticleType> registerParticle(String name, boolean overrideLimiter) {
        ResourceKey<ParticleType<?>> key = IRegistryHelper.particleTypeKey(name);
        Identifier id = key.identifier();
        SimpleParticleType registered = Registry.register(
                BuiltInRegistries.PARTICLE_TYPE,
                id,
                FabricParticleTypes.simple(overrideLimiter)
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public SimpleParticleType get() {
                return registered;
            }
        };
    }

    @Override
    public RegistryHandle<SoundEvent> registerSoundEvent(String name) {
        ResourceKey<SoundEvent> key = IRegistryHelper.soundKey(name);
        Identifier id = key.identifier();

        SoundEvent registered = Registry.register(
                BuiltInRegistries.SOUND_EVENT,
                id,
                SoundEvent.createVariableRangeEvent(id)
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public SoundEvent get() {
                return registered;
            }
        };
    }
}
