package io.github.cherrybxrry.inflatablemobgirls.datagen;

import com.mojang.math.Quadrant;
import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.blocks.CreepSporeBlock;
import io.github.cherrybxrry.inflatablemobgirls.blocks.HugeCreepshroomStemBlock;
import io.github.cherrybxrry.inflatablemobgirls.init.ModBlocks;
import io.github.cherrybxrry.inflatablemobgirls.init.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiPartGenerator;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.block.dispatch.Variant;
import net.minecraft.client.renderer.block.dispatch.VariantMutator;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.neoforged.neoforge.client.model.generators.template.ExtendedModelTemplateBuilder;
import org.jspecify.annotations.NonNull;

import java.util.Optional;

public class ModModelProvider extends ModelProvider {
    public static final ModelTemplate STEM_COLUMN;
    public static final ModelTemplate STEM_COLUMN_HORIZONTAL;
    public static final ModelTemplate NETHER_GEYSER;
    public static final ModelTemplate CREEPSPORE_CROP;

    public ModModelProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    protected void registerModels(@NonNull BlockModelGenerators blockModels, @NonNull ItemModelGenerators itemModels) {
        // Blocks
        blockModels.createPlantWithDefaultItem(ModBlocks.CREEPSHROOM.block().get(), ModBlocks.POTTED_CREEPSHROOM.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        createBlockEntityCropBlock(blockModels, ModBlocks.CREEPSPORE_CROP.get(), CREEPSPORE_CROP, CreepSporeBlock.AGE, 0, 1, 2, 3, 4, 5);

        createMushroomBlock(blockModels, ModBlocks.CREEPSHROOM_BLOCK.block().get());
        createConnectedColumn(blockModels, ModBlocks.HUGE_CREEPSHROOM_STEM.block().get());

        createNetherGeyser(blockModels, ModBlocks.NETHER_GEYSER.block().get());

        // Items
        itemModels.generateFlatItem(ModItems.BELLOWS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.SOUL_CHOCOLATE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CREEPER_GIRL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GHAST_GIRL_SPAWN_EGG.get(), ModelTemplates.FLAT_ITEM);
    }

    private static ModelTemplate createTemplate(final String id, final TextureSlot... slots) {
        return new ModelTemplate(Optional.of(Constants.id("block/" + id)), Optional.empty(), slots);
    }

    public Identifier modBlockLocation(String path) {
        return modLocation("block/" + path);
    }

    public void createBlockEntityCropBlock(BlockModelGenerators blockModels, Block block, ModelTemplate template, Property<Integer> property, int... stages) {
        blockModels.registerSimpleFlatItemModel(block.asItem());
        if (property.getPossibleValues().size() != stages.length) {
            throw new IllegalArgumentException();
        } else {
            Int2ObjectMap<Identifier> models = new Int2ObjectOpenHashMap<>();
            blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block).with(PropertyDispatch.initial(property).generate((i) -> {
                int stage = stages[i];
                return BlockModelGenerators.plainVariant(models.computeIfAbsent(stage, (s) -> blockModels.createSuffixedVariant(block, "_stage" + s, template, TextureMapping::crop)));
            })));
        }
    }

    public void createNetherGeyser(BlockModelGenerators blockModels, Block block) {
        MultiVariant model = BlockModelGenerators.plainVariant(NETHER_GEYSER.model.get());
        blockModels.blockStateOutput.accept(MultiVariantGenerator.dispatch(block, model).with(BlockModelGenerators.ROTATION_HORIZONTAL_FACING));
    }

    public void createMushroomBlock(BlockModelGenerators blockModels, Block block) {
        MultiVariant skin = BlockModelGenerators.plainVariant(ModelTemplates.SINGLE_FACE.create(block, TextureMapping.defaultTexture(block), blockModels.modelOutput));
        MultiVariant skinless = BlockModelGenerators.plainVariant(ModelTemplates.SINGLE_FACE.createWithOverride(block, "_inside", TextureMapping.defaultTexture(TextureMapping.getBlockTexture(block, "_inside")), blockModels.modelOutput));
        blockModels.blockStateOutput.accept(
                MultiPartGenerator.multiPart(block)
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, true),
                                skin)
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, true),
                                skin.with(BlockModelGenerators.Y_ROT_90).with(BlockModelGenerators.UV_LOCK))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, true),
                                skin.with(BlockModelGenerators.Y_ROT_180).with(BlockModelGenerators.UV_LOCK))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, true),
                                skin.with(BlockModelGenerators.Y_ROT_270).with(BlockModelGenerators.UV_LOCK))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.UP, true),
                                skin.with(BlockModelGenerators.X_ROT_270).with(BlockModelGenerators.UV_LOCK))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.DOWN, true),
                                skin.with(BlockModelGenerators.X_ROT_90).with(BlockModelGenerators.UV_LOCK))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.NORTH, false),
                                skinless)
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.EAST, false),
                                skinless.with(BlockModelGenerators.Y_ROT_90))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.SOUTH, false),
                                skinless.with(BlockModelGenerators.Y_ROT_180))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.WEST, false),
                                skinless.with(BlockModelGenerators.Y_ROT_270))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.UP, false),
                                skinless.with(BlockModelGenerators.X_ROT_270))
                        .with(BlockModelGenerators.condition().term(BlockStateProperties.DOWN, false),
                                skinless.with(BlockModelGenerators.X_ROT_90)));
        blockModels.registerSimpleItemModel(block, TexturedModel.CUBE.createWithSuffix(block, "_inventory", blockModels.modelOutput));
    }

    private void createConnectedColumn(BlockModelGenerators blockModels, Block block) {
        Identifier verticalColumn = STEM_COLUMN.create(
                this.modBlockLocation("connected_column"),
                new TextureMapping(),
                blockModels.modelOutput
        );

        Identifier horizontalColumn = STEM_COLUMN_HORIZONTAL.create(
                this.modBlockLocation("connected_column_horizontal"),
                new TextureMapping(),
                blockModels.modelOutput
        );

        ModelTemplate verticalColumnParent = ExtendedModelTemplateBuilder.builder()
                .parent(verticalColumn)
                .requiredTextureSlot(TextureSlot.END)
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .build();

        ModelTemplate horizontalColumnParent = ExtendedModelTemplateBuilder.builder()
                .parent(horizontalColumn)
                .requiredTextureSlot(TextureSlot.END)
                .requiredTextureSlot(TextureSlot.SIDE)
                .requiredTextureSlot(TextureSlot.PARTICLE)
                .build();

        MultiVariant verticalColumnVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.create(
                block,
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnDVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_d",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnDLVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_d_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnDLRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_d_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnDRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_d_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnLVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnLRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnUVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnUDVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_d",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnUDLVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_d_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnUDLRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_d_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnUDRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_d_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnULVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnULRVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant verticalColumnURVar = BlockModelGenerators.variant(new Variant(verticalColumnParent.createWithOverride(
                block,
                "_u_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnDVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_d",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnDLVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_d_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnDLRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_d_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnDRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_d_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_d_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnLVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnLRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnUVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnUDVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_d",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnUDLVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_d_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnUDLRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_d_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnUDRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_d_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_d_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnULVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_l",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_l"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnULRVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_l_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_l_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));

        MultiVariant horizontalColumnURVar = BlockModelGenerators.variant(new Variant(horizontalColumnParent.createWithOverride(
                block,
                "_horizontal_u_r",
                new TextureMapping()
                        .put(TextureSlot.END, TextureMapping.getBlockTexture(block, "_top_u_r"))
                        .put(TextureSlot.SIDE, TextureMapping.getBlockTexture(block, "_sides"))
                        .put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(block, "_sides")),
                blockModels.modelOutput
        )));


        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(
                        block
                ).with(
                        PropertyDispatch.initial(HugeCreepshroomStemBlock.AXIS, HugeCreepshroomStemBlock.DOWN, HugeCreepshroomStemBlock.LEFT, HugeCreepshroomStemBlock.RIGHT, HugeCreepshroomStemBlock.UP)
                                // Axis X (horizontal models with 90 x and 90 y rotation in JSON)
                                .select(Direction.Axis.X, false, false, false, false, horizontalColumnVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, false, false, false, horizontalColumnDVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, true, false, false, horizontalColumnLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, false, true, false, horizontalColumnRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, false, false, true, horizontalColumnUVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, true, false, false, horizontalColumnDLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, false, true, false, horizontalColumnDRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, true, true, false, horizontalColumnLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, false, false, true, horizontalColumnUDVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, true, false, true, horizontalColumnULVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, false, true, true, horizontalColumnURVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, true, true, false, horizontalColumnDLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, true, false, true, horizontalColumnUDLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, false, true, true, horizontalColumnUDRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, false, true, true, true, horizontalColumnULRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.X, true, true, true, true, horizontalColumnUDLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)).with(VariantMutator.Y_ROT.withValue(Quadrant.R90)))

                                // Axis Y (vertical models — use the non-horizontal variants)
                                .select(Direction.Axis.Y, false, false, false, false, verticalColumnVar)
                                .select(Direction.Axis.Y, true, false, false, false, verticalColumnDVar)
                                .select(Direction.Axis.Y, false, true, false, false, verticalColumnLVar)
                                .select(Direction.Axis.Y, false, false, true, false, verticalColumnRVar)
                                .select(Direction.Axis.Y, false, false, false, true, verticalColumnUVar)
                                .select(Direction.Axis.Y, true, true, false, false, verticalColumnDLVar)
                                .select(Direction.Axis.Y, true, false, true, false, verticalColumnDRVar)
                                .select(Direction.Axis.Y, false, true, true, false, verticalColumnLRVar)
                                .select(Direction.Axis.Y, true, false, false, true, verticalColumnUDVar)
                                .select(Direction.Axis.Y, false, true, false, true, verticalColumnULVar)
                                .select(Direction.Axis.Y, false, false, true, true, verticalColumnURVar)
                                .select(Direction.Axis.Y, true, true, true, false, verticalColumnDLRVar)
                                .select(Direction.Axis.Y, true, true, false, true, verticalColumnUDLVar)
                                .select(Direction.Axis.Y, true, false, true, true, verticalColumnUDRVar)
                                .select(Direction.Axis.Y, false, true, true, true, verticalColumnULRVar)
                                .select(Direction.Axis.Y, true, true, true, true, verticalColumnUDLRVar)

                                // Axis Z (horizontal models with 90 x rotation in JSON)
                                .select(Direction.Axis.Z, false, false, false, false, horizontalColumnVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, false, false, false, horizontalColumnDVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, true, false, false, horizontalColumnLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, false, true, false, horizontalColumnRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, false, false, true, horizontalColumnUVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, true, false, false, horizontalColumnDLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, false, true, false, horizontalColumnDRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, true, true, false, horizontalColumnLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, false, false, true, horizontalColumnUDVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, true, false, true, horizontalColumnULVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, false, true, true, horizontalColumnURVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, true, true, false, horizontalColumnDLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, true, false, true, horizontalColumnUDLVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, false, true, true, horizontalColumnUDRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, false, true, true, true, horizontalColumnULRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                                .select(Direction.Axis.Z, true, true, true, true, horizontalColumnUDLRVar.with(VariantMutator.X_ROT.withValue(Quadrant.R90)))
                )
        );
    }

    static {
        STEM_COLUMN = ExtendedModelTemplateBuilder.builder()
                .parent(Identifier.withDefaultNamespace("block/block"))
                .element(elementBuilder -> elementBuilder
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.NORTH))
                        .face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.EAST))
                        .face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.SOUTH))
                        .face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.WEST))
                        .face(Direction.UP, faceBuilder -> faceBuilder.uvs(16, 16, 0, 0).texture(TextureSlot.END).cullface(Direction.UP))
                        .face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(16, 0, 0, 16).texture(TextureSlot.END).cullface(Direction.DOWN))
                )
                .build();
        STEM_COLUMN_HORIZONTAL = ExtendedModelTemplateBuilder.builder()
                .parent(Identifier.withDefaultNamespace("block/block"))
                .element(elementBuilder -> elementBuilder
                        .from(0, 0, 0)
                        .to(16, 16, 16)
                        .face(Direction.NORTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.NORTH))
                        .face(Direction.EAST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.EAST))
                        .face(Direction.SOUTH, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.SOUTH))
                        .face(Direction.WEST, faceBuilder -> faceBuilder.texture(TextureSlot.SIDE).cullface(Direction.WEST))
                        .face(Direction.UP, faceBuilder -> faceBuilder.uvs(0, 16, 16, 0).texture(TextureSlot.END).cullface(Direction.UP))
                        .face(Direction.DOWN, faceBuilder -> faceBuilder.uvs(0, 0, 16, 16).texture(TextureSlot.END).cullface(Direction.DOWN))
                )
                .build();
        NETHER_GEYSER = createTemplate("nether_geyser", TextureSlot.ALL);
        CREEPSPORE_CROP = createTemplate("creepspore_crop", TextureSlot.CROP);
    }
}
