package io.github.cherrybxrry.inflatablemobgirls.init;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.platform.Services;
import io.github.cherrybxrry.inflatablemobgirls.platform.util.RegistryHandle;
import net.minecraft.sounds.SoundEvent;

public final class ModSounds {
    private ModSounds() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static void load() {
        Constants.LOG.info(Constants.REGISTRY_MARKER, "Registering Mod Sounds");
    }

    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BABY_BELLY_AMBIENCE =
            Services.REGISTRY.registerSoundEvent("creeper_girl_baby_belly_ambience");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BABY_INFLATE =
            Services.REGISTRY.registerSoundEvent("creeper_girl_baby_inflate");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BABY_DEFLATE =
            Services.REGISTRY.registerSoundEvent("creeper_girl_baby_deflate");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BABY_POPPING =
            Services.REGISTRY.registerSoundEvent("creeper_girl_baby_popping");

    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BELLY_AMBIENCE1 =
            Services.REGISTRY.registerSoundEvent("creeper_girl_belly_ambience1");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_BELLY_AMBIENCE2 =
            Services.REGISTRY.registerSoundEvent("creeper_girl_belly_ambience2");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_INFLATE = 
            Services.REGISTRY.registerSoundEvent("creeper_girl_inflate");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_DEFLATE =
            Services.REGISTRY.registerSoundEvent("creeper_girl_deflate");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_POPPING =
            Services.REGISTRY.registerSoundEvent("creeper_girl_popping");
    public static final RegistryHandle<SoundEvent> CREEPER_GIRL_POPPING_CHARGED =
            Services.REGISTRY.registerSoundEvent("creeper_girl_popping_charged");

    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_PUMP1 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_pump1");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_PUMP2 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_pump2");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_PUMP3 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_pump3");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_VENT1 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_vent1");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_VENT2 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_vent2");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_INFLATE_VENT3 =
            Services.REGISTRY.registerSoundEvent("ghast_girl_inflate_vent3");
    public static final RegistryHandle<SoundEvent> GHAST_GIRL_BELLY_AMBIENCE =
            Services.REGISTRY.registerSoundEvent("ghast_girl_belly_ambience");
}
