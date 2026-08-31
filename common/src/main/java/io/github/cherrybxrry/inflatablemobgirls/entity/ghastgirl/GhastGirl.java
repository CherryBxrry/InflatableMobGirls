package io.github.cherrybxrry.inflatablemobgirls.entity.ghastgirl;

import io.github.cherrybxrry.inflatablemobgirls.entity.InflatableFlyingMobGirl;
import io.github.cherrybxrry.inflatablemobgirls.entity.InflatableMobGirl;
import io.github.cherrybxrry.inflatablemobgirls.entity.PlayerRideableMeleeAttacking;
import io.github.cherrybxrry.inflatablemobgirls.entity.PlayerRideableRangedAttacking;
import io.github.cherrybxrry.inflatablemobgirls.entity.ai.goal.LandWhenOrderedToGoal;
import io.github.cherrybxrry.inflatablemobgirls.entity.ai.goal.MeleeAttackAnimatedGoal;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItemTags;
import io.github.cherrybxrry.inflatablemobgirls.init.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.hurtingprojectile.LargeFireball;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

public class GhastGirl extends InflatableFlyingMobGirl implements PlayerRideableMeleeAttacking, PlayerRideableRangedAttacking {
    private static final EntityDataAccessor<Integer> DATA_FUEL_LEVEL;
    private static final EntityDataAccessor<Boolean> DATA_IS_CHARGING;
    private static final EntityDataAccessor<Boolean> DATA_IS_LEASH_HOLDER;
    private static final EntityDataAccessor<Boolean> DATA_ON_GEYSER;
    private static final String TAG_EXPLOSION_POWER = "ExplosionPower";
    private static final String TAG_FUEL = "Fuel";
    private static final String TAG_ON_GEYSER = "OnGeyser";
    private int explosionPower = 0;
    private int leashHolderTime = 0;

    private static final int DEFAULT_FUEL_LEVEL = 0;
    private static final byte DEFAULT_EXPLOSION_POWER = 1;

    private static final int FUEL_PER_STAGE = 20;

    // Taming
    private static final float START_HEALTH = 10.0F;
    private static final float TAME_HEALTH = 20.0F;

    // Hitbox
    public static final float STAND_HEIGHT = 4.8F;
    public static final float BLIMP_HEIGHT = 4.5F;
    public static final float SIT_HEIGHT = 2.8F;
    public static final float EYE_OFFSET = 0.6F;
    public static final float BLIMP_EYE_OFFSET = 0.8F;
    public static final float BLIMP_SITTING_EYE_OFFSET = 2.8F;
    public static final float WIDTH = 1.99F;
    public static final float BLIMP_WIDTH = 2.99F;
    public static final float LEASH_OFFSET = 0.24F;

    private int kickTime = 0;
    private int rangedCooldown = 0;

    // Animation States
    public AnimationState idleStand0Animation = new AnimationState();
    public AnimationState idleStand1Animation = new AnimationState();
    public AnimationState idleStand2Animation = new AnimationState();
    public AnimationState idleStand3Animation = new AnimationState();

    public AnimationState idleRide0Animation = new AnimationState();
    public AnimationState idleRide1Animation = new AnimationState();
    public AnimationState idleRide2Animation = new AnimationState();
    public AnimationState idleRide3Animation = new AnimationState();

    public AnimationState sit0Animation = new AnimationState();
    public AnimationState sit1Animation = new AnimationState();
    public AnimationState sit2Animation = new AnimationState();
    public AnimationState sit3Animation = new AnimationState();

    public AnimationState inflate1Animation = new AnimationState();
    public AnimationState inflate2Animation = new AnimationState();
    public AnimationState inflate3Animation = new AnimationState();

    public AnimationState sitInflate1Animation = new AnimationState();
    public AnimationState sitInflate2Animation = new AnimationState();
    public AnimationState sitInflate3Animation = new AnimationState();

    public AnimationState ventInflate1Animation = new AnimationState();
    public AnimationState ventInflate2Animation = new AnimationState();
    public AnimationState ventInflate3Animation = new AnimationState();

    public AnimationState attack0Animation = new AnimationState();
    public AnimationState attack1Animation = new AnimationState();
    public AnimationState attack2Animation = new AnimationState();
    public AnimationState attack3Animation = new AnimationState();

    public AnimationState blinkAnimation = new AnimationState();

    public final List<AnimationState> idleAnimations = List.of(
            idleStand0Animation,
            idleStand1Animation,
            idleStand2Animation,
            idleStand3Animation,

            idleRide0Animation,
            idleRide1Animation,
            idleRide2Animation,
            idleRide3Animation
    );

    public final List<AnimationState> sittingAnimations = List.of(
            sit0Animation,
            sit1Animation,
            sit2Animation,
            sit3Animation
    );

    public final List<AnimationState> inflateAnimations = List.of(
            inflate1Animation,
            inflate2Animation,
            inflate3Animation,

            sitInflate1Animation,
            sitInflate2Animation,
            sitInflate3Animation,

            ventInflate1Animation,
            ventInflate2Animation,
            ventInflate3Animation
    );

    public final List<AnimationState> attackAnimations = List.of(
            attack0Animation,
            attack1Animation,
            attack2Animation,
            attack3Animation
    );

    private static final SoundEvent[] INFLATION_SOUNDS = new SoundEvent[]{
            ModSounds.GHAST_GIRL_INFLATE_PUMP1.get(),
            ModSounds.GHAST_GIRL_INFLATE_PUMP2.get(),
            ModSounds.GHAST_GIRL_INFLATE_PUMP3.get(),
            ModSounds.GHAST_GIRL_INFLATE_VENT1.get(),
            ModSounds.GHAST_GIRL_INFLATE_VENT2.get(),
            ModSounds.GHAST_GIRL_INFLATE_VENT3.get()
    };

    public GhastGirl(EntityType<? extends InflatableMobGirl> entityType, Level level) {
        super(entityType, level);

        this.setTame(false, false);
        this.setPathfindingMalus(PathType.LAVA, 0.0F);
        this.setPathfindingMalus(PathType.FIRE_IN_NEIGHBOR, 0.0F);
        this.setPathfindingMalus(PathType.FIRE, 0.0F);
        this.xpReward = 5;
    }

    /**
     * Builds the attribute supplier for GhastGirl.
     *
     * @return attribute builder
     */
    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, START_HEALTH)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.TEMPT_RANGE, 16.0D)
                .add(Attributes.STEP_HEIGHT, 1.0F)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.FLYING_SPEED, 0.1F)
                .add(Attributes.CAMERA_DISTANCE, 8.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 1.0f)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, 1.0F)
                .add(Attributes.ATTACK_KNOCKBACK, 3.0F);
    }

    /**
     * Registers AI goals and target selectors.
     */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new PanicGoal(this, 1.5D, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new LandWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new GhastGirlInflateGoal(this));
        this.goalSelector.addGoal(2, new GhastGirlShootFireballGoal(this, 24.0F, 12.0F, 1.2D));
        this.goalSelector.addGoal(3, new MeleeAttackAnimatedGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new GhastGirlSitOnNetherGeyserGoal(this, 1.1D));
        this.goalSelector.addGoal(5, new HybridFollowOwnerGoal(this, 1.0D, 10.0F, 3.0F));
        this.goalSelector.addGoal(6, new TemptGoal(this, 1.2D, this::isFood, false, 7.0F));
        this.goalSelector.addGoal(7, new WaterAvoidingHybridWanderGoal(this, 1.0F));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 16.0F));
        this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.targetSelector.addGoal(3, (new HurtByTargetGoal(this)).setAlertOthers());
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Player.class, 10, true, false, this::isAngryAt));
        this.targetSelector.addGoal(5, new ResetUniversalAngerTargetGoal<>(this, true));
    }

    /**
     * Define synchronised entity data used for networking.
     */
    @Override
    protected void defineSynchedData(SynchedEntityData.@NonNull Builder entityData) {
        super.defineSynchedData(entityData);
        entityData.define(DATA_ON_GEYSER, false);
        entityData.define(DATA_IS_CHARGING, false);
        entityData.define(DATA_IS_LEASH_HOLDER, false);
        entityData.define(DATA_FUEL_LEVEL, 0);
    }

    /**
     * Saves custom NBT data.
     */
    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putByte(TAG_EXPLOSION_POWER, (byte) this.explosionPower);
        output.putInt(TAG_FUEL, this.getFuelLevel());
        output.putBoolean(TAG_ON_GEYSER, this.onGeyser());
    }

    /**
     * Loads custom NBT data.
     */
    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.explosionPower = input.getByteOr(TAG_EXPLOSION_POWER, DEFAULT_EXPLOSION_POWER);
        this.setFuelLevel(input.getIntOr(TAG_FUEL, DEFAULT_FUEL_LEVEL));
        this.setOnGeyser(input.getBooleanOr(TAG_ON_GEYSER, false));
    }

    /**
     * Adjusts stats when tamed.
     */
    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(TAME_HEALTH);
            this.setHealth(TAME_HEALTH);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(START_HEALTH);
        }
    }

    // ==============================
    // Sounds and footsteps
    // ==============================

    /**
     * Custom footstep sound.
     */
    protected void playStepSound(@NotNull BlockPos pos, @NotNull BlockState blockIn) {
        this.playSound(SoundEvents.WOLF_STEP.value(), 0.15f, 1.0f);
    }

    /**
     * Sound when hurt.
     */
    protected SoundEvent getHurtSound(@NotNull DamageSource pDamageSource) {
        return SoundEvents.HAPPY_GHAST_HURT;
    }

    /**
     * Sound when killed.
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.HAPPY_GHAST_DEATH;
    }

    /**
     * Ambient sounds.
     */
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return this.getStage() > 0 ? ModSounds.GHAST_GIRL_BELLY_AMBIENCE.get() : null;
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    @Override
    public boolean canUseSlot(@NonNull EquipmentSlot slot) {
        return slot != EquipmentSlot.BODY ? super.canUseSlot(slot) : this.isAlive() && !this.isBaby();
    }

    @Override
    protected boolean canDispenserEquipIntoSlot(@NonNull EquipmentSlot slot) {
        return slot == EquipmentSlot.BODY;
    }

    protected boolean canShearEquipment(@NonNull Player player) {
        return this.isOwnedBy(player);
    }

    @Override
    protected int getRestrictionRadius() {
        return this.getItemBySlot(EquipmentSlot.BODY).isEmpty() ? 64 : 32;
    }

    @Override
    public int getMaxStage() {
        return 3;
    }

    @Override
    protected @NonNull EntityDimensions getStandingDimensions() {
        EntityDimensions standingDimension;

        if (this.isMaxStage()) {
            standingDimension = EntityDimensions.scalable(BLIMP_WIDTH, BLIMP_HEIGHT).withEyeHeight(BLIMP_HEIGHT - BLIMP_EYE_OFFSET);
        } else {
            standingDimension = EntityDimensions.scalable(WIDTH, STAND_HEIGHT).withEyeHeight(STAND_HEIGHT - EYE_OFFSET);
        }
        return standingDimension;
    }

    @Override
    protected @NonNull EntityDimensions getSittingDimensions() {
        EntityDimensions sittingDimension;

        if (this.isMaxStage()) {
            sittingDimension = EntityDimensions.scalable(BLIMP_WIDTH, BLIMP_HEIGHT).withEyeHeight(BLIMP_HEIGHT - BLIMP_SITTING_EYE_OFFSET);
        } else {
            sittingDimension = EntityDimensions.scalable(WIDTH, SIT_HEIGHT).withEyeHeight(SIT_HEIGHT - EYE_OFFSET);
        }
        return sittingDimension;
    }

    @Override
    protected void setupAnimationStates() {
        super.setupAnimationStates();

        boolean charging = this.isCharging();
        boolean inflating = this.isVisuallyInflating();
        boolean onGeyser = this.onGeyser();
        boolean sitting = this.isInSittingPose();
        boolean vehicle = this.isVehicle();
        int stage = this.getStage();

        if (inflating) {
            this.stopAllFromExcept(this.idleAnimations);
            this.stopAllFromExcept(this.sittingAnimations);

            int inflateIndex = Mth.clamp(stage + (onGeyser ? 5 : sitting ? 2 : -1), 0, this.inflateAnimations.size() - 1);

            AnimationState inflateAnimation = this.inflateAnimations.get(inflateIndex);

            this.stopAllFromExcept(this.inflateAnimations, inflateAnimation);
            inflateAnimation.startIfStopped(this.tickCount);
        } else if (sitting) {
            this.stopAllFromExcept(this.inflateAnimations);
            this.stopAllFromExcept(this.idleAnimations);

            AnimationState sittingAnimation = this.sittingAnimations.get(stage);

            this.stopAllFromExcept(this.sittingAnimations, sittingAnimation);
            sittingAnimation.startIfStopped(this.tickCount);
        } else {
            this.stopAllFromExcept(this.inflateAnimations);
            this.stopAllFromExcept(this.sittingAnimations);

            int idleIndex = Mth.clamp(stage + (vehicle ? 4 : 0), 0, this.idleAnimations.size() - 1);

            AnimationState idleAnimation = this.idleAnimations.get(idleIndex);

            this.stopAllFromExcept(this.idleAnimations, idleAnimation);
            idleAnimation.startIfStopped(this.tickCount);
        }

        this.blinkAnimation.animateWhen(this.isMaxStage() && !inflating, this.tickCount);

        if (charging && stage != 0) {
            this.attackAnimations.get(Math.min(stage, this.attackAnimations.size() - 1)).startIfStopped(this.tickCount);
        } else {
            this.stopAllFromExcept(this.attackAnimations, this.attack0Animation);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            if (this.leashHolderTime > 0) {
                --this.leashHolderTime;
            }

            this.setLeashHolder(this.leashHolderTime > 0);

            if (this.isTame() && this.getOwner() == null) {
                this.getPassengers().forEach(Entity::stopRiding);
            }
        }

        if (this.kickTime > 0) {
            --this.kickTime;
            if (this.kickTime == 5) {
                this.setAggressive(false);
                if (this.level() instanceof ServerLevel serverLevel) {
                    this.kick(serverLevel);
                }
            }
        }

        if (this.rangedCooldown > 0) {
            --this.rangedCooldown;
            if (this.rangedCooldown == 40) {
                this.shootFireball(null, this.level());
                this.setCharging(false);
            }
        }
    }

    protected void kick(ServerLevel level) {
        Vec3 look = this.getLookAngle();
        AABB kickHitbox = this.getBoundingBox()
                .setMaxY(this.getY(0.25))
                .move(look.x, 0, look.z);

        List<LivingEntity> targets = level.getEntitiesOfClass(
                LivingEntity.class,
                kickHitbox,
                target -> target != this
                        && target.isAlive()
                        && !this.getPassengers().contains(target)
                        && !(target instanceof TamableAnimal pet && pet.getOwner() == this.getOwner())
        );

        targets.forEach(target -> this.doHurtTarget(level, target));
    }

    protected void shootFireball(@Nullable Entity target, Level level) {
        double xzOffset = 2.0F;
        Vec3 viewVector = this.getViewVector(1.0F);
        double x = this.getX() + viewVector.x * xzOffset;
        double y = this.getY(0.65F);
        double z = this.getZ() + viewVector.z * xzOffset;
        double xdd;
        double ydd;
        double zdd;

        if (target != null) {
            xdd = target.getX() - x;
            ydd = target.getY(0.5F) - y;
            zdd = target.getZ() - z;
        } else {
            double viewOffset = 4.0F;
            xdd = this.getX() + (viewVector.x * viewOffset) - x;
            ydd = viewVector.y * viewOffset;
            zdd = this.getZ() + (viewVector.z * viewOffset) - z;
        }

        Vec3 direction = new Vec3(xdd, ydd, zdd);
        if (!this.isSilent()) {
            level.levelEvent(null, 1016, this.blockPosition(), 0);
        }

        LargeFireball entity = new LargeFireball(level, this, direction.normalize(), this.getExplosionPower());
        entity.setPos(x, y, z);
        level.addFreshEntity(entity);

        int fuelLevel = this.getFuelLevel() - 1;
        this.setFuelLevel(fuelLevel);
        this.setStage((int) Math.ceil((double) fuelLevel / FUEL_PER_STAGE));
    }

    @Override
    protected @Nullable AnimationState getAttackAnimation() {
        return this.attack0Animation;
    }

    @Override
    protected int getAttackLength() {
        return 10;
    }

    @Override
    public int getAttackDelay() {
        return 5;
    }

    @Override
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        if (this.isInflating() || this.getPassengers().contains(player)) return InteractionResult.PASS;
        ItemStack itemStack = player.getItemInHand(hand);

        if (!itemStack.isEmpty()) {
            if (this.isInflateItem(itemStack) && !this.isMaxStage() && !this.isFlying()) {
                int stage = this.getStage() + 1;
                this.setStage(stage);
                this.playSound(INFLATION_SOUNDS[stage - 1]);
                this.inflateForLength(!this.isMaxStage() ? 45 : 53);
                this.setFuelLevel(FUEL_PER_STAGE * stage);

                return InteractionResult.SUCCESS;
            }

            InteractionResult interactionResult = itemStack.interactLivingEntity(player, this, hand);
            if (interactionResult.consumesAction()) {
                return interactionResult;
            }
        }

        if (this.isWearingBodyArmor() && !player.isSecondaryUseActive() && !(this.isOrderedToSit() || this.isInSittingPose())) {
            this.doPlayerRide(player);
            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    private void doPlayerRide(Player player) {
        if (!this.level().isClientSide()) {
            player.startRiding(this);
            if (!this.isVehicle()) {
                this.clearHome();
            }
        }
    }

    @Override
    protected @NonNull Vec3 getPassengerAttachmentPoint(@NonNull Entity passenger, @NonNull EntityDimensions dimensions, float scale) {
        int index = Math.max(this.getPassengers().indexOf(passenger), 0);
        boolean driver = index == 0;
        float verticalOffset = !driver ? (this.isMaxStage() ? 3.5F : this.getBbHeight()) : switch (this.getStage()) {
            case 1, 2 -> 3.0F;
            case 3 -> 3.5F;
            default -> 2.5F;
        };
        float horizontalOffset = driver ? (this.isMaxStage() ? 0.1F : 0.75F) : (this.isMaxStage() ? -0.7F : 0.0F);

        if (passenger instanceof Animal) {
            verticalOffset -= 0.2F;
        }

        return (new Vec3(0.0F, verticalOffset, horizontalOffset * scale)).yRot(-this.yBodyRot * ((float) Math.PI / 180F));
    }

    @Override
    protected void addPassenger(@NonNull Entity passenger) {
        if (!this.isVehicle()) {
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.HARNESS_GOGGLES_DOWN, this.getSoundSource(), 1.0F, 1.0F);
        }

        super.addPassenger(passenger);
    }

    @Override
    protected void removePassenger(@NonNull Entity passenger) {
        super.removePassenger(passenger);

        if (!this.isVehicle()) {
            this.clearHome();
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.HARNESS_GOGGLES_UP, this.getSoundSource(), 1.0F, 1.0F);
        }

    }

    @Override
    protected boolean canAddPassenger(@NonNull Entity passenger) {
        return this.getPassengers().size() < 2;
    }

    @Override
    public @Nullable LivingEntity getControllingPassenger() {
        if (this.isWearingBodyArmor()) {
            Entity firstPassenger = this.getFirstPassenger();
            if (firstPassenger instanceof Player player) {
                return player;
            }
        }

        return super.getControllingPassenger();
    }

    @Override
    protected @NonNull Vec3 getRiddenInput(@NonNull Player controller, @NonNull Vec3 selfInput) {
        float strafe;
        float forward;

        if (this.canFly()) {
            strafe = controller.xxa;
            forward = 0.0F;
            float up = 0.0F;
            if (controller.zza != 0.0F) {
                float forwardLook = Mth.cos(controller.getXRot() * ((float) Math.PI / 180F));
                float upLook = -Mth.sin(controller.getXRot() * ((float) Math.PI / 180F));
                if (controller.zza < 0.0F) {
                    forwardLook *= -0.5F;
                    upLook *= -0.5F;
                }

                up = upLook;
                forward = forwardLook;
            }

            if (controller.isJumping()) {
                up += 0.5F;
            }

            return (new Vec3(strafe, up, forward)).scale((double) 3.9F * this.getAttributeValue(Attributes.FLYING_SPEED));
        } else {
            strafe = controller.xxa * 0.5F;
            forward = controller.zza;
            if (forward <= 0.0F) {
                forward *= 0.25F;
            }

            return new Vec3(strafe, 0.0, forward);
        }
    }

    @Override
    protected float getRiddenSpeed(@NonNull Player controller) {
        return this.canFly() ? super.getRiddenSpeed(controller) : (float) this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    protected Vec2 getRiddenRotation(LivingEntity controller) {
        return new Vec2(controller.getXRot() * 0.5F, controller.getYRot());
    }

    @Override
    protected void tickRidden(@NonNull Player controller, @NonNull Vec3 riddenInput) {
        super.tickRidden(controller, riddenInput);
        Vec2 rotation = this.getRiddenRotation(controller);

        if (this.canFly()) {
            float yRot = this.getYRot();
            float diff = Mth.wrapDegrees(rotation.y - yRot);
            yRot += diff * 0.08F;
            this.setRot(yRot, rotation.x);
            this.yRotO = this.yBodyRot = this.yHeadRot = yRot;
        } else {
            this.setRot(rotation.y, (this.getPassengers().size() > 1 && !this.isMaxStage()) ? 0 :  rotation.x);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
        }
    }

    public void setOnGeyser(boolean onGeyser) {
        entityData.set(DATA_ON_GEYSER, onGeyser);
    }

    public boolean onGeyser() {
        return entityData.get(DATA_ON_GEYSER);
    }

    private void setLeashHolder(boolean isLeashHolder) {
        this.entityData.set(DATA_IS_LEASH_HOLDER, isLeashHolder);
    }

    public boolean isLeashHolder() {
        return this.entityData.get(DATA_IS_LEASH_HOLDER);
    }

    public void setFuelLevel(int fuelLevel) {
        this.entityData.set(DATA_FUEL_LEVEL, Math.clamp(fuelLevel, 0, FUEL_PER_STAGE * this.getMaxStage()));
    }

    public int getFuelLevel() {
        return this.entityData.get(DATA_FUEL_LEVEL);
    }

    public boolean isCharging() {
        return this.entityData.get(DATA_IS_CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(DATA_IS_CHARGING, charging);
    }

    public int getExplosionPower() {
        return this.explosionPower;
    }

    @Override
    protected boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(ModItemTags.TAMES_GHAST_GIRL);
    }

    @Override
    protected boolean isInflateItem(ItemStack itemStack) {
        return itemStack.is(ModItemTags.INFLATES_GHAST_GIRL);
    }

    @Override
    public boolean isFood(@NonNull ItemStack itemStack) {
        return itemStack.is(ModItemTags.GHAST_GIRL_FOOD);
    }

    private static boolean isReflectedFireball(DamageSource source) {
        return source.getDirectEntity() instanceof LargeFireball && source.getEntity() instanceof Player;
    }

    @Override
    public boolean isInvulnerableTo(@NonNull ServerLevel level, @NonNull DamageSource source) {
        return this.isInvulnerable() && !source.is(DamageTypeTags.BYPASSES_INVULNERABILITY) || !isReflectedFireball(source) && super.isInvulnerableTo(level, source);
    }

    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float damage) {
        if (this.getControllingPassenger() instanceof Player controlling && controlling == source.getEntity()) return false;

        if (isReflectedFireball(source)) {
            super.hurtServer(level, source, 1000.0F);
            return true;
        } else {
            return !this.isInvulnerableTo(level, source) && super.hurtServer(level, source, damage);
        }
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NonNull ServerLevel serverLevel, @NonNull AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean canMate(@NonNull Animal partner) {
        return false;
    }

    @Override
    public boolean canMeleeAttack() {
        return !this.isMaxStage();
    }

    @Override
    public void handleStartMeleeAttack() {
        this.kickTime = this.getAttackLength();
        this.setAggressive(true);
    }

    @Override
    public void handleStopMeleeAttack() {

    }

    @Override
    public int getMeleeAttackCooldown() {
        return this.kickTime;
    }

    @Override
    public boolean canRangedAttack() {
        return this.getItemBySlot(EquipmentSlot.BODY).is(ItemTags.HARNESSES) && this.getStage() > 0;
    }

    @Override
    public void handleStartRangedAttack() {
        this.rangedCooldown = 50;
        if (!this.isSilent()) this.level().levelEvent(null, 1015, this.blockPosition(), 0);
        this.setCharging(true);
    }

    @Override
    public void handleStopRangedAttack() {

    }

    @Override
    public int getRangedAttackCooldown() {
        return this.rangedCooldown;
    }

    /**
     * Adjusts leash placement.
     */
    @Override
    public @NotNull Vec3 getLeashOffset() {
        return new Vec3(0.0F, (this.getEyeHeight() - LEASH_OFFSET), 0);
    }

    @Override
    public Vec3 @NonNull [] getQuadLeashHolderOffsets() {
        return getQuadLeashOffsets();
    }

    @Override
    public Vec3 @NonNull [] getQuadLeashOffsets() {
        double height = this.isInSittingPose() ? 0.37 : 0.64;
        double offset = this.isMaxStage() ? 0.14 : 0.0;
        double frontBack = this.isMaxStage() ? 0.1 : 0.15;
        double leftRight = this.isMaxStage() ? 0.15 : 0.25;

        return Leashable.createQuadLeashOffsets(this, offset, frontBack, leftRight, height);
    }

    @Override
    public boolean supportQuadLeash() {
        return true;
    }

    @Override
    public boolean supportQuadLeashAsHolder() {
        return true;
    }

    @Override
    public double leashElasticDistance() {
        return 10.0F;
    }

    @Override
    public double leashSnapDistance() {
        return 16.0F;
    }

    @Override
    public void notifyLeashHolder(Leashable entity) {
        if (entity.supportQuadLeash()) {
            this.leashHolderTime = 5;
        }
    }

    @Override
    public boolean isFlying() {
        return super.isFlying() && this.isMaxStage();
    }

    @Override
    protected boolean canFlyToOwner() {
        return this.isMaxStage();
    }

    @Override
    public boolean canFly() {
        return this.isMaxStage();
    }

    @Override
    public boolean isFlyingVehicle() {
        return this.isMaxStage();
    }

    @Override
    protected boolean canBeABaby() {
        return false;
    }

    @Override
    public float getWalkTargetValue(@NonNull BlockPos pos, @NonNull LevelReader level) {
        return level.getBlockState(pos.below()).is(Blocks.NETHERRACK) ? 10.0F : -level.getPathfindingCostFromLightLevels(pos);
    }

    // ==============================
    //   Synced Data Registration
    // ==============================

    static {
        DATA_FUEL_LEVEL = SynchedEntityData.defineId(GhastGirl.class, EntityDataSerializers.INT);
        DATA_IS_CHARGING = SynchedEntityData.defineId(GhastGirl.class, EntityDataSerializers.BOOLEAN);
        DATA_IS_LEASH_HOLDER = SynchedEntityData.defineId(GhastGirl.class, EntityDataSerializers.BOOLEAN);
        DATA_ON_GEYSER = SynchedEntityData.defineId(GhastGirl.class, EntityDataSerializers.BOOLEAN);
    }

    private static class GhastGirlSitOnNetherGeyserGoal extends MoveToBlockGoal {
        GhastGirl ghastGirl;

        public GhastGirlSitOnNetherGeyserGoal(GhastGirl ghastGirl, double speedModifier) {
            super(ghastGirl, speedModifier, 8);

            this.ghastGirl = ghastGirl;
            this.verticalSearchStart = 1;
            this.setFlags(EnumSet.of(Flag.JUMP, Flag.MOVE));
        }

        public boolean canUse() {
            return !this.ghastGirl.isOrderedToSit() && !this.ghastGirl.isMaxStage() && !this.ghastGirl.isVehicle() && super.canUse();
        }

        @Override
        public boolean canContinueToUse() {
            return !this.ghastGirl.isOrderedToSit() && !this.ghastGirl.isMaxStage() && !this.ghastGirl.isVehicle() && super.canContinueToUse();
        }

        @Override
        public void tick() {
            BlockPos moveToTarget = this.getMoveToTarget();
            Vec3 targetCenter = Vec3.atCenterOf(moveToTarget);

            if (!moveToTarget.closerToCenterThan(this.mob.position(), this.acceptedDistance())) {
                if (this.ghastGirl.navigation.isDone()) {
                    this.ghastGirl.moveControl.setWantedPosition(targetCenter.x(), targetCenter.y(), targetCenter.z(), 1.0D);
                }
            } else if (this.isReachedTarget() && !this.ghastGirl.isInflating()) {
                this.ghastGirl.setOnGeyser(this.isReachedTarget());

                int stage = this.ghastGirl.getStage() + 1;
                this.ghastGirl.setStage(stage);
                this.ghastGirl.playSound(INFLATION_SOUNDS[stage + 2]);
                this.ghastGirl.inflateForLength(!this.ghastGirl.isMaxStage() ? 45 : 53);
                this.ghastGirl.setFuelLevel(FUEL_PER_STAGE * stage);
            }

            super.tick();
        }

        @Override
        public double acceptedDistance() {
            return 1;
        }

        @Override
        protected boolean isValidTarget(@NonNull LevelReader level, @NonNull BlockPos pos) {
            if (!canFitAt(pos.above())) {
                return false;
            } else {
                BlockState blockState = level.getBlockState(pos);
                return blockState.is(ModBlocks.NETHER_GEYSER.block().get());
            }
        }

        public boolean canFitAt(@NonNull BlockPos wanted) {
            Vec3 travel = new Vec3(wanted.getX() - this.ghastGirl.getX(), wanted.getY() - this.ghastGirl.getY(), wanted.getZ() - this.ghastGirl.getZ());
            AABB aabb = this.ghastGirl.getBoundingBox();
            AABB aabbAtDestination = aabb.move(travel);

            for (BlockPos pos : BlockPos.betweenClosed(aabbAtDestination)) {
                if (!this.blockTraversalPossible(this.ghastGirl.level(), pos)) {
                    return false;
                }
            }

            return true;
        }

        private boolean blockTraversalPossible(@NonNull BlockGetter level, BlockPos pos) {
            BlockState state = level.getBlockState(pos);
            if (state.isAir()) {
                return true;
            } else {
                boolean pathNoCollisions = state.getCollisionShape(level, pos).isEmpty();
                if (state.is(BlockTags.HAPPY_GHAST_AVOIDS)) {
                    return false;
                } else {
                    FluidState fluidState = level.getFluidState(pos);
                    if (!fluidState.isEmpty()) {
                        if (fluidState.is(FluidTags.WATER)) {
                            return false;
                        }

                        if (fluidState.is(FluidTags.LAVA)) {
                            return false;
                        }
                    }

                    return pathNoCollisions;
                }
            }
        }
    }

    private static class GhastGirlInflateGoal extends InflateGoal {
        GhastGirl ghastGirl;

        public GhastGirlInflateGoal(GhastGirl ghastGirl) {
            super(ghastGirl);

            this.ghastGirl = ghastGirl;
        }

        @Override
        public void stop() {
            this.ghastGirl.setOnGeyser(false);
        }
    }

    private static class GhastGirlShootFireballGoal extends Goal {
        private final GhastGirl ghastGirl;
        private final float attackRadiusSqr;
        private final float stopDistanceSqr;
        private final double speedModifier;
        public int chargeTime;

        private int seeTime;
        private boolean strafingClockwise;
        private boolean strafingBackwards;
        private int strafingTime = -1;

        public GhastGirlShootFireballGoal(GhastGirl ghastGirl, float attackRadius, double speedModifier) {
            this(ghastGirl, attackRadius, 0, speedModifier);
        }

        public GhastGirlShootFireballGoal(GhastGirl ghastGirl, float attackRadius, float stopDistance, double speedModifier) {
            this.ghastGirl = ghastGirl;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.stopDistanceSqr = stopDistance * stopDistance;
            this.speedModifier = speedModifier;

            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        }

        public boolean canUse() {
            LivingEntity target = this.ghastGirl.getTarget();

            if (target == null) return false;
            else if (this.ghastGirl.getFuelLevel() <= 0) return false;
            else if (this.ghastGirl.isVehicle()) return false;
            else if (this.unableToMove()) return false;

            return this.ghastGirl.isMaxStage() || this.stopDistanceSqr == 0 || this.ghastGirl.distanceToSqr(target) > this.stopDistanceSqr;
        }

        private boolean unableToMove() {
            return this.ghastGirl.isOrderedToSit() || this.ghastGirl.isPassenger();
        }

        public void start() {
            this.chargeTime = 0;
        }

        public void stop() {
            LivingEntity target = this.ghastGirl.getTarget();
            if (target != null && !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
                this.ghastGirl.setTarget(null);
            }

            this.seeTime = 0;
            this.ghastGirl.setCharging(false);
            this.ghastGirl.navigation.stop();
        }

        public boolean requiresUpdateEveryTick() {
            return true;
        }

        public void tick() {
            LivingEntity target = this.ghastGirl.getTarget();
            if (target == null) return;

            this.ghastGirl.getLookControl().setLookAt(target, 10, this.ghastGirl.getMaxHeadXRot());

            double targetDistSqr = this.ghastGirl.distanceToSqr(target.getX(), target.getY(), target.getZ());
            boolean hasLineOfSight = this.ghastGirl.getSensing().hasLineOfSight(target);
            boolean hadLineOfSight = this.seeTime > 0;
            if (hasLineOfSight != hadLineOfSight) {
                this.seeTime = 0;
            }

            if (hasLineOfSight) {
                ++this.seeTime;
            } else {
                --this.seeTime;
            }

            if (!(targetDistSqr > (double) this.attackRadiusSqr) && this.seeTime >= 20) {
                this.ghastGirl.getNavigation().stop();
                ++this.strafingTime;
            } else {
                this.ghastGirl.getNavigation().moveTo(target, this.speedModifier);
                this.strafingTime = -1;
            }

            if (this.strafingTime >= 20) {
                if ((double) this.ghastGirl.getRandom().nextFloat() < 0.3) {
                    this.strafingClockwise = !this.strafingClockwise;
                }

                if ((double) this.ghastGirl.getRandom().nextFloat() < 0.3) {
                    this.strafingBackwards = !this.strafingBackwards;
                }

                this.strafingTime = 0;
            }

            if (this.strafingTime > -1) {
                if (targetDistSqr > (double) (this.attackRadiusSqr * 0.75F)) {
                    this.strafingBackwards = false;
                } else if (targetDistSqr < (double) (this.attackRadiusSqr * 0.25F)) {
                    this.strafingBackwards = true;
                }

                this.ghastGirl.getMoveControl().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
                Entity var7 = this.ghastGirl.getControlledVehicle();
                if (var7 instanceof Mob vehicle) {
                    vehicle.lookAt(target, 30.0F, 30.0F);
                }

                this.ghastGirl.lookAt(target, 30.0F, 30.0F);
            } else {
                this.ghastGirl.getLookControl().setLookAt(target, 30.0F, 30.0F);
            }

            double maxDist = 64.0F;
            if (target.distanceToSqr(this.ghastGirl) < maxDist * maxDist && this.ghastGirl.hasLineOfSight(target)) {
                Level level = this.ghastGirl.level();
                ++this.chargeTime;
                if (this.chargeTime == 10 && !this.ghastGirl.isSilent()) {
                    level.levelEvent(null, 1015, this.ghastGirl.blockPosition(), 0);
                }

                if (this.chargeTime == 20) {
                    this.ghastGirl.shootFireball(target, level);
                    this.chargeTime = -40;
                }
            } else if (this.chargeTime > 0) {
                --this.chargeTime;
            }

            this.ghastGirl.setCharging(this.chargeTime > 10);
        }
    }
}
