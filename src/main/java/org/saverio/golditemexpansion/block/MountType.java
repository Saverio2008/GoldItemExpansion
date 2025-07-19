package org.saverio.golditemexpansion.block;

import net.minecraft.util.StringIdentifiable;

public enum MountType implements StringIdentifiable {
    FLOOR("floor"),
    WALL("wall"),
    CEILING("ceiling");

    private final String name;

    MountType(String name) {
        this.name = name;
    }

    @Override
    public String asString() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}