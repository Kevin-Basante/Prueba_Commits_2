package com.prosthetics.ui.controller;

import com.prosthetics.model.Prosthesis;
import com.prosthetics.service.ProsthesisService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.util.function.BiConsumer;

/**
 * Controller for the "Template Gallery" tab.
 *
 * <p>Lists every clinical template currently registered in
 * {@link com.prosthetics.prototype.ProsthesisTemplateRegistry} and lets
 * the user clone one into the Builder wizard for further customization.
 * This is the primary showcase of the Prototype pattern in the UI.
 */
public class TemplateGalleryController {

    @FXML private ListView<String> templateListView;
    @FXML private TextArea templateDetailsArea;

    private ProsthesisService service;

    /** Receives (templateKey, clonedProsthesis) when the user clicks "Clone & Customize". */
    private BiConsumer<String, Prosthesis> onCloneRequested;

    public void setService(ProsthesisService service) {
        this.service = service;
        refresh();
    }

    public void setOnCloneRequested(BiConsumer<String, Prosthesis> callback) {
        this.onCloneRequested = callback;
    }

    @FXML
    public void initialize() {
        templateListView.getSelectionModel().selectedItemProperty().addListener((obs, oldKey, newKey) -> {
            if (newKey != null && service != null) {
                templateDetailsArea.setText(service.peekTemplate(newKey).toString());
            }
        });
    }

    /** Reloads the list of template names from the service (e.g. after a new one is saved). */
    public void refresh() {
        if (service == null) return;
        String previouslySelected = templateListView.getSelectionModel().getSelectedItem();
        templateListView.setItems(FXCollections.observableArrayList(service.getTemplateKeys()));
        if (previouslySelected != null && service.templateExists(previouslySelected)) {
            templateListView.getSelectionModel().select(previouslySelected);
        }
    }

    @FXML
    public void handleCloneAndCustomize() {
        String selectedKey = templateListView.getSelectionModel().getSelectedItem();
        if (selectedKey == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Select a template from the list first.");
            alert.setHeaderText("No template selected");
            alert.showAndWait();
            return;
        }
        Prosthesis clone = service.cloneTemplate(selectedKey);
        if (onCloneRequested != null) {
            onCloneRequested.accept(selectedKey, clone);
        }
    }
}
