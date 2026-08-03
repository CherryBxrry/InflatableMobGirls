package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.BlockWithItemRegistryHandle;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.ItemLike;
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

public interface IRegistryHelper {
    <T extends Block> RegistryHandle<T> registerBlock(String name, Function<BlockBehaviour.Properties, T> block);

    <T extends BlockEntity> RegistryHandle<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Set<Supplier<? extends Block>> validBlocks);

    default <T extends BlockEntity> RegistryHandle<BlockEntityType<T>> registerBlockEntity(String name, BlockEntityType.BlockEntitySupplier<? extends T> factory, Supplier<? extends Block> validBlock) {
        return registerBlockEntity(name, factory, Set.of(validBlock));
    }

    <T extends BlockItem> RegistryHandle<T> registerBlockItem(String name, RegistryHandle<? extends Block> block, BiFunction<Block, Item.Properties, T> item);

    default <T extends Block> BlockWithItemRegistryHandle<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block) {
        return registerBlockWithItem(name, block, BlockItem::new);
    }

    default <T extends Block> BlockWithItemRegistryHandle<T> registerBlockWithItem(String name, Function<BlockBehaviour.Properties, T> block, BiFunction<Block, Item.Properties, BlockItem> item) {
        RegistryHandle<T> blockHandle = registerBlock(name, block);
        RegistryHandle<BlockItem> itemHandle = registerBlockItem(name, blockHandle, item);

        return new BlockWithItemRegistryHandle<>(blockHandle, itemHandle);
    }

    <T extends ConsumeEffect> RegistryHandle<ConsumeEffect.Type<T>> registerConsumeEffect(String name, MapCodec<T> codec, StreamCodec<RegistryFriendlyByteBuf, T> streamCodec);

    RegistryHandle<CreativeModeTab> registerCreativeTab(String name, Supplier<ItemStack> icon, Consumer<CreativeTabOutput> entries);

    <T> RegistryHandle<DataComponentType<T>> registerDataComponentType(String name, Function<DataComponentType.Builder<T>, DataComponentType.Builder<T>> builder);

    <T extends Item> RegistryHandle<T> registerItem(String name, Function<Item.Properties, T> item);

    <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, EntityDataSerializer<T> serializer);

    <T> RegistryHandle<EntityDataSerializer<T>> registerEntityDataSerializer(String name, StreamCodec<? super RegistryFriendlyByteBuf, T> streamCodec);

    <T extends Entity> RegistryHandle<EntityType<T>> registerEntityType(String name, EntityType.Builder<T> builder);

    <C extends FeatureConfiguration, F extends Feature<C>> RegistryHandle<F> registerFeature(String name, F feature);

    RegistryHandle<SimpleParticleType> registerParticle(String name, boolean overrideLimiter);

    RegistryHandle<SoundEvent> registerSoundEvent(String name);

    static ResourceKey<Block> blockKey(String name) {
        return ResourceKey.create(Registries.BLOCK, Constants.id(name));
    }

    static ResourceKey<BlockEntityType<?>> blockEntityKey(String name) {
        return ResourceKey.create(Registries.BLOCK_ENTITY_TYPE, Constants.id(name));
    }

    static ResourceKey<ConsumeEffect.Type<?>> consumeEffectKey(String name) {
        return ResourceKey.create(Registries.CONSUME_EFFECT_TYPE, Constants.id(name));
    }

    static ResourceKey<CreativeModeTab> creativeTabKey(String name) {
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Constants.id(name));
    }

    static ResourceKey<DataComponentType<?>> dataComponentKey(String name) {
        return ResourceKey.create(Registries.DATA_COMPONENT_TYPE, Constants.id(name));
    }

    static ResourceKey<Item> itemKey(String name) {
        return ResourceKey.create(Registries.ITEM, Constants.id(name));
    }

    static ResourceKey<EntityType<?>> entityTypeKey(String name) {
        return ResourceKey.create(Registries.ENTITY_TYPE, Constants.id(name));
    }

    static ResourceKey<Feature<?>> featureKey(String name) {
        return ResourceKey.create(Registries.FEATURE, Constants.id(name));
    }

    static ResourceKey<ParticleType<?>> particleTypeKey(String name) {
        return ResourceKey.create(Registries.PARTICLE_TYPE, Constants.id(name));
    }

    static ResourceKey<SoundEvent> soundEventKey(String name) {
        return ResourceKey.create(Registries.SOUND_EVENT, Constants.id(name));
    }

    @FunctionalInterface
    interface CreativeTabOutput {
        void accept(ItemLike itemlike);
    }
}
