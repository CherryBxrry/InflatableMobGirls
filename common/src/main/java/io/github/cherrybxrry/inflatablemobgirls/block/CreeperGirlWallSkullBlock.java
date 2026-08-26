package io.github.cherrybxrry.inflatablemobgirls.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

import java.util.Map;

public class CreeperGirlWallSkullBlock extends MobGirlWallSkullBlock {
    public static final MapCodec<CreeperGirlWallSkullBlock> CODEC = simpleCodec(CreeperGirlWallSkullBlock::new);
    private static final Map<Direction, VoxelShape> SHAPES = Shapes.rotateHorizontal(Block.boxZ(10.0F, 8.0F, 8.0F, 16.0F));

    @Override
    protected @NonNull MapCodec<? extends AbstractMobGirlSkullBlock> codec() {
        return CODEC;
    }

    public CreeperGirlWallSkullBlock(Properties properties) {
        super(Types.CREEPER_GIRL, properties);
    }

    protected @NonNull VoxelShape getShape(BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }
}
