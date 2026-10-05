package com.prosthetics.prototype;

import com.prosthetics.model.Prosthesis;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Prototype Registry.
 *
 * <p>Holds a catalog of clinically validated {@link Prosthesis} templates.
 * Instead of rebuilding a configuration from scratch for every new patient,
 * clinicians retrieve a template by key and receive an independent
 * {@link Prosthesis#clone() deep clone} of it, ready to be fine-tuned
 * (socket size, cosmetic color, etc.) for the specific case.
 *
 * <p>This is the central class of the Prototype pattern in this project.
 */
public class ProsthesisTemplateRegistry {

    private final Map<String, Prosthesis> templates = new LinkedHashMap<>();

    /**
     * Registers a prototype under the given key. The registry keeps its
     * own defensive clone so future external mutation of the caller's
     * object cannot corrupt the stored template.
     */
    public void registerTemplate(String key, Prosthesis prototype) {
        if (key == null || key.isBlank()) {
            throw new IllegalArgumentException("Template key cannot be empty.");
        }
        if (prototype == null) {
            throw new IllegalArgumentException("Cannot register a null prototype.");
        }
        templates.put(key, prototype.clone());
    }

    /**
     * Returns a brand-new, independent clone of the template registered
     * under {@code key}. Mutating the returned object never affects the
     * stored template nor any previously created clone.
     */
    public Prosthesis createFromTemplate(String key) {
        Prosthesis template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("Unknown template: " + key);
        }
        return template.clone();
    }

    /** Read-only peek at a template, without cloning (useful for UI previews/comparisons). */
    public Prosthesis peekTemplate(String key) {
        Prosthesis template = templates.get(key);
        if (template == null) {
            throw new IllegalArgumentException("Unknown template: " + key);
        }
        return template;
    }

    public boolean containsTemplate(String key) {
        return templates.containsKey(key);
    }

    public Set<String> getTemplateKeys() {
        return templates.keySet();
    }

    public void removeTemplate(String key) {
        templates.remove(key);
    }
}
