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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import monopooly.Arranque;
import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.General;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.*;
import monopooly.excepciones.ExcepcionAcontecimiento;
import monopooly.excepciones.ExcepcionComando;
import monopooly.excepciones.ExcepcionComandoInexistente;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.*;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Caer;
import monopooly.sucesos.tipoSucesos.Guardado;

import javax.annotation.PostConstruct;
import java.util.Objects;

import static monopooly.Partida.interprete;

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
    @ActionTrigger("listarEnVenta")
    private JFXToggleButton botonEnVenta;

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

    @FXML
    private Label modDin;

    @FXML
    private Label dineroJugadorActual;

    @FXML
    private Label nombreJugadorActual;

    @FXML
    private Label tipoAvatar;




    /**
     * Coloca una tarjeta en el panel de informacion
     * @param titulo Titulo de la tarjeta
     * @param texto Mensaje de la tarjeta
     * @param color Color de la cabecera
     */
    private void emitirTarjeta(String titulo, String texto, String color) {
        infoSucesos.getChildren().addAll(TarjetasSucesos.crearTarjeta(titulo, texto, color));
        scrollSucesos.requestLayout();
        JFXScrollPane.smoothScrolling(scrollSucesos);
        scrollSucesos.setVvalue(1.0);

    }

    /* init() se llama despues de construir toda la vista del .fxml */

    @PostConstruct
    public void init() throws Exception {
        /* Para la prueba */
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
        AccionesPanelTablero.dibujarTablero(panelTablero);

        listaJugadores.getChildren().get(0).getStyleClass().add("boton-jugador-con-turno");

        /* Registro en los sucesos */
        interprete.registrar(this);
        this.setSubject(interprete);

        /* Preparado del panel de control */
        this.botonLanzarDados.setText("Lanzar\nDados");
        this.nombreJugadorActual.textProperty().bind(Tablero.getPrompt().nombreJugadorPropertyProperty());
        this.dineroJugadorActual.textProperty().bind(Tablero.getPrompt().dineroPropertyProperty());
        this.modDin.textProperty().bind(Tablero.getPrompt().modDIneroPropertyProperty());
        this.tipoAvatar.textProperty().bind(Tablero.getPrompt().tipoAvatarPropertyProperty());

    }

    /* Mostrado de los sucesos como tarjetas */
    @Override
    public void update() {
        botonPasarTurno.requestFocus();
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        if (suceso instanceof Caer) {
            Caer caida = (Caer) suceso;
            panelTablero.getChildren().stream()
                    .filter(Objects::nonNull)
                    .map(node -> (Pane) node)
                    .forEach(lado -> lado.getChildren().stream()
                            .filter(child -> child.getId() != null &&
                                    child.getId().equalsIgnoreCase(
                                            caida.getPosicion().getX() + ""
                                    )
                            ).findFirst()
                            .ifPresent(child -> {
                                if (suceso.getDeshacer()) {
                                    child.getStyleClass().remove("casilla-resaltada");
                                } else if (!child.getStyleClass().contains("casilla-resaltada")){
                                    child.getStyleClass().add("casilla-resaltada");
                                }
                            }));
        }

        if (suceso instanceof Guardado) {
            return;
        }

        if (suceso.getDeshacer()) {
            infoSucesos.getChildren().remove(suceso.getTarjeta());
        } else {
            infoSucesos.getChildren().add(suceso.getTarjeta());
        }

        Platform.runLater(() -> scrollSucesos.requestLayout());
        scrollSucesos.requestLayout();
        JFXScrollPane.smoothScrolling(scrollSucesos);
        scrollSucesos.setVvalue(1.0);

        scrollSucesos.requestFocus();
        botonPasarTurno.requestFocus();

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
        public void click() {
            switch (menuEstadisticas.getSelectionModel().getSelectedIndex()) {
                case 0:
                    // Estadisticas globales
                    AccionesPanelIzquierdo.mostrarEstadisticasGlobales();
                    break;
                case 1:
                    // Insertar comandos
                    AccionesPanelIzquierdo.insertarComando();
                    break;
                case 2:
                    AccionesPanelIzquierdo.listarJugadores();
                    break;
                case 3:
                    AccionesPanelIzquierdo.listarEnventa();
                    break;
                default:
                    // Nada
            }
        }
    }




    /* ActionMethods con las acciones para cada boton */

    @ActionMethod("pasarTurno")
    public void pasarTurno() {
        AccionesPanelControl.pasarTurno(
                listaJugadores,
                botonNitroso,
                panelTablero,
                infoSucesos,
                botonLanzarDados
        );
    }

    @ActionMethod("lanzarDados")
    public void lanzarDados() {
        try {
            Jugador jugador = Tablero.getTablero().getJugadorTurno();
            interprete.lanzar(jugador);
            this.botonLanzarDados.setText(jugador.getDados().getDado1() + " - " + jugador.getDados().getDado2() + "\n" + jugador.getDados().tirada());
        } catch (ExcepcionMonopooly excepcionMonopooly) {
            excepcionMonopooly.mostrarError();
        }
    }

    @ActionMethod("modificarNitroso")
    public void modificarNitroso(){
        try{
            interprete.cambiarModo(Tablero.getTablero().getJugadorTurno().getAvatar());
        }catch(ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.mostrarError();
        }
        this.botonNitroso.setSelected(Tablero.getPrompt().getJugador().getAvatar().isNitroso());

    }

    @ActionMethod("listarEnVenta")
    public void listarEnVenta(){
        System.out.println("HOLA");


    }

    @ActionMethod("construirCasa")
    public void construirCasa(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

            try {
                interprete.edificar(
                        casillaConstruir, Edificio.TIPO.casa
                );
            }catch (ExcepcionMonopooly excepcionMonopooly){
                excepcionMonopooly.mostrarError();
            }
    }
    @ActionMethod("construirHotel")
    public void construirHotel(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            interprete.edificar(
                    casillaConstruir, Edificio.TIPO.hotel
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.mostrarError();
        }
    }

    @ActionMethod("construirPiscina")
    public void construirPiscina(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            interprete.edificar(
                    casillaConstruir, Edificio.TIPO.piscina
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.mostrarError();
        }
    }
    @ActionMethod("construirPistaDeporte")
    public void construirPistaDeporte(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

        try {
            interprete.edificar(
                    casillaConstruir, Edificio.TIPO.deporte
            );
        }catch (ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.mostrarError();
        }

    }
    /* Metodos que se llaman con distintas acciones. Se asignan en init() */

    /**
     * Acciones que se hacen cuando se hace click en el circulo de un jugador
     * @param id String con el nombre del jugador correspondiente al botón
     */
    private void handleClickJugador(String id) {
        AccionesPanelIzquierdo.clickJugador(id);
    }


}
