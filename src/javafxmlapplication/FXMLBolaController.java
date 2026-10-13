/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package javafxmlapplication;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.ColorPicker;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author javi
 */
public class FXMLBolaController implements Initializable {

    @FXML
    private Circle circulo;
    @FXML
    private GridPane gridBola;

    double mouseX = 0, mouseY = 0;
    @FXML
    private ColorPicker colorPick;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        circulo.setFocusTraversable(true);
        circulo.requestFocus();

        // Asignación mediante 
        circulo.setOnMousePressed(this::handleMousePress);
        circulo.setOnMouseDragged(this::handleMouseDrag);
        circulo.setOnMouseReleased(this::handleMouseRelease);

        //colorPick.valueProperty().addListener((a, b, c) ->{circulo.setFill(c);});
        circulo.fillProperty().bind(colorPick.valueProperty());
        circulo.strokeProperty().bind(colorPick.valueProperty());
        // ??? -> Aquí falta que si está marcado el color de la bola  no se hacerlo con .bind()
        
        // Asignación del tamaño automático de la bola
        circulo.radiusProperty().bind(Bindings.min(gridBola.widthProperty().divide(10).subtract(4), gridBola.heightProperty().divide(10).subtract(4)));
    }

    @FXML
    private void handleBolaKeyPress(KeyEvent event) {

        KeyCode code = event.getCode();
        int fila = getRow(circulo);
        int columna = getColumn(circulo);

        switch (code) {
            case UP:
                fila--;
                break;
            case DOWN:
                fila++;
                break;
            case LEFT:
                columna--;
                break;
            case RIGHT:
                columna++;
                break;
        }

        // Marca el evento como gestionado correctamente, de esta forma evita que al presionar una flecha se cambie el focus a otro compoennte
        event.consume();

        GridPane.setConstraints(circulo, columnNorm(gridBola, columna), rowNorm(gridBola, fila));

    }

    @FXML //satlata bola
    private void handleClick(MouseEvent event) {

        // get focus 
        circulo.requestFocus();

        dejaBola(event.getX(), event.getY());

    }

    /**
     * ***************
     * For drag ball * *************
     */
    private void handleMousePress(MouseEvent event) {

        mouseX = event.getSceneX();
        mouseY = event.getSceneY();

    }

    private void handleMouseDrag(MouseEvent event) {

        circulo.setTranslateX(event.getSceneX() - mouseX);
        circulo.setTranslateY(event.getSceneY() - mouseY);

    }

    private void handleMouseRelease(MouseEvent event) {

        circulo.setTranslateX(0);
        circulo.setTranslateY(0);

        Point2D fin = gridBola.sceneToLocal(event.getSceneX(), event.getSceneY());

        dejaBola(fin.getX(), fin.getY());

        event.consume();

    }

    private void dejaBola(double x, double y) {

        int col = columnCalc(gridBola, x);
        int row = rowCalc(gridBola, y);

        GridPane.setConstraints(circulo, col, row);
    }

    private int getRow(Node n) {
        Integer f = gridBola.getRowIndex(n);
        return (f == null) ? 0 : f;
    }

    private int getColumn(Node n) {
        Integer c = gridBola.getColumnIndex(n);
        return (c == null) ? 0 : c;
    }

    public int rowNorm(GridPane grid, int fila) {
        int n = grid.getRowCount();
        return (fila + n) % n;
    }

    public int columnNorm(GridPane grid, int columna) {
        int n = grid.getColumnCount();
        return (columna + n) % n;
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
