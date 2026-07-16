package io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import io.github.cherrybxrry.inflatablemobgirls.blocks.CreepSporeBlock;
import io.github.cherrybxrry.inflatablemobgirls.blocks.entity.CreepSporeBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.client.model.blockentity.CreepSporeCropModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.state.CreepSporeCropState;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModModelLayers;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;

public class CreepSporeCropRenderer implements BlockEntityRenderer<CreepSporeBlockEntity, CreepSporeCropState> {
    private static final Map<Direction, Transformation> TRANSFORMATIONS = Util.makeEnumMap(Direction.class, CreepSporeCropRenderer::createModelTransformation);

    private final CreepSporeCropModel model;

    public CreepSporeCropRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new CreepSporeCropModel(context.bakeLayer(ModModelLayers.CREEPSPORE_CROP));
    }

    @Override
    public @NonNull CreepSporeCropState createRenderState() {
        return new CreepSporeCropState();
    }

    @Override
    public void extractRenderState(@NonNull CreepSporeBlockEntity blockEntity, @NonNull CreepSporeCropState state, float partialTicks, @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.ageInTicks = blockEntity.tickCount + partialTicks;
        state.age = blockEntity.getBlockState().getValueOrElse(CreepSporeBlock.AGE, 0);
        state.direction = blockEntity.getBlockState().getValueOrElse(CreepSporeBlock.FACING, Direction.NORTH);

        state.idle0Animation.copyFrom(blockEntity.idle0Animation);
        state.idle1Animation.copyFrom(blockEntity.idle1Animation);
        state.idle2Animation.copyFrom(blockEntity.idle2Animation);
        state.idle3Animation.copyFrom(blockEntity.idle3Animation);

        state.ahogeShiverAnimation.copyFrom(blockEntity.ahogeShiverAnimation);
        state.headShiverAnimation.copyFrom(blockEntity.headShiverAnimation);

        state.blinkAnimation.copyFrom(blockEntity.blinkAnimation);

        state.texture = blockEntity.getTexture();
    }

    @Override
    public void submit(@NonNull CreepSporeCropState creepSporeCropState, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        this.model.setupAnim(creepSporeCropState);
        poseStack.pushPose();
        poseStack.mulPose(modelTransformation(creepSporeCropState.direction));
        submitNodeCollector.submitModel(this.model, creepSporeCropState, poseStack, creepSporeCropState.texture, creepSporeCropState.lightCoords, OverlayTexture.NO_OVERLAY, 0, creepSporeCropState.breakProgress);
        poseStack.popPose();
    }

    public static Transformation modelTransformation(Direction facing) {
        return TRANSFORMATIONS.get(facing);
    }

    private static Transformation createModelTransformation(Direction entityDirection) {
        return new Transformation((new Matrix4f())
                .translation(0.5F, 0.0F, 0.5F)
                .rotate(Axis.YP.rotationDegrees(-entityDirection.getOpposite().toYRot()))
                .rotate(Axis.ZP.rotationDegrees(180F))
        );
    }
}
