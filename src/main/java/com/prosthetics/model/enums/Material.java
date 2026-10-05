package com.prosthetics.model.enums;

/** Structural materials used to manufacture the socket. */
public enum Material {
    CARBON_FIBER("Carbon Fiber"),
    THERMOPLASTIC("Thermoplastic"),
    TITANIUM_ALLOY("Titanium Alloy"),
    SILICONE_LINED_COMPOSITE("Silicone-Lined Composite");

    private final String displayName;

    Material(String displayName) {
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
