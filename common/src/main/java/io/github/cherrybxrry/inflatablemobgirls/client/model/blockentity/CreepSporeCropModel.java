package io.github.cherrybxrry.inflatablemobgirls.client.model.blockentity;

import io.github.cherrybxrry.inflatablemobgirls.client.animation.definitions.CreepSporeCropAnimation;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.state.CreepSporeCropState;
import net.minecraft.client.animation.KeyframeAnimation;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import org.jspecify.annotations.NonNull;

public class CreepSporeCropModel extends Model<CreepSporeCropState> {
    private final KeyframeAnimation idle0Animation;
    private final KeyframeAnimation idle1Animation;
    private final KeyframeAnimation idle2Animation;
    private final KeyframeAnimation idle3Animation;

    private final KeyframeAnimation ahogeShiverAnimation;
    private final KeyframeAnimation headShiverAnimation;

    private final KeyframeAnimation blinkAnimation;

    public CreepSporeCropModel(ModelPart root) {
        super(root, RenderTypes::entityCutout);

        this.idle0Animation = CreepSporeCropAnimation.IDLE0.bake(root);
        this.idle1Animation = CreepSporeCropAnimation.IDLE1.bake(root);
        this.idle2Animation = CreepSporeCropAnimation.IDLE2.bake(root);
        this.idle3Animation = CreepSporeCropAnimation.IDLE3.bake(root);

        this.ahogeShiverAnimation = CreepSporeCropAnimation.AHOGE_SHIVER.bake(root);
        this.headShiverAnimation = CreepSporeCropAnimation.HEAD_SHIVER.bake(root);

        this.blinkAnimation = CreepSporeCropAnimation.BLINK.bake(root);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, 1.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, -2.0F, 0.0F));

        PartDefinition hips = body.addOrReplaceChild("hips", CubeListBuilder.create().texOffs(24, 8).addBox(-2.0F, -3.0F, -5.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(44, 28).addBox(-0.5F, -2.75F, -5.002F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.0F, 3.0F));

        PartDefinition spine = hips.addOrReplaceChild("spine", CubeListBuilder.create().texOffs(14, 38).addBox(-1.4F, -3.0F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-0.1F, -3.0F, -2.5F));

        PartDefinition tits = spine.addOrReplaceChild("tits", CubeListBuilder.create(), PartPose.offsetAndRotation(0.1F, -2.5F, -2.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition tit_r = tits.addOrReplaceChild("tit_r", CubeListBuilder.create().texOffs(42, 15).mirror().addBox(-2.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.1309F, 0.0F));

        PartDefinition tit_l = tits.addOrReplaceChild("tit_l", CubeListBuilder.create().texOffs(42, 15).addBox(0.0F, 0.0F, -2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.1309F, 0.0F));

        PartDefinition neck = spine.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(8, 43).addBox(-0.5F, -0.75F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.1F, -3.0F, -0.5F));

        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 21).addBox(-3.0F, -5.0F, -2.5F, 6.0F, 5.0F, 5.0F, new CubeDeformation(0.0F))
                .texOffs(0, 11).addBox(-3.5F, -5.0F, -2.5F, 7.0F, 5.0F, 5.0F, new CubeDeformation(0.25F))
                .texOffs(24, 15).addBox(-2.5F, -5.75F, -2.0F, 5.0F, 1.0F, 4.0F, new CubeDeformation(0.25F)), PartPose.offset(0.0F, -0.25F, 0.0F));

        PartDefinition smile = head.addOrReplaceChild("smile", CubeListBuilder.create().texOffs(40, 4).addBox(-2.0F, -9.5F, -3.502F, 4.0F, 4.0F, 2.0F, new CubeDeformation(-1.5F)), PartPose.offset(0.0F, 7.0F, 0.5F));

        PartDefinition eyes = head.addOrReplaceChild("eyes", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -2.002F));

        PartDefinition eye_r = eyes.addOrReplaceChild("eye_r", CubeListBuilder.create().texOffs(34, 42).mirror().addBox(-2.75F, -10.75F, -3.002F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 9.25F, 2.502F));

        PartDefinition pupil_r = eye_r.addOrReplaceChild("pupil_r", CubeListBuilder.create().texOffs(44, 25).mirror().addBox(-0.5F, -1.0F, -0.502F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(-1.25F, -8.75F, -2.502F));

        PartDefinition eye_l = eyes.addOrReplaceChild("eye_l", CubeListBuilder.create().texOffs(34, 42).addBox(0.75F, -1.5F, -0.5F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition pupil_l = eye_l.addOrReplaceChild("pupil_l", CubeListBuilder.create().texOffs(44, 25).addBox(-0.5F, -1.0F, -0.502F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(1.25F, 0.5F, 0.0F));

        PartDefinition eyesclosed = head.addOrReplaceChild("eyesclosed", CubeListBuilder.create(), PartPose.offset(0.0F, -2.5F, -1.002F));

        PartDefinition closed_r = eyesclosed.addOrReplaceChild("closed_r", CubeListBuilder.create().texOffs(40, 10).addBox(-3.25F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition closed_l = eyesclosed.addOrReplaceChild("closed_l", CubeListBuilder.create().texOffs(40, 10).mirror().addBox(0.25F, -1.0F, -1.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(-0.5F)).mirror(false), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition fringe = head.addOrReplaceChild("fringe", CubeListBuilder.create().texOffs(26, 38).addBox(-4.65F, 0.25F, 0.25F, 6.0F, 3.0F, 1.0F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0.0F, -5.25F, -2.752F, -0.0873F, 0.0F, 0.0F));

        PartDefinition ahoge = head.addOrReplaceChild("ahoge", CubeListBuilder.create().texOffs(44, 19).addBox(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 0.01F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -6.0F, 0.0F));

        PartDefinition pigtails = head.addOrReplaceChild("pigtails", CubeListBuilder.create(), PartPose.offset(1.0F, 6.5F, 0.0F));

        PartDefinition pigtails_l = pigtails.addOrReplaceChild("pigtails_l", CubeListBuilder.create().texOffs(40, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -7.0F, 2.5F, 0.48F, 0.1309F, -0.48F));

        PartDefinition pigtails_r = pigtails.addOrReplaceChild("pigtails_r", CubeListBuilder.create().texOffs(40, 35).addBox(-1.5F, 0.0F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 43).addBox(-1.0F, 2.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -7.0F, 2.5F, 0.48F, -0.1309F, 0.48F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    @Override
    public void setupAnim(@NonNull CreepSporeCropState state) {
        super.setupAnim(state);

        this.idle0Animation.apply(state.idle0Animation, state.ageInTicks);
        this.idle1Animation.apply(state.idle1Animation, state.ageInTicks);
        this.idle2Animation.apply(state.idle2Animation, state.ageInTicks);
        this.idle3Animation.apply(state.idle3Animation, state.ageInTicks);

        this.ahogeShiverAnimation.apply(state.ahogeShiverAnimation, state.ageInTicks);
        this.headShiverAnimation.apply(state.headShiverAnimation, state.ageInTicks);

        this.blinkAnimation.apply(state.blinkAnimation, state.ageInTicks);
    }
}
