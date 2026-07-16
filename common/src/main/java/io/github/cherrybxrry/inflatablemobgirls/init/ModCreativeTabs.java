package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public final class ModCreativeTabs {
    private ModCreativeTabs() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Creative Mode Tabs");
    }

    public static final RegistryHandle<CreativeModeTab> ITEM_TAB = Services.REGISTRY.registerCreativeTab(
            "items_tab",
            () -> new ItemStack(ModItems.SOUL_CHOCOLATE.get()),
            output -> {
                output.accept(ModItems.BELLOWS.get());
                output.accept(ModItems.CREEPSPORE.get());
                output.accept(ModItems.SOUL_CHOCOLATE.get());

                output.accept(ModBlocks.CREEPSHROOM.item().get());
                output.accept(ModBlocks.CREEPSHROOM_BLOCK.item().get());
                output.accept(ModBlocks.HUGE_CREEPSHROOM_STEM.item().get());
                output.accept(ModBlocks.NETHER_GEYSER.item().get());
            }
    );

    public static final RegistryHandle<CreativeModeTab> GIRLS_TAB = Services.REGISTRY.registerCreativeTab(
            "girls_tab",
            () -> new ItemStack(ModItems.CREEPER_GIRL_SPAWN_EGG.get()),
            output -> {
                output.accept(ModItems.CREEPER_GIRL_SPAWN_EGG.get());
                output.accept(ModItems.GHAST_GIRL_SPAWN_EGG.get());
            }
    );
}
