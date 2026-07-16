package io.github.cherrybxrry.inflatablemobgirls.platform.util;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public record BlockWithItemRegistryHandle<T extends Block>(
        RegistryHandle<T> block,
        RegistryHandle<? extends BlockItem> item
) {
    public ResourceKey<Item> itemKey() {
        @SuppressWarnings("unchecked")
        ResourceKey<Item> key = (ResourceKey<Item>) (ResourceKey<?>) item.key();
        return key;
    }
}
