/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxmlapplication;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author jsoler
 */
public class FXMLDocumentController implements Initializable {

    private Label labelMessage;
    @FXML
    private ToggleButton tttButton;
    @FXML
    private ToggleButton puzzleButton;
    @FXML
    private BorderPane rootPane;
    
    private Node tttView = null , puzzleView =null, bolaView = null;
    @FXML
    private ToggleGroup toolBarGroup;
    @FXML
    private ToggleButton bolaButton;

    //=========================================================
    // you must initialize here all related with the object 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        try {

            // Cargar vista Tres en Raya
            FXMLLoader tttLoader = new FXMLLoader(
                    getClass().getResource("FXMLTttView.fxml")
            );

            tttView = tttLoader.load();

            // Cargar vista 8-Puzzle
            FXMLLoader puzzleLoader = new FXMLLoader(
                    getClass().getResource("FXML8PuzzleView.fxml")
            );
            puzzleView = puzzleLoader.load();
            // Cargar vista Bola
            FXMLLoader bolaLoader = new FXMLLoader(
                    getClass().getResource("FXMLBola.fxml")
            );
            bolaView = bolaLoader.load();

        } catch (IOException e) {
        }

        // Permite el cambio entre los diferentes botones
        toolBarGroup.selectedToggleProperty().addListener((obs, oldT, newT) -> {
            if (newT == tttButton) {
                rootPane.setCenter(tttView);
            } else if (newT == puzzleButton) {
                rootPane.setCenter(puzzleView);
            }
            else if (newT == bolaButton){
                rootPane.setCenter(bolaView);
            }
            
        });
        
        // asigna por defecto la
        rootPane.setCenter(tttView);


    }
    
    
    

}
