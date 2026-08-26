package io.github.cherrybxrry.inflatablemobgirls.client.model.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.CreeperGirlRenderState;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public abstract class CreeperGirlModel extends EntityModel<CreeperGirlRenderState> implements SpawnerModel {
    private static final float MAX_WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final KeyframeAnimation walkAnimation;
    private final KeyframeAnimation attackAnimation;

    protected CreeperGirlModel(ModelPart root, final AnimationDefinition walk, final AnimationDefinition attack) {
        super(root);

        this.walkAnimation = walk.bake(root);
        this.attackAnimation = attack.bake(root);
    }

    @Override
    public void setupAnim(@NonNull CreeperGirlRenderState state) {
        super.setupAnim(state);
        this.applyHeadRotation(state.yRot, state.xRot);

        this.walkAnimation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
        this.attackAnimation.apply(state.attackAnimation, state.ageInTicks);
    }

    private void applyHeadRotation(float yRot, float xRot) {
        yRot = Mth.clamp(yRot, -30.0F, 30.0F);
        xRot = Mth.clamp(xRot, -25.0F, 45.0F);

        this.getNeck().yRot = (yRot * ((float) Math.PI / 180F)) / 2;
        this.getNeck().xRot = (xRot * ((float) Math.PI / 180F)) / 2;
        this.getHead().yRot = (yRot * ((float) Math.PI / 180F)) / 2;
        this.getHead().xRot = (xRot * ((float) Math.PI / 180F)) / 2;
    }

    protected abstract @NotNull ModelPart getHead();

    protected abstract @NotNull ModelPart getNeck();
}
