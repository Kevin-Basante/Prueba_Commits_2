package com.prosthetics.model.enums;

/** Power sources available for bionic / microprocessor-controlled prostheses. */
public enum PowerType {
    NONE("No Power Source (Passive Device)"),
    LITHIUM_BATTERY("Rechargeable Lithium Battery"),
    KINETIC_HARVESTING("Kinetic Energy Harvesting Cell");

    private final String displayName;

    PowerType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
