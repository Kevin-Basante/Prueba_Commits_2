package com.prosthetics.model;

import com.prosthetics.model.enums.JointType;

/** Represents the articulation (joint) system of the prosthesis. */
public class JointSystem implements Cloneable {

    private JointType type;

    public JointSystem(JointType type) {
        this.type = type;
    }

    public JointType getType() {
        return type;
    }

    @Override
    public JointSystem clone() {
        try {
            return (JointSystem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("JointSystem is Cloneable, this should never happen", e);
        }
    }

    @Override
    public String toString() {
        return String.format("JointSystem[type=%s]", type);
    }
}
