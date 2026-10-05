package com.prosthetics.prototype;

import com.prosthetics.builder.ProsthesisBuilder;
import com.prosthetics.model.Prosthesis;
import com.prosthetics.model.enums.JointType;
import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.PowerType;
import com.prosthetics.model.enums.SocketType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProsthesisTemplateRegistryTest {

    private ProsthesisTemplateRegistry registry;
    private Prosthesis original;

    @BeforeEach
    void setUp() {
        registry = new ProsthesisTemplateRegistry();
        original = new ProsthesisBuilder()
                .withLimbType("Transtibial")
                .withSocket(SocketType.TOTAL_SURFACE_BEARING, Material.CARBON_FIBER, 30.0)
                .withJointSystem(JointType.MECHANICAL_HINGE)
                .withPowerSource(PowerType.NONE)
                .withCosmeticCover("Beige", "Matte")
                .build();
        registry.registerTemplate("test-template", original);
    }

    @Test
    void clonedProsthesisIsANewIndependentObject() {
        Prosthesis clone = registry.createFromTemplate("test-template");

        assertNotSame(original, clone);
        assertNotSame(original.getSocket(), clone.getSocket());
        assertEquals(original.getSocket().getSizeInCm(), clone.getSocket().getSizeInCm());
    }

    @Test
    void mutatingTheCloneDoesNotAffectTheStoredTemplate() {
        Prosthesis clone = registry.createFromTemplate("test-template");
        clone.getSocket().setSizeInCm(99.0);

        Prosthesis storedTemplate = registry.peekTemplate("test-template");
        assertEquals(30.0, storedTemplate.getSocket().getSizeInCm());
    }

    @Test
    void unknownTemplateKeyThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> registry.createFromTemplate("does-not-exist"));
    }

    @Test
    void mutatingTheOriginalAfterRegistrationDoesNotAffectStoredTemplate() {
        original.getSocket().setSizeInCm(15.0); // mutate the original object AFTER registering it

        Prosthesis storedTemplate = registry.peekTemplate("test-template");
        assertEquals(30.0, storedTemplate.getSocket().getSizeInCm(),
                "Registry must keep its own defensive clone, unaffected by later external mutation.");
    }
}
