package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public final class ModEntityTypeTags {
    private ModEntityTypeTags() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final TagKey<EntityType<?>> INFLATABLE_MOB_GIRL = bind("inflatable_mob_girl");

    private static TagKey<EntityType<?>> bind(final String name) {
        return TagKey.create(Registries.ENTITY_TYPE, Constants.id(name));
    }
}
