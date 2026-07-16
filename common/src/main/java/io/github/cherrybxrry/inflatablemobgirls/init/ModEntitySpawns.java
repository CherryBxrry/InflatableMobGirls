package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.entities.InflatableMobGirl;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

public final class ModEntitySpawns {
    private ModEntitySpawns() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Services.SPAWN_PLACEMENTS.registerSpawnPlacement(
                ModEntityTypes.CREEPER_GIRL,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                InflatableMobGirl::checkLightInflatableMobSpawnRules
        );

        Services.SPAWN_PLACEMENTS.registerSpawnPlacement(
                ModEntityTypes.GHAST_GIRL,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                InflatableMobGirl::checkInflatableMobSpawnRules
        );
    }

    public static final SpawnSettings CREEPER_GIRL = new SpawnSettings(10, 1, 1);
    public static final SpawnSettings GHAST_GIRL_BASALT_DELTAS = new SpawnSettings(15, 1, 1);
    public static final SpawnSettings GHAST_GIRL_NETHER_WASTES = new SpawnSettings(10, 1, 1);
    public static final SpawnSettings GHAST_GIRL_SOUL_SAND_VALLEY = new SpawnSettings(10, 1, 1);

    public record SpawnSettings(int weight, int minGroupSize, int maxGroupSize) {

    }
}
