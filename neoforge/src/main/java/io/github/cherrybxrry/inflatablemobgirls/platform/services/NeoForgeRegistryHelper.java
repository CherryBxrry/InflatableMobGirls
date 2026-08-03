package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.*;

import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(Constants.MOD_ID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<ConsumeEffect.Type<?>> CONSUME_EFFECTS = DeferredRegister.create(Registries.CONSUME_EFFECT_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Constants.MOD_ID);
    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Constants.MOD_ID);
    public static final DeferredRegister.Entities ENTITIES = DeferredRegister.createEntities(Constants.MOD_ID);
    public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, Constants.MOD_ID);
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(BuiltInRegistries.FEATURE, Constants.MOD_ID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Constants.MOD_ID);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, Constants.MOD_ID);
    public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, Constants.MOD_ID);

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        BLOCK_ENTITY_TYPES.register(eventBus);
        DATA_COMPONENTS.register(eventBus);
        ITEMS.register(eventBus);
        ENTITY_DATA_SERIALIZERS.register(eventBus);
        ENTITIES.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
        CONSUME_EFFECTS.register(eventBus);
        PARTICLE_TYPES.register(eventBus);
        FEATURES.register(eventBus);
        SOUNDS.register(eventBus);
    }

    @Override
    public <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block) {
        ResourceKey<Block> key = IRegistryHelper.blockKey(name);
        Identifier id = Constants.id(name);
        DeferredBlock<T> deferredBlock = BLOCKS.registerBlock(name, block);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<Block> key() {
                return key;
            }

            @Override
            public T get() {
                return deferredBlock.get();
            }
        };
    }

    @Override
    public final <T extends BlockEntity> RegistryHandle<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Set<Supplier<? extends Block>> validBlocks) {
        ResourceKey<BlockEntityType<?>> key = IRegistryHelper.blockEntityKey(name);
        Identifier id = Constants.id(name);

        if (validBlocks.isEmpty()) {
            Constants.LOG.warn("Block entity type {} requires at least one valid block to be defined!", id);
        }

        DeferredHolder<BlockEntityType<?>, BlockEntityType<T>> deferredBlockEntityType =
                BLOCK_ENTITY_TYPES.register(name, () -> {
                    Block[] blocks = validBlocks.stream()
                            .map(Supplier::get)
                            .toArray(Block[]::new);
                    return new BlockEntityType<>(factory, java.util.Set.of(blocks));
                });

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<BlockEntityType<?>> key() {
                return key;
            }

            @Override
            public BlockEntityType<T> get() {
                return deferredBlockEntityType.get();
            }
        };
    }

    @Override
    public <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item) {
        return registerItem(name, properties -> item.apply(block.get(), properties));
    }

    @Override
    public RegistryHandle<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Consumer<CreativeTabOutput> entries) {
        ResourceKey<CreativeModeTab> key = IRegistryHelper.creativeTabKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<CreativeModeTab, CreativeModeTab> deferredTab = CREATIVE_MODE_TABS.register(
                name,
                () -> CreativeModeTab.builder()
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
            public ResourceKey<CreativeModeTab> key() {
                return key;
            }

            @Override
            public CreativeModeTab get() {
                return deferredTab.get();
            }
        };
    }

    @Override
    public <T extends ConsumeEffect> RegistryHandle<ConsumeEffect.Type<T>> registerConsumeEffect(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec) {
        ResourceKey<ConsumeEffect.Type<?>> key = IRegistryHelper.consumeEffectKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<ConsumeEffect.Type<?>, ConsumeEffect.Type<T>> deferredConsumeEffectType = CONSUME_EFFECTS.register(
                name,
                () -> new ConsumeEffect.Type<>(codec, streamCodec)
        );

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<ConsumeEffect.Type<?>> key() {
                return key;
            }

            @Override
            public ConsumeEffect.Type<T> get() {
                return deferredConsumeEffectType.get();
            }
        };
    }

    @Override
    public <T> RegistryHandle<DataComponentType<T>> registerDataComponentType(String name, Function<DataComponentType.Builder<T>, DataComponentType.Builder<T>> builder) {
        ResourceKey<DataComponentType<?>> key = IRegistryHelper.dataComponentKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<DataComponentType<?>, DataComponentType<T>> deferredDataComponent =
                DATA_COMPONENTS.register(name, () -> builder.apply(DataComponentType.builder()).build());

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<DataComponentType<?>> key() {
                return key;
            }

            @Override
            public DataComponentType<T> get() {
                return deferredDataComponent.get();
            }
        };
    }

    @Override
    public <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item) {
        Identifier id = Constants.id(name);
        ResourceKey<Item> key = IRegistryHelper.itemKey(name);
        DeferredItem<T> deferredItem = ITEMS.registerItem(name, item);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<Item> key() {
                return key;
            }

            @Override
            public T get() {
                return deferredItem.get();
            }
        };
    }

    @Override
    public <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, EntityDataSerializer<T> serializer) {
        Identifier id = Constants.id(name);
        DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<T>> deferredEntityDataSerializer =
                ENTITY_DATA_SERIALIZERS.register(name, () -> serializer);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<?> key() {
                return null;
            }

            @Override
            public EntityDataSerializer<T> get() {
                return deferredEntityDataSerializer.get();
            }
        };
    }

    @Override
    public <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec) {
        Identifier id = Constants.id(name);
        EntityDataSerializer<T> serializer = EntityDataSerializer.forValueType(streamCodec);
        DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<T>> deferredEntityDataSerializer =
                ENTITY_DATA_SERIALIZERS.register(name, () -> serializer);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<?> key() {
                return null;
            }

            @Override
            public EntityDataSerializer<T> get() {
                return deferredEntityDataSerializer.get();
            }
        };
    }

    @Override
    public <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = IRegistryHelper.entityTypeKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<EntityType<?>, EntityType<T>> deferredEntity = ENTITIES.register(name, () -> builder.build(key));

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<EntityType<?>> key() {
                return key;
            }

            @Override
            public EntityType<T> get() {
                return deferredEntity.get();
            }
        };
    }

    @Override
    public <C extends FeatureConfiguration, F extends Feature<C>> RegistryHandle<F> registerFeature(String name, F feature) {
        ResourceKey<Feature<?>> key = IRegistryHelper.featureKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<Feature<?>, F> deferredFeature = FEATURES.register(name, () -> feature);

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<Feature<?>> key() {
                return key;
            }

            @Override
            public F get() {
                return deferredFeature.get();
            }
        };
    }

    @Override
    public RegistryHandle<SimpleParticleType> registerParticle(String name, boolean overrideLimiter) {
        ResourceKey<ParticleType<?>> key = IRegistryHelper.particleTypeKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<ParticleType<?>, SimpleParticleType> deferredParticle = PARTICLE_TYPES.register(name, () -> new SimpleParticleType(overrideLimiter));

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<ParticleType<?>> key() {
                return key;
            }

            @Override
            public SimpleParticleType get() {
                return deferredParticle.get();
            }
        };
    }

    @Override
    public RegistryHandle<SoundEvent> registerSoundEvent(String name) {
        ResourceKey<SoundEvent> key = IRegistryHelper.soundEventKey(name);
        Identifier id = Constants.id(name);
        DeferredHolder<SoundEvent, SoundEvent> deferredSoundEvent = SOUNDS.register(name, () -> SoundEvent.createVariableRangeEvent(id));

        return new RegistryHandle<>() {
            @Override
            public Identifier id() {
                return id;
            }

            @Override
            public ResourceKey<SoundEvent> key() {
                return key;
            }

            @Override
            public SoundEvent get() {
                return deferredSoundEvent.get();
            }
        };
    }
}