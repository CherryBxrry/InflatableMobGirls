package io.github.cherrybxrry.inflatablemobgirls.networking.packets;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public record ServerboundRiddenInputPacket(Action action) implements CustomPacketPayload {
    public static final Identifier RIDDEN_INPUT_PACKET = Constants.id("ridden_input");

    public static final CustomPacketPayload.Type<ServerboundRiddenInputPacket> TYPE =
            new CustomPacketPayload.Type<>(RIDDEN_INPUT_PACKET);

    public static final StreamCodec<ByteBuf, ServerboundRiddenInputPacket> CODEC = StreamCodec.of(
            (buf, packet) -> {
                FriendlyByteBuf buffer = new FriendlyByteBuf(buf);
                buffer.writeEnum(packet.action());
            },
            buf -> {
                FriendlyByteBuf buffer = new FriendlyByteBuf(buf);
                return new ServerboundRiddenInputPacket(buffer.readEnum(Action.class));
            }
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Action {
        START_MELEE_ATTACK,
        STOP_MELEE_ATTACK,
        START_RANGED_ATTACK,
        STOP_RANGED_ATTACK
    }
}
