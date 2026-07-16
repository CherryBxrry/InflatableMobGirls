package io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.state;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.AnimationState;

public class CreepSporeCropState extends BlockEntityRenderState {
    private static final Identifier DEFAULT_TEXTURE = Constants.id("textures/entity/creeper_girl/creeper_girl_baby.png");

    public float ageInTicks;
    public int age = 0;
    public Direction direction = Direction.NORTH;

    public AnimationState idle0Animation = new AnimationState();
    public AnimationState idle1Animation = new AnimationState();
    public AnimationState idle2Animation = new AnimationState();
    public AnimationState idle3Animation = new AnimationState();

    public AnimationState ahogeShiverAnimation = new AnimationState();
    public AnimationState headShiverAnimation = new AnimationState();

    public AnimationState blinkAnimation = new AnimationState();

    public Identifier texture = DEFAULT_TEXTURE;
}
