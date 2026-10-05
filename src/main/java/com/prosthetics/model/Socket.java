package com.prosthetics.model;

import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.SocketType;

/**
 * Represents the socket component: the custom-fitted interface between
 * the residual limb and the rest of the prosthetic device.
 */
public class Socket implements Cloneable {

    private SocketType type;
    private Material material;
    private double sizeInCm;

    public Socket(SocketType type, Material material, double sizeInCm) {
        this.type = type;
        this.material = material;
        this.sizeInCm = sizeInCm;
    }

    public SocketType getType() {
        return type;
    }

    public Material getMaterial() {
        return material;
    }

    public double getSizeInCm() {
        return sizeInCm;
    }

    public void setSizeInCm(double sizeInCm) {
        this.sizeInCm = sizeInCm;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    /**
     * Sockets contain only primitive/immutable fields, so a shallow copy
     * produced by {@link Object#clone()} is already a safe deep copy here.
     */
    @Override
    public Socket clone() {
        try {
            return (Socket) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Socket is Cloneable, this should never happen", e);
        }
    }

    @Override
    public String toString() {
        return String.format("Socket[type=%s, material=%s, size=%.1fcm]", type, material, sizeInCm);
    }
}
