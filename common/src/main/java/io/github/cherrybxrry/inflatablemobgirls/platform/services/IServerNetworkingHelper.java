package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public interface IServerNetworkingHelper {
    <T extends CustomPacketPayload> void sendToServer(ServerPlayer player, T packet);

    <T extends CustomPacketPayload> void registerClientboundPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler);

    void applyClientboundPacketRegistrations(ClientBoundRegistrar registrar);

    interface ClientBoundRegistrar {
        <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler);
    }

    @FunctionalInterface
    interface ClientboundHandler<T extends CustomPacketPayload> {
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
