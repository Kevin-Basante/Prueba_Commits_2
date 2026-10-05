package com.prosthetics.model;

import com.prosthetics.model.enums.PowerType;

/** Represents the power source of a bionic prosthesis, if any. */
public class PowerSource implements Cloneable {

    private final PowerType type;

    public PowerSource(PowerType type) {
        this.type = type;
    }

    public PowerType getType() {
        return type;
    }

    @Override
    public PowerSource clone() {
        try {
            return (PowerSource) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("PowerSource is Cloneable, this should never happen", e);
        }
    }

    @Override
    public String toString() {
        return type.getDisplayName();
    }
}
