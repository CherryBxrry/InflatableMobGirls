package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state;

import io.github.cherrybxrry.inflatablemobgirls.entity.InflatableMobGirl;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public abstract class InflatableMobGirlRenderState extends LivingEntityRenderState {
    public int stage = 0;
    public boolean isInflating;
    public boolean isSpawner;

    public static void extractInflatableMobGirlRenderState(InflatableMobGirl entity, InflatableMobGirlRenderState state) {
        state.stage = entity.getStage();
        state.isInflating = entity.isVisuallyInflating();
        state.isSpawner = entity.level().getEntity(entity.getUUID()) == null && entity.isAlive() && entity.tickCount <= 0;
    }
}
