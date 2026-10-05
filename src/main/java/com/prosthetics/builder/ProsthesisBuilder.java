package com.prosthetics.builder;

import com.prosthetics.model.CosmeticCover;
import com.prosthetics.model.JointSystem;
import com.prosthetics.model.PowerSource;
import com.prosthetics.model.Prosthesis;
import com.prosthetics.model.Sensor;
import com.prosthetics.model.Socket;
import com.prosthetics.model.enums.JointType;
import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.PowerType;
import com.prosthetics.model.enums.SocketType;

/**
 * Builder pattern implementation.
 *
 * <p>Assembles a {@link Prosthesis} step by step, allowing optional
 * components (sensors, power source, cosmetic cover) to be added only
 * when relevant, and validating the final configuration before it is
 * handed to the client via {@link #build()}.
 *
 * <p>Every {@code with*}/{@code add*} method returns {@code this} to allow
 * fluent chaining, which is the hallmark of this pattern.
 */
public class ProsthesisBuilder {

    private final Prosthesis prosthesis = new Prosthesis();

    public ProsthesisBuilder withLimbType(String limbType) {
        prosthesis.setLimbType(limbType);
        return this;
    }

    public ProsthesisBuilder withSocket(SocketType type, Material material, double sizeInCm) {
        prosthesis.setSocket(new Socket(type, material, sizeInCm));
        return this;
    }

    public ProsthesisBuilder withJointSystem(JointType type) {
        prosthesis.setJoint(new JointSystem(type));
        return this;
    }

    public ProsthesisBuilder withPowerSource(PowerType type) {
        if (type == null || type == PowerType.NONE) {
            prosthesis.setPowerSource(null);
        } else {
            prosthesis.setPowerSource(new PowerSource(type));
        }
        return this;
    }

    /**
     * Adds a sensor, enforcing the domain rule that any sensor requiring
     * power cannot be attached before a power source has been configured.
     */
    public ProsthesisBuilder addSensor(Sensor sensor) {
        if (sensor.requiresPower() && prosthesis.getPowerSource() == null) {
            throw new IllegalStateException(
                    "Cannot add " + sensor + ": this sensor requires a power source. "
                            + "Call withPowerSource(...) first.");
        }
        prosthesis.getSensors().add(sensor);
        return this;
    }

    public ProsthesisBuilder withCosmeticCover(String colorOrSkinTone, String finish) {
        prosthesis.setCover(new CosmeticCover(colorOrSkinTone, finish));
        return this;
    }

    /**
     * Validates the accumulated configuration and returns the finished product.
     *
     * @throws IllegalStateException if a mandatory component is missing.
     */
    public Prosthesis build() {
        validate();
        return prosthesis;
    }

    private void validate() {
        if (prosthesis.getLimbType() == null || prosthesis.getLimbType().isBlank()) {
            throw new IllegalStateException("Limb type is mandatory (e.g. Transfemoral, Transtibial).");
        }
        if (prosthesis.getSocket() == null) {
            throw new IllegalStateException("Socket is mandatory. Call withSocket(...) before build().");
        }
        if (prosthesis.getJoint() == null) {
            throw new IllegalStateException("Joint system is mandatory. Call withJointSystem(...) before build().");
        }
        if (prosthesis.getCover() == null) {
            throw new IllegalStateException("Cosmetic cover is mandatory. Call withCosmeticCover(...) before build().");
        }
    }
}
