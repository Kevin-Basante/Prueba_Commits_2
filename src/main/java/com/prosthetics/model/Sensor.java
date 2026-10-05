package com.prosthetics.model;

import com.prosthetics.model.enums.SensorType;

/** Represents a single sensor attached to the prosthesis. */
public class Sensor implements Cloneable {

    private final SensorType type;

    public Sensor(SensorType type) {
        this.type = type;
    }

    public SensorType getType() {
        return type;
    }

    public boolean requiresPower() {
        return type.requiresPower();
    }

    @Override
    public Sensor clone() {
        try {
            return (Sensor) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Sensor is Cloneable, this should never happen", e);
        }
    }

    @Override
    public String toString() {
        return type.getDisplayName();
    }
}
