package com.prosthetics.ui.controller;

import com.prosthetics.model.Prosthesis;
import com.prosthetics.service.ProsthesisService;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * Root controller for main.fxml.
 *
 * <p>Owns the single shared {@link ProsthesisService} instance and wires
 * the three included tabs together:
 * <ul>
 *   <li>Builder Wizard (Builder pattern)</li>
 *   <li>Template Gallery (Prototype pattern)</li>
 *   <li>Comparison (visualizes the effect of cloning)</li>
 * </ul>
 */
public class MainController {

    @FXML private TabPane mainTabPane;
    @FXML private Tab builderTab;
    @FXML private Tab galleryTab;
    @FXML private Tab comparisonTab;

    // Injected automatically by FXMLLoader because of fx:id="builderWizard" + "Controller" suffix
    // on the <fx:include> elements in main.fxml.
    @FXML private BuilderWizardController builderWizardController;
    @FXML private TemplateGalleryController templateGalleryController;
    @FXML private ComparisonController comparisonController;

    private final ProsthesisService service = new ProsthesisService();

    private String lastClonedTemplateKey;

    @FXML
    public void initialize() {
        builderWizardController.setService(service);
        templateGalleryController.setService(service);

        // When a template is cloned in the Gallery tab, load it into the Builder
        // wizard for customization and jump the user straight there.
        templateGalleryController.setOnCloneRequested((templateKey, clonedProsthesis) -> {
            lastClonedTemplateKey = templateKey;
            builderWizardController.loadFromPrototype(clonedProsthesis);
            mainTabPane.getSelectionModel().select(builderTab);
        });

        // When the user saves a new template from the Builder tab, refresh the gallery
        // and, if it came from a clone, update the comparison view.
        builderWizardController.setOnTemplateSaved(unused -> {
            templateGalleryController.refresh();
            updateComparisonIfApplicable();
        });
    }

    @FXML
    public void handleShowComparison() {
        updateComparisonIfApplicable();
        mainTabPane.getSelectionModel().select(comparisonTab);
    }

    private void updateComparisonIfApplicable() {
        Prosthesis lastBuilt = builderWizardController.getLastBuilt();
        if (lastClonedTemplateKey != null && service.templateExists(lastClonedTemplateKey)) {
            Prosthesis original = service.peekTemplate(lastClonedTemplateKey);
            comparisonController.showComparison(lastClonedTemplateKey, original, lastBuilt);
        }
    }
}
