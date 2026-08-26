package io.github.cherrybxrry.inflatablemobgirls.block;


import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MushroomBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jspecify.annotations.NonNull;

public class CreepshroomBlock extends MushroomBlock {
    private static final VoxelShape SHAPE = Block.column(6.0F, 0.0F, 12.0F);

    public CreepshroomBlock(ResourceKey<ConfiguredFeature<?, ?>> feature, Properties properties) {
        super(feature, properties);
    }

    @Override
    protected @NonNull VoxelShape getShape(@NonNull BlockState state, @NonNull BlockGetter level, @NonNull BlockPos pos, @NonNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected boolean canSurvive(@NonNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState below = level.getBlockState(belowPos);
        return below.is(BlockTags.OVERRIDES_MUSHROOM_LIGHT_REQUIREMENT) || this.mayPlaceOn(below, level, belowPos);
    }
}
