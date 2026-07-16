package io.github.cherrybxrry.inflatablemobgirls.mixin;

import net.minecraft.world.level.block.SkullBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SkullBlock.Types.class)
enum SkullBlockTypesMixin {
    inflatablemobgirls_CREEPER_GIRL("inflatablemobgirls:creeper_girl");


    @Shadow
    SkullBlockTypesMixin(String name) {
    }
}
