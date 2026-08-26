package io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import com.mojang.math.Transformation;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.block.AbstractMobGirlSkullBlock;
import io.github.cherrybxrry.inflatablemobgirls.block.MobGirlSkullBlock;
import io.github.cherrybxrry.inflatablemobgirls.block.MobGirlWallSkullBlock;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.MobGirlSkullBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.client.model.blockentity.CreeperGirlHeadModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.state.MobGirlSkullBlockRenderState;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.WallAndGroundTransformations;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class MobGirlSkullBlockRenderer implements BlockEntityRenderer<MobGirlSkullBlockEntity, MobGirlSkullBlockRenderState> {
    public static final WallAndGroundTransformations<Transformation> TRANSFORMATIONS = new WallAndGroundTransformations<>(MobGirlSkullBlockRenderer::createWallTransformation, MobGirlSkullBlockRenderer::createGroundTransformation, 16);
    private final Function<AbstractMobGirlSkullBlock.Type, SkullModelBase> modelByType;
    private static final Map<AbstractMobGirlSkullBlock.Type, Identifier> SKIN_BY_TYPE = Util.make(Maps.newHashMap(), (map) -> {
        map.put(AbstractMobGirlSkullBlock.Types.CREEPER_GIRL, Constants.id("textures/entity/creeper_girl/creeper_girl.png"));
    });

    public static @Nullable SkullModelBase createModel(EntityModelSet modelSet, AbstractMobGirlSkullBlock.Type type) {
        if (type instanceof AbstractMobGirlSkullBlock.Types modType) {
            switch (modType) {
                case CREEPER_GIRL: return new CreeperGirlHeadModel(modelSet.bakeLayer(ModModelLayers.CREEPER_GIRL_HEAD));
                default: throw new MatchException(null, null);
            }
        } else {
            return null;
        }
    }

    public MobGirlSkullBlockRenderer(BlockEntityRendererProvider.Context context) {
        EntityModelSet modelSet = context.entityModelSet();
        this.modelByType = Util.memoize((type) -> Objects.requireNonNull(createModel(modelSet, type)));
    }

    @Override
    public @NonNull MobGirlSkullBlockRenderState createRenderState() {
        return new MobGirlSkullBlockRenderState();
    }

    @Override
    public void extractRenderState(@NonNull MobGirlSkullBlockEntity blockEntity, @NonNull MobGirlSkullBlockRenderState state, float partialTicks, @NonNull Vec3 cameraPosition, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, state, partialTicks, cameraPosition, breakProgress);
        state.animationProgress = blockEntity.getAnimation(partialTicks);
        BlockState blockState = blockEntity.getBlockState();
        if (blockState.getBlock() instanceof MobGirlWallSkullBlock) {
            Direction facing = blockState.getValue(MobGirlWallSkullBlock.FACING);
            state.transformation = TRANSFORMATIONS.wallTransformation(facing);
        } else {
            state.transformation = TRANSFORMATIONS.freeTransformations(blockState.getValue(MobGirlSkullBlock.ROTATION));
        }

        state.skullType = ((AbstractMobGirlSkullBlock)blockState.getBlock()).getType();
        state.renderType = getSkullRenderType(state.skullType);
    }

    @Override
    public void submit(@NonNull MobGirlSkullBlockRenderState state, @NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, @NonNull CameraRenderState cameraRenderState) {
        SkullModelBase model = this.modelByType.apply(state.skullType);
        poseStack.pushPose();
        poseStack.mulPose(state.transformation);
        submitSkull(state.animationProgress, poseStack, submitNodeCollector, state.lightCoords, model, state.renderType, 0, state.breakProgress);
        poseStack.popPose();
    }

    public static void submitSkull(float animationValue, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, SkullModelBase model, RenderType renderType, int outlineColor, ModelFeatureRenderer.@Nullable CrumblingOverlay breakProgress) {
        SkullModelBase.State modelState = new SkullModelBase.State();
        modelState.animationPos = animationValue;
        submitNodeCollector.submitModel(model, modelState, poseStack, renderType, lightCoords, OverlayTexture.NO_OVERLAY, outlineColor, breakProgress);
    }

    public static RenderType getSkullRenderType(AbstractMobGirlSkullBlock.Type type) {
        return getSkullRenderType(type, null);
    }

    public static RenderType getSkullRenderType(AbstractMobGirlSkullBlock.Type type, Identifier textureOverride) {
        return RenderTypes.entityCutoutZOffset(textureOverride != null ? textureOverride : SKIN_BY_TYPE.get(type));
    }

    private static Transformation createWallTransformation(Direction wallDirection) {
        float offset = 0.25F;
        return new Transformation(new Vector3f(0.5F - (float)wallDirection.getStepX() * offset, offset, 0.5F - (float)wallDirection.getStepZ() * offset), Axis.YP.rotationDegrees(-wallDirection.getOpposite().toYRot()), new Vector3f(-1.0F, -1.0F, 1.0F), null);
    }

    private static Transformation createGroundTransformation(int segment) {
        return new Transformation((new Matrix4f()).translation(0.5F, 0.0F, 0.5F).rotate(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(segment))).scale(-1.0F, -1.0F, 1.0F));
    }
}
