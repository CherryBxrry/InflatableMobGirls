package io.github.cherrybxrry.inflatablemobgirls.block.state.property;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum NetherGeyserState implements StringRepresentable {
    DORMANT("dormant"),
    ERUPTING("erupting"),
    CONTINUOUS("continuous");

    private final String name;

    NetherGeyserState(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public @NonNull String getSerializedName() {
        return this.name;
    }
}
