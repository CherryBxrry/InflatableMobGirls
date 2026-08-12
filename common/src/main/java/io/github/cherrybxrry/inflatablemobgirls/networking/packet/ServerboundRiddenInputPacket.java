package io.github.cherrybxrry.inflatablemobgirls.networking.packet;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ByIdMap;
import org.jspecify.annotations.NonNull;

public record ServerboundRiddenInputPacket(Action action) implements CustomPacketPayload {
    public static final Identifier RIDDEN_INPUT_PACKET = Constants.id("ridden_input");

    public static final Type<ServerboundRiddenInputPacket> TYPE =
            new Type<>(RIDDEN_INPUT_PACKET);

    public static final StreamCodec<FriendlyByteBuf, ServerboundRiddenInputPacket> STREAM_CODEC = StreamCodec.composite(
            Action.STREAM_CODEC,
            ServerboundRiddenInputPacket::action,

            ServerboundRiddenInputPacket::new
    );

    @Override
    public @NonNull Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public enum Action {
        START_MELEE_ATTACK,
        STOP_MELEE_ATTACK,
        START_RANGED_ATTACK,
        STOP_RANGED_ATTACK;

        public static final StreamCodec<ByteBuf, Action> STREAM_CODEC = ByteBufCodecs.idMapper(ByIdMap.continuous(Action::ordinal, Action.values(), ByIdMap.OutOfBoundsStrategy.ZERO), Action::ordinal);
    }
}
