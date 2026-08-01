package io.github.cherrybxrry.inflatablemobgirls.platform.services.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface IClientNetworkingHelper {
    <T extends CustomPacketPayload> void sendToServer(T packet);

    <T extends CustomPacketPayload> void registerServerboundPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ServerboundHandler<T> handler);

    void applyServerboundPacketRegistrations(ServerBoundRegistrar registrar);

    interface ServerBoundRegistrar {
        <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ServerboundHandler<T> handler);
    }

    @FunctionalInterface
    interface ServerboundHandler<T> {
        void handle(T packet, Context context);
    }

    interface Context {
        Player player();
        CompletableFuture<Void> enqueueWork(Runnable task);
        <R> CompletableFuture<R> enqueueWork(Supplier<R> task);
        void reply(CustomPacketPayload payload);
        void disconnect(Component reason);
    }
}
