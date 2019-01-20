package monopooly.gui.controllers;

import com.jfoenix.controls.*;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import monopooly.Partida;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.HelperGui;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/juego/Juego.fxml", title = "MonoPOOly")
public class JuegoController implements Observador {

    private Subject subject;

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

    @FXML
    private ScrollPane scrollSucesos;

    @FXML
    private JFXMasonryPane infoSucesos;

    @FXML
    private JFXHamburger botonHamburguesa;

    @FXML
    private GridPane listaJugadores;

    private JFXPopup popupEstadisticas;

    @FXML
    @ActionTrigger("pasarTurno")
    private JFXButton botonPasarTurno;

    @FXML
    @ActionTrigger("lanzarDados")
    private JFXButton botonLanzarDados;

    @FXML
    @ActionTrigger("modificarNitroso")
    private JFXToggleButton botonNitroso;


    private void asignarAccionCasillas(GridPane lado) {
        for (Node child : lado.getChildren()) {
            child.setOnMouseClicked(event -> handleClickCasilla(((GridPane) event.getSource()).getId()));
        }
    }

    private void dibujarTablero() {
        HelperGui.colocarCasillas(panelTablero);
        asignarAccionCasillas((GridPane) panelTablero.getTop());
        asignarAccionCasillas((GridPane) panelTablero.getBottom());
        asignarAccionCasillas((GridPane) panelTablero.getLeft());
        asignarAccionCasillas((GridPane) panelTablero.getRight());
    }

    private void emitirTarjeta(String titulo, String texto, String color) {
        infoSucesos.getChildren().add(TarjetasSucesos.crearTarjeta(titulo, texto, color));
        JFXScrollPane.smoothScrolling(scrollSucesos);
        infoSucesos.requestLayout();
        Platform.runLater(() -> infoSucesos.requestLayout());
        infoSucesos.requestLayout();
    }

    private void emitirTarjeta(String titulo, String texto) {
        emitirTarjeta(titulo, texto, "#f88229");
    }


    @PostConstruct
    public void init() throws Exception {
        /* Para la prueba */
        Tablero tablero = Tablero.getTablero();
        Jugador saul = new Jugador("Saul", Avatar.TIPO.esfinge);
        Jugador dani = new Jugador("Dani", Avatar.TIPO.esfinge);

        tablero.meterJugador(dani);
        tablero.meterJugador(saul);
        emitirTarjeta("Nueva partida", "Have fun !", "#23C9FF");
        /* Fin cosas de prueba */


        /* Preparacion del menu de estadisticas */
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("/fxml/juego/PopupEstadisticas.fxml"));
        fxmlLoader.setController(new EstadisticasController());
        popupEstadisticas = new JFXPopup(fxmlLoader.load());
        botonHamburguesa.setOnMouseClicked(event -> {
            popupEstadisticas.show(
                    botonHamburguesa,
                    JFXPopup.PopupVPosition.TOP,
                    JFXPopup.PopupHPosition.LEFT,
                    0,
                    0
            );
        });

        /* Botones de los jugadores */
        HelperGui.colocarJugadores(listaJugadores);
        for (Node botonJugador : listaJugadores.getChildren()) {
            botonJugador.setOnMouseClicked(event -> handleClickJugador(((JFXButton) event.getSource()).getId()));
        }

        /* Pintado del tablero */
        dibujarTablero();

        /* Registro en los sucesos */
        Partida.interprete.registrar(this);
        this.setSubject(Partida.interprete);

    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        if (suceso.getDeshacer()) {
            infoSucesos.getChildren().remove(suceso.tarjeta());
            return;
        }
        infoSucesos.getChildren().add(suceso.tarjeta());
        infoSucesos.requestLayout();
        scrollSucesos.autosize();
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    private static final class EstadisticasController {
        @FXML
        private JFXListView<?> menuEstadisticas;

        @FXML
        private void submit() {
            switch (menuEstadisticas.getSelectionModel().getSelectedIndex()) {
                case 0:
                    // Estadisticas globales
                    System.out.println("Estadisticas globales");
                    break;
                case 1:
                    // Insertar comandos
                    System.out.println("InsertarComandos");
                    break;
                default:
                    // Nada
            }
        }
    }

    @ActionMethod("pasarTurno")
    public void pasarTurno() {
        try {
            Tablero.getTablero().pasarTurno();
            emitirTarjeta("Turno", "Cambio de turno", "#23C9FF");
        } catch (ExcepcionMonopooly excepcionMonopooly) {
            System.out.println(excepcionMonopooly.getMessage());
        }
    }

    @ActionMethod("lanzarDados")
    public void lanzarDados() {
        try {
            Partida.interprete.lanzar(Tablero.getTablero().getJugadorTurno());
        } catch (ExcepcionMonopooly excepcionMonopooly) {
            System.out.println(excepcionMonopooly.getMessage());
        }
    }

    @ActionMethod("modificarNitroso")
    public void modificarNitroso(){
        System.out.println("modificar nitroso");
    }


    private void handleClickJugador(String id) {
        System.out.println(id);
    }

    private void handleClickCasilla(String id) {
        System.out.println(id);
        emitirTarjeta(id, "texto casilla");
    }
}
