package io.github.cherrybxrry.inflatablemobgirls.entities;

import com.google.common.annotations.VisibleForTesting;
import io.github.cherrybxrry.inflatablemobgirls.init.ModDataComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public abstract class InflatableMobGirl extends TamableAnimal implements NeutralMob {
    private static final EntityDataAccessor<Long> DATA_ANGER_END_TIME;
    public static final EntityDataAccessor<Long> DATA_LAST_INFLATE_TICK;
    protected static final EntityDataAccessor<Integer> DATA_STAGE;
    public static final EntityDataAccessor<Long> DATA_INFLATE_TICKS;

    private static final String TAG_INFLATE_TICKS = "InflateTicks";
    private static final String TAG_LAST_INFLATE_TICK = "LastInflateTick";
    private static final String TAG_STAGE = "Stage";

    private static final UniformInt PERSISTENT_ANGER_TIME;
    private @Nullable EntityReference<LivingEntity> persistentAngerTarget;

    private static final int STAGE_MASK = 0xFF;
    private static final int SITTING_BIT = 1 << 8;

    public int attackAnimationTimeout = 0;

    public InflatableMobGirl(EntityType<? extends InflatableMobGirl> entityType, Level level) {
        super(entityType, level);
        this.setTame(false, false);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_INFLATE_TICKS, 0L);
        entityData.define(DATA_LAST_INFLATE_TICK, 0L);
        entityData.define(DATA_STAGE, 0);
        entityData.define(DATA_ANGER_END_TIME, -1L);
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == DATA_STAGE) {
            this.refreshDimensions();
        }
    }

    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putLong(TAG_INFLATE_TICKS, this.entityData.get(DATA_INFLATE_TICKS));
        output.putLong(TAG_LAST_INFLATE_TICK, this.entityData.get(DATA_LAST_INFLATE_TICK));
        output.putInt(TAG_STAGE, this.getStage());
        this.addPersistentAngerSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setInflateLength(input.getLong(TAG_INFLATE_TICKS).orElse(this.getInflateLength()));
        this.resetLastInflateTick(input.getLong(TAG_LAST_INFLATE_TICK).orElse(0L));
        this.setStage(input.getInt(TAG_STAGE).orElse(this.getStage()));
        this.readPersistentAngerSaveData(this.level(), input);
    }

    // Sounds

    @Override
    public @NonNull SoundSource getSoundSource() {
        return SoundSource.NEUTRAL;
    }

    protected float getSoundVolume() {
        return 0.4F;
    }

    @Override
    public void playSound(@NotNull SoundEvent sound) {
        if (!this.isSilent()) {
            this.playSound(sound, this.getSoundSource(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    public void playSound(SoundEvent sound, SoundSource soundSource, float volume, float pitch) {
        if (!this.isSilent()) {
            this.level().playSound(null, this, sound, soundSource, volume, pitch);
        }
    }

    @Override
    public void playAmbientSound() {
        SoundEvent soundevent = this.getAmbientSound();
        if (soundevent != null) {
            this.playSound(soundevent, this.getSoundSource(), this.getSoundVolume(), this.getVoicePitch());
        }
    }

    // Shared getters/setters
    public int getStage() {
        return entityData.get(DATA_STAGE);
    }

    public void setStage(int stage) {
        entityData.set(DATA_STAGE, Math.clamp(stage, 0, this.getMaxStage()));
    }

    public abstract int getMaxStage();

    public boolean isMaxStage() {
        return this.getStage() == this.getMaxStage();
    }

    @Override
    public long getPersistentAngerEndTime() {
        return this.entityData.get(DATA_ANGER_END_TIME);
    }

    @Override
    public void setPersistentAngerEndTime(long endTime) {
        this.entityData.set(DATA_ANGER_END_TIME, endTime);
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setTimeToRemainAngry(PERSISTENT_ANGER_TIME.sample(this.random));
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return this.persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable EntityReference<LivingEntity> persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    public boolean hasInflated() {
        return this.getInflateTime() >= this.getInflateLength();
    }

    public boolean hasVisuallyInflated() {
        return this.getInflateTime() < 0L != this.hasInflated();
    }

    public boolean isInflating() {
        return this.getInflateTime() < this.getInflateLength();
    }

    public boolean isVisuallyInflating() {
        return this.getInflateTime() < this.getInflateLength() && this.getInflateTime() >= 0L;
    }

    @VisibleForTesting
    public void resetLastInflateTick(long syncedPoseTickTime) {
        this.entityData.set(DATA_LAST_INFLATE_TICK, syncedPoseTickTime);
    }

    private void resetLastInflateTickToFullInflated(long currentTime) {
        this.resetLastInflateTick(Math.max(0L, currentTime - 52L - 1L));
    }

    public long getInflateTime() {
        return this.level().getGameTime() - Math.abs(this.entityData.get(DATA_LAST_INFLATE_TICK));
    }

    public void inflateForLength(long inflateLength) {
        this.setInflateLength(inflateLength);

        this.gameEvent(GameEvent.ENTITY_ACTION);
        this.resetLastInflateTick(-this.level().getGameTime());
    }

    protected void setInflateLength(long inflateLength) {
        this.entityData.set(DATA_INFLATE_TICKS, inflateLength);
    }

    protected long getInflateLength() {
        return this.entityData.get(DATA_INFLATE_TICKS);
    }

    @NotNull
    protected abstract EntityDimensions getStandingDimensions();

    @NotNull
    protected abstract EntityDimensions getSittingDimensions();

    @Override
    public void setInSittingPose(boolean value) {
        if (this.getPose() != Pose.SITTING && value) {
            this.setPose(Pose.SITTING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.refreshDimensions();
        } else if (this.getPose() != Pose.STANDING && !value) {
            this.setPose(Pose.STANDING);
            this.gameEvent(GameEvent.ENTITY_ACTION);
            this.refreshDimensions();
        }

        super.setInSittingPose(value);
    }

    @Override
    public boolean onClimbable() {
        return false;
    }

    protected abstract void setupAnimationStates();

    @Nullable
    protected abstract AnimationState getAttackAnimation();

    protected void performAttackAnimation() {
        if (this.getAttackAnimation() == null) return;

        if (this.isAggressive() && this.attackAnimationTimeout <= 0) {
            this.attackAnimationTimeout = this.getAttackLength();
        } else if (this.attackAnimationTimeout > 0) {
            --this.attackAnimationTimeout;
        }

        this.getAttackAnimation().animateWhen(this.attackAnimationTimeout > 0, this.tickCount);
    }

    abstract protected int getAttackLength();

    public abstract int getAttackDelay();

    /**
     * Stops all animations in a list of animations except for a list of animations.
     */
    protected void stopAllFromExcept(List<AnimationState> animationStates, AnimationState... except) {
        Set<AnimationState> exceptSet = (except == null || except.length == 0) ? Collections.emptySet() : new HashSet<>(Arrays.asList(except));
        for (AnimationState animation : animationStates) {
            if (animation != null && !exceptSet.contains(animation)) {
                animation.stop();
            }
        }
    }

    @Override
    protected void updateWalkAnimation(float distance) {
        float targetSpeed;
        if (this.getPose() == Pose.STANDING) {
            targetSpeed = Math.min(distance * 6.0F, 1.0F);
        } else {
            targetSpeed = 0.0F;
        }

        this.walkAnimation.update(targetSpeed, 0.2F, 1.0F);
    }

    @Override
    protected @NonNull EntityDimensions getDefaultDimensions(@NonNull Pose pose) {
        return pose == Pose.SITTING ? this.getSittingDimensions() : this.getStandingDimensions();
    }

    /**
     * Defines the item for the entity to be tamed by.
     */
    protected abstract boolean isTameItem(ItemStack itemStack);

    /**
     * Defines the item for the entity to be inflated by.
     */
    protected abstract boolean isInflateItem(ItemStack itemStack);

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (this.isTame()) {
            if (this.isFood(itemStack) && this.getHealth() < this.getMaxHealth()) {
                this.feed(player, hand, itemStack, 2.0F, 2.0F);
                return InteractionResult.SUCCESS;
            }

            InteractionResult interactionResult = super.mobInteract(player, hand);
            if (!interactionResult.consumesAction() && this.isOwnedBy(player)) {
                this.setOrderedToSit(!this.isOrderedToSit());
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                return InteractionResult.SUCCESS.withoutItem();
            }

            return interactionResult;
        } else if (!this.level().isClientSide() && this.isTameItem(itemStack) && !this.isAngry()) {
            itemStack.consume(1, player);
            this.tryToTame(player);
            return InteractionResult.SUCCESS_SERVER;
        }

        return super.mobInteract(player, hand);
    }

    private void tryToTame(Player player) {
        if (this.random.nextInt(3) == 0) {
            this.tame(player);
            this.navigation.stop();
            this.setTarget(null);
            this.setOrderedToSit(true);
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_SUCCEEDED);
        } else {
            this.level().broadcastEntityEvent(this, EntityEvent.TAMING_FAILED);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isPassenger() && !this.isInSittingPose()) {
            this.setInSittingPose(true);
        }

        if (this.level().isClientSide()) {
            this.setupAnimationStates();
        }
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(), true);
        }
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isInSittingPose() ? 20 : super.getMaxHeadXRot();
    }

    // Damage
    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float damage) {
        if (this.isInvulnerableTo(level, source)) return false;

        if (source.getEntity() instanceof TamableAnimal tamableAttacker
                && tamableAttacker.getOwner() == this.getOwner()) return false;

        if (source.getDirectEntity() instanceof TamableAnimal tamableDirect
                && tamableDirect.getOwner() == this.getOwner()) return false;

        this.setOrderedToSit(false);
        return super.hurtServer(level, source, damage);
    }

    public boolean wantsToAttack(@NotNull LivingEntity target, @NotNull LivingEntity owner) {
        if (target instanceof Creeper || target instanceof Ghast || target instanceof ArmorStand) {
            return false;
        } else if (target instanceof InflatableMobGirl girlTarget) {
            return !girlTarget.isTame() || girlTarget.getOwner() != owner;
        } else if (target instanceof Player playerTarget && owner instanceof Player playerOwner && !playerOwner.canHarmPlayer(playerTarget)) {
            return false;
        } else {
            return (!(target instanceof AbstractHorse horse) || !horse.isTamed()) && !(target instanceof TamableAnimal animal && animal.isTame());
        }
    }

    @Override
    public void setTarget(LivingEntity target) {
        if (target instanceof TamableAnimal tamableTarget) {
            if (this.isTame() && tamableTarget.isTame() && this.getOwner() == tamableTarget.getOwner()) {
                return;
            }
        }

        super.setTarget(target);
    }

    public boolean canBeLeashed() {
        return !this.isAngry();
    }

    @Override
    public boolean canFallInLove() {
        return false;
    }

    static {
        DATA_INFLATE_TICKS = SynchedEntityData.defineId(InflatableMobGirl.class, EntityDataSerializers.LONG);
        DATA_LAST_INFLATE_TICK = SynchedEntityData.defineId(InflatableMobGirl.class, EntityDataSerializers.LONG);
        DATA_ANGER_END_TIME = SynchedEntityData.defineId(InflatableMobGirl.class, EntityDataSerializers.LONG);
        PERSISTENT_ANGER_TIME = TimeUtil.rangeOfSeconds(20, 39);
        DATA_STAGE = SynchedEntityData.defineId(InflatableMobGirl.class, EntityDataSerializers.INT);
    }

    /**
     * Finalises spawn data.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level, @NonNull DifficultyInstance difficulty, @NonNull EntitySpawnReason spawnReason, SpawnGroupData groupData) {
        this.setBaby(false);
        this.resetLastInflateTickToFullInflated(level.getLevel().getGameTime());
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    public static boolean isDarkEnoughToSpawn(ServerLevelAccessor level, BlockPos pos, RandomSource random) {
        if (level.getBrightness(LightLayer.SKY, pos) > random.nextInt(32)) {
            return false;
        } else {
            DimensionType dimensionType = level.dimensionType();
            int blockLightLimit = dimensionType.monsterSpawnBlockLightLimit();
            if (blockLightLimit < 15 && level.getBrightness(LightLayer.BLOCK, pos) > blockLightLimit) {
                return false;
            } else {
                int brightness = level.getLevel().isThundering() ? level.getMaxLocalRawBrightness(pos, 10) : level.getMaxLocalRawBrightness(pos);
                return brightness <= dimensionType.monsterSpawnLightTest().sample(random);
            }
        }
    }

    public static boolean checkLightInflatableMobSpawnRules(EntityType<? extends InflatableMobGirl> entityType, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return checkAnimalSpawnRules(entityType, level, spawnReason, pos, random);
    }

    public static boolean checkDarkInflatableMobSpawnRules(EntityType<? extends InflatableMobGirl> entityType, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return Monster.checkMonsterSpawnRules(entityType, level, spawnReason, pos, random);
    }

    public static boolean checkDarkSurfaceInflatableMobSpawnRules(EntityType<? extends Mob> type, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return Monster.checkSurfaceMonstersSpawnRules(type, level, spawnReason, pos, random);
    }

    public static boolean checkInflatableMobSpawnRules(EntityType<? extends InflatableMobGirl> entityType, ServerLevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return checkMobSpawnRules(entityType, level, spawnReason, pos, random);
    }

    /**
     * Called once when added to the world. Starts default animations.
     */
    @Override
    public @NonNull Packet<ClientGamePacketListener> getAddEntityPacket(@NonNull ServerEntity serverEntity) {
        return new ClientboundAddEntityPacket(this, serverEntity, this.packEntityData());
    }

    protected int packEntityData() {
        int stage = Mth.clamp(this.getStage(), 0, 0xFF);
        int packed = (stage & STAGE_MASK);
        if (this.isOrderedToSit() || this.isInSittingPose()) packed |= SITTING_BIT;

        return packed;
    }

    @Override
    public void recreateFromPacket(@NonNull ClientboundAddEntityPacket packet) {
        super.recreateFromPacket(packet);
        this.applyPackedData(packet.getData());
    }

    protected void applyPackedData(int packed) {
        int stage = packed & STAGE_MASK;
        boolean sitting = (packed & SITTING_BIT) != 0;

        this.setStage(stage);
        this.setOrderedToSit(sitting);
        this.setInSittingPose(sitting);

        this.setupAnimationStates();
    }

    // Goals
    protected static class InflateGoal extends Goal {
        private final InflatableMobGirl entity;

        public InflateGoal(InflatableMobGirl entity) {
            this.entity = entity;
            this.setFlags(EnumSet.of(Flag.MOVE));
        }

        @Override
        public boolean canUse() {
            return this.entity.isInflating() && !this.entity.isOrderedToSit();
        }

        @Override
        public void start() {
            this.entity.getNavigation().stop();
        }
    }
}
