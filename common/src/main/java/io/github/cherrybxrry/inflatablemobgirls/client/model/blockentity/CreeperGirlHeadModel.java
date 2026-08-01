package io.github.cherrybxrry.inflatablemobgirls.client.model.blockentity;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.model.object.skull.SkullModelBase;
import org.jspecify.annotations.NonNull;

public class CreeperGirlHeadModel extends SkullModelBase {
    private final ModelPart head;
    private final ModelPart ahoge;
    private final ModelPart mouth_happy;

    public CreeperGirlHeadModel(ModelPart root) {
        super(root);
        this.head = root.getChild("head");
        this.ahoge = this.head.getChild("ahoge");
        this.mouth_happy = this.head.getChild("mouth_happy");
    }

    public static LayerDefinition createHeadLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 1).addBox(-4.0F, -7.0F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .texOffs(52, 57).addBox(-4.0F, -7.0F, -4.5F, 8.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(4.0F, -7.0F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(32, 0).addBox(-5.0F, -7.0F, -4.5F, 1.0F, 7.0F, 9.0F, new CubeDeformation(0.0F))
                .texOffs(26, 16).addBox(-4.0F, -8.0F, -4.5F, 8.0F, 1.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.ZERO);

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

        return LayerDefinition.create(mesh, 128, 128);
    }

    public void setupAnim(final SkullModelBase.@NonNull State state) {
        super.setupAnim(state);
        this.head.yRot = state.yRot * ((float)Math.PI / 180F);
        this.head.xRot = state.xRot * ((float)Math.PI / 180F);

        this.ahoge.zRot = (float) Math.sin(state.animationPos * (float)Math.PI * 0.5F) * 0.5F;

        this.mouth_happy.y = -1.5F;
        this.mouth_happy.z = -4.01F;
        this.mouth_happy.xScale = 0.5F;
        this.mouth_happy.yScale = 0.5F;
        this.mouth_happy.zScale = 0.5F;
    }
}
