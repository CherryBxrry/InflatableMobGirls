package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.GhastGirlHarnessModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.GhastGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.layers.GhastGirlRopesLayer;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.GhastGirlRenderState;
import io.github.cherrybxrry.inflatablemobgirls.entities.ghastgirl.GhastGirl;
import io.github.cherrybxrry.inflatablemobgirls.enums.client.ModEquipmentLayerTypes;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.SimpleEquipmentLayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import org.jspecify.annotations.NonNull;

public class GhastGirlRenderer extends MobRenderer<GhastGirl, GhastGirlRenderState, GhastGirlModel> implements SpawnerEntityRenderer {
    private static final Identifier GHAST_GIRL = Constants.id("textures/entity/ghast_girl/ghast_girl.png");
    private static final Identifier GHAST_GIRL_ROPES = Constants.id("textures/entity/ghast_girl/ghast_girl_ropes.png");

    public GhastGirlRenderer(EntityRendererProvider.Context context) {
        super(context, new GhastGirlModel(context.bakeLayer(ModModelLayers.GHAST_GIRL)), GhastGirl.WIDTH * 0.75F);
        this.addLayer(new SimpleEquipmentLayer<>(this, context.getEquipmentRenderer(), ModEquipmentLayerTypes.GHAST_GIRL_BODY, (state) -> state.bodyItem, new GhastGirlHarnessModel(context.bakeLayer(ModModelLayers.GHAST_GIRL_HARNESS)), null));
        this.addLayer(new GhastGirlRopesLayer<>(this, context.getModelSet(), GHAST_GIRL_ROPES));
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull GhastGirlRenderState state) {
        return GHAST_GIRL;
    }

    @Override
    public @NonNull GhastGirlRenderState createRenderState() {
        return new GhastGirlRenderState();
    }

    @Override
    public void extractRenderState(@NonNull GhastGirl entity, @NonNull GhastGirlRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idleStand0Animation.copyFrom(entity.idleStand0Animation);
        state.idleStand1Animation.copyFrom(entity.idleStand1Animation);
        state.idleStand2Animation.copyFrom(entity.idleStand2Animation);
        state.idleStand3Animation.copyFrom(entity.idleStand3Animation);

        state.idleRide0Animation.copyFrom(entity.idleRide0Animation);
        state.idleRide1Animation.copyFrom(entity.idleRide1Animation);
        state.idleRide2Animation.copyFrom(entity.idleRide2Animation);
        state.idleRide3Animation.copyFrom(entity.idleRide3Animation);

        state.sit0Animation.copyFrom(entity.sit0Animation);
        state.sit1Animation.copyFrom(entity.sit1Animation);
        state.sit2Animation.copyFrom(entity.sit2Animation);
        state.sit3Animation.copyFrom(entity.sit3Animation);

        state.inflate1Animation.copyFrom(entity.inflate1Animation);
        state.inflate2Animation.copyFrom(entity.inflate2Animation);
        state.inflate3Animation.copyFrom(entity.inflate3Animation);

        state.sitInflate1Animation.copyFrom(entity.sitInflate1Animation);
        state.sitInflate2Animation.copyFrom(entity.sitInflate2Animation);
        state.sitInflate3Animation.copyFrom(entity.sitInflate3Animation);

        state.ventInflate1Animation.copyFrom(entity.ventInflate1Animation);
        state.ventInflate2Animation.copyFrom(entity.ventInflate2Animation);
        state.ventInflate3Animation.copyFrom(entity.ventInflate3Animation);

        state.attack0Animation.copyFrom(entity.attack0Animation);
        state.attack1Animation.copyFrom(entity.attack1Animation);
        state.attack2Animation.copyFrom(entity.attack2Animation);
        state.attack3Animation.copyFrom(entity.attack3Animation);

        state.blinkAnimation.copyFrom(entity.blinkAnimation);

        state.stage = entity.getStage();
        state.bodyItem = entity.getItemBySlot(EquipmentSlot.BODY).copy();
        state.isRidden = entity.isVehicle();
        state.isLeashHolder = entity.isLeashHolder();
        state.isSpawner = this.isSpawner(entity);
    }
}
