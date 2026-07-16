package io.github.cherrybxrry.inflatablemobgirls.consume_effects;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.entities.InflatableMobGirl;
import io.github.cherrybxrry.inflatablemobgirls.init.ModConsumeEffectTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.consume_effects.ConsumeEffect;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.NonNull;

public record InflateConsumeEffect() implements ConsumeEffect {
    public static final InflateConsumeEffect INSTANCE = new InflateConsumeEffect();
    public static final MapCodec<InflateConsumeEffect> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, InflateConsumeEffect> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public InflateConsumeEffect() {
    }

    @Override
    public @NonNull Type<? extends ConsumeEffect> getType() {
        return ModConsumeEffectTypes.INFLATE.get();
    }

    @Override
    public boolean apply(@NonNull Level level, @NonNull ItemStack itemStack, @NonNull LivingEntity livingEntity) {
        if (!level.isClientSide() && livingEntity instanceof InflatableMobGirl girl) {
            girl.setStage(girl.getStage() + 1);

            return true;
        }

        return false;
    }
}
