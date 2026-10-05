package com.prosthetics.model.enums;

/** Types of articulation systems (knee, elbow, ankle, etc.). */
public enum JointType {
    MECHANICAL_HINGE("Mechanical Hinge Joint"),
    HYDRAULIC("Hydraulic Joint"),
    MICROPROCESSOR_KNEE("Microprocessor-Controlled Knee"),
    MYOELECTRIC_ELBOW("Myoelectric Elbow Joint");

    private final String displayName;

    JointType(String displayName) {
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
