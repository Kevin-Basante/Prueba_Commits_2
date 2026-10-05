package com.prosthetics.ui.controller;

import com.prosthetics.model.Prosthesis;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

/**
 * Controller for the "Comparison" tab.
 *
 * <p>Displays the original clinical template side by side with the
 * customized clone the user built from it, making the effect of the
 * Prototype pattern (independent deep copies) visually obvious.
 */
public class ComparisonController {

    @FXML private Label originalTitleLabel;
    @FXML private TextArea originalArea;
    @FXML private Label clonedTitleLabel;
    @FXML private TextArea clonedArea;

    public void showComparison(String templateKey, Prosthesis original, Prosthesis customizedClone) {
        originalTitleLabel.setText("Original template: " + templateKey);
        originalArea.setText(original.toString());

        clonedTitleLabel.setText("Customized clone (independent copy)");
        clonedArea.setText(customizedClone != null
                ? customizedClone.toString()
                : "Not built yet. Go to \"Build New Prosthesis\" and click Build.");
    }

    @FXML
    public void initialize() {
        originalTitleLabel.setText("Original template");
        clonedTitleLabel.setText("Customized clone");
        originalArea.setText("Clone a template from the Gallery tab to see the comparison here.");
        clonedArea.setText("");
    }
}
