package io.github.cherrybxrry.inflatablemobgirls.datagen;

import io.github.cherrybxrry.inflatablemobgirls.Constants;
import io.github.cherrybxrry.inflatablemobgirls.init.ModParticleTypes;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.data.ParticleDescriptionProvider;

public class ModParticleDescriptionProvider extends ParticleDescriptionProvider {
    public ModParticleDescriptionProvider(PackOutput output) {
        super(output);
    }


    @Override
    protected void addDescriptions() {
        spriteSet(ModParticleTypes.CHARGED_SPARK.get(), Constants.id("charged_spark"), 16, false);
    }
}
