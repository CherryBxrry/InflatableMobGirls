package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

public class CreeperGirlRenderState extends InflatableMobGirlRenderState {
    private static final Identifier DEFAULT_TEXTURE = Constants.id("textures/entity/creeper_girl/creeper_girl.png");

    public final AnimationState idle0Animation = new AnimationState();
    public final AnimationState idle1Animation = new AnimationState();
    public final AnimationState idle2Animation = new AnimationState();
    public final AnimationState idleRegen0Animation = new AnimationState();
    public final AnimationState idleRegen1Animation = new AnimationState();
    public final AnimationState idleRegen2Animation = new AnimationState();

    public final AnimationState idleCharged0Animation = new AnimationState();
    public final AnimationState idleCharged1Animation = new AnimationState();
    public final AnimationState idleCharged2Animation = new AnimationState();

    public final AnimationState sit0Animation = new AnimationState();
    public final AnimationState sit1Animation = new AnimationState();
    public final AnimationState sit2Animation = new AnimationState();

    public final AnimationState sitCharged0Animation = new AnimationState();
    public final AnimationState sitCharged1Animation = new AnimationState();
    public final AnimationState sitCharged2Animation = new AnimationState();

    public final AnimationState inflate1Animation = new AnimationState();
    public final AnimationState inflate2Animation = new AnimationState();
    public final AnimationState inflate3Animation = new AnimationState();
    public final AnimationState regen1Animation = new AnimationState();
    public final AnimationState regen2Animation = new AnimationState();
    public final AnimationState regen3Animation = new AnimationState();

    public final AnimationState inflateCharged1Animation = new AnimationState();
    public final AnimationState inflateCharged2Animation = new AnimationState();
    public final AnimationState inflateCharged3Animation = new AnimationState();

    public final AnimationState attackAnimation = new AnimationState();

    public Identifier texture = DEFAULT_TEXTURE;
}
