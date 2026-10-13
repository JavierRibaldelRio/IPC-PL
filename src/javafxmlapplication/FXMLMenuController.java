/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author javi
 */
public class FXMLMenuController implements Initializable {

    @FXML
    private Label dificultadLabel;
    @FXML
    private Label resumenLabel;
    @FXML
    private ComboBox<String> modoCombo;
    @FXML
    private CheckBox aceptoCheck;
    @FXML
    private TextField nombreField;
    @FXML
    private Label nombreErrorLabel;
    @FXML
    private Slider dificultadSlider;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dificultadLabel.textProperty().bind(Bindings.concat(
                "Dificultad: ", dificultadSlider.valueProperty().asString("%.0f")));

        modoCombo.getItems().addAll("Clásico", "Contrarreloj");
        modoCombo.setValue("Clásico");

        this.nombreField.focusedProperty().addListener((a, b, c) -> {
            // Si has perdido el foco
            if (!c) {
                if (nombreField.getText().length() < 3) {
                    this.nombreErrorLabel.setVisible(true);
                    nombreField.setStyle("-fx-border-color: red;");
                } else {
                    this.nombreErrorLabel.setVisible(false);
                    nombreField.setStyle("");
                }

            }
        });

    }

    @FXML
    private void empezarButton(ActionEvent event) {
    }

}
