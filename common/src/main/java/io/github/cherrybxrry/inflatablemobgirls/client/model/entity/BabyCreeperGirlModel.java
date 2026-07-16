package io.github.cherrybxrry.inflatablemobgirls.client.model.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.animation.definitions.BabyCreeperGirlAnimation;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.CreeperGirlRenderState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class BabyCreeperGirlModel extends CreeperGirlModel {
    private final ModelPart neck;
    private final ModelPart head;

    private final KeyframeAnimation idle0Animation;
    private final KeyframeAnimation idle1Animation;
    private final KeyframeAnimation idle2Animation;

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

    private final KeyframeAnimation inflateCharged1Animation;
    private final KeyframeAnimation inflateCharged2Animation;
    private final KeyframeAnimation inflateCharged3Animation;

    public BabyCreeperGirlModel(ModelPart root) {
        super(root, BabyCreeperGirlAnimation.WALK, BabyCreeperGirlAnimation.ATTACK);
        this.neck = root
                .getChild("root")
                .getChild("body")
                .getChild("hips")
                .getChild("spine")
                .getChild("neck");
        this.head = this.neck.getChild("head");

        this.idle0Animation = BabyCreeperGirlAnimation.IDLE0.bake(root);
        this.idle1Animation = BabyCreeperGirlAnimation.IDLE1.bake(root);
        this.idle2Animation = BabyCreeperGirlAnimation.IDLE2.bake(root);

        this.idleCharged0Animation = BabyCreeperGirlAnimation.IDLE0.bake(root);
        this.idleCharged1Animation = BabyCreeperGirlAnimation.IDLE2.bake(root);
        this.idleCharged2Animation = BabyCreeperGirlAnimation.IDLE_CHARGED.bake(root);

        this.sit0Animation = BabyCreeperGirlAnimation.SITTING0.bake(root);
        this.sit1Animation = BabyCreeperGirlAnimation.SITTING1.bake(root);
        this.sit2Animation = BabyCreeperGirlAnimation.SITTING2.bake(root);

        this.sitCharged0Animation = BabyCreeperGirlAnimation.SITTING0.bake(root);
        this.sitCharged1Animation = BabyCreeperGirlAnimation.SITTING2.bake(root);
        this.sitCharged2Animation = BabyCreeperGirlAnimation.SITTING_CHARGED.bake(root);

        this.inflate1Animation = BabyCreeperGirlAnimation.INFLATE1.bake(root);
        this.inflate2Animation = BabyCreeperGirlAnimation.INFLATE2.bake(root);
        this.inflate3Animation = BabyCreeperGirlAnimation.INFLATE3.bake(root);

        this.inflateCharged1Animation = BabyCreeperGirlAnimation.INFLATE_CHARGED1.bake(root);
        this.inflateCharged2Animation = BabyCreeperGirlAnimation.INFLATE_CHARGED2.bake(root);
        this.inflateCharged3Animation = BabyCreeperGirlAnimation.INFLATE_CHARGED3.bake(root);
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

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -5.0F, 0.0F));

        body.addOrReplaceChild("leg_br", CubeListBuilder.create().texOffs(14, 32).addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.0F, 2.0F));

        body.addOrReplaceChild("leg_bl", CubeListBuilder.create().texOffs(14, 32).mirror().addBox(-1.0F, 0.0F, -0.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 2.0F, 2.0F));

        body.addOrReplaceChild("leg_fr", CubeListBuilder.create().texOffs(40, 41).addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 2.0F, -2.0F));

        body.addOrReplaceChild("leg_fl", CubeListBuilder.create().texOffs(40, 41).mirror().addBox(-1.0F, 0.0F, -1.5F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(2.0F, 2.0F, -2.0F));

        PartDefinition hips = body.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(24, 8).addBox(-2.0F, -3.0F, -5.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 28).addBox(-0.5F, -2.75F, -5.002F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 3.0F));

        hips.addOrReplaceChild("guts", CubeListBuilder.create().texOffs(44, 22).addBox(0.0F, -2.02F, -2.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0F, -1.0F, -2.0F));

        PartDefinition spine = hips.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(14, 38).addBox(-1.4F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, -3.0F, -2.5F));

        PartDefinition tits = spine.addOrReplaceChild("tits", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1F, -2.5F, -2.0F, 0.1309F, 0.0F, 0.0F));

        tits.addOrReplaceChild("tit_r", CubeListBuilder.create().texOffs(42, 15).mirror().addBox(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

        tits.addOrReplaceChild("tit_l", CubeListBuilder.create().texOffs(42, 15).addBox(0.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition belly = spine.addOrReplaceChild("belly", CubeListBuilder.create().texOffs(22, 31).addBox(-2.0F, 0.0F, -4.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1F, -0.7F, -0.5F, 0.1745F, 0.0F, 0.0F));

        belly.addOrReplaceChild("belly_button", CubeListBuilder.create().texOffs(26, 42).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 1.55F, -4.0F));

        PartDefinition belly2 = spine.addOrReplaceChild("belly2", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, 0.0F, -6.0F, 6.0F, 5.0F, 6.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1F, -1.5F, -0.5F, 0.1745F, 0.0F, 0.0F));

        belly2.addOrReplaceChild("belly_button2", CubeListBuilder.create().texOffs(26, 42).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(-0.4F)), PartPose.offset(0.0F, 2.65F, -6.0F));

        PartDefinition belly3 = spine.addOrReplaceChild("belly3", CubeListBuilder.create().texOffs(8, 46).addBox(-3.5F, -0.25F, -6.75F, 7.0F, 6.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(0.1F, -1.5F, -0.5F, 0.1745F, 0.0F, 0.0F));

        belly3.addOrReplaceChild("belly_button3", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -0.5F, -0.5F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offset(0.0F, 2.9F, -7.0F));

        PartDefinition neck = spine.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(8, 43).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, -3.0F, -0.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 21).addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-3.5F, -5.0F, -2.5F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(24, 15).addBox(-2.5F, -5.75F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.25F))
                .texOffs(44, 19).addBox(-2.0F, -9.0F, 0.0F, 4.0F, 3.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -0.25F, 0.0F));

        head.addOrReplaceChild("blush", CubeListBuilder.create().texOffs(38, 31).addBox(-3.0F, -3.0F, 0.0008F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, -1.502F));

        head.addOrReplaceChild("blush2", CubeListBuilder.create().texOffs(0, 39).addBox(-5.75F, -2.0F, 0.5008F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, -1.0F, -2.002F));

        head.addOrReplaceChild("blush3", CubeListBuilder.create().texOffs(40, 0).addBox(-5.75F, -2.0F, 0.5008F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.75F, -1.0F, -2.002F));

        head.addOrReplaceChild("smile", CubeListBuilder.create().texOffs(40, 4).addBox(-2.0F, -9.5F, -3.502F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 7.0F, 0.5F));

        head.addOrReplaceChild("puffy", CubeListBuilder.create().texOffs(24, 0).addBox(-2.0F, -9.5F, -3.502F, 4.0F, 4.0F, 4.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 6.75F, 0.5F));

        head.addOrReplaceChild("shock", CubeListBuilder.create().texOffs(0, 32).addBox(-2.0F, -1.5F, -2.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, -1.0F, -1.002F));

        head.addOrReplaceChild("happy", CubeListBuilder.create().texOffs(0, 21).addBox(-3.0F, -2.5F, -2.75F, 6.0F, 6.0F, 5.0F, new CubeDeformation(-2.25F)), PartPose.offset(0.0F, -1.0F, -1.002F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -2.002F));

        PartDefinition eye_r = eyes.addOrReplaceChild("eye_r", CubeListBuilder.create().texOffs(34, 42).mirror().addBox(-2.75F, -10.75F, -3.002F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.25F, 2.502F));

        eye_r.addOrReplaceChild("pupil_r", CubeListBuilder.create().texOffs(44, 25).mirror().addBox(-0.5F, -1.0F, -0.502F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.25F, -8.75F, -2.502F));

        PartDefinition eye_l = eyes.addOrReplaceChild("eye_l", CubeListBuilder.create().texOffs(34, 42).addBox(0.75F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        eye_l.addOrReplaceChild("pupil_l", CubeListBuilder.create().texOffs(44, 25).addBox(-0.5F, -1.0F, -0.502F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, 0.5F, 0.0F));

        PartDefinition eyesclosed = head.addOrReplaceChild("eyesclosed", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -1.002F));

        eyesclosed.addOrReplaceChild("closed_r", CubeListBuilder.create().texOffs(40, 10).addBox(-3.25F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        eyesclosed.addOrReplaceChild("closed_l", CubeListBuilder.create().texOffs(40, 10).mirror().addBox(0.25F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("fringe", CubeListBuilder.create().texOffs(26, 38).addBox(-4.65F, 0.25F, 0.25F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -5.25F, -2.752F, -0.0873F, 0.0F, 0.0F));

        PartDefinition pigtails = head.addOrReplaceChild("pigtails", CubeListBuilder.create(), PartPose.offset(1.0F, 6.5F, 0.0F));

        pigtails.addOrReplaceChild("pigtails_l", CubeListBuilder.create().texOffs(40, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -7.0F, 2.5F, 0.48F, 0.1309F, -0.48F));

        pigtails.addOrReplaceChild("pigtails_r", CubeListBuilder.create().texOffs(40, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -7.0F, 2.5F, 0.48F, -0.1309F, 0.48F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull CreeperGirlRenderState state) {
        super.setupAnim(state);

        this.idle0Animation.apply(state.idle0AnimationState, state.ageInTicks);
        this.idle1Animation.apply(state.idle1AnimationState, state.ageInTicks);
        this.idle2Animation.apply(state.idle2AnimationState, state.ageInTicks);

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

        this.inflateCharged1Animation.apply(state.inflateCharged1AnimationState, state.ageInTicks);
        this.inflateCharged2Animation.apply(state.inflateCharged2AnimationState, state.ageInTicks);
        this.inflateCharged3Animation.apply(state.inflateCharged3AnimationState, state.ageInTicks);
    }
}
