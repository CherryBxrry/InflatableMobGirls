package io.github.cherrybxrry.inflatablemobgirls.init;

import net.minecraft.world.food.FoodProperties;

public final class ModFoods {
    private ModFoods() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final FoodProperties SOUL_CHOCOLATE = new FoodProperties.Builder()
            .alwaysEdible()
            .nutrition(2)
            .saturationModifier(0.1F)
            .build();
}
