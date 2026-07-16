package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

public class CreeperGirlRenderState extends LivingEntityRenderState {
    private static final Identifier DEFAULT_TEXTURE = Constants.id("textures/entity/creeper_girl/creeper_girl.png");

    public final AnimationState idle0AnimationState = new AnimationState();
    public final AnimationState idle1AnimationState = new AnimationState();
    public final AnimationState idle2AnimationState = new AnimationState();
    public final AnimationState idleRegen0AnimationState = new AnimationState();
    public final AnimationState idleRegen1AnimationState = new AnimationState();
    public final AnimationState idleRegen2AnimationState = new AnimationState();

    public final AnimationState idleCharged0AnimationState = new AnimationState();
    public final AnimationState idleCharged1AnimationState = new AnimationState();
    public final AnimationState idleCharged2AnimationState = new AnimationState();

    public final AnimationState sit0AnimationState = new AnimationState();
    public final AnimationState sit1AnimationState = new AnimationState();
    public final AnimationState sit2AnimationState = new AnimationState();

    public final AnimationState sitCharged0AnimationState = new AnimationState();
    public final AnimationState sitCharged1AnimationState = new AnimationState();
    public final AnimationState sitCharged2AnimationState = new AnimationState();

    public final AnimationState inflate1AnimationState = new AnimationState();
    public final AnimationState inflate2AnimationState = new AnimationState();
    public final AnimationState inflate3AnimationState = new AnimationState();
    public final AnimationState regen1AnimationState = new AnimationState();
    public final AnimationState regen2AnimationState = new AnimationState();
    public final AnimationState regen3AnimationState = new AnimationState();

    public final AnimationState inflateCharged1AnimationState = new AnimationState();
    public final AnimationState inflateCharged2AnimationState = new AnimationState();
    public final AnimationState inflateCharged3AnimationState = new AnimationState();

    public final AnimationState attackAnimationState = new AnimationState();

    public Identifier texture = DEFAULT_TEXTURE;
}
