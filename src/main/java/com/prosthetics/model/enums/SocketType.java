package com.prosthetics.model.enums;

/**
 * Types of prosthetic sockets, the interface between the residual limb
 * and the rest of the prosthetic device.
 */
public enum SocketType {
    ISCHIAL_CONTAINMENT("Ischial Containment Socket"),
    PATELLAR_TENDON_BEARING("Patellar Tendon Bearing Socket"),
    SUBISCHIAL("Subischial Socket"),
    TOTAL_SURFACE_BEARING("Total Surface Bearing Socket");

    private final String displayName;

    SocketType(String displayName) {
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
