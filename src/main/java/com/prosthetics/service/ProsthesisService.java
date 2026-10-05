package com.prosthetics.service;

import com.prosthetics.model.Prosthesis;
import com.prosthetics.prototype.ProsthesisTemplateRegistry;
import com.prosthetics.prototype.TemplateInitializer;

import java.util.Set;

/**
 * Application-level facade used by the UI layer.
 *
 * <p>Keeps JavaFX controllers free of direct dependencies on the
 * builder/prototype packages: controllers only ever talk to this class.
 */
public class ProsthesisService {

    private final ProsthesisTemplateRegistry registry = new ProsthesisTemplateRegistry();

    public ProsthesisService() {
        TemplateInitializer.loadDefaultTemplates(registry);
    }

    public Set<String> getTemplateKeys() {
        return registry.getTemplateKeys();
    }

    public Prosthesis peekTemplate(String key) {
        return registry.peekTemplate(key);
    }

    public Prosthesis cloneTemplate(String key) {
        return registry.createFromTemplate(key);
    }

    public void saveAsTemplate(String key, Prosthesis prosthesis) {
        registry.registerTemplate(key, prosthesis);
    }

    public boolean templateExists(String key) {
        return registry.containsTemplate(key);
    }
}
