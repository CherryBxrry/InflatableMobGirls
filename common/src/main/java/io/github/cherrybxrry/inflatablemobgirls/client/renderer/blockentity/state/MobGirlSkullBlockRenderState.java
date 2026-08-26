package io.github.cherrybxrry.inflatablemobgirls.client.renderer.blockentity.state;

import com.mojang.math.Transformation;
import io.github.cherrybxrry.inflatablemobgirls.block.AbstractMobGirlSkullBlock;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderType;

public class MobGirlSkullBlockRenderState extends BlockEntityRenderState {
    public float animationProgress;
    public Transformation transformation;
    public AbstractMobGirlSkullBlock.Type skullType;
    public RenderType renderType;

    public MobGirlSkullBlockRenderState() {
        this.transformation = Transformation.IDENTITY;
        this.skullType = AbstractMobGirlSkullBlock.Types.CREEPER_GIRL;
    }
}
