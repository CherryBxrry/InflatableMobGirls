package io.github.cherrybxrry.inflatablemobgirls.block.entity;

import io.github.cherrybxrry.inflatablemobgirls.block.NetherGeyserBlock;
import io.github.cherrybxrry.inflatablemobgirls.block.state.property.NetherGeyserState;
import io.github.cherrybxrry.inflatablemobgirls.entity.ghastgirl.GhastGirl;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.GeyserParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.levelgen.XoroshiroRandomSource;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

public class NetherGeyserBlockEntity extends BlockEntity {
    private static final Predicate<Entity> EFFECT_PREDICATE;
    public static final int PARTICLE_FREQUENCY_TICKS = 20;
    public static final int SOUND_FREQUENCY_TICKS = 40;
    private static final float GEYSER_BASE_LAUNCH_SPEED = 0.7F;
    private static final float GEYSER_LAUNCH_FORCE = 0.2F;
    public int waitingCountdown = -1;
    public long eruptionTick = -1L;
    public static Function<SoundEvent, BlockEntityTicker<NetherGeyserBlockEntity>> CLIENT_GEYSER_PLUME_TICKER;
    public static BlockEntityTicker<NetherGeyserBlockEntity> SERVER_WAITING_COUNTDOWN_TICKER;
    public static final long GEYSER_SALT = -904011478L;
    public static BlockEntityTicker<NetherGeyserBlockEntity> LAUNCH_ENTITY_TICKER;

    public NetherGeyserBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntityTypes.NETHER_GEYSER.get(), pos, state);
    }

    protected void saveAdditional(final @NonNull ValueOutput output) {
        super.saveAdditional(output);
        output.putInt("countdown", this.waitingCountdown);
    }

    protected void loadAdditional(final @NonNull ValueInput input) {
        super.loadAdditional(input);
        input.getInt("countdown").ifPresent((value) -> this.waitingCountdown = value);
    }

    public void setLevel(final @NonNull Level level) {
        super.setLevel(level);
        if (this.eruptionTick == -1L) {
            this.eruptionTick = level.getGameTime();
        }
    }

    public void resetCountdown() {
        this.waitingCountdown = -1;
    }

    public static RandomSource geyserPositional(final ServerLevel level, final BlockPos pos) {
        return (new XoroshiroRandomSource(level.getSeed() ^ GEYSER_SALT)).forkPositional().at(pos);
    }

    private static void spawnGeyserParticle(final Level level, final BlockPos sourcePos) {
        level.addParticle(new GeyserParticleOptions(ParticleTypes.GEYSER, 3), (double)sourcePos.getX() + (double)0.5F, sourcePos.getY(), (double)sourcePos.getZ() + (double)0.5F, 0.0F, 0.0F, 0.0F);
    }

    private static int getUnobstructedBlockCount(final Level level, final BlockPos pos) {
        int geyserForceHeight = 14;
        CollisionContext geyserPositionContext = CollisionContext.positionContext(pos.below().getY());

        for(int i = 0; i < geyserForceHeight; ++i) {
            BlockPos currentPos = pos.above(i);
            BlockState state = level.getBlockState(currentPos);
            if (!isGeyserPassableBlock(state, level, currentPos, geyserPositionContext)) {
                return i;
            }
        }

        return geyserForceHeight;
    }

    private static boolean isGeyserPassableBlock(final BlockState state, final Level level, final BlockPos pos, final CollisionContext context) {
        return state.isAir() || state.is(Blocks.WATER) || state.getCollisionShape(level, pos, context).isEmpty();
    }

    private static @Nullable BlockPos findNoxiousGasSourceBlock(final Level level, final BlockPos origin) {
        int maxY = origin.getY() + 4 + 1;
        CollisionContext geyserPositionContext = CollisionContext.positionContext(origin.getY());
        BlockPos.MutableBlockPos pos = origin.above(1).mutable();

        while(true) {
            if (pos.getY() <= maxY) {
                BlockState state = level.getBlockState(pos);
                boolean isWaterLogged = level.getFluidState(pos).isSourceOfType(Fluids.WATER);
                if (isWaterLogged && (state.is(Blocks.WATER) || isGeyserPassableBlock(state, level, pos, geyserPositionContext))) {
                    pos.move(Direction.UP);
                    continue;
                }

                if (state.isAir() || isGeyserPassableBlock(state, level, pos, geyserPositionContext)) {
                    return pos.immutable();
                }
            }

            return null;
        }
    }

    static {
        EFFECT_PREDICATE = EntitySelector.NO_SPECTATORS.and(EntitySelector.ENTITY_STILL_ALIVE);
        CLIENT_GEYSER_PLUME_TICKER = (sound) -> (level, pos, _, entity) -> {
            BlockPos sourceBlock = findNoxiousGasSourceBlock(level, pos);
            if (sourceBlock != null) {
                long eruptionTime = level.getGameTime() - entity.eruptionTick;
                if (eruptionTime % PARTICLE_FREQUENCY_TICKS == 0L) {
                    spawnGeyserParticle(level, sourceBlock);
                }

                if (eruptionTime % SOUND_FREQUENCY_TICKS == 0L) {
                    level.playLocalSound((double)sourceBlock.getX() + (double)0.5F, (double)sourceBlock.getY() + (double)0.5F, (double)sourceBlock.getZ() + (double)0.5F, sound, SoundSource.BLOCKS, 1.0F, 1.0F, false);
                }

            }
        };
        SERVER_WAITING_COUNTDOWN_TICKER = (level, pos, state, entity) -> {
            if (level.getGameTime() % PARTICLE_FREQUENCY_TICKS == 0L) {
                BlockPos sourceBlock = findNoxiousGasSourceBlock(level, pos);
                if (sourceBlock != null) {
                    if (entity.waitingCountdown <= 0) {
                        int waterBlocks = sourceBlock.getY() - pos.getY() - 1;
                        RandomSource geyserPositional = geyserPositional((ServerLevel)level, pos);
                        if (state.getValue(NetherGeyserBlock.STATE) == NetherGeyserState.DORMANT) {
                            entity.waitingCountdown = 10 * (waterBlocks - 1) + geyserPositional.nextIntBetweenInclusive(15, 30);
                        } else {
                            geyserPositional.nextInt();
                            entity.waitingCountdown = waterBlocks - 1 + geyserPositional.nextIntBetweenInclusive(1, 2);
                        }
                    }

                    if (entity.waitingCountdown > 0) {
                        --entity.waitingCountdown;
                    }

                    if (entity.waitingCountdown == 0) {
                        NetherGeyserState stateToSet = state.getValue(NetherGeyserBlock.STATE) == NetherGeyserState.DORMANT ? NetherGeyserState.ERUPTING : NetherGeyserState.DORMANT;
                        level.setBlock(pos, state.setValue(NetherGeyserBlock.STATE, stateToSet), 3);
                        if (stateToSet == NetherGeyserState.DORMANT) {
                            level.gameEvent(GameEvent.BLOCK_DEACTIVATE, pos, GameEvent.Context.of(state));
                        }
                    }

                }
            }
        };
        LAUNCH_ENTITY_TICKER = (level, pos, _, _) -> {
            BlockPos sourceBlock = findNoxiousGasSourceBlock(level, pos);
            if (sourceBlock != null) {
                int geyserForceHeight = getUnobstructedBlockCount(level, pos.above());
                AABB aabb = (new AABB(pos.above())).expandTowards(0.0F, geyserForceHeight - 1, 0.0F);

                for(Entity entityToBeLaunched : level.getEntitiesOfClass(Entity.class, aabb, EFFECT_PREDICATE)) {
                    Vec3 entityVelocity = entityToBeLaunched.getDeltaMovement();
                    entityToBeLaunched.checkFallDistanceAccumulation();
                    if (entityToBeLaunched.canSimulateMovement()) {
                        if (entityToBeLaunched instanceof Player player) {
                            if (player.getAbilities().flying) {
                                continue;
                            }
                        }

                        if (entityToBeLaunched instanceof GhastGirl ghastGirl) {
                            if (ghastGirl.onGeyser()) {
                                continue;
                            }
                        }

                        if (!entityToBeLaunched.isPassenger() && !entityToBeLaunched.is(EntityTypeTags.NOT_AFFECTED_BY_GEYSERS) && entityVelocity.y < (double)GEYSER_BASE_LAUNCH_SPEED) {
                            entityToBeLaunched.addDeltaMovement(new Vec3(0.0F, GEYSER_LAUNCH_FORCE, 0.0F));
                            entityToBeLaunched.needsSync = true;
                        }
                    }
                }

            }
        };
    }
}
