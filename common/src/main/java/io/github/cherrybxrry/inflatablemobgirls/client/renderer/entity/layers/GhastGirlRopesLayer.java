package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.GhastGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.GhastGirlRopesModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.GhastGirlRenderState;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModModelLayers;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.NonNull;

public class GhastGirlRopesLayer<M extends GhastGirlModel> extends RenderLayer<GhastGirlRenderState, M> {
    private final Identifier ropesTexture;
    private final GhastGirlRopesModel model;

    public GhastGirlRopesLayer(final RenderLayerParent<GhastGirlRenderState, M> renderer, final EntityModelSet modelSet, final Identifier ropesTexture) {
        super(renderer);
        this.ropesTexture = ropesTexture;
        this.model = new GhastGirlRopesModel(modelSet.bakeLayer(ModModelLayers.GHAST_GIRL_ROPES));
    }

    @Override
    public void submit(final @NonNull PoseStack poseStack, final @NonNull SubmitNodeCollector submitNodeCollector, final int lightCoords, final @NonNull GhastGirlRenderState state, final float yRot, final float xRot) {
        if (state.isLeashHolder) {
            submitNodeCollector.submitModel(this.model, state, poseStack, this.ropesTexture, lightCoords, OverlayTexture.NO_OVERLAY, state.outlineColor, null);
        }
    }
}
