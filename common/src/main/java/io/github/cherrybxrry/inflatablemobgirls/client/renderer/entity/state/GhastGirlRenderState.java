package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state;

import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemStack;

public class GhastGirlRenderState extends InflatableMobGirlRenderState {
    public int stage = 0;
    public ItemStack bodyItem;
    public boolean isRidden;
    public boolean isLeashHolder;

    public AnimationState idleStand0Animation = new AnimationState();
    public AnimationState idleStand1Animation = new AnimationState();
    public AnimationState idleStand2Animation = new AnimationState();
    public AnimationState idleStand3Animation = new AnimationState();

    public AnimationState idleRide0Animation = new AnimationState();
    public AnimationState idleRide1Animation = new AnimationState();
    public AnimationState idleRide2Animation = new AnimationState();
    public AnimationState idleRide3Animation = new AnimationState();

    public AnimationState sit0Animation = new AnimationState();
    public AnimationState sit1Animation = new AnimationState();
    public AnimationState sit2Animation = new AnimationState();
    public AnimationState sit3Animation = new AnimationState();

    public AnimationState inflate1Animation = new AnimationState();
    public AnimationState inflate2Animation = new AnimationState();
    public AnimationState inflate3Animation = new AnimationState();

    public AnimationState sitInflate1Animation = new AnimationState();
    public AnimationState sitInflate2Animation = new AnimationState();
    public AnimationState sitInflate3Animation = new AnimationState();

    public AnimationState ventInflate1Animation = new AnimationState();
    public AnimationState ventInflate2Animation = new AnimationState();
    public AnimationState ventInflate3Animation = new AnimationState();

    public AnimationState attack0Animation = new AnimationState();
    public AnimationState attack1Animation = new AnimationState();
    public AnimationState attack2Animation = new AnimationState();
    public AnimationState attack3Animation = new AnimationState();

    public AnimationState blinkAnimation = new AnimationState();
}
