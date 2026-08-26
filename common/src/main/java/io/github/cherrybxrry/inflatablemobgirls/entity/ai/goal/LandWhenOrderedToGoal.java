package io.github.cherrybxrry.inflatablemobgirls.entity.ai.goal;

import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class LandWhenOrderedToGoal extends Goal {
    private final TamableAnimal mob;
    protected double wantedX;
    protected double wantedY;
    protected double wantedZ;
    private final double speedModifier;

    public LandWhenOrderedToGoal(TamableAnimal mob) {
        this(mob, 1.0D);
    }

    public LandWhenOrderedToGoal(TamableAnimal mob, double speedModifier) {
        this.mob = mob;
        this.speedModifier = speedModifier;

        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    @Override
    public boolean canUse() {
        boolean orderedToSit = this.mob.isOrderedToSit();
        if (!orderedToSit && !this.mob.isTame()) {
            return false;
        } else if (this.mob.isInWater() || this.mob.onGround() || this.mob.hasControllingPassenger()) {
            return false;
        } else {
            Vec3 pos = LandRandomPos.getPos(this.mob, 5, 100);
            if (pos == null) {
                return false;
            } else {
                this.wantedX = pos.x;
                this.wantedY = pos.y;
                this.wantedZ = pos.z;
            }

            return orderedToSit;
        }
    }

    @Override
    public boolean canContinueToUse() {
        return this.mob.isOrderedToSit() && !this.mob.getNavigation().isDone() && !this.mob.onGround() && !this.mob.hasControllingPassenger();
    }

    @Override
    public void start() {
        this.mob.getNavigation().moveTo(this.wantedX, this.wantedY, this.wantedZ, this.speedModifier);
    }

    public void stop() {
        this.mob.getNavigation().stop();
        super.stop();
    }
}
