package com.prosthetics.prototype;

import com.prosthetics.builder.ClinicalConfigurationDirector;

/**
 * Bootstraps the {@link ProsthesisTemplateRegistry} with a set of
 * clinically validated templates built via the
 * {@link ClinicalConfigurationDirector} (Builder pattern).
 *
 * <p>This class is the explicit "handshake" between the two patterns:
 * Builder constructs each master configuration once; Prototype then
 * takes over to clone it for every new patient.
 */
public final class TemplateInitializer {

    private TemplateInitializer() {
        // Utility class; not meant to be instantiated.
    }

    public static void loadDefaultTemplates(ProsthesisTemplateRegistry registry) {
        ClinicalConfigurationDirector director = new ClinicalConfigurationDirector();

        registry.registerTemplate("Standard Transfemoral (Microprocessor Knee)",
                director.buildStandardTransfemoral());

        registry.registerTemplate("Transtibial Sport Configuration",
                director.buildStandardTranstibialSport());

        registry.registerTemplate("Basic Passive Transradial",
                director.buildBasicPassiveTransradial());

        registry.registerTemplate("Advanced Bionic Transradial",
                director.buildAdvancedBionicTransradial());
    }
}
