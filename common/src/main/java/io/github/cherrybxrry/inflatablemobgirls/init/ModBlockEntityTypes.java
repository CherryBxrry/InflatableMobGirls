package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.blocks.entity.CreepSporeBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.blocks.entity.NetherGeyserBlockEntity;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntityTypes {
    private ModBlockEntityTypes() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Block Entities");
    }

    public static final RegistryHandle<BlockEntityType<CreepSporeBlockEntity>> CREEPSPORE_CROP = Services.REGISTRY.registerBlockEntity(
            "creepspore_crop",
            CreepSporeBlockEntity::new,
            ModBlocks.CREEPSPORE_CROP);

    public static final RegistryHandle<BlockEntityType<NetherGeyserBlockEntity>> NETHER_GEYSER = Services.REGISTRY.registerBlockEntity(
            "nether_geyser",
            NetherGeyserBlockEntity::new,
            ModBlocks.NETHER_GEYSER.block());
}
