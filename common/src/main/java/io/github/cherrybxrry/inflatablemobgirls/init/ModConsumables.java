package io.github.cherrybxrry.inflatablemobgirls.init;

import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;

public final class ModConsumables {
    private ModConsumables() {
        throw new UnsupportedOperationException("This class is a registry class");
    }

    public static final Consumable SOUL_CHOCOLATE = Consumables.defaultFood()
            .consumeSeconds(0.8F)
            .build();
}
