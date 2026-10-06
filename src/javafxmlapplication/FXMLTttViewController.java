/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author javi
 */
public class FXMLTttViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private char[][] tablero = new char[3][3];

    private int turnosJugados = 0;

    private boolean juegaCirculo = true;
    @FXML
    private Button reiniciarButton;
    @FXML
    private Label labelTurno;
    @FXML
    private GridPane panelGridttt;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reiniciarButton.setOnAction(event -> {
            turnosJugados = 0;
            tablero = new char[3][3];
            juegaCirculo = true;
            labelTurno.setText(generateLabel());
            panelGridttt.getChildren().forEach(celda -> {
                ((Button) celda).setDisable(false);
                ((Button) celda).setText("");
            });

        });
    }

    @FXML
    private void jugar(ActionEvent event) {

        turnosJugados++;

        // Obtener botón que ha sido disparado
        Button celda = (Button) event.getSource();

        // obtner fila columna
        int fila = getRow(celda);
        int columna = getColumn(celda);

        celda.setDisable(true);         // Deshabilitar la escritura

        // Marcar en array 
        char turno = getTurno();
        tablero[fila][columna] = turno;

        // Acualtizar en casilla
        celda.setText(Character.toString(turno));

        // Cambiar turno
        juegaCirculo = !juegaCirculo;

        // Comprobar si el juego a terminado / actulziar eqitueta
        Character ganador = (turnosJugados >= 5) ? calcularGanador() : null;

        if (ganador != null) {
            labelTurno.setText("Ganador " + ganador);
            bloquearBotones();

        } else if (turnosJugados == 9) {
            labelTurno.setText("Empate");
        } else {
            labelTurno.setText(generateLabel());
        }

    }

    private void bloquearBotones() {
        panelGridttt.getChildren().forEach(celda -> {
            ((Button) celda).setDisable(true);
        });

    }

    // Comprueba el ganador
    private Character calcularGanador() {

        // filas y columnas
        for (int i = 0; i < 3; i++) {

            // Filas
            if (tablero[i][0] == tablero[i][1] && tablero[i][1] == tablero[i][2] && tablero[i][0] != '\0') {
                return tablero[i][0];
            }

            //Columnas
            if (tablero[0][i] == tablero[1][i] && tablero[1][i] == tablero[2][i] && tablero[0][i] != '\0') {
                return tablero[0][i];
            }
        }

        //Diagonal principal
        if (tablero[1][1] != '\0') {
            if (tablero[0][0] == tablero[1][1] && tablero[1][1] == tablero[2][2]) {
                return tablero[0][0];
            }

            //Digaonal secundaria
            if (tablero[0][2] == tablero[1][1] && tablero[1][1] == tablero[2][0]) {
                return tablero[0][2];
            }
        }
        return null;

    }

    private char getTurno() {
        return juegaCirculo ? 'O' : 'X';
    }

    private String generateLabel() {

        return "Turno " + getTurno();
    }

    private int getRow(Node n) {
        Integer f = GridPane.getRowIndex(n);
        return (f == null) ? 0 : f;
    }

    private int getColumn(Node n) {
        Integer c = GridPane.getColumnIndex(n);
        return (c == null) ? 0 : c;
    }

    public int columnCalc(GridPane grid, double x) {
        double anchoCelda = grid.getWidth() / grid.getColumnCount();
        return (int) (x / anchoCelda);
    }

    public int rowCalc(GridPane grid, double y) {
        double altoCelda = grid.getHeight() / grid.getRowCount();
        return (int) (y / altoCelda);
    }
}
