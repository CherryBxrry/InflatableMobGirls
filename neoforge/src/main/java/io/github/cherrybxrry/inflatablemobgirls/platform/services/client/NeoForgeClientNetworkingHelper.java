package io.github.cherrybxrry.inflatablemobgirls.platform.services.client;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class NeoForgeClientNetworkingHelper implements IClientNetworkingHelper {
    private final List<ServerboundPacketEntry<?>> serverBoundPackets = new ArrayList<>();

    @Override
    public <T extends CustomPacketPayload> void sendToServer(T packet) {
        ClientPacketDistributor.sendToServer(packet);
    }

    @Override
    public <T extends CustomPacketPayload> void registerServerboundPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ServerboundHandler<T> handler) {
        this.serverBoundPackets.add(new ServerboundPacketEntry<>(type, codec, handler));
    }

    @Override
    public void applyServerboundPacketRegistrations(ServerBoundRegistrar registrar) {
        for (ServerboundPacketEntry<?> entry : this.serverBoundPackets) {
            entry.register(registrar);
        }
    }

    public ServerBoundRegistrar createServerboundRegistrar(PayloadRegistrar registrar) {
        return new ServerBoundRegistrar() {
            @Override
            public <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ServerboundHandler<T> handler) {
                registrar.commonToServer(
                        type,
                        codec,
                        (payload, context) -> {
                            Context ctx = new NeoForgeContext(context);
                            handler.handle(payload, ctx);
                        }
                );
            }
        };
    }

    private record ServerboundPacketEntry<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ServerboundHandler<T> handler) {
        private void register(ServerBoundRegistrar registrar) {
            registrar.register(this.type, this.codec, this.handler);
        }
    }

    static class NeoForgeContext implements IClientNetworkingHelper.Context {
        private final IPayloadContext context;

        public NeoForgeContext(IPayloadContext context) {
            this.context = context;
        }

        @Override
        public Player player() {
            return context.player();
        }

        @Override
        public CompletableFuture<Void> enqueueWork(Runnable task) {
            return context.enqueueWork(task);
        }

        @Override
        public <R> CompletableFuture<R> enqueueWork(Supplier<R> task) {
            return context.enqueueWork(task);
        }

        @Override
        public void reply(CustomPacketPayload payload) {
            context.reply(payload);
        }

        @Override
        public void disconnect(Component reason) {
            context.disconnect(reason);
        }
    }
}
