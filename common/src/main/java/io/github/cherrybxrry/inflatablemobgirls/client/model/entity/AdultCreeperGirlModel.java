package io.github.cherrybxrry.inflatablemobgirls.client.model.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.animation.definitions.AdultCreeperGirlAnimation;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.CreeperGirlRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class AdultCreeperGirlModel extends CreeperGirlModel {
    private final ModelPart neck;
    private final ModelPart head;

    private final KeyframeAnimation idle0Animation;
    private final KeyframeAnimation idle1Animation;
    private final KeyframeAnimation idle2Animation;
    private final KeyframeAnimation idle3Animation;
    private final KeyframeAnimation idle4Animation;
    private final KeyframeAnimation idle5Animation;

    private final KeyframeAnimation idleCharged0Animation;
    private final KeyframeAnimation idleCharged1Animation;
    private final KeyframeAnimation idleCharged2Animation;

    private final KeyframeAnimation sit0Animation;
    private final KeyframeAnimation sit1Animation;
    private final KeyframeAnimation sit2Animation;

    private final KeyframeAnimation sitCharged0Animation;
    private final KeyframeAnimation sitCharged1Animation;
    private final KeyframeAnimation sitCharged2Animation;

    private final KeyframeAnimation inflate1Animation;
    private final KeyframeAnimation inflate2Animation;
    private final KeyframeAnimation inflate3Animation;
    private final KeyframeAnimation regen1Animation;
    private final KeyframeAnimation regen2Animation;
    private final KeyframeAnimation regen3Animation;

    private final KeyframeAnimation inflateCharged1Animation;
    private final KeyframeAnimation inflateCharged2Animation;
    private final KeyframeAnimation inflateCharged3Animation;

    public AdultCreeperGirlModel(ModelPart root) {
        super(root, AdultCreeperGirlAnimation.WALK, AdultCreeperGirlAnimation.ATTACK);
        this.neck = root
                .getChild("root")
                .getChild("hips")
                .getChild("spine")
                .getChild("chest")
                .getChild("neck");
        this.head = this.neck.getChild("head");

        this.idle0Animation = AdultCreeperGirlAnimation.IDLE0.bake(root);
        this.idle1Animation = AdultCreeperGirlAnimation.IDLE1.bake(root);
        this.idle2Animation = AdultCreeperGirlAnimation.IDLE2.bake(root);
        this.idle3Animation = AdultCreeperGirlAnimation.SITTING3.bake(root);
        this.idle4Animation = AdultCreeperGirlAnimation.IDLE4.bake(root);
        this.idle5Animation = AdultCreeperGirlAnimation.IDLE5.bake(root);

        this.idleCharged0Animation = AdultCreeperGirlAnimation.IDLE0.bake(root);
        this.idleCharged1Animation = AdultCreeperGirlAnimation.IDLE2.bake(root);
        this.idleCharged2Animation = AdultCreeperGirlAnimation.IDLE_CHARGED.bake(root);

        this.sit0Animation = AdultCreeperGirlAnimation.SITTING0.bake(root);
        this.sit1Animation = AdultCreeperGirlAnimation.SITTING1.bake(root);
        this.sit2Animation = AdultCreeperGirlAnimation.SITTING2.bake(root);

        this.sitCharged0Animation = AdultCreeperGirlAnimation.SITTING0.bake(root);
        this.sitCharged1Animation = AdultCreeperGirlAnimation.SITTING2.bake(root);
        this.sitCharged2Animation = AdultCreeperGirlAnimation.SITTING_CHARGED.bake(root);

        this.inflate1Animation = AdultCreeperGirlAnimation.INFLATE1.bake(root);
        this.inflate2Animation = AdultCreeperGirlAnimation.INFLATE2.bake(root);
        this.inflate3Animation = AdultCreeperGirlAnimation.INFLATE3.bake(root);
        this.regen1Animation = AdultCreeperGirlAnimation.REGEN1.bake(root);
        this.regen2Animation = AdultCreeperGirlAnimation.REGEN2.bake(root);
        this.regen3Animation = AdultCreeperGirlAnimation.REGEN3.bake(root);

        this.inflateCharged1Animation = AdultCreeperGirlAnimation.INFLATE_CHARGED1.bake(root);
        this.inflateCharged2Animation = AdultCreeperGirlAnimation.INFLATE_CHARGED2.bake(root);
        this.inflateCharged3Animation = AdultCreeperGirlAnimation.INFLATE_CHARGED3.bake(root);
    }

    @Override
    protected @NotNull ModelPart getHead() {
        return this.head;
    }

    @Override
    protected @NotNull ModelPart getNeck() {
        return this.neck;
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 16.0F, 0.0F));

        root.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(0, 45).mirror().addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, -3.0F));

        root.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(0, 45).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, -3.0F));

        root.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(4.0F, 2.0F, 3.0F));

        root.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(0, 55).addBox(-2.0F, 0.0F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 3.0F));

        PartDefinition hips = root.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(0, 35).addBox(-4.0F, -4.0F, -6.0F, 8.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 3.0F));

        hips.addOrReplaceChild("inside", CubeListBuilder.create().texOffs(74, 0).addBox(-4.0F, -9.0F, -3.0F, 8.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 6.0F, -3.0F));

        PartDefinition spine = hips.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(0, 26).addBox(-3.0F, -4.0F, -2.0F, 6.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -4.0F, -3.0F));

        PartDefinition chest = spine.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -5.0F, -2.5F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -3.0F, 0.0F));

        PartDefinition breasts = chest.addOrReplaceChild("breasts", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -4.0F, -2.5F, 0.3054F, 0.0F, 0.0F));

        breasts.addOrReplaceChild("breast_l", CubeListBuilder.create().texOffs(16, 70).addBox(0.0F, 0.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(10, 66).addBox(1.0F, 1.0F, -4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1745F, 0.0F));

        breasts.addOrReplaceChild("breast_r", CubeListBuilder.create().texOffs(16, 70).mirror().addBox(-4.0F, 0.0F, -4.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(10, 66).mirror().addBox(-3.0F, 1.0F, -4.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1745F, 0.0F));

        PartDefinition neck = chest.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -1.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -5.0F, 0.0F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 1).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 57).addBox(-4.0F, -7.0F, -4.5F, 8.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(4.0F, -7.0F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-5.0F, -7.0F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(26, 16).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.5F, 0.0F));

        head.addOrReplaceChild("ahoge", CubeListBuilder.create().texOffs(27, 22).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -8.0F, -0.5F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, -4.01F));

        eyes.addOrReplaceChild("eyelash_r", CubeListBuilder.create().texOffs(0, 7).addBox(-2.0F, -1.0F, -0.025F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, -2.0F, 0.0F));

        eyes.addOrReplaceChild("eyelash_l", CubeListBuilder.create().texOffs(0, 7).addBox(-1.0F, -1.0F, -0.025F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, -2.0F, 0.0F));

        PartDefinition eye_r = eyes.addOrReplaceChild("eye_r", CubeListBuilder.create().texOffs(0, 4).addBox(-2.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 0.0F, 0.0F));

        eye_r.addOrReplaceChild("pupil_r", CubeListBuilder.create().texOffs(0, 16).addBox(-1.0F, -2.0F, -0.02F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0F, -0.25F, 0.0F));

        PartDefinition eye_l = eyes.addOrReplaceChild("eye_l", CubeListBuilder.create().texOffs(0, 4).mirror().addBox(-1.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 0.0F, 0.0F));

        eye_l.addOrReplaceChild("pupil_l", CubeListBuilder.create().texOffs(0, 16).addBox(0.0F, -2.0F, -0.02F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -0.25F, 0.0F));

        eyes.addOrReplaceChild("eyelid_l", CubeListBuilder.create().texOffs(21, 16).mirror().addBox(-1.0F, 0.0F, -0.022F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, -3.0F, 1.0F));

        eyes.addOrReplaceChild("eyelid_r", CubeListBuilder.create().texOffs(21, 16).addBox(-1.0F, 0.0F, -0.022F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.0F, -3.0F, 1.0F));

        head.addOrReplaceChild("fringe", CubeListBuilder.create().texOffs(60, 25).addBox(-5.0F, 0.0F, 0.0F, 10.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.0F, -7.0F, -4.5F, -0.0873F, 0.0F, 0.0F));

        head.addOrReplaceChild("pigtail_l", CubeListBuilder.create().texOffs(20, 27).addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(70, 3).addBox(-1.0F, 4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.0F, -1.0F, 4.5F, 0.5236F, 0.0F, -0.5236F));

        head.addOrReplaceChild("pigtail_r", CubeListBuilder.create().texOffs(20, 27).mirror().addBox(-2.0F, 0.0F, -2.0F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false)
                .texOffs(70, 3).mirror().addBox(-1.0F, 4.0F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-5.0F, -1.0F, 4.5F, 0.5236F, 0.0F, 0.5236F));

        head.addOrReplaceChild("mouth_happy", CubeListBuilder.create().texOffs(22, 35).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.01F));

        head.addOrReplaceChild("mouth_smile", CubeListBuilder.create().texOffs(43, 0).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.01F));

        head.addOrReplaceChild("mouth_gasp", CubeListBuilder.create().texOffs(43, 3).addBox(-4.0F, 0.0F, 0.0F, 8.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -3.01F));

        head.addOrReplaceChild("blush", CubeListBuilder.create().texOffs(43, 6).addBox(-4.0F, 0.5F, 3.0F, 8.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -2.5F, -6.01F));

        spine.addOrReplaceChild("belly1", CubeListBuilder.create().texOffs(60, 6).addBox(-5.0F, -0.3F, -9.7F, 10.0F, 9.0F, 10.0F, new CubeDeformation(-0.3F)), PartPose.offsetAndRotation(0.0F, -4.5F, 0.5F, 0.0873F, 0.0F, 0.0F));

        PartDefinition belly2 = spine.addOrReplaceChild("belly2", CubeListBuilder.create().texOffs(6, 79).addBox(-5.0F, -1.1F, -10.9F, 10.0F, 11.0F, 10.0F, new CubeDeformation(-0.1F))
                .texOffs(17, 47).addBox(-6.0F, -0.1F, -10.9F, 12.0F, 9.0F, 10.0F, new CubeDeformation(-0.1F))
                .texOffs(28, 26).addBox(-5.0F, -0.1F, -11.9F, 10.0F, 9.0F, 12.0F, new CubeDeformation(-0.1F)), PartPose.offsetAndRotation(0.0F, -4.5F, 1.0F, 0.0873F, 0.0F, 0.0F));

        belly2.addOrReplaceChild("bellybutton", CubeListBuilder.create().texOffs(0, 66).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 4.9F, -11.5F));

        PartDefinition belly_overexerted = spine.addOrReplaceChild("belly_overexerted", CubeListBuilder.create().texOffs(46, 73).addBox(-7.5F, -1.0F, -15.0F, 15.0F, 13.0F, 14.0F, new CubeDeformation(0.0F))
                .texOffs(72, 31).addBox(-6.5F, 12.0F, -14.0F, 13.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(72, 44).addBox(-6.5F, -2.0F, -14.0F, 13.0F, 1.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(0, 100).addBox(7.5F, 0.0F, -14.0F, 1.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(100, 0).addBox(-8.5F, 0.0F, -14.0F, 1.0F, 11.0F, 12.0F, new CubeDeformation(0.0F))
                .texOffs(86, 57).addBox(-6.5F, 0.0F, -16.0F, 13.0F, 11.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(26, 100).addBox(-6.5F, 0.0F, -1.0F, 13.0F, 11.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -6.0F, 1.0F, 0.1309F, 0.0F, 0.0F));

        belly_overexerted.addOrReplaceChild("BellyButton2", CubeListBuilder.create().texOffs(24, 0).addBox(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 5.5F, -16.0F));

        return LayerDefinition.create(meshdefinition, 128, 128);
    }

    @Override
    public void setupAnim(@NonNull CreeperGirlRenderState state) {
        super.setupAnim(state);
        this.applySpawnerAnimation(this.idle0Animation, state.isSpawner);

        this.idle0Animation.apply(state.idle0AnimationState, state.ageInTicks);
        this.idle1Animation.apply(state.idle1AnimationState, state.ageInTicks);
        this.idle2Animation.apply(state.idle2AnimationState, state.ageInTicks);
        this.idle3Animation.apply(state.idleRegen0AnimationState, state.ageInTicks);
        this.idle4Animation.apply(state.idleRegen1AnimationState, state.ageInTicks);
        this.idle5Animation.apply(state.idleRegen2AnimationState, state.ageInTicks);

        this.idleCharged0Animation.apply(state.idleCharged0AnimationState, state.ageInTicks);
        this.idleCharged1Animation.apply(state.idleCharged1AnimationState, state.ageInTicks);
        this.idleCharged2Animation.apply(state.idleCharged2AnimationState, state.ageInTicks);

        this.sit0Animation.apply(state.sit0AnimationState, state.ageInTicks);
        this.sit1Animation.apply(state.sit1AnimationState, state.ageInTicks);
        this.sit2Animation.apply(state.sit2AnimationState, state.ageInTicks);

        this.sitCharged0Animation.apply(state.sitCharged0AnimationState, state.ageInTicks);
        this.sitCharged1Animation.apply(state.sitCharged1AnimationState, state.ageInTicks);
        this.sitCharged2Animation.apply(state.sitCharged2AnimationState, state.ageInTicks);

        this.inflate1Animation.apply(state.inflate1AnimationState, state.ageInTicks);
        this.inflate2Animation.apply(state.inflate2AnimationState, state.ageInTicks);
        this.inflate3Animation.apply(state.inflate3AnimationState, state.ageInTicks);
        this.regen1Animation.apply(state.regen1AnimationState, state.ageInTicks);
        this.regen2Animation.apply(state.regen2AnimationState, state.ageInTicks);
        this.regen3Animation.apply(state.regen3AnimationState, state.ageInTicks);

        this.inflateCharged1Animation.apply(state.inflateCharged1AnimationState, state.ageInTicks);
        this.inflateCharged2Animation.apply(state.inflateCharged2AnimationState, state.ageInTicks);
        this.inflateCharged3Animation.apply(state.inflateCharged3AnimationState, state.ageInTicks);
    }
}
