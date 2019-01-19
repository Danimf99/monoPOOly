package monopooly.gui.controllers;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import monopooly.colocacion.Tablero;
import monopooly.gui.componentes.DibujoTableroHelper;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

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

    private void asignarAccionCasillas(GridPane lado) {
        for (Node child : lado.getChildren()) {
            child.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
        }
    }

    private void dibujarTablero() {
        DibujoTableroHelper.colocarCasillas(panelTablero);
        asignarAccionCasillas((GridPane) panelTablero.getTop());
        asignarAccionCasillas((GridPane) panelTablero.getBottom());
        asignarAccionCasillas((GridPane) panelTablero.getLeft());
        asignarAccionCasillas((GridPane) panelTablero.getRight());
    }

    @PostConstruct
    public void init() {
        /* Para la prueba */
        Tablero tablero = Tablero.getTablero();
        Jugador saul = new Jugador("Saul", Avatar.TIPO.esfinge);
        Jugador dani = new Jugador("Dani", Avatar.TIPO.esfinge);

        tablero.meterJugador(dani);
        tablero.meterJugador(saul);
        /* Fin cosas de prueba */
        dibujarTablero();


    }

    private void handleClickCasilla(String id) {
        System.out.println(id);
    }
}
