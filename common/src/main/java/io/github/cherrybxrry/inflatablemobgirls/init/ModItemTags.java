package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public final class ModItemTags {
    private ModItemTags() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final TagKey<Item> CREEPER_GIRL_FOOD = bind("creeper_girl_food");

    public static final TagKey<Item> GHAST_GIRL_FOOD = bind("ghast_girl_food");

    public static final TagKey<Item> INFLATES_CREEPER_GIRL = bind("inflates_creeper_girl");

    public static final TagKey<Item> TAMES_CREEPER_GIRL = bind("tames_creeper_girl");

    public static final TagKey<Item> INFLATES_GHAST_GIRL = bind("inflates_ghast_girl");

    public static final TagKey<Item> TAMES_GHAST_GIRL = bind("tames_ghast_girl");

    private static TagKey<Item> bind(final String name) {
        return TagKey.create(Registries.ITEM, Constants.id(name));
    }
}
