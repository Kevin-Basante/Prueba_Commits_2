package com.prosthetics.builder;

import com.prosthetics.model.Prosthesis;
import com.prosthetics.model.Sensor;
import com.prosthetics.model.enums.JointType;
import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.PowerType;
import com.prosthetics.model.enums.SensorType;
import com.prosthetics.model.enums.SocketType;

/**
 * Director of the Builder pattern.
 *
 * <p>Encapsulates well-known, clinically validated "recipes" so that
 * common configurations don't have to be re-typed by every caller.
 * These recipes are later registered as prototypes in
 * {@link com.prosthetics.prototype.ProsthesisTemplateRegistry}, which is
 * exactly where Builder and Prototype meet in this project: Builder
 * creates the master template, Prototype clones it for each new patient.
 */
public class ClinicalConfigurationDirector {

    public Prosthesis buildStandardTransfemoral() {
        return new ProsthesisBuilder()
                .withLimbType("Transfemoral (above-knee)")
                .withSocket(SocketType.ISCHIAL_CONTAINMENT, Material.CARBON_FIBER, 38.0)
                .withJointSystem(JointType.MICROPROCESSOR_KNEE)
                .withPowerSource(PowerType.LITHIUM_BATTERY)
                .addSensor(new Sensor(SensorType.MYOELECTRIC))
                .addSensor(new Sensor(SensorType.GYROSCOPIC))
                .withCosmeticCover("Natural Beige", "Realistic Skin Texture")
                .build();
    }

    public Prosthesis buildStandardTranstibialSport() {
        return new ProsthesisBuilder()
                .withLimbType("Transtibial (below-knee) - Sport")
                .withSocket(SocketType.TOTAL_SURFACE_BEARING, Material.CARBON_FIBER, 32.0)
                .withJointSystem(JointType.MECHANICAL_HINGE)
                .withPowerSource(PowerType.NONE)
                .addSensor(new Sensor(SensorType.PRESSURE))
                .withCosmeticCover("Matte Black", "Matte")
                .build();
    }

    public Prosthesis buildBasicPassiveTransradial() {
        return new ProsthesisBuilder()
                .withLimbType("Transradial (below-elbow) - Passive")
                .withSocket(SocketType.SUBISCHIAL, Material.THERMOPLASTIC, 24.0)
                .withJointSystem(JointType.MECHANICAL_HINGE)
                .withPowerSource(PowerType.NONE)
                .withCosmeticCover("Natural Tan", "Realistic Skin Texture")
                .build();
    }

    public Prosthesis buildAdvancedBionicTransradial() {
        return new ProsthesisBuilder()
                .withLimbType("Transradial (below-elbow) - Bionic")
                .withSocket(SocketType.PATELLAR_TENDON_BEARING, Material.TITANIUM_ALLOY, 22.5)
                .withJointSystem(JointType.MYOELECTRIC_ELBOW)
                .withPowerSource(PowerType.KINETIC_HARVESTING)
                .addSensor(new Sensor(SensorType.MYOELECTRIC))
                .addSensor(new Sensor(SensorType.TEMPERATURE))
                .withCosmeticCover("Carbon Grey", "Glossy")
                .build();
    }
}
