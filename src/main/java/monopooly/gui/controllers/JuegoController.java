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
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
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

    @FXML
    @ActionTrigger("construirCasa")
    private JFXButton botonConstruirCasa;

    @FXML
    @ActionTrigger("construirHotel")
    private JFXButton botonConstruirHotel;

    @FXML
    @ActionTrigger("construirPiscina")
    private JFXButton botonConstruirPiscina;

    @FXML
    @ActionTrigger("construirPistaDeporte")
    private JFXButton botonConstruirPistaDeporte;
    /**
     * Asigna la accion predeterminada a todas las casillas de un lado del tablero
     * @param lado Gridpane con las casillas
     */
    private void asignarAccionCasillas(GridPane lado) {
        lado.getChildren().forEach(
                child -> child.setOnMouseClicked(
                        event -> handleClickCasilla(((GridPane) event.getSource()).getId())
                )
        );
    }

    /**
     * Coloca las casillas en el tablero y les asigna la accion predeterminada
     */
    private void dibujarTablero() {
        HelperGui.colocarCasillas(panelTablero);
        asignarAccionCasillas((GridPane) panelTablero.getTop());
        asignarAccionCasillas((GridPane) panelTablero.getBottom());
        asignarAccionCasillas((GridPane) panelTablero.getLeft());
        asignarAccionCasillas((GridPane) panelTablero.getRight());
    }

    /**
     * Coloca una tarjeta en el panel de informacion
     * @param titulo Titulo de la tarjeta
     * @param texto Mensaje de la tarjeta
     * @param color Color de la cabecera
     */
    private void emitirTarjeta(String titulo, String texto, String color) {
        infoSucesos.getChildren().add(TarjetasSucesos.crearTarjeta(titulo, texto, color));
        JFXScrollPane.smoothScrolling(scrollSucesos);
        infoSucesos.requestLayout();
        Platform.runLater(() -> infoSucesos.requestLayout());
        infoSucesos.requestLayout();
    }

    /**
     * Coloca una tarjeta en el panel de informacion con el color predeterminado
     * @param titulo Titulo de la tarjeta
     * @param texto Mensaje de la tarjeta
     */
    private void emitirTarjeta(String titulo, String texto) {
        emitirTarjeta(titulo, texto, "#f88229");
    }

    /* init() se llama despues de construir toda la vista del .fxml */

    @PostConstruct
    public void init() throws Exception {
        /* Para la prueba */
        Tablero tablero = Tablero.getTablero();
        Jugador saul = new Jugador("Saul", Avatar.TIPO.coche);
        Jugador dani = new Jugador("Dani", Avatar.TIPO.coche);

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

    /* Mostrado de los sucesos como tarjetas */
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

    /**
     * Este es un pequeño controlador para el menu que aparece arriba a la
     * izquierda en la interfaz. Tiene las estadisticas y alguna cosilla suelta
     * que no quedaba bien por el medio de la interfaz; como insertar comandos
     * de consola.
     */
    public static final class EstadisticasController {
        @FXML
        private JFXListView<?> menuEstadisticas;

        @FXML
        private void click() {
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

    /* ActionMethods con las acciones para cada boton */

    @ActionMethod("pasarTurno")
    public void pasarTurno() {
        try {
            Tablero.getTablero().pasarTurno();
            // TODO: Cambiar el color de los circulos de Jugador a la izquierda.
            // El que tiene turno habría que resaltarlo, y poner los demás en blanco.
            if(Tablero.getTablero().getJugadorTurno().getAvatar().isNitroso()) {
              botonNitroso.setSelected(true);
            }
            else{
                botonNitroso.setSelected(false);
            }
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
        try{
            Partida.interprete.cambiarModo(Tablero.getTablero().getJugadorTurno().getAvatar());
        }catch(ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }

    }

    @ActionMethod("construirCasa")
    public void construirCasa(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

            try {
                Partida.interprete.edificar(
                        casillaConstruir, Edificio.TIPO.casa
                );
            }catch (ExcepcionMonopooly excepcionMonopooly){
                excepcionMonopooly.imprimeError();
            }
    }
    @ActionMethod("construirHotel")
    public void construirHotel(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            Partida.interprete.edificar(
                    casillaConstruir, Edificio.TIPO.hotel
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }
    }

    @ActionMethod("construirPiscina")
    public void construirPiscina(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            Partida.interprete.edificar(
                    casillaConstruir, Edificio.TIPO.piscina
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }
    }
    @ActionMethod("construirPistaDeporte")
    public void construirPistaDeporte(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            Partida.interprete.edificar(
                    casillaConstruir, Edificio.TIPO.deporte
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }
    }
    /* Metodos que se llaman con distintas acciones. Se asignan en init() */

    /**
     * Acciones que se hacen cuando se hace click en el circulo de un jugador
     * @param id String con el nombre del jugador correspondiente al botón
     */
    private void handleClickJugador(String id) {
        System.out.println(id);
    }

    /**
     * Acciones que se hacen cuando se hace click en una casilla
     * @param id String con el número correspondiente a la posición actual de
     *           esa casilla
     */
    private void handleClickCasilla(String id) {
        System.out.println(id);
    }
}
