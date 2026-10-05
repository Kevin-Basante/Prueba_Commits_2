package com.prosthetics.builder;

import com.prosthetics.model.Prosthesis;
import com.prosthetics.model.Sensor;
import com.prosthetics.model.enums.JointType;
import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.PowerType;
import com.prosthetics.model.enums.SensorType;
import com.prosthetics.model.enums.SocketType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProsthesisBuilderTest {

    @Test
    void buildsACompleteValidProsthesis() {
        Prosthesis result = new ProsthesisBuilder()
                .withLimbType("Transtibial")
                .withSocket(SocketType.PATELLAR_TENDON_BEARING, Material.CARBON_FIBER, 30.0)
                .withJointSystem(JointType.MECHANICAL_HINGE)
                .withPowerSource(PowerType.NONE)
                .withCosmeticCover("Beige", "Matte")
                .build();

        assertEquals("Transtibial", result.getLimbType());
        assertEquals(SocketType.PATELLAR_TENDON_BEARING, result.getSocket().getType());
        assertEquals(30.0, result.getSocket().getSizeInCm());
    }

    @Test
    void missingMandatorySocketThrowsException() {
        ProsthesisBuilder builder = new ProsthesisBuilder()
                .withLimbType("Transtibial")
                .withJointSystem(JointType.MECHANICAL_HINGE)
                .withCosmeticCover("Beige", "Matte");

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void sensorRequiringPowerCannotBeAddedWithoutPowerSource() {
        ProsthesisBuilder builder = new ProsthesisBuilder()
                .withLimbType("Transradial")
                .withSocket(SocketType.SUBISCHIAL, Material.THERMOPLASTIC, 22.0)
                .withJointSystem(JointType.MYOELECTRIC_ELBOW)
                .withPowerSource(PowerType.NONE);

        assertThrows(IllegalStateException.class,
                () -> builder.addSensor(new Sensor(SensorType.MYOELECTRIC)));
    }

    @Test
    void sensorRequiringPowerIsAcceptedOncePowerSourceIsSet() {
        Prosthesis result = new ProsthesisBuilder()
                .withLimbType("Transradial")
                .withSocket(SocketType.SUBISCHIAL, Material.THERMOPLASTIC, 22.0)
                .withJointSystem(JointType.MYOELECTRIC_ELBOW)
                .withPowerSource(PowerType.LITHIUM_BATTERY)
                .addSensor(new Sensor(SensorType.MYOELECTRIC))
                .withCosmeticCover("Tan", "Realistic Skin Texture")
                .build();

        assertEquals(1, result.getSensors().size());
    }
}
