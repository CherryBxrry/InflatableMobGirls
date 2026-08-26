package io.github.cherrybxrry.inflatablemobgirls.client.renderer.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public abstract class InflatableMobGirlRenderState extends LivingEntityRenderState {
    public int stage = 0;
    public boolean isSpawner;
}
