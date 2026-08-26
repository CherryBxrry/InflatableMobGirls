package io.github.cherrybxrry.inflatablemobgirls.block;

import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.NetherGeyserBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.block.state.property.NetherGeyserState;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class NetherGeyserBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<NetherGeyserBlock> CODEC = simpleCodec(NetherGeyserBlock::new);
    public static final EnumProperty<NetherGeyserState> STATE;
    
    private static final VoxelShape SHAPE_NORTH;
    private static final VoxelShape SHAPE_SOUTH;
    private static final VoxelShape SHAPE_EAST;
    private static final VoxelShape SHAPE_WEST;
    public static final BooleanProperty WATERLOGGED;
    public static final EnumProperty<Direction> FACING;

    public NetherGeyserBlock(Properties properties) {
        super(properties);
        
        this.registerDefaultState(this.getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(STATE, NetherGeyserState.DORMANT).setValue(WATERLOGGED, false));
    }

    @Override
    @NotNull
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        VoxelShape hitbox;
        switch (state.getValue(FACING)) {
            case SOUTH -> hitbox = SHAPE_SOUTH;
            case EAST -> hitbox = SHAPE_EAST;
            case WEST -> hitbox = SHAPE_WEST;
            default -> hitbox = SHAPE_NORTH;
        }
        return hitbox;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        LevelAccessor levelaccessor = context.getLevel();
        BlockPos blockpos = context.getClickedPos();
        boolean flag = levelaccessor.getFluidState(blockpos).getType() == Fluids.WATER;
        return validBlockState(this.defaultBlockState(), levelaccessor, blockpos).setValue(FACING, context.getHorizontalDirection().getOpposite()).setValue(WATERLOGGED, flag);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        if (state.getValue(WATERLOGGED)) {
            ticks.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
        }

        return directionToNeighbour == Direction.DOWN ? validBlockState(state, level, pos) : super.updateShape(state, level, ticks, pos, directionToNeighbour, neighbourPos, neighbourState, random);
    }

    private static BlockState validBlockState(BlockState state, LevelReader level, BlockPos pos) {
        if (level.getBlockState(pos.below()).is(BlockTags.CAUSES_PERIODIC_GEYSER_ERUPTIONS)) {
            return state.setValue(STATE, NetherGeyserState.CONTINUOUS);
        } else {
            boolean isGeyser = state.getValue(STATE) == NetherGeyserState.ERUPTING || state.getValue(STATE) == NetherGeyserState.DORMANT;
            if (!isGeyser) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity instanceof NetherGeyserBlockEntity netherGeyserEntity) {
                    netherGeyserEntity.resetCountdown();
                }
            }

            return state.getValue(STATE) == NetherGeyserState.ERUPTING ? state : state.setValue(STATE, NetherGeyserState.DORMANT);
        }
    }

    @Override
    public void onPlace(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState oldState, boolean movedByPiston) {
        super.onPlace(state, level, pos, oldState, movedByPiston);

        if (state.getValue(STATE) == NetherGeyserState.ERUPTING || state.getValue(STATE) == NetherGeyserState.CONTINUOUS) {
            level.blockEvent(pos, this, 0, 0);
            level.playSound(null, pos, state.getValue(STATE) == NetherGeyserState.CONTINUOUS ? SoundEvents.GEYSER_CONTINUOUS_START : SoundEvents.GEYSER_ERUPTION_START, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(GameEvent.BLOCK_ACTIVATE, pos, GameEvent.Context.of(state));
        }
    }

    public boolean placeLiquid(@NonNull LevelAccessor level, @NonNull BlockPos pos, BlockState state, @NonNull FluidState fluidState) {
        if (!(Boolean)state.getValue(BlockStateProperties.WATERLOGGED) && fluidState.is(Fluids.WATER)) {
            level.setBlock(pos, state.setValue(WATERLOGGED, true), 3);
            level.scheduleTick(pos, fluidState.getType(), fluidState.getType().getTickDelay(level));
            return true;
        } else {
            return false;
        }
    }

    @NotNull
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    protected boolean triggerEvent(@NonNull BlockState state, Level level, @NonNull BlockPos pos, int b0, int b1) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof NetherGeyserBlockEntity entity) {
            entity.eruptionTick = level.getGameTime();
        }

        return true;
    }

    public void animateTick(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull RandomSource random) {
        if (random.nextInt(5) == 0) {
            for (int i = 0; i < random.nextInt(1) + 1; ++i) {
                level.addParticle(ParticleTypes.LAVA, (double) pos.getX() + 1, (double) pos.getY() + 1D, (double) pos.getZ() + 0.5D, random.nextFloat() / 2.0F, 5.0E-5D, random.nextFloat() / 2.0F);
            }
        }
    }

    @Override
    @NotNull
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    @Override
    @NotNull
    public BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING).add(WATERLOGGED).add(STATE);
    }

    public static VoxelShape makeShape(){
        VoxelShape shape = Shapes.empty();
        shape = Shapes.join(shape, Shapes.box(0.21875, 0.0625, 0.59375, 0.34375, 0.1875, 0.71875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.5625, 0.375, 0.0625, 0.75), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.6875, 0, 0.0625, 0.9375, 0.125, 0.3125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.71875, 0.125, 0.09375, 0.90625, 0.25, 0.28125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.09375, 0.1875, 0.15625, 0.34375, 0.375, 0.40625), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.1875, 0.3125, 0.6875, 0.375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.0625, 0, 0.125, 0.375, 0.1875, 0.4375), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.53125, 0.75, 0.53125, 0.78125, 1.0625, 0.78125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.4375, 0, 0.4375, 0.875, 0.25, 0.875), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.5, 0.5, 0.5, 0.8125, 0.75, 0.8125), BooleanOp.OR);
        shape = Shapes.join(shape, Shapes.box(0.46875, 0.25, 0.46875, 0.84375, 0.5, 0.84375), BooleanOp.OR);

        return shape;
    }

    public static VoxelShape rotateShape(Direction to, VoxelShape shape) {
        VoxelShape[] buffer = new VoxelShape[]{shape, Shapes.empty()};

        int times = (to.ordinal() - Direction.NORTH.get2DDataValue() + 4) % 4;
        for (int i = 0; i < times; i++) {
            buffer[0].forAllBoxes((minX, minY, minZ, maxX, maxY, maxZ) -> buffer[1] = Shapes.or(buffer[1],
                    Shapes.create(1 - maxZ, minY, minX, 1 - minZ, maxY, maxX)));
            buffer[0] = buffer[1];
            buffer[1] = Shapes.empty();
        }

        return buffer[0];
    }

    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
        return true;
    }

    @Override
    @NotNull
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    protected @NonNull MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(@NonNull BlockPos blockPos, @NonNull BlockState blockState) {
        return new NetherGeyserBlockEntity(blockPos, blockState);
    }

    @Override
    public <T extends BlockEntity> @Nullable BlockEntityTicker<T> getTicker(Level level, BlockState blockState, @NonNull BlockEntityType<T> type) {
        boolean client = level.isClientSide();
        BlockEntityType<NetherGeyserBlockEntity> blockEntityType = ModBlockEntityTypes.NETHER_GEYSER.get();
        BlockEntityTicker<? super NetherGeyserBlockEntity> ticker;
        switch (blockState.getValue(STATE)) {
            case DORMANT -> ticker = client ? null : NetherGeyserBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER;
            case ERUPTING -> ticker = client ? (NetherGeyserBlockEntity.CLIENT_GEYSER_PLUME_TICKER.apply(SoundEvents.GEYSER_ERUPTION_ACTIVE)).andThen(NetherGeyserBlockEntity.LAUNCH_ENTITY_TICKER) : NetherGeyserBlockEntity.LAUNCH_ENTITY_TICKER.andThen(NetherGeyserBlockEntity.SERVER_WAITING_COUNTDOWN_TICKER);
            case CONTINUOUS -> ticker = client ? (NetherGeyserBlockEntity.CLIENT_GEYSER_PLUME_TICKER.apply(SoundEvents.GEYSER_CONTINUOUS_ACTIVE)).andThen(NetherGeyserBlockEntity.LAUNCH_ENTITY_TICKER) : NetherGeyserBlockEntity.LAUNCH_ENTITY_TICKER;
            default -> throw new MatchException(null, null);
        }

        return createTickerHelper(type, blockEntityType, ticker);
    }

    static {
        STATE = EnumProperty.create("nether_geyser_state", NetherGeyserState.class);
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        FACING = BlockStateProperties.HORIZONTAL_FACING;
        SHAPE_NORTH = makeShape();
        SHAPE_SOUTH = rotateShape(Direction.WEST, SHAPE_NORTH);
        SHAPE_EAST = rotateShape(Direction.SOUTH, SHAPE_NORTH);
        SHAPE_WEST = rotateShape(Direction.EAST, SHAPE_NORTH);
    }
}
