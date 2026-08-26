package io.github.cherrybxrry.inflatablemobgirls.entity.ai.goal;

import io.github.cherrybxrry.inflatablemobgirls.entity.InflatableMobGirl;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.pathfinder.Path;
import org.jspecify.annotations.NonNull;

import java.util.EnumSet;

public class MeleeAttackAnimatedGoal extends Goal {
    protected static final long COOLDOWN_BETWEEN_CAN_USE_CHECKS = 20L;
    protected final InflatableMobGirl inflatableMobGirl;
    protected final double speedModifier;
    protected final boolean followingTargetEvenIfNotSeen;
    protected long lastCanUseCheck;
    private Path path;
    private double pathedTargetX;
    private double pathedTargetY;
    private double pathedTargetZ;
    private int ticksUntilNextPathRecalculation;
    private int ticksUntilNextAttack;
    private final int attackInterval = 20;
    private final int animationDelay;
    private int animationTicks;

    public MeleeAttackAnimatedGoal(InflatableMobGirl inflatableMobGirl, double speedModifier, boolean followingTargetEvenIfNotSeen) {
        this.inflatableMobGirl = inflatableMobGirl;
        this.speedModifier = speedModifier;
        this.followingTargetEvenIfNotSeen = followingTargetEvenIfNotSeen;
        this.animationDelay = inflatableMobGirl.getAttackDelay();

        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    public boolean canUse() {
        if (this.isImmobile()) return false;

        long time = this.inflatableMobGirl.level().getGameTime();
        if (time - this.lastCanUseCheck < COOLDOWN_BETWEEN_CAN_USE_CHECKS) {
            return false;
        } else {
            this.lastCanUseCheck = time;
            LivingEntity target = this.inflatableMobGirl.getTarget();
            if (target == null) {
                return false;
            } else if (!target.isAlive()) {
                return false;
            } else {
                this.path = this.inflatableMobGirl.getNavigation().createPath(target, 0);
                return this.path != null || this.inflatableMobGirl.isWithinMeleeAttackRange(target);
            }
        }
    }

    public boolean canContinueToUse() {
        if (this.inflatableMobGirl.isInflating()) return false;

        LivingEntity target = this.inflatableMobGirl.getTarget();
        if (target == null) {
            return false;
        } else if (!target.isAlive()) {
            return false;
        } else if (!this.followingTargetEvenIfNotSeen) {
            return !this.inflatableMobGirl.getNavigation().isDone();
        } else {
            boolean canContinue;
            if (!this.inflatableMobGirl.isWithinHome(target.blockPosition())) {
                canContinue = false;
            } else {
                if (target instanceof Player player) {
                    if (player.isSpectator() || player.isCreative()) {
                        return false;
                    }
                }

                canContinue = true;
            }

            return canContinue;
        }
    }

    protected boolean isImmobile() {
        return this.inflatableMobGirl.isInflating();
    }

    public void start() {
        this.inflatableMobGirl.getNavigation().moveTo(this.path, this.speedModifier);
        this.inflatableMobGirl.setAggressive(true);
        this.ticksUntilNextPathRecalculation = 0;
        this.ticksUntilNextAttack = 0;
        this.animationTicks = 0;
    }

    public void stop() {
        LivingEntity target = this.inflatableMobGirl.getTarget();
        if (target != null && !EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(target)) {
            this.inflatableMobGirl.setTarget(null);
        }

        this.inflatableMobGirl.setAggressive(false);
        this.inflatableMobGirl.getNavigation().stop();
    }

    public boolean requiresUpdateEveryTick() {
        return true;
    }

    public void tick() {
        LivingEntity target = this.inflatableMobGirl.getTarget();
        if (target != null) {
            this.inflatableMobGirl.getLookControl().setLookAt(target, 30.0F, 30.0F);
            this.ticksUntilNextPathRecalculation = Math.max(this.ticksUntilNextPathRecalculation - 1, 0);
            if ((this.followingTargetEvenIfNotSeen || this.inflatableMobGirl.getSensing().hasLineOfSight(target)) && this.ticksUntilNextPathRecalculation <= 0 && (this.pathedTargetX == (double)0.0F && this.pathedTargetY == (double)0.0F && this.pathedTargetZ == (double)0.0F || target.distanceToSqr(this.pathedTargetX, this.pathedTargetY, this.pathedTargetZ) >= (double)1.0F || this.inflatableMobGirl.getRandom().nextFloat() < 0.05F)) {
                this.pathedTargetX = target.getX();
                this.pathedTargetY = target.getY();
                this.pathedTargetZ = target.getZ();
                this.ticksUntilNextPathRecalculation = 4 + this.inflatableMobGirl.getRandom().nextInt(7);
                double targetDistanceSqr = this.inflatableMobGirl.distanceToSqr(target);
                if (targetDistanceSqr > (double)1024.0F) {
                    this.ticksUntilNextPathRecalculation += 10;
                } else if (targetDistanceSqr > (double)256.0F) {
                    this.ticksUntilNextPathRecalculation += 5;
                }

                if (!this.inflatableMobGirl.getNavigation().moveTo(target, this.speedModifier)) {
                    this.ticksUntilNextPathRecalculation += 15;
                }

                this.ticksUntilNextPathRecalculation = this.adjustedTickDelay(this.ticksUntilNextPathRecalculation);
            }

            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
            this.checkAndPerformAttack(target);
        }

    }

    protected void checkAndPerformAttack(LivingEntity target) {
        if (this.canPerformAttack(target)) {
            this.animationTicks++;

            this.inflatableMobGirl.setAggressive(true);
            if (this.animationTicks >= this.animationDelay) {
                this.resetAttackCooldown();
                this.inflatableMobGirl.swing(InteractionHand.MAIN_HAND);
                this.inflatableMobGirl.doHurtTarget(getServerLevel(this.inflatableMobGirl), target);
            }
        } else {
            this.resetAttackAnimation();
        }
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.adjustedTickDelay(this.attackInterval);
    }

    protected void resetAttackAnimation() {
        this.inflatableMobGirl.setAggressive(false);
        this.animationTicks = 0;
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected boolean canPerformAttack(@NonNull LivingEntity target) {
        return this.isTimeToAttack() && this.inflatableMobGirl.isWithinMeleeAttackRange(target) && this.inflatableMobGirl.getSensing().hasLineOfSight(target);
    }

    protected int getTicksUntilNextAttack() {
        return this.ticksUntilNextAttack;
    }

    protected int getAttackInterval() {
        return this.adjustedTickDelay(20);
    }
}
