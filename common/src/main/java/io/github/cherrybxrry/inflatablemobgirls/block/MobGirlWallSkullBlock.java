package io.github.cherrybxrry.inflatablemobgirls.block;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class MobGirlWallSkullBlock extends AbstractMobGirlSkullBlock {
    public static final MapCodec<MobGirlWallSkullBlock> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(Type.CODEC.fieldOf("kind").forGetter(AbstractMobGirlSkullBlock::getType), propertiesCodec()).apply(i, MobGirlWallSkullBlock::new));
    public static final EnumProperty<Direction> FACING;
    private static final Map<Direction, VoxelShape> SHAPES;

    @Override
    protected @NonNull MapCodec<? extends AbstractMobGirlSkullBlock> codec() {
        return CODEC;
    }

    protected MobGirlWallSkullBlock(Type type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockState state = super.getStateForPlacement(context);
        BlockGetter level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        Direction[] directions = context.getNearestLookingDirections();

        for(Direction direction : directions) {
            if (direction.getAxis().isHorizontal()) {
                Direction facing = direction.getOpposite();
                state = state.setValue(FACING, facing);
                if (!level.getBlockState(pos.relative(direction)).canBeReplaced(context)) {
                    return state;
                }
            }
        }

        return null;
    }

    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(FACING);
    }

    static {
        FACING = HorizontalDirectionalBlock.FACING;
        SHAPES = Shapes.rotateHorizontal(Block.boxZ(8.0F, 8.0F, 16.0F));
    }
}
