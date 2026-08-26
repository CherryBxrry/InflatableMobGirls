package io.github.cherrybxrry.inflatablemobgirls.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class CreeperGirlSkullBlock extends MobGirlSkullBlock {
    public static final MapCodec<CreeperGirlSkullBlock> CODEC = simpleCodec(CreeperGirlSkullBlock::new);
    private static final VoxelShape SHAPE = Block.column(10.0F, 0.0F, 8.0F);

    @Override
    protected @NonNull MapCodec<? extends AbstractMobGirlSkullBlock> codec() {
        return CODEC;
    }

    public CreeperGirlSkullBlock(Properties properties) {
        super(Types.CREEPER_GIRL, properties);
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }
}
