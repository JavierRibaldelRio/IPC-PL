/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author javi
 */
public class FXML8PuzzleViewController implements Initializable {

    /**
     * Initializes the controller class.
     */
    private static final char HUECO = ' ';
    private static final char[][] SOLUCION = {{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', HUECO}};
    @FXML
    private GridPane grid;
    private char[][] tablero = new char[3][3];
    private IntegerProperty movimientos = new SimpleIntegerProperty(0);
    private BooleanProperty resuelto = new SimpleBooleanProperty(false);
    private Posicion hueco;
    @FXML
    private Label movimientosLabel;
    @FXML
    private Label resueltoLabel;
    @FXML
    private Button newGame;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generarEstadoInicial();
        actualizarBotones();
        movimientosLabel.textProperty().bind(Bindings.concat("Movimientos: ", movimientos));
        resueltoLabel.visibleProperty().bind(resuelto);

        this.newGame.setOnMouseClicked((MouseEvent e) -> {
            generarEstadoInicial();
            actualizarBotones();
            movimientos.set(0);
            resuelto.set(false);
        });

    }

    private List<Posicion> posicionesVecinas(Posicion p) {
        List<Posicion> vecinas = new ArrayList<>();
        int[][] direcciones = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] d : direcciones) {
            int fila = p.fila + d[0], columna = p.columna + d[1];
            if (fila >= 0 && fila < 3 && columna >= 0 && columna < 3) {
                vecinas.add(new Posicion(fila, columna));
            }
        }
        return vecinas;
    }

    private void generarEstadoInicial() {
        tablero = new char[][]{{'1', '2', '3'}, {'4', '5', '6'}, {'7', '8', HUECO}};
        hueco = new Posicion(2, 2);
        Random random = new Random();
        for (int i = 0; i < 150; i++) {
            List<Posicion> vecinas = posicionesVecinas(hueco);
            Posicion destino = vecinas.get(random.nextInt(vecinas.size()));
            tablero[hueco.fila][hueco.columna] = tablero[destino.fila][destino.columna];
            tablero[destino.fila][destino.columna] = HUECO;
            hueco = destino;
        }
    }

    private void actualizarBotones() {
        for (Node n : grid.getChildren()) {
            int fila = getRow(n), columna = getColumn(n);
            char valor = tablero[fila][columna];
            ((Button) n).setText(valor == HUECO ? "" : String.valueOf(valor));
        }
    }

    private boolean esSolucion() {
        for (int fila = 0; fila < 3; fila++) {
            for (int columna = 0; columna < 3; columna++) {
                if (tablero[fila][columna] != SOLUCION[fila][columna]) {
                    return false;
                }
            }
        }
        return true;
    }

    @FXML
    private void handlePuzzleClick(ActionEvent event) {
        Button ficha = (Button) event.getSource();
        Posicion clic = new Posicion(getRow(ficha), getColumn(ficha));
        if (clic.esAdyacenteA(hueco)) {
            tablero[hueco.fila][hueco.columna] = tablero[clic.fila][clic.columna];
            tablero[clic.fila][clic.columna] = HUECO;
            hueco = clic;
            movimientos.set(movimientos.get() + 1);
            actualizarBotones();
            if (esSolucion()) {

                resuelto.set(true);
            }
        }
    }

    private int getRow(Node n) {
        Integer f = grid.getRowIndex(n);
        return (f == null) ? 0 : f;
    }

    private int getColumn(Node n) {
        Integer c = grid.getColumnIndex(n);
        return (c == null) ? 0 : c;
    }

}

class Posicion {

    int fila, columna;

    Posicion(int fila, int columna) {
        this.fila = fila;
        this.columna = columna;
    }

    boolean esAdyacenteA(Posicion otra) {
        return (fila == otra.fila && Math.abs(columna - otra.columna) == 1) || (columna == otra.columna && Math.abs(fila - otra.fila) == 1);
    }
}
