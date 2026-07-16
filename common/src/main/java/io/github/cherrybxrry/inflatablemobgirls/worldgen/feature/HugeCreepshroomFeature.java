package io.github.cherrybxrry.inflatablemobgirls.worldgen.feature;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.AbstractHugeMushroomFeature;
import net.minecraft.world.level.levelgen.feature.configurations.HugeMushroomFeatureConfiguration;
import org.jspecify.annotations.NonNull;

public class HugeCreepshroomFeature extends AbstractHugeMushroomFeature {
    public HugeCreepshroomFeature(Codec<HugeMushroomFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    protected int getTreeRadiusForHeight(int i, int height, int foliageRadius, int y) {
        int radius = 0;
        if (y < height && y >= height - 3) {
            radius = foliageRadius;
        } else if (y == height) {
            radius = foliageRadius;
        }

        return radius;
    }

    @Override
    protected void makeCap(@NonNull WorldGenLevel level, @NonNull RandomSource random, @NonNull BlockPos pos, int treeHeight, BlockPos.@NonNull MutableBlockPos mutablePos, @NonNull HugeMushroomFeatureConfiguration config) {
        treeHeight = treeHeight + 4;
        for(int posY = treeHeight - 6; posY <= treeHeight; ++posY) {
            int j = posY < treeHeight ? config.foliageRadius() : config.foliageRadius() - 1;

            for(int posX = -j; posX <= j; ++posX) {
                for(int posZ = -j; posZ <= j; ++posZ) {
                    boolean noCorners = !((posX == j || posX == -j) && (posZ == posX || posZ == -posX));
                    boolean ring = (posX == -j || posX == j) || (posZ == -j || posZ == j);
                    boolean alternating = (posX == -j || posX == j || posZ == -j || posZ == j) && (posX + posZ) % 2 == 0;
                    boolean centers = (posX == 0 && (Math.abs(posZ) == j)) || (posZ == 0 && (Math.abs(posX) == j));
                    boolean segment1 = posY <= treeHeight - 1 && posY >= treeHeight - 2 && noCorners;
                    boolean segment2 = posY <= treeHeight - 3 && posY >= treeHeight - 4 && ring;
                    boolean segment3 = posY == treeHeight - 5 && alternating;
                    boolean segment4 = posY == treeHeight - 6 && centers;
                    boolean shape = segment1 || segment2 || segment3 || segment4;
                    if (posY >= treeHeight || shape) {
                        mutablePos.setWithOffset(pos, posX, posY, posZ);
                        if (!level.getBlockState(mutablePos).isSolidRender()) {
                            BlockState blockstate = config.capProvider().getState(level, random, pos);
                            this.setBlock(level, mutablePos, blockstate);
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void placeTrunk(@NonNull WorldGenLevel level, @NonNull RandomSource random, @NonNull BlockPos origin, @NonNull HugeMushroomFeatureConfiguration config, int treeHeight, BlockPos.@NonNull MutableBlockPos currentPos) {
        super.placeTrunk(level, random, origin, config, treeHeight, currentPos);
        for(int y = 0; y < treeHeight + 2; ++y) {
            currentPos.set(origin).move(Direction.UP, y);
            if (!level.getBlockState(currentPos).isSolidRender()) {
                if (y <= treeHeight) {
                    this.setBlock(level, currentPos, config.stemProvider().getState(level, random, origin));
                } else {
                    for (int x = -1; x <=1; ++x) {
                        for (int z = -1; z <=1; ++z) {
                            currentPos.setWithOffset(origin, x, y, z);
                            this.setBlock(level, currentPos, config.stemProvider().getState(level, random, currentPos));
                        }
                    }
                }
            }
        }
    }
}
