package com.prosthetics.ui.controller;

import com.prosthetics.builder.ProsthesisBuilder;
import com.prosthetics.model.Prosthesis;
import com.prosthetics.model.Sensor;
import com.prosthetics.model.enums.JointType;
import com.prosthetics.model.enums.Material;
import com.prosthetics.model.enums.PowerType;
import com.prosthetics.model.enums.SensorType;
import com.prosthetics.model.enums.SocketType;
import com.prosthetics.service.ProsthesisService;
import com.prosthetics.service.ValidationService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.function.Consumer;

/**
 * Controller for the "Build New Prosthesis" tab.
 *
 * <p>Drives the {@link ProsthesisBuilder} step by step from user input,
 * and can also be pre-populated from a cloned {@link Prosthesis} coming
 * from the Template Gallery tab (Prototype pattern), so the user edits
 * a copy instead of starting from a blank form.
 */
public class BuilderWizardController {

    @FXML private TextField limbTypeField;
    @FXML private ChoiceBox<SocketType> socketTypeChoice;
    @FXML private ChoiceBox<Material> materialChoice;
    @FXML private TextField socketSizeField;
    @FXML private ChoiceBox<JointType> jointTypeChoice;
    @FXML private ChoiceBox<PowerType> powerTypeChoice;
    @FXML private CheckBox myoelectricSensorCheck;
    @FXML private CheckBox pressureSensorCheck;
    @FXML private CheckBox gyroscopicSensorCheck;
    @FXML private CheckBox temperatureSensorCheck;
    @FXML private TextField coverColorField;
    @FXML private TextField coverFinishField;
    @FXML private TextField saveAsTemplateNameField;
    @FXML private TextArea resultArea;

    private ProsthesisService service;
    private Prosthesis lastBuilt;
    private Consumer<Void> onTemplateSaved; // callback to refresh the gallery tab

    @FXML
    public void initialize() {
        socketTypeChoice.getItems().setAll(SocketType.values());
        materialChoice.getItems().setAll(Material.values());
        jointTypeChoice.getItems().setAll(JointType.values());
        powerTypeChoice.getItems().setAll(PowerType.values());
        powerTypeChoice.setValue(PowerType.NONE);
    }

    public void setService(ProsthesisService service) {
        this.service = service;
    }

    public void setOnTemplateSaved(Consumer<Void> callback) {
        this.onTemplateSaved = callback;
    }

    /** Pre-fills the form fields from a cloned prototype, ready for editing. */
    public void loadFromPrototype(Prosthesis prototype) {
        limbTypeField.setText(prototype.getLimbType());
        if (prototype.getSocket() != null) {
            socketTypeChoice.setValue(prototype.getSocket().getType());
            materialChoice.setValue(prototype.getSocket().getMaterial());
            socketSizeField.setText(String.valueOf(prototype.getSocket().getSizeInCm()));
        }
        if (prototype.getJoint() != null) {
            jointTypeChoice.setValue(prototype.getJoint().getType());
        }
        powerTypeChoice.setValue(prototype.getPowerSource() != null
                ? prototype.getPowerSource().getType() : PowerType.NONE);

        myoelectricSensorCheck.setSelected(hasSensor(prototype, SensorType.MYOELECTRIC));
        pressureSensorCheck.setSelected(hasSensor(prototype, SensorType.PRESSURE));
        gyroscopicSensorCheck.setSelected(hasSensor(prototype, SensorType.GYROSCOPIC));
        temperatureSensorCheck.setSelected(hasSensor(prototype, SensorType.TEMPERATURE));

        if (prototype.getCover() != null) {
            coverColorField.setText(prototype.getCover().getColorOrSkinTone());
            coverFinishField.setText(prototype.getCover().getFinish());
        }
        resultArea.setText("Loaded from template as a starting point. Adjust the fields and click \"Build\".");
    }

    private boolean hasSensor(Prosthesis prosthesis, SensorType type) {
        return prosthesis.getSensors().stream().anyMatch(s -> s.getType() == type);
    }

    @FXML
    public void handleBuild() {
        try {
            ProsthesisBuilder builder = new ProsthesisBuilder()
                    .withLimbType(ValidationService.requireNonBlank(limbTypeField.getText(), "Limb type"))
                    .withSocket(
                            requireChoice(socketTypeChoice, "Socket type"),
                            requireChoice(materialChoice, "Material"),
                            ValidationService.parseSocketSize(socketSizeField.getText()))
                    .withJointSystem(requireChoice(jointTypeChoice, "Joint type"))
                    .withPowerSource(powerTypeChoice.getValue());

            if (myoelectricSensorCheck.isSelected()) builder.addSensor(new Sensor(SensorType.MYOELECTRIC));
            if (pressureSensorCheck.isSelected()) builder.addSensor(new Sensor(SensorType.PRESSURE));
            if (gyroscopicSensorCheck.isSelected()) builder.addSensor(new Sensor(SensorType.GYROSCOPIC));
            if (temperatureSensorCheck.isSelected()) builder.addSensor(new Sensor(SensorType.TEMPERATURE));

            builder.withCosmeticCover(
                    ValidationService.requireNonBlank(coverColorField.getText(), "Cover color"),
                    ValidationService.requireNonBlank(coverFinishField.getText(), "Cover finish"));

            lastBuilt = builder.build();
            resultArea.setText("Successfully built:\n\n" + lastBuilt);
        } catch (IllegalArgumentException | IllegalStateException e) {
            lastBuilt = null;
            showError(e.getMessage());
        }
    }

    private <T> T requireChoice(ChoiceBox<T> choiceBox, String fieldName) {
        T value = choiceBox.getValue();
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must be selected.");
        }
        return value;
    }

    @FXML
    public void handleSaveAsTemplate() {
        if (lastBuilt == null) {
            showError("Build a valid prosthesis first before saving it as a template.");
            return;
        }
        String name;
        try {
            name = ValidationService.requireNonBlank(saveAsTemplateNameField.getText(), "Template name");
        } catch (IllegalArgumentException e) {
            showError(e.getMessage());
            return;
        }
        service.saveAsTemplate(name, lastBuilt);
        resultArea.appendText("\n\nSaved as new template: \"" + name + "\"");
        saveAsTemplateNameField.clear();
        if (onTemplateSaved != null) {
            onTemplateSaved.accept(null);
        }
    }

    public Prosthesis getLastBuilt() {
        return lastBuilt;
    }

    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR, message);
        alert.setHeaderText("Cannot build prosthesis");
        alert.showAndWait();
    }
}
