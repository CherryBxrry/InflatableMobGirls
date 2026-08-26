package io.github.cherrybxrry.inflatablemobgirls.block.entity;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.block.CreepSporeBlock;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirlVariant;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirlVariants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class CreepSporeBlockEntity extends BlockEntity {
    public static BlockEntityTicker<CreepSporeBlockEntity> ANIMATION_TICKER;

    private Holder<CreeperGirlVariant> variant;

    public int tickCount;
    public int randomShiver = 20;
    public int randomBlink = 20;
    @Nullable
    private UUID ownerUUID = null;

    public AnimationState idle0Animation = new AnimationState();
    public AnimationState idle1Animation = new AnimationState();
    public AnimationState idle2Animation = new AnimationState();
    public AnimationState idle3Animation = new AnimationState();

    public AnimationState ahogeShiverAnimation = new AnimationState();
    public AnimationState headShiverAnimation = new AnimationState();

    public AnimationState blinkAnimation = new AnimationState();

    public final List<AnimationState> idleAnimationStates = List.of(
            idle0Animation,
            idle1Animation,
            idle2Animation,
            idle3Animation
    );

    public CreepSporeBlockEntity(BlockPos worldPosition, BlockState blockState) {
        super(ModBlockEntityTypes.CREEPSPORE_CROP.get(), worldPosition, blockState);
    }

    public @Nullable UUID getOwnerUUID() {
        return this.ownerUUID;
    }

    public void setOwnerUUID(@org.jspecify.annotations.Nullable UUID uuid) {
        this.ownerUUID = uuid;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(@NonNull ValueOutput output) {
        super.saveAdditional(output);

        if (this.ownerUUID != null) {
            int[] uuidInts = UUIDUtil.uuidToIntArray(this.ownerUUID);
            output.putIntArray("Owner", uuidInts);
        }

        VariantUtils.writeVariant(output, this.getVariant());
    }

    @Override
    protected void loadAdditional(@NonNull ValueInput input) {
        super.loadAdditional(input);
        Optional<int[]> owner = input.getIntArray("Owner");
        if (owner.isPresent()) {
            int[] arr = owner.get();
            if (arr.length == 4) {
                this.ownerUUID = UUIDUtil.uuidFromIntArray(arr);
            } else {
                Constants.LOG.warn("CreepSporeBlockEntity: OwnerIntArray length unexpected: {}", arr.length);
            }
        }

        VariantUtils.readVariant(input, ModRegistries.CREEPER_GIRL_VARIANT).ifPresent(this::setVariant);
    }

    /**
     * Stops all animations in a list of animations except for a list of animations.
     */
    public void stopAllFromExcept(List<AnimationState> animationStates, AnimationState... except) {
        Set<AnimationState> exceptSet = (except == null || except.length == 0) ? Collections.emptySet() : new HashSet<>(Arrays.asList(except));
        for (AnimationState animation : animationStates) {
            if (animation != null && !exceptSet.contains(animation)) {
                animation.stop();
            }
        }
    }

    public Holder<CreeperGirlVariant> getVariant() {
        if (this.variant == null && this.level != null) {
            return VariantUtils.getDefaultOrAny(this.level.registryAccess(), CreeperGirlVariants.DEFAULT);
        }

        return this.variant;
    }

    public void setVariant(Holder<CreeperGirlVariant> variant) {
        this.variant = variant;
        this.setChanged();
    }

    public Identifier getTexture() {
        return this.getVariant().value().babyInfo().normal().texturePath();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public @NonNull CompoundTag getUpdateTag(HolderLookup.@NonNull Provider registries) {
        return this.saveWithoutMetadata(registries);
    }

    static {
        ANIMATION_TICKER = (level, _, state, entity) -> {
            entity.tickCount++;

            int age = state.getValueOrElse(CreepSporeBlock.AGE, 0);

            AnimationState idle = entity.idleAnimationStates.get(age);
            entity.stopAllFromExcept(entity.idleAnimationStates, idle);

            idle.startIfStopped(entity.tickCount);

            if (entity.randomShiver < 0) {
                entity.randomShiver = 40 + level.getRandom().nextInt(40);

                if (age == 1) {
                    entity.headShiverAnimation.start(entity.tickCount);
                }

                entity.ahogeShiverAnimation.start(entity.tickCount);
            } else {
                entity.randomShiver--;
            }

            if (entity.randomBlink < 0) {
                entity.randomBlink = 20 + level.getRandom().nextInt(40);
            } else {
                entity.randomBlink--;
            }
        };
    }
}
