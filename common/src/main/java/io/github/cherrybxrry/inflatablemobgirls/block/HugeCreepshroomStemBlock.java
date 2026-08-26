package io.github.cherrybxrry.inflatablemobgirls.block;

import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class HugeCreepshroomStemBlock extends RotatedPillarBlock {
    public static final BooleanProperty UP;
    public static final BooleanProperty DOWN;
    public static final BooleanProperty LEFT;
    public static final BooleanProperty RIGHT;

    private static final Map<Direction.Axis, Map<Direction, BooleanProperty>> DIRECTION_TO_PROPERTY;

    public HugeCreepshroomStemBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(this.defaultBlockState()
                .setValue(AXIS, Direction.Axis.Y)
                .setValue(UP, false)
                .setValue(DOWN, false)
                .setValue(LEFT, false)
                .setValue(RIGHT, false)
        );
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AXIS, UP, DOWN, LEFT, RIGHT);
    }

    @Override
    protected @NonNull BlockState updateShape(@NonNull BlockState state, @NonNull LevelReader level, @NonNull ScheduledTickAccess ticks, @NonNull BlockPos pos, @NonNull Direction directionToNeighbour, @NonNull BlockPos neighbourPos, @NonNull BlockState neighbourState, @NonNull RandomSource random) {
        // Checks if the neighbour is a stem block that is on the same axis
        if (directionToNeighbour.getAxis() == state.getValue(AXIS)) return state;

        boolean isAlignedStem = neighbourState.is(ModBlocks.HUGE_CREEPSHROOM_STEM.block().get())
                && neighbourState.getValue(AXIS) == state.getValue(AXIS);

        Map<Direction, BooleanProperty> map = DIRECTION_TO_PROPERTY.get(state.getValue(AXIS));
        if (map == null) return state;

        BooleanProperty prop = map.get(directionToNeighbour);
        if (prop == null) return state;

        return state.setValue(prop, isAlignedStem);
    }

    static {
        UP = BooleanProperty.create("up");
        DOWN = BooleanProperty.create("down");
        LEFT = BooleanProperty.create("left");
        RIGHT = BooleanProperty.create("right");

        DIRECTION_TO_PROPERTY = Map.of(
                Direction.Axis.X, Map.of(
                        Direction.UP, UP,
                        Direction.DOWN, DOWN,
                        Direction.SOUTH, RIGHT,
                        Direction.NORTH, LEFT
                ),
                Direction.Axis.Y, Map.of(
                        Direction.UP, DOWN,
                        Direction.DOWN, UP,
                        Direction.SOUTH, UP,
                        Direction.NORTH, DOWN,
                        Direction.WEST, RIGHT,
                        Direction.EAST, LEFT
                ),
                Direction.Axis.Z, Map.of(
                        Direction.UP, UP,
                        Direction.DOWN, DOWN,
                        Direction.EAST, RIGHT,
                        Direction.WEST, LEFT
                )
        );
    }
}
