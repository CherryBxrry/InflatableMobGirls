package io.github.cherrybxrry.inflatablemobgirls.blocks.state.properties;

import net.minecraft.util.StringRepresentable;
import org.jspecify.annotations.NonNull;

public enum NetherGeyserState implements StringRepresentable {
    DORMANT("dormant"),
    ERUPTING("erupting"),
    CONTINUOUS("continuous");

    private final String name;

    private NetherGeyserState(final String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public @NonNull String getSerializedName() {
        return this.name;
    }
}
