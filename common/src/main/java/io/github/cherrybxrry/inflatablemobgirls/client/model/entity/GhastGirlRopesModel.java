package io.github.cherrybxrry.inflatablemobgirls.client.model.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.animation.definitions.GhastGirlAnimation;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.GhastGirlRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class GhastGirlRopesModel extends EntityModel<GhastGirlRenderState> {
    private static final float MAX_WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final KeyframeAnimation idleStand0Animation;
    private final KeyframeAnimation idleStand1Animation;
    private final KeyframeAnimation idleStand2Animation;
    private final KeyframeAnimation idleStand3Animation;

    private final KeyframeAnimation idleRide0Animation;
    private final KeyframeAnimation idleRide1Animation;
    private final KeyframeAnimation idleRide2Animation;
    private final KeyframeAnimation idleRide3Animation;

    private final KeyframeAnimation sit0Animation;
    private final KeyframeAnimation sit1Animation;
    private final KeyframeAnimation sit2Animation;
    private final KeyframeAnimation sit3Animation;

    private final KeyframeAnimation inflate1Animation;
    private final KeyframeAnimation inflate2Animation;
    private final KeyframeAnimation inflate3Animation;

    private final KeyframeAnimation sitInflate1Animation;
    private final KeyframeAnimation sitInflate2Animation;
    private final KeyframeAnimation sitInflate3Animation;

    private final KeyframeAnimation ventInflate1Animation;
    private final KeyframeAnimation ventInflate2Animation;
    private final KeyframeAnimation ventInflate3Animation;

    private final KeyframeAnimation walkStand0Animation;
    private final KeyframeAnimation walkStand1Animation;
    private final KeyframeAnimation walkStand2Animation;
    private final KeyframeAnimation walkStand3Animation;

    private final KeyframeAnimation walkRide0Animation;
    private final KeyframeAnimation walkRide1Animation;
    private final KeyframeAnimation walkRide2Animation;
    private final KeyframeAnimation walkRide3Animation;

    private final KeyframeAnimation attackMeleeAnimation;
    private final KeyframeAnimation attack1Animation;
    private final KeyframeAnimation attack2Animation;
    private final KeyframeAnimation attack3Animation;

    public GhastGirlRopesModel(ModelPart root) {
        super(root);

        this.idleStand0Animation = GhastGirlAnimation.IDLE_STAND0.bake(root);
        this.idleStand1Animation = GhastGirlAnimation.IDLE_STAND1.bake(root);
        this.idleStand2Animation = GhastGirlAnimation.IDLE_STAND2.bake(root);
        this.idleStand3Animation = GhastGirlAnimation.IDLE_STAND3.bake(root);

        this.idleRide0Animation = GhastGirlAnimation.IDLE_RIDE0.bake(root);
        this.idleRide1Animation = GhastGirlAnimation.IDLE_RIDE1.bake(root);
        this.idleRide2Animation = GhastGirlAnimation.IDLE_RIDE2.bake(root);
        this.idleRide3Animation = GhastGirlAnimation.IDLE_RIDE3.bake(root);

        this.sit0Animation = GhastGirlAnimation.SITTING0.bake(root);
        this.sit1Animation = GhastGirlAnimation.SITTING1.bake(root);
        this.sit2Animation = GhastGirlAnimation.SITTING2.bake(root);
        this.sit3Animation = GhastGirlAnimation.SITTING3.bake(root);

        this.inflate1Animation = GhastGirlAnimation.INFLATE1.bake(root);
        this.inflate2Animation = GhastGirlAnimation.INFLATE2.bake(root);
        this.inflate3Animation = GhastGirlAnimation.INFLATE3.bake(root);

        this.sitInflate1Animation = GhastGirlAnimation.SITTING_INFLATE1.bake(root);
        this.sitInflate2Animation = GhastGirlAnimation.SITTING_INFLATE2.bake(root);
        this.sitInflate3Animation = GhastGirlAnimation.SITTING_INFLATE3.bake(root);

        this.ventInflate1Animation = GhastGirlAnimation.VENT_INFLATE1.bake(root);
        this.ventInflate2Animation = GhastGirlAnimation.VENT_INFLATE2.bake(root);
        this.ventInflate3Animation = GhastGirlAnimation.VENT_INFLATE3.bake(root);

        this.walkStand0Animation = GhastGirlAnimation.WALK_STAND0.bake(root);
        this.walkStand1Animation = GhastGirlAnimation.WALK_STAND1.bake(root);
        this.walkStand2Animation = GhastGirlAnimation.WALK_STAND2.bake(root);
        this.walkStand3Animation = GhastGirlAnimation.WALK_STAND3.bake(root);

        this.walkRide0Animation = GhastGirlAnimation.WALK_RIDE0.bake(root);
        this.walkRide1Animation = GhastGirlAnimation.WALK_RIDE1.bake(root);
        this.walkRide2Animation = GhastGirlAnimation.WALK_RIDE2.bake(root);
        this.walkRide3Animation = GhastGirlAnimation.WALK_RIDE3.bake(root);

        this.attackMeleeAnimation = GhastGirlAnimation.ATTACK0.bake(root);
        this.attack1Animation = GhastGirlAnimation.ATTACK1.bake(root);
        this.attack2Animation = GhastGirlAnimation.ATTACK2.bake(root);
        this.attack3Animation = GhastGirlAnimation.ATTACK3.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.5F, -40.0F, 2.0F, 0.0F, 0.0F, -0.0436F));

        PartDefinition leg_r2 = leg_r.addOrReplaceChild("leg_r2", CubeListBuilder.create(), PartPose.offset(1.5F, 18.0F, -6.5F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l", CubeListBuilder.create(), PartPose.offsetAndRotation(8.5F, -40.0F, 2.0F, 0.0F, 0.0F, 0.0436F));

        PartDefinition leg_l2 = leg_l.addOrReplaceChild("leg_l2", CubeListBuilder.create(), PartPose.offset(-1.5F, 18.0F, -6.5F));

        PartDefinition hips = root.addOrReplaceChild("hips", CubeListBuilder.create(), PartPose.offset(0.0F, -42.0F, -0.5F));

        PartDefinition spine = hips.addOrReplaceChild("spine", CubeListBuilder.create(), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition belly_huge = spine.addOrReplaceChild("belly_huge", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 1.5F, 0.0436F, 0.0F, 0.0F));

        PartDefinition bellybutton2 = belly_huge.addOrReplaceChild("bellybutton2", CubeListBuilder.create(), PartPose.offset(0.0F, 10.5F, -38.5F));

        PartDefinition belly_big = spine.addOrReplaceChild("belly_big", CubeListBuilder.create(), PartPose.offsetAndRotation(1.0F, 3.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition bellybutton1 = belly_big.addOrReplaceChild("bellybutton1", CubeListBuilder.create(), PartPose.offset(-1.0F, 0.5F, -23.0F));

        PartDefinition belly = spine.addOrReplaceChild("belly", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 2.0F, 2.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition chest = spine.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -10.0F, -5.25F, 16.0F, 10.0F, 11.0F, new CubeDeformation(0.2F)), PartPose.offset(0.0F, -4.0F, -0.25F));

        PartDefinition breasts = chest.addOrReplaceChild("breasts", CubeListBuilder.create(), PartPose.offset(0.0F, -8.25F, -5.25F));

        PartDefinition breast_l = breasts.addOrReplaceChild("breast_l", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, -0.1309F, 0.0F));

        PartDefinition breast_r = breasts.addOrReplaceChild("breast_r", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.1309F, 0.0F));

        PartDefinition breasts2 = chest.addOrReplaceChild("breasts2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -4.25F));

        PartDefinition breast_l2 = breasts2.addOrReplaceChild("breast_l2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.75F, -3.0F, 0.0F, -0.0873F, -0.1309F, -0.0436F));

        PartDefinition breast_r2 = breasts2.addOrReplaceChild("breast_r2", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.75F, -3.0F, 0.0F, -0.0873F, 0.1309F, 0.0436F));

        PartDefinition arm_l = chest.addOrReplaceChild("arm_l", CubeListBuilder.create(), PartPose.offset(8.0F, -10.0F, -0.25F));

        PartDefinition arm_l2 = arm_l.addOrReplaceChild("arm_l2", CubeListBuilder.create(), PartPose.offset(13.5F, 0.0F, 0.0F));

        PartDefinition hand_l = arm_l2.addOrReplaceChild("hand_l", CubeListBuilder.create(), PartPose.offset(13.0F, 0.0F, 0.5F));

        PartDefinition arm_r = chest.addOrReplaceChild("arm_r", CubeListBuilder.create(), PartPose.offset(-8.0F, -10.0F, -0.25F));

        PartDefinition arm_r2 = arm_r.addOrReplaceChild("arm_r2", CubeListBuilder.create(), PartPose.offset(-13.5F, 0.0F, 0.0F));

        PartDefinition hand_r = arm_r2.addOrReplaceChild("hand_r", CubeListBuilder.create(), PartPose.offset(-13.0F, 0.0F, 0.5F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create(), PartPose.offset(0.0F, -9.0F, 0.75F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(0.0F, -10.5F, 0.0F));

        PartDefinition mouth_cry = head.addOrReplaceChild("mouth_cry", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -6.755F));

        PartDefinition mouth_smile = head.addOrReplaceChild("mouth_smile", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -7.755F));

        PartDefinition mouth_shoot = head.addOrReplaceChild("mouth_shoot", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -6.755F));

        PartDefinition blush = head.addOrReplaceChild("blush", CubeListBuilder.create(), PartPose.offset(0.0F, 6.0F, -6.755F));

        PartDefinition ear_l = head.addOrReplaceChild("ear_l", CubeListBuilder.create(), PartPose.offsetAndRotation(8.0F, 1.0F, -4.5F, 0.0F, 0.0F, 0.1745F));

        PartDefinition ear_r = head.addOrReplaceChild("ear_r", CubeListBuilder.create(), PartPose.offsetAndRotation(-8.0F, 1.0F, -4.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(-6.0F, 2.0F, -7.755F));

        PartDefinition eye_l = eyes.addOrReplaceChild("eye_l", CubeListBuilder.create(), PartPose.offset(4.0F, 2.0F, 0.0F));

        PartDefinition eye_r = eyes.addOrReplaceChild("eye_r", CubeListBuilder.create(), PartPose.offset(8.0F, 2.0F, 0.0F));

        PartDefinition pupil_l = eyes.addOrReplaceChild("pupil_l", CubeListBuilder.create(), PartPose.offset(9.0F, 2.0F, 0.0F));

        PartDefinition pupil_r = eyes.addOrReplaceChild("pupil_r", CubeListBuilder.create(), PartPose.offset(3.0F, 2.0F, 0.0F));

        PartDefinition eyelash_l = eyes.addOrReplaceChild("eyelash_l", CubeListBuilder.create(), PartPose.offset(9.0F, -1.0F, 0.0F));

        PartDefinition el_3 = eyelash_l.addOrReplaceChild("el_3", CubeListBuilder.create(), PartPose.offset(3.5F, 1.0F, -0.26F));

        PartDefinition el_4 = eyelash_l.addOrReplaceChild("el_4", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 0.74F));

        PartDefinition eyelash_r = eyes.addOrReplaceChild("eyelash_r", CubeListBuilder.create(), PartPose.offset(0.0F, -1.0F, 0.0F));

        PartDefinition el_2 = eyelash_r.addOrReplaceChild("el_2", CubeListBuilder.create(), PartPose.offset(3.5F, 1.0F, -0.26F));

        PartDefinition el_1 = eyelash_r.addOrReplaceChild("el_1", CubeListBuilder.create(), PartPose.offset(-1.0F, 1.0F, 0.74F));

        PartDefinition hair_l = head.addOrReplaceChild("hair_l", CubeListBuilder.create(), PartPose.offset(8.75F, 8.75F, -2.25F));

        PartDefinition hair_l1 = hair_l.addOrReplaceChild("hair_l1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair_r = head.addOrReplaceChild("hair_r", CubeListBuilder.create(), PartPose.offset(-8.75F, 8.75F, -2.25F));

        PartDefinition hair_r1 = hair_r.addOrReplaceChild("hair_r1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition hair_lb = head.addOrReplaceChild("hair_lb", CubeListBuilder.create(), PartPose.offset(8.75F, 8.75F, 7.25F));

        PartDefinition hair_lb1 = hair_lb.addOrReplaceChild("hair_lb1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition hair_rb = head.addOrReplaceChild("hair_rb", CubeListBuilder.create(), PartPose.offset(-8.75F, 8.75F, 7.25F));

        PartDefinition hair_rb1 = hair_rb.addOrReplaceChild("hair_rb1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition hair_b = head.addOrReplaceChild("hair_b", CubeListBuilder.create(), PartPose.offset(0.0F, 8.75F, 8.75F));

        PartDefinition hair_b1 = hair_b.addOrReplaceChild("hair_b1", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition stevesit = root.addOrReplaceChild("stevesit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pump = root.addOrReplaceChild("pump", CubeListBuilder.create(), PartPose.offset(-4.0F, -39.0F, -14.0F));

        PartDefinition membrane = pump.addOrReplaceChild("membrane", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 3.0F));

        PartDefinition bottom = pump.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2443F, 0.0F, 0.0F));

        PartDefinition top = pump.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.2443F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull GhastGirlRenderState state) {
        super.setupAnim(state);

        this.idleStand0Animation.apply(state.idleStand0Animation, state.ageInTicks);
        this.idleStand1Animation.apply(state.idleStand1Animation, state.ageInTicks);
        this.idleStand2Animation.apply(state.idleStand2Animation, state.ageInTicks);
        this.idleStand3Animation.apply(state.idleStand3Animation, state.ageInTicks);

        this.idleRide0Animation.apply(state.idleRide0Animation, state.ageInTicks);
        this.idleRide1Animation.apply(state.idleRide1Animation, state.ageInTicks);
        this.idleRide2Animation.apply(state.idleRide2Animation, state.ageInTicks);
        this.idleRide3Animation.apply(state.idleRide3Animation, state.ageInTicks);

        this.sit0Animation.apply(state.sit0Animation, state.ageInTicks);
        this.sit1Animation.apply(state.sit1Animation, state.ageInTicks);
        this.sit2Animation.apply(state.sit2Animation, state.ageInTicks);
        this.sit3Animation.apply(state.sit3Animation, state.ageInTicks);

        this.inflate1Animation.apply(state.inflate1Animation, state.ageInTicks);
        this.inflate2Animation.apply(state.inflate2Animation, state.ageInTicks);
        this.inflate3Animation.apply(state.inflate3Animation, state.ageInTicks);

        this.sitInflate1Animation.apply(state.sitInflate1Animation, state.ageInTicks);
        this.sitInflate2Animation.apply(state.sitInflate2Animation, state.ageInTicks);
        this.sitInflate3Animation.apply(state.sitInflate3Animation, state.ageInTicks);

        this.ventInflate1Animation.apply(state.ventInflate1Animation, state.ageInTicks);
        this.ventInflate2Animation.apply(state.ventInflate2Animation, state.ageInTicks);
        this.ventInflate3Animation.apply(state.ventInflate3Animation, state.ageInTicks);

        if (!state.isRidden) {
            switch (state.stage) {
                case 1 ->
                        this.walkStand1Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                case 2 ->
                        this.walkStand2Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                case 3 ->
                        this.walkStand3Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                default ->
                        this.walkStand0Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
            }
        } else {
            switch (state.stage) {
                case 1 ->
                        this.walkRide1Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                case 2 ->
                        this.walkRide2Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                case 3 ->
                        this.walkRide3Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
                default ->
                        this.walkRide0Animation.applyWalk(state.walkAnimationPos, state.walkAnimationSpeed, MAX_WALK_ANIMATION_SPEED, WALK_ANIMATION_SCALE_FACTOR);
            }
        }

        this.attackMeleeAnimation.apply(state.attack0Animation, state.ageInTicks);
        this.attack1Animation.apply(state.attack1Animation, state.ageInTicks);
        this.attack2Animation.apply(state.attack2Animation, state.ageInTicks);
        this.attack3Animation.apply(state.attack3Animation, state.ageInTicks);
    }
}
