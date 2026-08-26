package io.github.cherrybxrry.inflatablemobgirls.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.util.HoverRandomPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathType;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.EnumSet;

public abstract class InflatableFlyingMobGirl extends InflatableMobGirl {

    public InflatableFlyingMobGirl(EntityType<? extends InflatableMobGirl> entityType, Level level) {
        super(entityType, level);

        this.moveControl = new FlyingMoveControl<>(this, 10, false);
    }

    @Override
    protected @NonNull PathNavigation createNavigation(@NonNull Level level) {
        PathNavigation pathNavigation = new FlyingPathNavigation(this, this.level());
        pathNavigation.setCanOpenDoors(false);
        pathNavigation.setCanFloat(true);
        return pathNavigation;
    }

    private void adultSetup() {
        if (this.level() instanceof ServerLevel) {
            this.removeAllGoals(_ -> true);
            this.registerGoals();
        }
    }

    private void babySetup() {
        if (this.level() instanceof ServerLevel) {
            this.removeAllGoals(_ -> true);
        }
    }

    protected void ageBoundaryReached() {
        if (this.isBaby()) {
            this.babySetup();
        } else {
            this.adultSetup();
        }

        super.ageBoundaryReached();
    }

    @Override
    public void tick() {
        super.tick();

        if (this.canFly() && this.navigation.canNavigateGround()) {
            this.setFlyingMovement();
        } else if (!this.canFly() && !this.navigation.canNavigateGround()) {
            this.setGroundMovement();
        }
    }

    @Override
    protected void checkFallDamage(double ya, boolean onGround, @NonNull BlockState onState, @NonNull BlockPos pos) {

    }

    protected void setFlyingMovement() {
        this.moveControl = new FlyingMoveControl<>(this, 10, false);
        this.navigation = new FlyingPathNavigation(this, this.level());
        this.navigation.setCanOpenDoors(false);
        this.navigation.setCanFloat(true);
    }

    protected void setGroundMovement() {
        this.setNoGravity(false);
        this.moveControl = new MoveControl<>(this);
        this.navigation = new GroundPathNavigation(this, this.level());
        this.navigation.setCanOpenDoors(false);
        this.navigation.setCanFloat(true);
    }

    public boolean isFlying() {
        return !this.onGround() && this.canFly();
    }

    public boolean canFly() {
        return true;
    }

    @Override
    protected boolean canFlyToOwner() {
        return true;
    }

    public void travel(@NonNull Vec3 input) {
        if (this.isFlying()) {
            float speed = (float) this.getAttributeValue(Attributes.FLYING_SPEED) * 5.0F / 3.0F;
            this.travelFlying(input, speed, speed, speed);
        } else {
            super.travel(input);
        }
    }

    protected boolean shouldStayCloseToLeashHolder() {
        return false;
    }

    @Override
    protected void customServerAiStep(@NonNull ServerLevel level) {
        this.checkRestriction();

        super.customServerAiStep(level);
    }

    protected abstract int getRestrictionRadius();

    private void checkRestriction() {
        if (!this.isLeashed() && !this.isVehicle()) {
            int radius = this.getRestrictionRadius();
            if (!this.hasHome() || !this.getHomePosition().closerThan(this.blockPosition(), (radius + 16)) || radius != this.getHomeRadius()) {
                this.setHomeTo(this.blockPosition(), radius);
            }
        }

    }

    public void onElasticLeashPull() {
        super.onElasticLeashPull();
        this.getMoveControl().setWait();
    }

    protected static class HybridFollowOwnerGoal extends Goal {
        private final InflatableFlyingMobGirl flyingMobGirl;
        private @Nullable LivingEntity owner;
        private final double speedModifier;
        private PathNavigation navigation;
        private int timeToRecalcPath;
        private final float stopDistance;
        private final float startDistance;
        private float oldWaterCost;

        public HybridFollowOwnerGoal(InflatableFlyingMobGirl flyingMobGirl, double speedModifier, float startDistance, float stopDistance) {
            this.flyingMobGirl = flyingMobGirl;
            this.speedModifier = speedModifier;
            this.navigation = flyingMobGirl.getNavigation();
            this.startDistance = startDistance;
            this.stopDistance = stopDistance;
            this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
            if (!(flyingMobGirl.getNavigation() instanceof GroundPathNavigation) && !(flyingMobGirl.getNavigation() instanceof FlyingPathNavigation)) {
                throw new IllegalArgumentException("Unsupported mob type for HybridFollowOwnerGoal");
            }
        }

        public boolean canUse() {
            LivingEntity owner = this.flyingMobGirl.getOwner();
            if (owner == null) {
                return false;
            } else if (this.flyingMobGirl.unableToMoveToOwner()) {
                return false;
            } else if (this.flyingMobGirl.distanceToSqr(owner) < (double) (this.startDistance * this.startDistance)) {
                return false;
            } else {
                this.owner = owner;
                return true;
            }
        }

        public boolean canContinueToUse() {
            if (this.navigation.isDone()) {
                return false;
            } else {
                return !this.flyingMobGirl.unableToMoveToOwner() && !(this.owner != null && this.flyingMobGirl.distanceToSqr(this.owner) <= (double) (this.stopDistance * this.stopDistance));
            }
        }

        public void start() {
            this.timeToRecalcPath = 0;
            this.oldWaterCost = this.flyingMobGirl.getPathfindingMalus(PathType.WATER);
            this.flyingMobGirl.setPathfindingMalus(PathType.WATER, 0.0F);
        }

        public void stop() {
            this.owner = null;
            this.navigation.stop();
            this.flyingMobGirl.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
        }

        public void tick() {
            if (this.owner == null) return;

            if (this.navigation != this.flyingMobGirl.getNavigation()) {
                this.navigation.stop();
                this.navigation = this.flyingMobGirl.getNavigation();
            }

            boolean isOwnerFarAway = this.flyingMobGirl.shouldTryTeleportToOwner();
            if (!isOwnerFarAway) {
                this.flyingMobGirl.getLookControl().setLookAt(this.owner, 10.0F, (float) this.flyingMobGirl.getMaxHeadXRot());
            }

            if (--this.timeToRecalcPath <= 0) {
                this.timeToRecalcPath = this.adjustedTickDelay(10);
                if (isOwnerFarAway) {
                    this.flyingMobGirl.tryToTeleportToOwner();
                } else {
                    this.navigation.moveTo(this.owner, this.speedModifier);
                }
            }
        }
    }

    protected static class WaterAvoidingHybridWanderGoal extends WaterAvoidingRandomStrollGoal {
        public WaterAvoidingHybridWanderGoal(PathfinderMob mob, double speedModifier) {
            super(mob, speedModifier);
        }

        protected @Nullable Vec3 getPosition() {
            if (this.mob instanceof InflatableFlyingMobGirl flyingGirl && flyingGirl.isFlying()) {
                Vec3 wanderDirection = this.mob.getViewVector(0.0F);
                int xzDist = 8;
                Vec3 groundBasedPosition = HoverRandomPos.getPos(this.mob, xzDist, 7, wanderDirection.x, wanderDirection.z, ((float) Math.PI / 2F), 3, 1);
                return groundBasedPosition != null ? groundBasedPosition : AirAndWaterRandomPos.getPos(this.mob, 8, 4, -2, wanderDirection.x, wanderDirection.z, (double) ((float) Math.PI / 2F));
            } else {
                return super.getPosition();
            }
        }
    }
}
