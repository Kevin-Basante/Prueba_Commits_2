package com.prosthetics.model.enums;

/** Sensor types that can be attached to a bionic prosthesis. */
public enum SensorType {
    MYOELECTRIC("Myoelectric Sensor", true),
    PRESSURE("Pressure Sensor", false),
    GYROSCOPIC("Gyroscopic Motion Sensor", true),
    TEMPERATURE("Temperature Sensor", false);

    private final String displayName;
    private final boolean requiresPower;

    SensorType(String displayName, boolean requiresPower) {
        this.displayName = displayName;
        this.requiresPower = requiresPower;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean requiresPower() {
        return requiresPower;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
