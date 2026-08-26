package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity;

import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.AdultCreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.BabyCreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.model.entity.CreeperGirlModel;
import io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state.CreeperGirlRenderState;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirl;
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

        state.idle0Animation.copyFrom(entity.idle0Animation);
        state.idle1Animation.copyFrom(entity.idle1Animation);
        state.idle2Animation.copyFrom(entity.idle2Animation);
        state.idleRegen0Animation.copyFrom(entity.idleRegen0Animation);
        state.idleRegen1Animation.copyFrom(entity.idleRegen1Animation);
        state.idleRegen2Animation.copyFrom(entity.idleRegen2Animation);

        state.idleCharged0Animation.copyFrom(entity.idleCharged0Animation);
        state.idleCharged1Animation.copyFrom(entity.idleCharged1Animation);
        state.idleCharged2Animation.copyFrom(entity.idleCharged2Animation);

        state.sit0Animation.copyFrom(entity.sit0Animation);
        state.sit1Animation.copyFrom(entity.sit1Animation);
        state.sit2Animation.copyFrom(entity.sit2Animation);

        state.sitCharged0Animation.copyFrom(entity.sitCharged0Animation);
        state.sitCharged1Animation.copyFrom(entity.sitCharged1Animation);
        state.sitCharged2Animation.copyFrom(entity.sitCharged2Animation);

        state.inflate1Animation.copyFrom(entity.inflate1Animation);
        state.inflate2Animation.copyFrom(entity.inflate2Animation);
        state.inflate3Animation.copyFrom(entity.inflate3Animation);
        state.regen1Animation.copyFrom(entity.regen1Animation);
        state.regen2Animation.copyFrom(entity.regen2Animation);
        state.regen3Animation.copyFrom(entity.regen3Animation);

        state.inflateCharged1Animation.copyFrom(entity.inflateCharged1Animation);
        state.inflateCharged2Animation.copyFrom(entity.inflateCharged2Animation);
        state.inflateCharged3Animation.copyFrom(entity.inflateCharged3Animation);

        state.attackAnimation.copyFrom(entity.attackAnimation);


        state.isSpawner = this.isSpawner(entity);
        state.texture = entity.getTexture();
    }
}
