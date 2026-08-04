package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModSounds;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class ModSoundDefinitionsProvider extends SoundDefinitionsProvider {
    public ModSoundDefinitionsProvider(PackOutput output) {
        super(output, Constants.MOD_ID);
    }

    @Override
    public void registerSounds() {
        // Overworld
        addSubtitled(ModSounds.CREEPER_GIRL_BABY_BELLY_AMBIENCE.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/baby_belly_ambience0")),
                        sound(Constants.id("creeper_girl/baby_belly_ambience1")),
                        sound(Constants.id("creeper_girl/baby_belly_ambience2")),
                        sound(Constants.id("creeper_girl/baby_belly_ambience3"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_BABY_DEFLATE.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/baby_deflate"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_BABY_INFLATE.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/baby_inflate"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_BABY_POPPING.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/baby_popping"))
                ));

        addSubtitled(ModSounds.CREEPER_GIRL_BELLY_AMBIENCE1.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/belly_ambience1_0")),
                        sound(Constants.id("creeper_girl/belly_ambience1_1")),
                        sound(Constants.id("creeper_girl/belly_ambience1_2")),
                        sound(Constants.id("creeper_girl/belly_ambience1_3"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_BELLY_AMBIENCE2.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/belly_ambience2_0")),
                        sound(Constants.id("creeper_girl/belly_ambience2_1")),
                        sound(Constants.id("creeper_girl/belly_ambience2_2"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_DEFLATE.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/deflate"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_INFLATE.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/inflate"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_POPPING.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/popping"))
                ));
        addSubtitled(ModSounds.CREEPER_GIRL_POPPING_CHARGED.id(), definition()
                .with(
                        sound(Constants.id("creeper_girl/popping_charged"))
                ));

        // Nether
        addSubtitled(ModSounds.GHAST_GIRL_BELLY_AMBIENCE.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/belly_ambience0")),
                        sound(Constants.id("ghast_girl/belly_ambience1")),
                        sound(Constants.id("ghast_girl/belly_ambience2")),
                        sound(Constants.id("ghast_girl/belly_ambience3")),
                        sound(Constants.id("ghast_girl/belly_ambience4")),
                        sound(Constants.id("ghast_girl/belly_ambience5"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_PUMP1.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_pump1"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_PUMP2.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_pump2"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_PUMP3.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_pump3"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_VENT1.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_vent1"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_VENT2.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_vent2"))
                ));
        addSubtitled(ModSounds.GHAST_GIRL_INFLATE_VENT3.id(), definition()
                .with(
                        sound(Constants.id("ghast_girl/inflate_vent3"))
                ));

        // End

    }

    private void addSubtitled(final Identifier soundEvent, final SoundDefinition definition) {
        add(
                soundEvent,
                definition.subtitle("sounds." + Constants.MOD_ID + "." + soundEvent.getPath())
        );
    }
}
