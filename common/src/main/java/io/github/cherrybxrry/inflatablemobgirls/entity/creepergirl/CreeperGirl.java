package io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.entity.InflatableMobGirl;
import io.github.cherrybxrry.inflatablemobgirls.entity.ai.goal.MeleeAttackAnimatedGoal;
import io.github.cherrybxrry.inflatablemobgirls.init.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.*;
import net.minecraft.world.entity.animal.feline.Cat;
import net.minecraft.world.entity.animal.feline.Ocelot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class CreeperGirl extends InflatableMobGirl {
    // ==============================
    // Synched data identifiers
    // ==============================
    private static final EntityDataAccessor<Boolean> DATA_CHARGED;
    private static final EntityDataAccessor<Boolean> DATA_REGENERATING;
    private static final EntityDataAccessor<Holder<CreeperGirlVariant>> DATA_VARIANT_ID;

    private static final String TAG_EXPLOSION_RADIUS = "ExplosionRadius";
    private static final String TAG_CHARGED = "Charged";
    private static final String TAG_REGENERATING = "Regenerating";

    // ==============================
    // Constants and configuration
    // ==============================
    // Taming
    private static final float START_HEALTH = 20.0F;
    private static final float TAME_HEALTH = 40.0F;
    private static final float START_DAMAGE = 1.0F;
    private static final float TAME_DAMAGE = 2.0F;

    // Adult hitbox scales
    public static final float STAND_HEIGHT = 1.7F;
    public static final float SIT_HEIGHT = 1.4F;
    public static final float REGEN0_HEIGHT = 0.325F;
    public static final float STAND_REGEN1_HEIGHT = 1.1F;
    public static final float SIT_REGEN1_HEIGHT = 0.8F;
    public static final float STAND_REGEN2_HEIGHT = 1.5F;
    public static final float SIT_REGEN2_HEIGHT = 1.2F;
    public static final float EYE_OFFSET = 0.36F;
    public static final float WIDTH = 0.6F;
    public static final float LEASH_OFFSET = 0.24F;

    // Baby hitbox scales
    public static final float BABY_STAND_HEIGHT = 0.95F;
    public static final float BABY_SIT_HEIGHT = 0.8F;
    public static final float BABY_EYE_OFFSET = 0.25F;
    public static final float BABY_WIDTH = 0.5F;
    public static final float BABY_LEASH_OFFSET = 0.16F;

    // Attack animation lengths (ticks)
    public static final int ATTACK_LENGTH = 10;
    public static final int ATTACK_DELAY = 5;

    // Explosion defaults
    private static final boolean DEFAULT_REGENERATING = false;
    private static final boolean DEFAULT_CHARGED = false;
    private static final byte DEFAULT_EXPLOSION_RADIUS = 3;
    private static final byte DEFAULT_EXPLOSION_RADIUS_BABY = 2;

    // Speed modifier for babies
    private static final Identifier SPEED_MODIFIER_BABY_ID = Constants.id("baby");
    private static final AttributeModifier SPEED_MODIFIER_BABY;

    private static final int MAX_STAGE = 3;
    private final static int REGEN_BIT = 1 << 9;
    private final static int CHARGED_BIT = 1 << 10;

    // ==============================
    // Instance fields
    // ==============================
    private int explosionRadius = this.isBaby() ? DEFAULT_EXPLOSION_RADIUS_BABY : DEFAULT_EXPLOSION_RADIUS;
    private boolean droppedSkulls;

    // Animations
    public final AnimationState idle0Animation = new AnimationState();
    public final AnimationState idle1Animation = new AnimationState();
    public final AnimationState idle2Animation = new AnimationState();
    public final AnimationState idleRegen0Animation = new AnimationState();
    public final AnimationState idleRegen1Animation = new AnimationState();
    public final AnimationState idleRegen2Animation = new AnimationState();

    public final AnimationState idleCharged0Animation = new AnimationState();
    public final AnimationState idleCharged1Animation = new AnimationState();
    public final AnimationState idleCharged2Animation = new AnimationState();

    public final AnimationState sit0Animation = new AnimationState();
    public final AnimationState sit1Animation = new AnimationState();
    public final AnimationState sit2Animation = new AnimationState();

    public final AnimationState sitCharged0Animation = new AnimationState();
    public final AnimationState sitCharged1Animation = new AnimationState();
    public final AnimationState sitCharged2Animation = new AnimationState();

    public final AnimationState inflate1Animation = new AnimationState();
    public final AnimationState inflate2Animation = new AnimationState();
    public final AnimationState inflate3Animation = new AnimationState();
    public final AnimationState regen1Animation = new AnimationState();
    public final AnimationState regen2Animation = new AnimationState();
    public final AnimationState regen3Animation = new AnimationState();

    public final AnimationState inflateCharged1Animation = new AnimationState();
    public final AnimationState inflateCharged2Animation = new AnimationState();
    public final AnimationState inflateCharged3Animation = new AnimationState();

    public final AnimationState attackAnimation = new AnimationState();

    // Animation lists for easier management
    public final List<AnimationState> idleAnimations = List.of(
            this.idle0Animation,
            this.idle1Animation,
            this.idle2Animation,
            this.idleRegen2Animation,
            this.idleRegen1Animation,
            this.idleRegen0Animation,
            this.idleCharged0Animation,
            this.idleCharged1Animation,
            this.idleCharged2Animation
    );

    public final List<AnimationState> sittingAnimations = List.of(
            this.sit0Animation,
            this.sit1Animation,
            this.sit2Animation,
            this.sitCharged0Animation,
            this.sitCharged1Animation,
            this.sitCharged2Animation
    );

    public final List<AnimationState> inflateAnimations = List.of(
            this.inflate1Animation,
            this.inflate2Animation,
            this.inflate3Animation,
            this.regen3Animation,
            this.regen2Animation,
            this.regen1Animation,
            this.inflateCharged1Animation,
            this.inflateCharged2Animation,
            this.inflateCharged3Animation
    );

    /**
     * Primary constructor.
     *
     * @param entityType entity type
     * @param level      world level
     */
    public CreeperGirl(EntityType<CreeperGirl> entityType, Level level) {
        super(entityType, level);

        this.setTame(false, false);
        this.setPathfindingMalus(PathType.POWDER_SNOW, -1.0F);
        this.setPathfindingMalus(PathType.ON_TOP_OF_POWDER_SNOW, -1.0F);
    }

    /**
     * Builds the attribute supplier for CreeperGirl.
     *
     * @return attribute builder
     */
    public static AttributeSupplier.Builder createAttributes() {
        return TamableAnimal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, START_HEALTH)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.TEMPT_RANGE, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25F)
                .add(Attributes.ATTACK_KNOCKBACK, 0.5f)
                .add(Attributes.ATTACK_SPEED, 1.0F)
                .add(Attributes.ATTACK_DAMAGE, START_DAMAGE);
    }

    /**
     * Registers AI goals and target selectors.
     */
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(0, new CreeperGirlPanicGoal(1.5D, DamageTypeTags.PANIC_ENVIRONMENTAL_CAUSES));
        this.goalSelector.addGoal(1, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(1, new InflateGoal(this));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Ocelot.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(2, new AvoidEntityGoal<>(this, Cat.class, 6.0F, 1.0D, 1.2D));
        this.goalSelector.addGoal(3, new MeleeAttackAnimatedGoal(this, 1.2D, true));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(5, new TemptGoal(this, 1.2D, this::isFood, false));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0F));
        this.goalSelector.addGoal(7, new CreeperGirlLookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new CreeperGirlRandomLookAroundGoal(this));

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
        entityData.define(DATA_CHARGED, DEFAULT_CHARGED);
        entityData.define(DATA_REGENERATING, DEFAULT_REGENERATING);
        entityData.define(DATA_VARIANT_ID, VariantUtils.getDefaultOrAny(this.registryAccess(), CreeperGirlVariants.DEFAULT));
    }

    @Override
    public void onSyncedDataUpdated(@NotNull EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == DATA_REGENERATING) {
            this.refreshDimensions();
        }
    }

    /**
     * Saves custom NBT data.
     */
    @Override
    protected void addAdditionalSaveData(@NonNull ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean(TAG_CHARGED, this.isCharged());
        output.putByte(TAG_EXPLOSION_RADIUS, (byte) this.explosionRadius);
        output.putBoolean(TAG_REGENERATING, this.isRegenerating());
        VariantUtils.writeVariant(output, this.getVariant());
    }

    /**
     * Loads custom NBT data.
     */
    @Override
    protected void readAdditionalSaveData(@NonNull ValueInput input) {
        super.readAdditionalSaveData(input);
        this.setCharged(input.getBooleanOr(TAG_CHARGED, this.isCharged()));
        this.explosionRadius = input.getByteOr(TAG_EXPLOSION_RADIUS, this.isBaby() ? DEFAULT_EXPLOSION_RADIUS_BABY : DEFAULT_EXPLOSION_RADIUS);
        this.setRegenerating(input.getBooleanOr(TAG_REGENERATING, this.isRegenerating()));

        VariantUtils.readVariant(input, ModRegistries.CREEPER_GIRL_VARIANT).ifPresent(this::setVariant);
    }

    @Override
    protected void applyImplicitComponents(@NonNull DataComponentGetter components) {
        this.applyImplicitComponentIfPresent(components, ModDataComponents.CREEPER_GIRL_VARIANT.get());
        super.applyImplicitComponents(components);
    }

    @Override
    protected <T> boolean applyImplicitComponent(@NonNull DataComponentType<T> type, @NonNull T value) {
        if (type == ModDataComponents.CREEPER_GIRL_VARIANT) {
            this.setVariant(castComponentValue(ModDataComponents.CREEPER_GIRL_VARIANT.get(), value));
            return true;
        } else {
            return super.applyImplicitComponent(type, value);
        }
    }

    // ==============================
    //       Variant Handling
    // ==============================

    public Identifier getTexture() {
        CreeperGirlVariant variant = this.getVariant().value();
        CreeperGirlVariant.AssetInfo assetInfo = this.isBaby() ? variant.babyInfo() : variant.adultInfo();

        if (this.isCharged()) {
            return assetInfo.charged().texturePath();
        } else {
            return assetInfo.normal().texturePath();
        }
    }

    public Holder<CreeperGirlVariant> getVariant() {
        return this.entityData.get(DATA_VARIANT_ID);
    }

    public void setVariant(Holder<CreeperGirlVariant> variant) {
        this.entityData.set(DATA_VARIANT_ID, variant);
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
        return SoundEvents.CREEPER_HURT;
    }

    /**
     * Sound when killed.
     */
    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    /**
     * Ambient sounds.
     */
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isRegenerating()) return null;

        if (this.isBaby()) {
            return 0 < this.getStage() && this.getStage() < 3 ? ModSounds.CREEPER_GIRL_BABY_BELLY_AMBIENCE.get() : null;
        }

        return switch (this.getStage()) {
            case 1 -> ModSounds.CREEPER_GIRL_BELLY_AMBIENCE1.get();
            case 2 -> ModSounds.CREEPER_GIRL_BELLY_AMBIENCE2.get();
            default -> null;
        };
    }

    @Override
    public float getVoicePitch() {
        return (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F;
    }

    /**
     * Defines the food for the entity to follow and use to heal.
     */
    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItemTags.CREEPER_GIRL_FOOD);
    }

    /**
     * Defines the item for the entity to be tamed by.
     */
    @Override
    protected boolean isTameItem(ItemStack itemStack) {
        return itemStack.is(ModItemTags.TAMES_CREEPER_GIRL);
    }

    @Override
    protected boolean isInflateItem(ItemStack itemStack) {
        return itemStack.is(ModItemTags.INFLATES_CREEPER_GIRL);
    }

    /**
     * Handles player interaction.
     */
    public @NotNull InteractionResult mobInteract(@NotNull Player player, @NotNull InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if (itemStack.getItem() instanceof BlockItem blockItem) {
            BlockState block = blockItem.getBlock().defaultBlockState();

            if (block.is(BlockTags.LIGHTNING_RODS) && !this.level().isClientSide()) {
                this.setCharged(false);
                return InteractionResult.SUCCESS;
            }
        }

        if ((this.getStage() == 3 && !this.isFood(itemStack)) || this.isInflating()) {
            return InteractionResult.PASS;
        }

        return super.mobInteract(player, hand);
    }

    /**
     * Adjusts stats when tamed.
     */
    @Override
    protected void applyTamingSideEffects() {
        if (this.isTame()) {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(TAME_HEALTH);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(TAME_DAMAGE);
            this.setHealth(TAME_HEALTH);
        } else {
            Objects.requireNonNull(this.getAttribute(Attributes.MAX_HEALTH)).setBaseValue(START_HEALTH);
            Objects.requireNonNull(this.getAttribute(Attributes.ATTACK_DAMAGE)).setBaseValue(START_DAMAGE);
        }
    }

    @Override
    public void setAge(int newAge) {
        super.setAge(newAge);

        if (!this.level().isClientSide()) {
            AttributeInstance speed = this.getAttribute(Attributes.MOVEMENT_SPEED);
            if (speed != null) {
                speed.removeModifier(SPEED_MODIFIER_BABY_ID);
                if (this.isBaby()) {
                    speed.addTransientModifier(SPEED_MODIFIER_BABY);
                }
            }
        }
    }

    @Override
    protected void ageBoundaryReached() {
        super.ageBoundaryReached();

        this.explosionRadius = DEFAULT_EXPLOSION_RADIUS;
    }

    @Override
    public AgeableMob getBreedOffspring(@NonNull ServerLevel serverLevel, @NonNull AgeableMob ageableMob) {
        return new CreeperGirl(ModEntityTypes.CREEPER_GIRL.get(), serverLevel);
    }

    /**
     * Adjusts leash placement.
     */
    public @NotNull Vec3 getLeashOffset() {
        return new Vec3(0.0F, (this.getEyeHeight() - (this.isBaby() ? BABY_LEASH_OFFSET : LEASH_OFFSET)), 0);
    }

    @Override
    public int getMaxHeadXRot() {
        return this.isRegenerating() ? 5 : super.getMaxHeadXRot();
    }

    @Override
    public int getMaxHeadYRot() {
        return this.isRegenerating() ? 5 : super.getMaxHeadYRot();
    }

    @Override
    protected @NonNull EntityDimensions getStandingDimensions() {
        EntityDimensions adultDimensions;
        EntityDimensions babyDimensions = EntityDimensions.scalable(BABY_WIDTH, BABY_STAND_HEIGHT).withEyeHeight(BABY_STAND_HEIGHT - BABY_EYE_OFFSET);

        if (this.isRegenerating() && !this.isBaby()) {
            int stage = this.getStage();

            adultDimensions = switch (stage) {
                case 3 -> EntityDimensions.scalable(WIDTH, REGEN0_HEIGHT).withEyeHeight(REGEN0_HEIGHT);
                case 2 ->
                        EntityDimensions.scalable(WIDTH, STAND_REGEN1_HEIGHT).withEyeHeight(STAND_REGEN1_HEIGHT - EYE_OFFSET);
                case 1 ->
                        EntityDimensions.scalable(WIDTH, STAND_REGEN2_HEIGHT).withEyeHeight(STAND_REGEN2_HEIGHT - EYE_OFFSET);
                default -> EntityDimensions.scalable(WIDTH, STAND_HEIGHT).withEyeHeight(STAND_HEIGHT - EYE_OFFSET);
            };
        } else {
            adultDimensions = EntityDimensions.scalable(WIDTH, STAND_HEIGHT).withEyeHeight(STAND_HEIGHT - EYE_OFFSET);
        }

        return this.isBaby() ? babyDimensions : adultDimensions;
    }

    @Override
    protected @NonNull EntityDimensions getSittingDimensions() {
        EntityDimensions adultDimensions;
        EntityDimensions babyDimensions = EntityDimensions.scalable(BABY_WIDTH, BABY_SIT_HEIGHT).withEyeHeight(BABY_SIT_HEIGHT - BABY_EYE_OFFSET);

        if (this.isRegenerating() && !this.isBaby()) {
            int stage = this.getStage();

            adultDimensions = switch (stage) {
                case 3 -> EntityDimensions.scalable(WIDTH, REGEN0_HEIGHT).withEyeHeight(REGEN0_HEIGHT);
                case 2 ->
                        EntityDimensions.scalable(WIDTH, SIT_REGEN1_HEIGHT).withEyeHeight(SIT_REGEN1_HEIGHT - EYE_OFFSET);
                case 1 ->
                        EntityDimensions.scalable(WIDTH, SIT_REGEN2_HEIGHT).withEyeHeight(SIT_REGEN2_HEIGHT - EYE_OFFSET);
                default -> EntityDimensions.scalable(WIDTH, SIT_HEIGHT).withEyeHeight(SIT_HEIGHT - EYE_OFFSET);
            };
        } else {
            adultDimensions = EntityDimensions.scalable(WIDTH, SIT_HEIGHT).withEyeHeight(SIT_HEIGHT - EYE_OFFSET);
        }

        return this.isBaby() ? babyDimensions : adultDimensions;
    }

    // ==============================
    //  State helpers
    // ==============================

    public boolean shouldExplode() {
        return this.getInflateTime() + 1L > this.getInflateLength() && this.getStage() == 3 && !this.isRegenerating();
    }

    public boolean shouldStopRegenerating() {
        return this.getInflateTime() + 1L > this.getInflateLength() && this.getStage() == 0 && this.isRegenerating();
    }

    public void setCharged(boolean charged) {
        this.entityData.set(DATA_CHARGED, charged);
    }

    public boolean isCharged() {
        return this.entityData.get(DATA_CHARGED);
    }

    public void setRegenerating(boolean regen) {
        this.entityData.set(DATA_REGENERATING, regen);
    }

    public boolean isRegenerating() {
        return this.entityData.get(DATA_REGENERATING);
    }

    @Override
    public int getMaxStage() {
        return MAX_STAGE;
    }

    @Override
    public boolean isOrderedToSit() {
        return super.isOrderedToSit() || this.getStage() == 3;
    }

    @Override
    public void aiStep() {
        if (this.level().isClientSide() && this.level().getRandom().nextInt(2) == 0 && this.isCharged()) {
            this.level().addParticle(ModParticleTypes.CHARGED_SPARK.get(), this.getRandomX(0.75F), this.getY(this.getRandom().nextFloat()), this.getRandomZ(0.75F), 0.0F, -0.01F, 0.0F);
        }

        super.aiStep();
    }

    /**
     * Called every tick. Handles inflating, deflating, and regenerating.
     */
    @Override
    public void tick() {
        if (this.isAlive() && !this.level().isClientSide()) {
            if (this.shouldExplode()) {
                this.setOrderedToSit(true);
                this.jumping = false;
                this.navigation.stop();
                this.setTarget(null);
                this.setRegenerating(true);
                this.explodeCreeperGirl();
            }

            if (!this.isInflating()) {
                double health = this.getHealth();
                double max = this.getMaxHealth();
                int stage = this.getStage();
                double inflateThreshold = max * Math.max(0.25, 0.75 - (0.25 * stage));
                double deflateThreshold = max * Math.min(1, 1 - 0.25 * stage);

                if (!this.isRegenerating()) {
                    if (health <= inflateThreshold) {
                        this.setStage(stage + 1);
                        this.playSound(this.isBaby() ? (this.isMaxStage() ? ModSounds.CREEPER_GIRL_BABY_POPPING.get() : ModSounds.CREEPER_GIRL_BABY_INFLATE.get()) : (this.isMaxStage() ? (this.isCharged() ? ModSounds.CREEPER_GIRL_POPPING_CHARGED.get() : ModSounds.CREEPER_GIRL_POPPING.get()) : ModSounds.CREEPER_GIRL_INFLATE.get()));
                        this.inflateForLength(this.isBaby() ? (this.isMaxStage() ? 46 : 16) : this.isMaxStage() ? (this.isCharged() ? 52 : 46) : 36);
                    } else if (health > deflateThreshold) {
                        this.setStage(stage - 1);
                        this.playSound(this.isBaby() ? ModSounds.CREEPER_GIRL_BABY_DEFLATE.get() : ModSounds.CREEPER_GIRL_DEFLATE.get());
                    }
                } else {
                    if (this.shouldStopRegenerating()) this.setRegenerating(false);

                    if (health > deflateThreshold) {
                        this.setStage(stage - 1);
                        this.inflateForLength(stage == 3 ? 30 : 25);
                    }
                }
            }
        }

        super.tick();
    }

    /**
     * Sets up animation states each tick based on current stage, inflation, regen, charged, and sitting flags.
     */
    @Override
    protected void setupAnimationStates() {
        int stage = this.getStage();
        boolean inflating = this.isVisuallyInflating();
        boolean regenerating = this.isRegenerating();
        boolean charged = this.isCharged();
        boolean sitting = this.isInSittingPose();

        // Inflation Animations
        if (inflating) {
            this.stopAllFromExcept(this.idleAnimations);

            int inflateIndex = Math.clamp(
                    stage + (regenerating ? 3 : charged ? 5 : -1),
                    0,
                    this.inflateAnimations.size() - 1
            );

            AnimationState inflateAnimation = this.inflateAnimations.get(inflateIndex);

            this.stopAllFromExcept(this.inflateAnimations, inflateAnimation);
            inflateAnimation.startIfStopped(this.tickCount);
        } else {
            this.stopAllFromExcept(this.inflateAnimations);

            int idleIndex = Math.clamp(
                    stage + (regenerating ? 2 : charged ? 6 : 0),
                    0,
                    this.inflateAnimations.size() - 1
            );

            AnimationState idleAnimation = this.idleAnimations.get(idleIndex);

            this.stopAllFromExcept(this.idleAnimations, idleAnimation);
            idleAnimation.startIfStopped(this.tickCount);
        }

        if (sitting && stage != 3) {
            AnimationState sittingAnimation;
            if (!regenerating) {
                int sittingIndex = Math.clamp(
                        stage + (charged ? 3 : 0),
                        0,
                        this.inflateAnimations.size() - 1
                );

                sittingAnimation = this.sittingAnimations.get(sittingIndex);
            } else {
                sittingAnimation = this.sittingAnimations.getFirst();
            }

            this.stopAllFromExcept(this.sittingAnimations, sittingAnimation);
            sittingAnimation.startIfStopped(this.tickCount);
        } else {
            this.stopAllFromExcept(this.sittingAnimations);
        }

        this.performAttackAnimation();
    }

    @Override
    protected @NonNull AnimationState getAttackAnimation() {
        return this.attackAnimation;
    }

    @Override
    protected int getAttackLength() {
        return ATTACK_LENGTH;
    }

    @Override
    public int getAttackDelay() {
        return ATTACK_DELAY;
    }

    /**
     * Charges entity when hit by thunder.
     */
    public void thunderHit(@NotNull ServerLevel level, @NotNull LightningBolt lightning) {
        super.thunderHit(level, lightning);
        this.setCharged(true);
    }

    /**
     * Makes entity explode.
     */
    private void explodeCreeperGirl() {
        if (this.level() instanceof ServerLevel level) {
            float explosionMultiplier = this.isCharged() ? 2.0F : 1.0F;
            level.explode(this, this.getX(), this.getY(), this.getZ(), this.explosionRadius * explosionMultiplier, Level.ExplosionInteraction.NONE);
            this.spawnLingeringCloud();
            if (!this.isTame() || this.isBaby()) {
                if (this.isTame()) {
                    LivingEntity owner = this.getOwner();
                    String name = this.getDisplayName().getString();
                    if (owner instanceof ServerPlayer serverPlayer) {
                        serverPlayer.sendSystemMessage(Component.literal(name + " popped!"));
                    }
                }

                this.dead = true;
                this.triggerOnDeathMobEffects(level, RemovalReason.KILLED);
                this.discard();
            } else if (this.isTame() && !this.isBaby()) {
                if (this.dropFromGiftLootTable(level, ModLootTables.EXPLODE_CREEPER_GIRL, this::spawnAtLocation)) {
                    this.gameEvent(GameEvent.ENTITY_PLACE);
                }
            }
        }
    }

    private void spawnLingeringCloud() {
        Collection<MobEffectInstance> activeEffects = this.getActiveEffects();
        if (!activeEffects.isEmpty() || this.getVariant().is(CreeperGirlVariants.SNOWY)) {
            AreaEffectCloud cloud = new AreaEffectCloud(this.level(), this.getX(), this.getY(), this.getZ());
            cloud.setRadius(2.5F);
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10);
            cloud.setDuration(300);
            cloud.setPotionDurationScale(0.25F);
            cloud.setRadiusPerTick(-cloud.getRadius() / cloud.getDuration());

            if (!activeEffects.isEmpty()) {
                for (MobEffectInstance mobEffect : activeEffects) {
                    cloud.addEffect(new MobEffectInstance(mobEffect));
                }
            } else {
                cloud.addEffect(new MobEffectInstance(MobEffects.SLOWNESS, 600));
            }

            this.level().addFreshEntity(cloud);
        }
    }

    /**
     * Logic for when entity is hurt.
     */
    @Override
    public boolean hurtServer(@NonNull ServerLevel level, @NonNull DamageSource source, float damage) {
        if (this.isInflating()) return false;
        boolean hurt = super.hurtServer(level, source, damage);

        if (this.isMaxStage()) {
            this.setOrderedToSit(true);
            this.jumping = false;
            this.navigation.stop();
            this.setTarget(null);
        }

        return hurt;
    }

    @Override
    public boolean killedEntity(@NonNull ServerLevel level, @NonNull LivingEntity entity, @NonNull DamageSource source) {
        if (this.shouldDropLoot(level) && this.isCharged() && !this.droppedSkulls) {
            entity.dropFromLootTable(level, source, false, BuiltInLootTables.CHARGED_CREEPER, itemStack -> {
                entity.spawnAtLocation(level, itemStack);
                this.droppedSkulls = true;
            });
        }

        return super.killedEntity(level, entity, source);
    }

    /**
     * Called when the entity is spawned naturally.
     * Assigns variant based on spawn biome.
     */
    @Override
    public SpawnGroupData finalizeSpawn(@NonNull ServerLevelAccessor level, @NonNull DifficultyInstance difficulty, @NonNull EntitySpawnReason spawnReason, SpawnGroupData groupData) {
        Optional<? extends Holder<CreeperGirlVariant>> selectedVariant = VariantUtils.selectVariantToSpawn(
                SpawnContext.create(level, this.blockPosition()), ModRegistries.CREEPER_GIRL_VARIANT
        );
        selectedVariant.ifPresent(this::setVariant);
        return super.finalizeSpawn(level, difficulty, spawnReason, groupData);
    }

    @Override
    protected int packEntityData() {
        int packed = super.packEntityData();

        if (this.isRegenerating()) packed |= REGEN_BIT;
        if (this.isCharged()) packed |= CHARGED_BIT;

        return packed;
    }

    @Override
    protected void applyPackedData(int packed) {
        final int REGEN_BIT = 1 << 9;
        final int CHARGED_BIT = 1 << 10;

        this.setRegenerating((packed & REGEN_BIT) != 0);
        this.setCharged((packed & CHARGED_BIT) != 0);

        super.applyPackedData(packed);
    }

    // ==============================
    //   Synced Data Registration
    // ==============================

    static {
        DATA_CHARGED = SynchedEntityData.defineId(CreeperGirl.class, EntityDataSerializers.BOOLEAN);
        DATA_REGENERATING = SynchedEntityData.defineId(CreeperGirl.class, EntityDataSerializers.BOOLEAN);
        DATA_VARIANT_ID = SynchedEntityData.defineId(CreeperGirl.class, ModEntityDataSerializers.CREEPER_GIRL_VARIANT.get());

        SPEED_MODIFIER_BABY = new AttributeModifier(SPEED_MODIFIER_BABY_ID, 0.5F, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    /**
     * Custom panic behaviour for CreeperGirl.
     */
    class CreeperGirlPanicGoal extends TamableAnimalPanicGoal {
        public CreeperGirlPanicGoal(double speedModifier, TagKey<DamageType> panicCausingDamageTypes) {
            super(speedModifier, panicCausingDamageTypes);
        }

        protected boolean shouldPanic() {
            if (((CreeperGirl) this.mob).getStage() == 3) return false;
            return super.shouldPanic();
        }
    }

    static class CreeperGirlLookAtPlayerGoal extends LookAtPlayerGoal {
        private final CreeperGirl creeperGirl;

        public CreeperGirlLookAtPlayerGoal(CreeperGirl creeperGirl, Class<Player> lookAtType, float lookDistance) {
            super(creeperGirl, lookAtType, lookDistance);
            this.creeperGirl = creeperGirl;
        }

        @Override
        public boolean canContinueToUse() {
            return super.canContinueToUse() && !(this.creeperGirl.getStage() == 3);
        }

        @Override
        public boolean canUse() {
            return super.canUse() && !(this.creeperGirl.getStage() == 3);
        }
    }

    static class CreeperGirlRandomLookAroundGoal extends RandomLookAroundGoal {
        private final CreeperGirl creeperGirl;

        public CreeperGirlRandomLookAroundGoal(CreeperGirl creeperGirl) {
            super(creeperGirl);
            this.creeperGirl = creeperGirl;
        }

        public boolean canContinueToUse() {
            return super.canContinueToUse() && !(this.creeperGirl.getStage() == 3);
        }

        public boolean canUse() {
            return super.canUse() && !(this.creeperGirl.getStage() == 3);
        }
    }
}
