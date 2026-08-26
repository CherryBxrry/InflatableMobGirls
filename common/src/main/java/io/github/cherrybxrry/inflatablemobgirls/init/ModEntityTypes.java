package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.entity.creepergirl.CreeperGirl;
import io.github.cherrybxrry.inflatablemobgirls.entity.ghastgirl.GhastGirl;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public final class ModEntityTypes {
    private ModEntityTypes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Entities");
    }

    public static final RegistryHandle<EntityType<CreeperGirl>> CREEPER_GIRL = Services.REGISTRY.registerEntityType(
            "creeper_girl",
            EntityType.Builder.of(CreeperGirl::new, MobCategory.MONSTER)
                    .sized(CreeperGirl.WIDTH, CreeperGirl.STAND_HEIGHT)
                    .eyeHeight(CreeperGirl.STAND_HEIGHT - CreeperGirl.EYE_OFFSET)
                    .clientTrackingRange(10)
    );

    public static final RegistryHandle<EntityType<GhastGirl>> GHAST_GIRL = Services.REGISTRY.registerEntityType(
            "ghast_girl",
            EntityType.Builder.of(GhastGirl::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(GhastGirl.WIDTH, GhastGirl.STAND_HEIGHT)
                    .eyeHeight(GhastGirl.STAND_HEIGHT - GhastGirl.EYE_OFFSET)
                    .clientTrackingRange(10)
    );

    public static ResourceKey<EntityType<?>> getRK(EntityType<?> entityType) {
        return BuiltInRegistries.ENTITY_TYPE.getResourceKey(entityType).get();
    }
}
