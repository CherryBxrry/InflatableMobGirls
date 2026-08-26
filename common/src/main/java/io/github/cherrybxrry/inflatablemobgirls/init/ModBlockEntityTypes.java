package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.CreepSporeBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.MobGirlSkullBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.block.entity.NetherGeyserBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Set;

public final class ModBlockEntityTypes {
    private ModBlockEntityTypes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Block Entities");
    }

    public static final RegistryHandle<BlockEntityType<MobGirlSkullBlockEntity>> MOB_GIRL_SKULL = Services.REGISTRY.registerBlockEntity(
            "mob_girl_skull",
            MobGirlSkullBlockEntity::new,
            Set.of(
                    ModBlocks.CREEPER_GIRL_HEAD,
                    ModBlocks.CREEPER_GIRL_WALL_HEAD
            )
    );

    public static final RegistryHandle<BlockEntityType<CreepSporeBlockEntity>> CREEPSPORE_CROP = Services.REGISTRY.registerBlockEntity(
            "creepspore_crop",
            CreepSporeBlockEntity::new,
            ModBlocks.CREEPSPORE_CROP
    );

    public static final RegistryHandle<BlockEntityType<NetherGeyserBlockEntity>> NETHER_GEYSER = Services.REGISTRY.registerBlockEntity(
            "nether_geyser",
            NetherGeyserBlockEntity::new,
            ModBlocks.NETHER_GEYSER.block()
    );
}
