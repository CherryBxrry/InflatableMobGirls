package io.github.cherrybxrry.inflatablemobgirls.blocks;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.blocks.entity.CreepSporeBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirl;
import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirlVariant;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import io.github.cherrybxrry.inflatablemobgirls.init.ModRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.variant.SpawnContext;
import net.minecraft.world.entity.variant.VariantUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gamerules.GameRules;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;

public class CreepSporeBlock extends BaseEntityBlock implements BonemealableBlock {
    public static final MapCodec<CreepSporeBlock> CODEC = simpleCodec(CreepSporeBlock::new);
    public static final int MAX_AGE = 4;
    public static final IntegerProperty AGE;
    private static final VoxelShape[] SHAPES;
    public static final EnumProperty<Direction> FACING;

    public @NonNull MapCodec<CreepSporeBlock> codec() {
        return CODEC;
    }

    public CreepSporeBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(this.getAgeProperty(), 0));
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES[this.getAge(state)];
    }

    protected @NonNull BlockState updateShape(BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        return !state.canSurvive(level, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    protected boolean mayPlaceOn(@NonNull BlockState state, BlockGetter level, BlockPos pos) {
        return state.is(BlockTags.SUPPORTS_CROPS);
    }

    protected @NonNull IntegerProperty getAgeProperty() {
        return AGE;
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getAge(BlockState state) {
        return state.getValue(this.getAgeProperty());
    }

    public BlockState getStateForAge(int age, BlockState state) {
        return state.setValue(this.getAgeProperty(), age);
    }

    public final boolean isMaxAge(BlockState state) {
        return this.getAge(state) >= this.getMaxAge();
    }

    protected boolean isRandomlyTicking(@NonNull BlockState state) {
        return !this.isMaxAge(state);
    }

    protected void randomTick(@NonNull BlockState state, ServerLevel level, @NonNull BlockPos pos, @NonNull RandomSource random) {
        if (level.getRawBrightness(pos, 0) >= 9) {
            int age = this.getAge(state);
            if (age < this.getMaxAge()) {
                float growthSpeed = getGrowthSpeed(this, level, pos);
                if (random.nextInt((int) (25.0F / growthSpeed) + 1) == 0) {
                    level.setBlock(pos, this.getStateForAge(age + 1, state), 2);
                }
            }
        }

    }

    public void growCrops(Level level, BlockPos pos, BlockState state) {
        int age = Math.min(this.getMaxAge(), this.getAge(state) + this.getBonemealAgeIncrease(level));

        if (age == this.getMaxAge() && !level.isClientSide()) {
            spawnEntity(level, pos, state);
            return;
        }

        level.setBlock(pos, this.getStateForAge(age, state), 2);
    }

    public void spawnEntity(Level level, BlockPos pos, BlockState state) {
        UUID ownerUUID;
        Holder<CreeperGirlVariant> variant;

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity == null) return;

        if (blockEntity instanceof CreepSporeBlockEntity creepSpore) {
            ownerUUID = creepSpore.getOwnerUUID();
            variant = creepSpore.getVariant();
        } else {
            ownerUUID = null;
            variant = null;
        }

        CreeperGirl creeperGirl = ModEntityTypes.CREEPER_GIRL.get().create(level, EntitySpawnReason.BREEDING);
        if (creeperGirl != null) {
            Vec3 spawnAt = Vec3.atCenterOf(pos);
            creeperGirl.setBaby(true);

            if (ownerUUID != null) {
                Player player = level.getPlayerByUUID(ownerUUID);
                if (player != null) {
                    creeperGirl.tame(player);
                    creeperGirl.level().broadcastEntityEvent(creeperGirl, EntityEvent.TAMING_SUCCEEDED);
                }
            }

            if (variant != null) creeperGirl.setVariant(variant);

            creeperGirl.snapTo(spawnAt.x(), spawnAt.y() -0.5, spawnAt.z(), Mth.wrapDegrees(state.getValue(FACING).toYRot()), 0.0F);
            creeperGirl.setYBodyRot(Mth.wrapDegrees(state.getValue(FACING).toYRot()));
            creeperGirl.setYHeadRot(Mth.wrapDegrees(state.getValue(FACING).toYRot()));

            level.addFreshEntity(creeperGirl);
        }

        level.destroyBlock(pos, false, creeperGirl);
    }

    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.getRandom(), 2, 5) / 3;
    }

    protected static float getGrowthSpeed(Block type, BlockGetter level, BlockPos pos) {
        float speed = 1.0F;
        BlockPos below = pos.below();

        for (int xx = -1; xx <= 1; ++xx) {
            for (int zz = -1; zz <= 1; ++zz) {
                float blockSpeed = 0.0F;
                BlockState blockState = level.getBlockState(below.offset(xx, 0, zz));
                if (blockState.is(BlockTags.GROWS_CROPS)) {
                    blockSpeed = 1.0F;
                    if (blockState.getValueOrElse(FarmlandBlock.MOISTURE, 0) > 0) {
                        blockSpeed = 3.0F;
                    }
                }

                if (xx != 0 || zz != 0) {
                    blockSpeed /= 4.0F;
                }

                speed += blockSpeed;
            }
        }

        BlockPos north = pos.north();
        BlockPos south = pos.south();
        BlockPos west = pos.west();
        BlockPos east = pos.east();
        boolean horizontal = level.getBlockState(west).is(type) || level.getBlockState(east).is(type);
        boolean vertical = level.getBlockState(north).is(type) || level.getBlockState(south).is(type);
        if (horizontal && vertical) {
            speed /= 2.0F;
        } else {
            boolean diagonal = level.getBlockState(west.north()).is(type) || level.getBlockState(east.north()).is(type) || level.getBlockState(east.south()).is(type) || level.getBlockState(west.south()).is(type);
            if (diagonal) {
                speed /= 2.0F;
            }
        }

        return speed;
    }

    protected boolean canSurvive(@NonNull BlockState state, @NonNull LevelReader level, @NonNull BlockPos pos) {
        BlockPos below = pos.below();
        return hasSufficientLight(level, pos) && this.mayPlaceOn(level.getBlockState(below), level, below);
    }

    protected static boolean hasSufficientLight(LevelReader level, BlockPos pos) {
        return level.getRawBrightness(pos, 0) >= 8;
    }

    protected boolean propagatesSkylightDown(BlockState state) {
        return state.getFluidState().isEmpty();
    }

    protected void entityInside(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull Entity entity, @NonNull InsideBlockEffectApplier effectApplier, boolean isPrecise) {
        if (level instanceof ServerLevel serverLevel) {
            if (entity instanceof Ravager && serverLevel.getGameRules().get(GameRules.MOB_GRIEFING)) {
                serverLevel.destroyBlock(pos, true, entity);
            }
        }

        super.entityInside(state, level, pos, entity, effectApplier, isPrecise);
    }

    protected @NonNull ItemLike getBaseSeedId() {
        return ModItems.CREEPSPORE.get();
    }

    protected @NonNull ItemStack getCloneItemStack(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state, boolean includeData) {
        return new ItemStack(this.getBaseSeedId());
    }

    public boolean isValidBonemealTarget(@NonNull LevelReader level, @NonNull BlockPos pos, @NonNull BlockState state) {
        return !this.isMaxAge(state);
    }

    public boolean isBonemealSuccess(@NonNull Level level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        return true;
    }

    public void performBonemeal(@NonNull ServerLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, @NonNull BlockState state) {
        this.growCrops(level, pos, state);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE, FACING);
    }

    @Override
    protected void onPlace(@NonNull BlockState state, @NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (level instanceof ServerLevelAccessor accessor && !movedByPiston && blockEntity instanceof CreepSporeBlockEntity creepSpore) {
            Optional<? extends Holder<CreeperGirlVariant>> selectedVariant = VariantUtils.selectVariantToSpawn(
                    SpawnContext.create(accessor, pos), ModRegistries.CREEPER_GIRL_VARIANT
            );
            selectedVariant.ifPresent(creepSpore::setVariant);
        }
    }

    @Override
    public void setPlacedBy(@NonNull Level level, @NonNull BlockPos pos, @NonNull BlockState state, @Nullable LivingEntity by, @NonNull ItemStack itemStack) {
        if (by != null && !level.isClientSide()) {
            UUID uuid = by.getUUID();
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof CreepSporeBlockEntity creepSpore) {
                creepSpore.setOwnerUUID(uuid);
            }
        }
    }

    @Override
    public @Nullable BlockState getStateForPlacement(@NonNull BlockPlaceContext context) {
        Direction direction = context.getHorizontalDirection().getOpposite();
        return this.defaultBlockState().setValue(FACING, direction);
    }

    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
        return type == PathComputationType.AIR && !this.hasCollision || super.isPathfindable(state, type);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(@NonNull Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        boolean client = level.isClientSide();
        BlockEntityType<CreepSporeBlockEntity> blockEntityType = ModBlockEntityTypes.CREEPSPORE_CROP.get();
        BlockEntityTicker<? super CreepSporeBlockEntity> ticker = CreepSporeBlockEntity.ANIMATION_TICKER;
        return createTickerHelper(type, blockEntityType, ticker);
    }

    @Override
    protected @NonNull RenderShape getRenderShape(@NonNull BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new CreepSporeBlockEntity(blockPos, blockState);
    }

    static {
        AGE = BlockStateProperties.AGE_5;
        SHAPES = new VoxelShape[]{
                Block.box(6.0F, 0.0F, 6.0F, 10.0F, 3.0F, 10.0F),
                Block.box(4.0F, 0.0F, 4.0F, 12.0F, 9.0F, 12.0F),
                Block.box(4.0F, 0.0F, 4.0F, 12.0F, 12.0F, 12.0F),
                Block.box(4.0F, 0.0F, 4.0F, 12.0F, 14.0F, 12.0F)
        };
        FACING = HorizontalDirectionalBlock.FACING;
    }
}
