package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.AdultCreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.BabyCreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.CreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.CreeperGirlRenderState;
import io.github.cherrybxrry.inflatablemobgirls.entities.creepergirl.CreeperGirl;
import io.github.cherrybxrry.inflatablemobgirls.init.client.ModModelLayers;
import net.minecraft.client.renderer.entity.AgeableMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.NonNull;

public class CreeperGirlRenderer extends AgeableMobRenderer<CreeperGirl, CreeperGirlRenderState, CreeperGirlModel> implements SpawnerEntityRenderer {
    public CreeperGirlRenderer(EntityRendererProvider.Context context) {
        super(context, new AdultCreeperGirlModel(context.bakeLayer(ModModelLayers.CREEPER_GIRL)), new BabyCreeperGirlModel(context.bakeLayer(ModModelLayers.CREEPER_GIRL_BABY)), CreeperGirl.WIDTH);
    }

    @Override
    public @NonNull Identifier getTextureLocation(@NonNull CreeperGirlRenderState state) {
        return state.texture;
    }

    @Override
    public @NonNull CreeperGirlRenderState createRenderState() {
        return new CreeperGirlRenderState();
    }

    @Override
    public void extractRenderState(@NonNull CreeperGirl entity, @NonNull CreeperGirlRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.idle0AnimationState.copyFrom(entity.idle0AnimationState);
        state.idle1AnimationState.copyFrom(entity.idle1AnimationState);
        state.idle2AnimationState.copyFrom(entity.idle2AnimationState);
        state.idleRegen0AnimationState.copyFrom(entity.idleRegen0AnimationState);
        state.idleRegen1AnimationState.copyFrom(entity.idleRegen1AnimationState);
        state.idleRegen2AnimationState.copyFrom(entity.idleRegen2AnimationState);

        state.idleCharged0AnimationState.copyFrom(entity.idleCharged0AnimationState);
        state.idleCharged1AnimationState.copyFrom(entity.idleCharged1AnimationState);
        state.idleCharged2AnimationState.copyFrom(entity.idleCharged2AnimationState);

        state.sit0AnimationState.copyFrom(entity.sit0AnimationState);
        state.sit1AnimationState.copyFrom(entity.sit1AnimationState);
        state.sit2AnimationState.copyFrom(entity.sit2AnimationState);

        state.sitCharged0AnimationState.copyFrom(entity.sitCharged0AnimationState);
        state.sitCharged1AnimationState.copyFrom(entity.sitCharged1AnimationState);
        state.sitCharged2AnimationState.copyFrom(entity.sitCharged2AnimationState);

        state.inflate1AnimationState.copyFrom(entity.inflate1AnimationState);
        state.inflate2AnimationState.copyFrom(entity.inflate2AnimationState);
        state.inflate3AnimationState.copyFrom(entity.inflate3AnimationState);
        state.regen1AnimationState.copyFrom(entity.regen1AnimationState);
        state.regen2AnimationState.copyFrom(entity.regen2AnimationState);
        state.regen3AnimationState.copyFrom(entity.regen3AnimationState);

        state.inflateCharged1AnimationState.copyFrom(entity.inflateCharged1AnimationState);
        state.inflateCharged2AnimationState.copyFrom(entity.inflateCharged2AnimationState);
        state.inflateCharged3AnimationState.copyFrom(entity.inflateCharged3AnimationState);

        state.attackAnimationState.copyFrom(entity.attackAnimationState);


        state.isSpawner = this.isSpawner(entity);
        state.texture = entity.getTexture();
    }
}
