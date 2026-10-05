package com.prosthetics.service;

/**
 * Small helper with UI-facing validation utilities that don't belong to
 * the domain builder itself (e.g. parsing/sanitizing raw text input from
 * JavaFX controls before it reaches {@link com.prosthetics.builder.ProsthesisBuilder}).
 */
public final class ValidationService {

    private ValidationService() {
    }

    /**
     * Parses a socket size typed by the user, ensuring it is a positive number
     * within a clinically plausible range.
     *
     * @throws IllegalArgumentException if the text is not a valid, plausible size.
     */
    public static double parseSocketSize(String rawText) {
        if (rawText == null || rawText.isBlank()) {
            throw new IllegalArgumentException("Socket size cannot be empty.");
        }
        double value;
        try {
            value = Double.parseDouble(rawText.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Socket size must be a valid number (in centimeters).");
        }
        if (value <= 0 || value > 100) {
            throw new IllegalArgumentException("Socket size must be between 0 and 100 cm.");
        }
        return value;
    }

    public static String requireNonBlank(String rawText, String fieldName) {
        if (rawText == null || rawText.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        return rawText.trim();
    }
}
