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

public class GhastGirlModel extends EntityModel<GhastGirlRenderState> {
    private static final float MAX_WALK_ANIMATION_SPEED = 2.0F;
    private static final float WALK_ANIMATION_SCALE_FACTOR = 2.5F;

    private final ModelPart neck;
    private final ModelPart head;

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

    private final KeyframeAnimation blinkAnimation;

    public GhastGirlModel(ModelPart root) {
        super(root);

        this.neck = root.getChild("root").getChild("hips").getChild("spine").getChild("chest").getChild("neck");
        this.head = this.neck.getChild("head");

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

        this.blinkAnimation = GhastGirlAnimation.BLINK.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition leg_r = root.addOrReplaceChild("leg_r", CubeListBuilder.create().texOffs(0, 136).addBox(-7.5F, -2.0F, -8.5F, 15.0F, 16.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(0, 169).addBox(-3.5F, 14.0F, -6.5F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.5F, -40.0F, 2.0F, 0.0F, 0.0F, -0.0436F));

        leg_r.addOrReplaceChild("leg_r2", CubeListBuilder.create().texOffs(0, 184).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(-0.002F))
                .texOffs(2, 203).addBox(-3.0F, 5.0F, 1.0F, 7.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(1.5F, 18.0F, -6.5F));

        PartDefinition leg_l = root.addOrReplaceChild("leg_l", CubeListBuilder.create().texOffs(0, 136).mirror().addBox(-7.5F, -2.0F, -8.5F, 15.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 169).mirror().addBox(-6.5F, 14.0F, -6.5F, 10.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.5F, -40.0F, 2.0F, 0.0F, 0.0F, 0.0436F));

        leg_l.addOrReplaceChild("leg_l2", CubeListBuilder.create().texOffs(2, 203).mirror().addBox(-4.0F, 5.0F, 1.0F, 7.0F, 17.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(0, 184).mirror().addBox(-5.0F, 0.0F, 0.0F, 10.0F, 6.0F, 10.0F, new CubeDeformation(-0.002F)).mirror(false), PartPose.offset(-1.5F, 18.0F, -6.5F));

        PartDefinition hips = root.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(1, 83).addBox(-6.0F, -1.0F, -4.5F, 12.0F, 6.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(0, 101).addBox(-9.0F, -3.0F, -5.0F, 18.0F, 6.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -42.0F, -0.5F));

        PartDefinition spine = hips.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(0, 67).addBox(-6.0F, -5.0F, -4.5F, 12.0F, 6.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition belly_huge = spine.addOrReplaceChild("belly_huge", CubeListBuilder.create().texOffs(32, 188).addBox(-18.0F, -10.0F, -37.0F, 36.0F, 36.0F, 36.0F, new CubeDeformation(0.0F))
                .texOffs(140, 186).addBox(-15.0F, 26.0F, -34.0F, 30.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(140, 151).addBox(-15.0F, -12.0F, -34.0F, 30.0F, 2.0F, 30.0F, new CubeDeformation(0.0F))
                .texOffs(159, 48).addBox(-15.0F, -7.0F, -1.0F, 30.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(196, 113).addBox(-15.0F, -7.0F, -39.0F, 30.0F, 30.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(196, 51).mirror().addBox(18.0F, -7.0F, -34.0F, 2.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(196, 51).addBox(-20.0F, -7.0F, -34.0F, 2.0F, 30.0F, 30.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 1.5F, 0.0436F, 0.0F, 0.0F));

        belly_huge.addOrReplaceChild("bellybutton2", CubeListBuilder.create().texOffs(8, 255).addBox(-1.5F, -1.5F, -2.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 10.5F, -38.5F));

        PartDefinition belly_big = spine.addOrReplaceChild("belly_big", CubeListBuilder.create().texOffs(101, 84).addBox(-12.5F, -11.0F, -23.0F, 23.0F, 23.0F, 23.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 3.0F, 3.0F, 0.1309F, 0.0F, 0.0F));

        belly_big.addOrReplaceChild("bellybutton1", CubeListBuilder.create().texOffs(172, 84).addBox(-1.5F, -1.5F, -1.0F, 3.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 0.5F, -23.0F));

        spine.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(84, 45).addBox(-8.5F, -9.0F, -19.0F, 17.0F, 17.0F, 19.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 2.5F, 0.1309F, 0.0F, 0.0F));

        PartDefinition chest = spine.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 44).addBox(-8.0F, -10.0F, -5.25F, 16.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -0.25F));

        PartDefinition breasts = chest.addOrReplaceChild("breasts", CubeListBuilder.create(), PartPose.offset(0.0F, -8.25F, -5.25F));

        breasts.addOrReplaceChild("breast_l", CubeListBuilder.create().texOffs(0, 121).addBox(0.0F, 0.0F, -7.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(5, 140).addBox(3.5F, 2.5F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, -0.1309F, 0.0F));

        breasts.addOrReplaceChild("breast_r", CubeListBuilder.create().texOffs(0, 121).mirror().addBox(-8.0F, 0.0F, -7.0F, 8.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(5, 140).mirror().addBox(-5.5F, 2.5F, -8.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2182F, 0.1309F, 0.0F));

        PartDefinition breasts2 = chest.addOrReplaceChild("breasts2", CubeListBuilder.create(), PartPose.offset(0.0F, -4.0F, -4.25F));

        breasts2.addOrReplaceChild("breast_l2", CubeListBuilder.create().texOffs(88, 134).addBox(-1.0F, -1.0F, -14.0F, 12.0F, 12.0F, 13.0F, new CubeDeformation(0.0F))
                .texOffs(120, 163).addBox(0.0F, 0.0F, -16.0F, 10.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(90, 163).addBox(0.0F, 0.0F, -1.0F, 10.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(97, 176).addBox(3.5F, 3.0F, -17.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.75F, -3.0F, 0.0F, -0.0873F, -0.1309F, -0.0436F));

        breasts2.addOrReplaceChild("breast_r2", CubeListBuilder.create().texOffs(88, 134).mirror().addBox(-11.0F, -1.0F, -14.0F, 12.0F, 12.0F, 13.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(120, 163).mirror().addBox(-10.0F, 0.0F, -16.0F, 10.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(90, 163).mirror().addBox(-10.0F, 0.0F, -1.0F, 10.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(97, 176).mirror().addBox(-7.5F, 3.0F, -17.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.75F, -3.0F, 0.0F, -0.0873F, 0.1309F, 0.0436F));

        PartDefinition arm_l = chest.addOrReplaceChild("arm_l", CubeListBuilder.create().texOffs(53, 113).addBox(0.5F, -3.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(76, 103).addBox(11.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(60, 103).addBox(-0.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(8.0F, -10.0F, -0.25F));

        PartDefinition arm_l2 = arm_l.addOrReplaceChild("arm_l2", CubeListBuilder.create().texOffs(53, 126).addBox(0.0F, -3.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(74, 144).addBox(11.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(13.5F, 0.0F, 0.0F));

        arm_l2.addOrReplaceChild("hand_l", CubeListBuilder.create().texOffs(53, 140).addBox(0.0F, -4.0F, -2.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(13.0F, 0.0F, 0.5F));

        PartDefinition arm_r = chest.addOrReplaceChild("arm_r", CubeListBuilder.create().texOffs(53, 113).mirror().addBox(-12.5F, -3.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(76, 103).mirror().addBox(-14.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(60, 103).mirror().addBox(-2.5F, -2.0F, -2.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-8.0F, -10.0F, -0.25F));

        PartDefinition arm_r2 = arm_r.addOrReplaceChild("arm_r2", CubeListBuilder.create().texOffs(53, 126).mirror().addBox(-12.0F, -3.0F, -3.0F, 12.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(74, 144).mirror().addBox(-14.0F, -2.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.5F, 0.0F, 0.0F));

        arm_r2.addOrReplaceChild("hand_r", CubeListBuilder.create().texOffs(53, 140).mirror().addBox(-7.0F, -4.0F, -2.0F, 7.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-13.0F, 0.0F, 0.5F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(1, 32).addBox(-3.0F, -4.0F, -4.0F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -9.0F, 0.75F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F, new CubeDeformation(0.0F))
                .texOffs(130, 0).addBox(-6.0F, -7.75F, -8.0F, 12.0F, 1.0F, 16.0F, new CubeDeformation(0.5F))
                .texOffs(64, 0).addBox(-8.0F, -6.0F, -8.0F, 16.0F, 14.0F, 16.0F, new CubeDeformation(0.75F)), PartPose.offset(0.0F, -10.5F, 0.0F));

        head.addOrReplaceChild("mouth_cry", CubeListBuilder.create().texOffs(38, 37).addBox(-4.0F, -2.0F, -0.25F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -6.755F));

        head.addOrReplaceChild("mouth_smile", CubeListBuilder.create().texOffs(38, 32).addBox(-4.0F, -2.0F, -0.25F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -7.755F));

        head.addOrReplaceChild("mouth_shoot", CubeListBuilder.create().texOffs(56, 32).addBox(-4.0F, -2.0F, -0.25F, 8.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -6.755F));

        head.addOrReplaceChild("blush", CubeListBuilder.create().texOffs(65, 39).addBox(-8.0F, -4.0F, -0.25F, 16.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -6.755F));

        head.addOrReplaceChild("ear_l", CubeListBuilder.create().texOffs(117, 1).addBox(0.0F, 0.0F, 0.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 1.0F, -4.5F, 0.0F, 0.0F, 0.1745F));

        head.addOrReplaceChild("ear_r", CubeListBuilder.create().texOffs(117, 1).mirror().addBox(-6.0F, 0.0F, 0.0F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-8.0F, 1.0F, -4.5F, 0.0F, 0.0F, -0.1745F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(-6.0F, 2.0F, -7.755F));

        eyes.addOrReplaceChild("eye_l", CubeListBuilder.create().texOffs(49, 43).addBox(-5.0F, -3.0F, -0.25F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

        eyes.addOrReplaceChild("eye_r", CubeListBuilder.create().texOffs(49, 43).mirror().addBox(0.0F, -3.0F, -0.25F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(8.0F, 2.0F, 0.0F));

        eyes.addOrReplaceChild("pupil_l", CubeListBuilder.create().texOffs(51, 48).mirror().addBox(0.0F, -2.0F, -0.255F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(9.0F, 2.0F, 0.0F));

        eyes.addOrReplaceChild("pupil_r", CubeListBuilder.create().texOffs(51, 48).mirror().addBox(-1.0F, -2.0F, -0.255F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(3.0F, 2.0F, 0.0F));

        PartDefinition eyelash_l = eyes.addOrReplaceChild("eyelash_l", CubeListBuilder.create().texOffs(1, 3).addBox(0.0F, -1.0F, -0.26F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -1.0F, 0.0F));

        eyelash_l.addOrReplaceChild("el_3", CubeListBuilder.create().texOffs(2, 1).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 1.0F, -0.26F));

        eyelash_l.addOrReplaceChild("el_4", CubeListBuilder.create().texOffs(2, 1).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 0.74F));

        PartDefinition eyelash_r = eyes.addOrReplaceChild("eyelash_r", CubeListBuilder.create().texOffs(1, 3).addBox(0.0F, -1.0F, -0.26F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -1.0F, 0.0F));

        eyelash_r.addOrReplaceChild("el_2", CubeListBuilder.create().texOffs(2, 1).addBox(-0.5F, -1.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 1.0F, -0.26F));

        eyelash_r.addOrReplaceChild("el_1", CubeListBuilder.create().texOffs(2, 1).addBox(0.0F, -1.0F, -1.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, 1.0F, 0.74F));

        PartDefinition hair_l = head.addOrReplaceChild("hair_l", CubeListBuilder.create(), PartPose.offset(8.75F, 8.75F, -2.25F));

        hair_l.addOrReplaceChild("hair_l1", CubeListBuilder.create().texOffs(161, 23).addBox(-1.5F, 0.5F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.2182F));

        PartDefinition hair_r = head.addOrReplaceChild("hair_r", CubeListBuilder.create(), PartPose.offset(-8.75F, 8.75F, -2.25F));

        hair_r.addOrReplaceChild("hair_r1", CubeListBuilder.create().texOffs(139, 23).addBox(0.5F, 0.5F, -5.0F, 1.0F, 8.0F, 10.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.2182F));

        PartDefinition hair_lb = head.addOrReplaceChild("hair_lb", CubeListBuilder.create(), PartPose.offset(8.75F, 8.75F, 7.25F));

        hair_lb.addOrReplaceChild("hair_lb1", CubeListBuilder.create().texOffs(184, 26).addBox(-1.5F, 0.5F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0873F));

        PartDefinition hair_rb = head.addOrReplaceChild("hair_rb", CubeListBuilder.create(), PartPose.offset(-8.75F, 8.75F, 7.25F));

        hair_rb.addOrReplaceChild("hair_rb1", CubeListBuilder.create().texOffs(155, 18).addBox(0.5F, 0.5F, -3.5F, 1.0F, 8.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0873F));

        PartDefinition hair_b = head.addOrReplaceChild("hair_b", CubeListBuilder.create(), PartPose.offset(0.0F, 8.75F, 8.75F));

        hair_b.addOrReplaceChild("hair_b1", CubeListBuilder.create().texOffs(104, 32).addBox(-8.0F, 0.5F, -1.5F, 16.0F, 9.0F, 1.0F, new CubeDeformation(0.5F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.2618F, 0.0F, 0.0F));

        root.addOrReplaceChild("stevesit", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pump = root.addOrReplaceChild("pump", CubeListBuilder.create().texOffs(219, 13).addBox(-0.5F, -0.5F, -1.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, -39.0F, -14.0F));

        pump.addOrReplaceChild("membrane", CubeListBuilder.create().texOffs(228, 14).addBox(-4.0F, -2.5F, 0.0F, 8.0F, 5.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 3.0F));

        pump.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(215, 0).addBox(-1.0F, 0.0F, 10.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(220, 0).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, -0.2443F, 0.0F, 0.0F));

        pump.addOrReplaceChild("top", CubeListBuilder.create().texOffs(220, 0).addBox(-5.0F, -1.0F, 0.0F, 10.0F, 1.0F, 10.0F, new CubeDeformation(0.0F))
                .texOffs(215, 0).addBox(-1.0F, -1.0F, 10.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 3.0F, 0.2443F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 260, 260);
    }

    @Override
    public void setupAnim(@NonNull GhastGirlRenderState state) {
        super.setupAnim(state);
        applyHeadRotation(state.yRot, state.xRot);

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

        this.blinkAnimation.apply(state.blinkAnimation, state.ageInTicks);
    }

    private void applyHeadRotation(float yRot, float xRot) {
        yRot = Mth.clamp(yRot, -30.0F, 30.0F);
        xRot = Mth.clamp(xRot, -25.0F, 45.0F);

        this.getNeck().yRot = (yRot * ((float)Math.PI / 180F)) / 2;
        this.getNeck().xRot = (xRot * ((float)Math.PI / 180F)) / 2;
        this.getHead().yRot = (yRot * ((float)Math.PI / 180F)) / 2;
        this.getHead().xRot = (xRot * ((float)Math.PI / 180F)) / 2;
    }

    protected @NotNull ModelPart getHead() {
        return this.head;
    }

    protected @NotNull ModelPart getNeck() {
        return this.neck;
    }
}
