package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.block.*;
import io.github.cherrybxrry.inflatablemobgirls.blocks.*;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.BlockWithItemRegistryHandle;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import io.github.cherrybxrry.inflatablemobgirls.worldgen.ModWorldGen;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.HugeMushroomBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ModBlocks {
    private ModBlocks() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Blocks");
    }

    public static final RegistryHandle<Block> CREEPER_GIRL_HEAD = Services.REGISTRY.registerBlock(
            "creeper_girl_head",
            properties -> new CreeperGirlSkullBlock(properties
                    .instrument(NoteBlockInstrument.CREEPER)
                    .strength(1.0F)
                    .pushReaction(PushReaction.DESTROY)
                    .noOcclusion())
    );

    public static final RegistryHandle<Block> CREEPER_GIRL_WALL_HEAD = Services.REGISTRY.registerBlock(
            "creeper_girl_wall_head",
            properties -> new CreeperGirlWallSkullBlock(wallVariant(properties, CREEPER_GIRL_HEAD.get(), true)
                    .strength(1.0F)
                    .pushReaction(PushReaction.DESTROY))
    );

    public static final BlockWithItemRegistryHandle<Block> CREEPSHROOM = Services.REGISTRY.registerBlockWithItem(
            "creepshroom",
            properties -> new CreepshroomBlock(
                    ModWorldGen.HUGE_CREEPSHROOM, properties
                    .mapColor(MapColor.COLOR_GREEN)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .postProcess(ModBlocks::postProcessSelf)
                    .pushReaction(PushReaction.DESTROY))
    );

    public static final RegistryHandle<Block> CREEPSPORE_CROP = Services.REGISTRY.registerBlock(
            "creepspore_crop",
            properties -> new CreepSporeBlock(properties
                    .mapColor(MapColor.PLANT)
                    .noCollision()
                    .randomTicks()
                    .instabreak()
                    .sound(SoundType.CROP)
                    .pushReaction(PushReaction.DESTROY))
    );

    public static final BlockWithItemRegistryHandle<Block> CREEPSHROOM_BLOCK = Services.REGISTRY.registerBlockWithItem(
            "creepshroom_block",
            properties -> new HugeMushroomBlock(properties
                    .mapColor(MapColor.COLOR_GREEN)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .ignitedByLava()
                    .sound(SoundType.FUNGUS)
            ));

    public static final BlockWithItemRegistryHandle<Block> HUGE_CREEPSHROOM_STEM = Services.REGISTRY.registerBlockWithItem(
            "creepshroom_stem",
            properties -> new HugeCreepshroomStemBlock(properties
                    .mapColor(MapColor.WOOL)
                    .instrument(NoteBlockInstrument.BASS)
                    .strength(0.2F)
                    .ignitedByLava()
                    .sound(SoundType.FUNGUS)
            ));

    public static final RegistryHandle<Block> POTTED_CREEPSHROOM = Services.REGISTRY.registerBlock(
            "potted_creepshroom",
            properties -> new FlowerPotBlock(CREEPSHROOM.block().get(), flowerPotProperties(properties))
    );

    public static final BlockWithItemRegistryHandle<Block> NETHER_GEYSER = Services.REGISTRY.registerBlockWithItem(
            "nether_geyser",
            properties -> new NetherGeyserBlock(properties
                    .mapColor(MapColor.NETHER)
                    .requiresCorrectToolForDrops()
                    .strength(0.4F)
                    .sound(SoundType.NETHERRACK)
            ));

    private static BlockBehaviour.Properties flowerPotProperties(BlockBehaviour.Properties properties) {
        return properties.instabreak().noOcclusion().pushReaction(PushReaction.DESTROY);
    }

    private static BlockBehaviour.Properties wallVariant(BlockBehaviour.Properties properties, Block standingBlock, boolean copyName) {
        BlockBehaviour.Properties wallProperties = properties.overrideLootTable(standingBlock.getLootTable());
        if (copyName) {
            wallProperties = wallProperties.overrideDescription(standingBlock.getDescriptionId());
        }

        return wallProperties;
    }

    private static BlockPos postProcessSelf(BlockState state, BlockGetter blockGetter, BlockPos blockPos) {
        return blockPos;
    }
}
