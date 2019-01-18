package monopooly.gui.controllers;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/juego/Juego.fxml", title = "MonoPOOly")
public class JuegoController {

    @FXML
    private BorderPane panelTablero;

    @FXML
    private GridPane ladoSur;

    @FXML
    private GridPane ladoNorte;

    @FXML
    private GridPane ladoIzquierda;

    @FXML
    private GridPane ladoDerecha;

    @FXML
    private Label label;

    private void dibujarLadoSur() {
        GridPane casillaTmp;
        for (int i = 1; i < 10; i++) {
            ladoSur.addColumn(i);
            casillaTmp = new GridPane();
            casillaTmp.getStyleClass().addAll("casilla", "casilla-normal");
            casillaTmp.setId("" + (10-i));
            casillaTmp.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
            casillaTmp.add(new Label("Cas " + casillaTmp.getId()), 0, 0);
            ladoSur.add(casillaTmp, i, 0);
        }
        ladoSur.addColumn(10);
        casillaTmp = new GridPane();
        casillaTmp.getStyleClass().addAll("casilla", "casilla-esquina");
        casillaTmp.setId("10");
        casillaTmp.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
        ladoSur.add(casillaTmp, 10, 0);

        casillaTmp = new GridPane();
        casillaTmp.getStyleClass().addAll("casilla", "casilla-esquina");
        casillaTmp.setId("0");
        casillaTmp.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
        ladoSur.add(casillaTmp, 0, 0);
    }


    private void dibujarLadoIzquierda() {
        GridPane casillaTmp;
        for (int i = 0; i < 9; i++) {
            ladoIzquierda.addColumn(i);
            casillaTmp = new GridPane();
            casillaTmp.setId("" + (19 - i));
            casillaTmp.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
            casillaTmp.getStyleClass().addAll("casilla", "casilla-normal");
            casillaTmp.add(new Label("Cas " + casillaTmp.getId()), 0, 0);
            ladoIzquierda.add(casillaTmp, i, 0);
        }
    }

    @PostConstruct
    public void init() {
        dibujarLadoSur();
        dibujarLadoIzquierda();



    }

    private void handleClickCasilla(String id) {
        System.out.println(id);
    }
}
