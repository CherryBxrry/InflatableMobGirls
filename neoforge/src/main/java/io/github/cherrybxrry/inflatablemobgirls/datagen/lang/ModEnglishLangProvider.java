package io.github.cherrybxrry.inflatablemobgirls.datagen.lang;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.*;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class ModEnglishLangProvider extends LanguageProvider {
    public ModEnglishLangProvider(PackOutput output) {
        super(output, Constants.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        // Advancements
        add(Component.translatable("advancements.inflatablemobgirls.adventure.spyglass_at_ghast_girl.title"), "Is It a Blimp?");
        add(Component.translatable("advancements.inflatablemobgirls.adventure.spyglass_at_ghast_girl.description"), "Look at a Ghast Girl through a Spyglass");
        add(Component.translatable("advancements.inflatablemobgirls.husbandry.tame_all_mob_girls.title"), "A Swell Group");
        add(Component.translatable("advancements.inflatablemobgirls.husbandry.tame_all_mob_girls.description"), "Tame All Mob Girls!");
        add(Component.translatable("advancements.inflatablemobgirls.husbandry.tame_a_mob_girl.title"), "A New Best Friend");
        add(Component.translatable("advancements.inflatablemobgirls.husbandry.tame_a_mob_girl.description"), "Tame a Mob Girl");
        add(Component.translatable("advancements.inflatablemobgirls.nether.oh_the_humanity.title"), "Oh, the Humanity!");
        add(Component.translatable("advancements.inflatablemobgirls.nether.oh_the_humanity.description"), "Destroy a Ghast Girl with a fireball");

        // Blocks
        add(ModBlocks.CREEPER_GIRL_HEAD.get(), "Creeper Girl Head");
        add(ModBlocks.CREEPSHROOM.block().get(), "Creepshroom");
        add(ModBlocks.CREEPSPORE_CROP.get(), "Creeper Girl Crop");
        add(ModBlocks.CREEPSHROOM_BLOCK.block().get(), "Creepshroom Block");
        add(ModBlocks.HUGE_CREEPSHROOM_STEM.block().get(), "Creepshroom Stem");
        add(ModBlocks.NETHER_GEYSER.block().get(), "Nether Geyser");
        add(ModBlocks.POTTED_CREEPSHROOM.get(), "Potted Creepshroom");

        // Block Tags

        // Creative Mod Tabs
        add(ModCreativeTabs.ITEM_TAB.get().getDisplayName(), "Inflatable Mob Girls Items");
        add(ModCreativeTabs.GIRLS_TAB.get().getDisplayName(), "Inflatable Mob Girls");

        // Entities
        add(ModEntityTypes.CREEPER_GIRL.get(), "Creeper Girl");
        add(ModEntityTypes.GHAST_GIRL.get(), "Ghast Girl");

        // Entity Tags
        add(ModEntityTypeTags.INFLATABLE_MOB_GIRL, "Inflatable Mob Girl");

        // Items
        add(ModItems.BELLOWS.get(), "Bellows");
        add(ModItems.CREEPER_GIRL_HEAD.get(), "Creeper Girl Head");
        add(ModItems.CREEPER_GIRL_SPAWN_EGG.get(), "Creeper Girl Spawn Egg");
        add(ModBlocks.CREEPSHROOM.item().get(), "Creepshroom");
        add(ModBlocks.CREEPSHROOM_BLOCK.item().get(), "Creepshroom Block");
        add(ModItems.CREEPSPORE.get(), "Creeper Girl Spore");
        add(ModItems.GHAST_GIRL_SPAWN_EGG.get(), "Ghast Girl Spawn Egg");
        add(ModBlocks.HUGE_CREEPSHROOM_STEM.item().get(), "Creepshroom Stem");
        add(ModBlocks.NETHER_GEYSER.item().get(), "Nether Geyser");
        add(ModItems.SOUL_CHOCOLATE.get(), "Soul Chocolate");

        // Item Tags
        add(ModItemTags.CREEPER_GIRL_FOOD, "Creeper Girl Food");
        add(ModItemTags.GHAST_GIRL_FOOD, "Ghast Girl Food");
        add(ModItemTags.INFLATES_CREEPER_GIRL, "Inflates Creeper Girl");
        add(ModItemTags.INFLATES_GHAST_GIRL, "Inflates Ghast Girl");
        add(ModItemTags.TAMES_CREEPER_GIRL, "Tames Creeper Girl");
        add(ModItemTags.TAMES_GHAST_GIRL, "Tames Ghast Girl");

        // Key Mappings
        add(Component.translatable("key.category.inflatablemobgirls.inflatable_mob_girls"), "Inflatable Mob Girls");
        add(Component.translatable("key.inflatablemobgirls.melee_attack"), "Ridden Melee Attack");
        add(Component.translatable("key.inflatablemobgirls.ranged_attack"), "Ridden Ranged Attack");

        // Sounds
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_baby_belly_ambience"), "Creeper Girl's belly gurgles");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_baby_inflate"), "Creeper Girl inflates");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_baby_deflate"), "Creeper Girl deflates");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_baby_popping"), "Creeper Girl is Popping");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_belly_ambience1"), "Creeper Girl's belly gurgles quietly");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_belly_ambience2"), "Creeper Girl's belly gurgles unhappily");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_inflate"), "Creeper Girl inflates");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_deflate"), "Creeper Girl deflates");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_popping"), "Creeper Girl is popping");
        add(Component.translatable("sounds.inflatablemobgirls.creeper_girl_popping_charged"), "Creeper Girl is popping violently");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_belly_ambience"), "Ghast Girl's belly gurgles quietly");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_pump1"), "Ghast Girl's belly swells");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_pump2"), "Ghast Girl's belly swells larger");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_pump3"), "Ghast Girl's belly swells to max capacity");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_vent1"), "Ghast Girl's belly gently swells");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_vent2"), "Ghast Girl's belly gently swells larger");
        add(Component.translatable("sounds.inflatablemobgirls.ghast_girl_inflate_vent3"), "Ghast Girl's belly gently swells to max capacity");
    }

    private void add(Component component, String value) {
        if (component.getContents() instanceof TranslatableContents translatableContents) {
            add(translatableContents.getKey(), value);
        }
    }
}
