package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.event.RegisterClientPayloadHandlersEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class NeoForgeServerNetworkingHelper implements IServerNetworkingHelper {
    private final List<ServerboundPacketEntry<?>> serverBoundPackets = new ArrayList<>();

    @Override
    public <T extends CustomPacketPayload> void sendToServer(ServerPlayer player, T packet) {
        PacketDistributor.sendToPlayer(player, packet);
    }

    @Override
    public <T extends CustomPacketPayload> void registerClientboundPacket(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler) {
        this.serverBoundPackets.add(new ServerboundPacketEntry<>(type, codec, handler));
    }

    @Override
    public void applyClientboundPacketRegistrations(ClientBoundRegistrar registrar) {
        for (ServerboundPacketEntry<?> entry : this.serverBoundPackets) {
            entry.register(registrar);
        }
    }

    public ClientBoundRegistrar createClientboundRegistrar(PayloadRegistrar registrar) {
        return new ClientBoundRegistrar() {
            @Override
            public <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler) {
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

    public ClientBoundRegistrar createClientboundRegistrar(RegisterClientPayloadHandlersEvent event) {
        return new ClientBoundRegistrar() {
            @Override
            public <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler) {
                event.register(
                        type,
                        (payload, context) -> {
                            Context ctx = new NeoForgeContext(context);
                            handler.handle(payload, ctx);
                        }
                );
            }
        };
    }

    private record ServerboundPacketEntry<T extends CustomPacketPayload>(CustomPacketPayload.Type<T> type,
                                                                         StreamCodec<? super FriendlyByteBuf, T> codec,
                                                                         ClientboundHandler<T> handler) {
        private void register(ClientBoundRegistrar registrar) {
            registrar.register(this.type, this.codec, this.handler);
        }
    }

    static class NeoForgeContext implements Context {
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
