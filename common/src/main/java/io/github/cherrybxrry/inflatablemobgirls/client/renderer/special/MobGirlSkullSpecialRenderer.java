package io.github.cherrybxrry.inflatablemobgirls.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.github.cherrybxrry.inflatablemobgirls.blocks.AbstractMobGirlSkullBlock;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.MobGirlSkullBlockRenderer;
import net.minecraft.client.model.object.skull.SkullModelBase;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.special.NoDataSpecialModelRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.resources.Identifier;
import org.joml.Vector3fc;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;

public class MobGirlSkullSpecialRenderer implements NoDataSpecialModelRenderer {
    private final SkullModelBase model;
    private final float animation;
    private final RenderType renderType;

    public MobGirlSkullSpecialRenderer(SkullModelBase model, float animation, RenderType renderType) {
        this.model = model;
        this.animation = animation;
        this.renderType = renderType;
    }

    public void submit(@NonNull PoseStack poseStack, @NonNull SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        MobGirlSkullBlockRenderer.submitSkull(this.animation, poseStack, submitNodeCollector, lightCoords, this.model, this.renderType, outlineColor, null);
    }

    public void getExtents(@NonNull Consumer<Vector3fc> output) {
        PoseStack poseStack = new PoseStack();
        SkullModelBase.State modelState = new SkullModelBase.State();
        modelState.animationPos = this.animation;
        this.model.setupAnim(modelState);
        this.model.root().getExtentsForGui(poseStack, output);
    }

    public record Unbaked(AbstractMobGirlSkullBlock.Type kind, Optional<Identifier> textureOverride, float animation) implements NoDataSpecialModelRenderer.Unbaked {
        public static final MapCodec<MobGirlSkullSpecialRenderer.Unbaked> MAP_CODEC = RecordCodecBuilder.mapCodec((i) -> i.group(AbstractMobGirlSkullBlock.Type.CODEC.fieldOf("kind").forGetter(MobGirlSkullSpecialRenderer.Unbaked::kind), Identifier.CODEC.optionalFieldOf("texture").forGetter(MobGirlSkullSpecialRenderer.Unbaked::textureOverride), Codec.FLOAT.optionalFieldOf("animation", 0.0F).forGetter(MobGirlSkullSpecialRenderer.Unbaked::animation)).apply(i, MobGirlSkullSpecialRenderer.Unbaked::new));

        public Unbaked(AbstractMobGirlSkullBlock.Type kind) {
            this(kind, Optional.empty(), 0.0F);
        }

        public @NonNull MapCodec<MobGirlSkullSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        public @Nullable MobGirlSkullSpecialRenderer bake(SpecialModelRenderer.BakingContext context) {
            SkullModelBase model = MobGirlSkullBlockRenderer.createModel(context.entityModelSet(), this.kind);
            Identifier textureOverride = this.textureOverride.map((t) -> t.withPath((p) -> "textures/entity/" + p + ".png")).orElse(null);
            if (model == null) {
                return null;
            } else {
                RenderType renderType = MobGirlSkullBlockRenderer.getSkullRenderType(this.kind, textureOverride);
                return new MobGirlSkullSpecialRenderer(model, this.animation, renderType);
            }
        }
    }
}
