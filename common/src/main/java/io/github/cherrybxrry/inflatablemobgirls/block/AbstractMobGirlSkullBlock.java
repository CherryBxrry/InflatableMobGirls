package io.github.cherrybxrry.inflatablemobgirls.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.MobGirlSkullBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.BlockPos;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.redstone.Orientation;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public abstract class AbstractMobGirlSkullBlock extends BaseEntityBlock {
    public static final BooleanProperty POWERED;
    private final Type type;

    protected AbstractMobGirlSkullBlock(Type type, Properties properties) {
        super(properties);
        this.type = type;
        this.registerDefaultState(this.stateDefinition.any().setValue(POWERED, false));
    }

    protected abstract @NonNull MapCodec<? extends AbstractMobGirlSkullBlock> codec();

    public BlockEntity newBlockEntity(@NonNull BlockPos worldPosition, @NonNull BlockState blockState) {
        return new MobGirlSkullBlockEntity(worldPosition, blockState);
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, @NonNull BlockState blockState, @NonNull BlockEntityType<T> type) {
        if (level.isClientSide()) {
            boolean isAnimated = blockState.is(ModBlocks.CREEPER_GIRL_HEAD.get()) || blockState.is(ModBlocks.CREEPER_GIRL_WALL_HEAD.get());
            if (isAnimated) {
                return createTickerHelper(type, ModBlockEntityTypes.MOB_GIRL_SKULL.get(), MobGirlSkullBlockEntity::animation);
            }
        }

        return null;
    }

    public Type getType() {
        return this.type;
    }

    protected boolean isPathfindable(@NonNull BlockState state, @NonNull PathComputationType type) {
        return false;
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POWERED, context.getLevel().hasNeighborSignal(context.getClickedPos()));
    }

    protected void neighborChanged(@NonNull BlockState state, Level level, @NonNull BlockPos pos, @NonNull Block block, @Nullable Orientation orientation, boolean movedByPiston) {
        if (!level.isClientSide()) {
            boolean signal = level.hasNeighborSignal(pos);
            if (signal != state.getValue(POWERED)) {
                level.setBlock(pos, state.setValue(POWERED, signal), 2);
            }
        }

    }

    static {
        POWERED = BlockStateProperties.POWERED;
    }

    public interface Type extends StringRepresentable {
        Map<String, Type> TYPES = new Object2ObjectArrayMap<>();
        Codec<Type> CODEC = Codec.stringResolver(StringRepresentable::getSerializedName, TYPES::get);
    }

    public enum Types implements Type {
        CREEPER_GIRL("creeper_girl");

        private final String name;

        Types(String name) {
            this.name = name;
            TYPES.put(name, this);
        }

        public @NonNull String getSerializedName() {
            return this.name;
        }
    }
}
