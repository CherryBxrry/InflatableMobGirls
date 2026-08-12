package io.github.cherrybxrry.inflatablemobgirls.platform.services;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public class FabricServerNetworkingHelper implements IServerNetworkingHelper {
    private final List<ServerboundPacketEntry<?>> serverBoundPackets = new ArrayList<>();

    @Override
    public <T extends CustomPacketPayload> void sendToServer(ServerPlayer player, T packet) {
        ServerPlayNetworking.send(player, packet);
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

    public ClientBoundRegistrar createClientboundRegistrar() {
        return new ClientBoundRegistrar() {
            @Override
            public <T extends CustomPacketPayload> void register(CustomPacketPayload.Type<T> type, StreamCodec<? super FriendlyByteBuf, T> codec, ClientboundHandler<T> handler) {
                PayloadTypeRegistry.clientboundPlay().register(type, codec);

                ServerPlayNetworking.registerGlobalReceiver(type, (payload, context) ->  {
                    Context ctx = new FabricContext(context);
                    handler.handle(payload, ctx);
                });
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

    static class FabricContext implements Context {
        private final ServerPlayNetworking.Context context;

        public FabricContext(ServerPlayNetworking.Context context) {
            this.context = context;
        }

        @Override
        public Player player() {
            return context.player();
        }

        @Override
        public CompletableFuture<Void> enqueueWork(Runnable task) {
            CompletableFuture<Void> future = new CompletableFuture<>();
            context.server().execute(() -> {
                try {
                    task.run();
                    future.complete(null);
                } catch (Throwable throwable) {
                    future.completeExceptionally(throwable);
                }
            });
            return future;
        }

        @Override
        public <R> CompletableFuture<R> enqueueWork(Supplier<R> task) {
            CompletableFuture<R> future = new CompletableFuture<>();
            context.server().execute(() -> {
                try {
                    future.complete(task.get());
                } catch (Throwable throwable) {
                    future.completeExceptionally(throwable);
                }
            });
            return future;
        }

        @Override
        public void reply(CustomPacketPayload payload) {
            context.responseSender().sendPacket(payload);
        }

        @Override
        public void disconnect(Component reason) {
            context.player().connection.disconnect(reason);
        }
    }
}
