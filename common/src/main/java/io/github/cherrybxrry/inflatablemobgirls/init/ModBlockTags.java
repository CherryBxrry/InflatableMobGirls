package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public final class ModBlockTags {
    private ModBlockTags() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    private static TagKey<Block> bind(final String name) {
        return TagKey.create(Registries.BLOCK, Constants.id(name));
    }
}
