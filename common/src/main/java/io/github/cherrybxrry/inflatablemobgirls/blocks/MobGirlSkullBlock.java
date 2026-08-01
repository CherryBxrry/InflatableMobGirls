package io.github.cherrybxrry.inflatablemobgirls.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class MobGirlSkullBlock extends AbstractMobGirlSkullBlock {
    public static final MapCodec<MobGirlSkullBlock> CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(Type.CODEC.fieldOf("kind").forGetter(AbstractMobGirlSkullBlock::getType), propertiesCodec()).apply(i, MobGirlSkullBlock::new));
    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    private static final int ROTATIONS;
    public static final IntegerProperty ROTATION;
    private static final VoxelShape SHAPE;

    @Override
    protected @NonNull MapCodec<? extends AbstractMobGirlSkullBlock> codec() {
        return CODEC;
    }

    public MobGirlSkullBlock(Type type, Properties properties) {
        super(type, properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ROTATION, 0));
    }

    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.getStateForPlacement(context).setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()));
    }

    protected @NonNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), ROTATIONS));
    }

    protected @NonNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), ROTATIONS));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(ROTATION);
    }

    static {
        ROTATIONS = MAX + 1;
        ROTATION = BlockStateProperties.ROTATION_16;
        SHAPE = Block.column(8.0F, 0.0F, 8.0F);
    }
}
