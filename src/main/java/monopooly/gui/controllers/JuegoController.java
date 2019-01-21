package monopooly.gui.controllers;

import com.jfoenix.controls.*;
import com.jfoenix.effects.JFXDepthManager;
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
import javafx.stage.Modality;
import monopooly.Arranque;
import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Prompt;
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
import monopooly.sucesos.tipoSucesos.Guardado;

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

    @FXML
    private Label modDin;

    @FXML
    private Label dineroJugadorActual;

    @FXML
    private Label nombreJugadorActual;

    @FXML
    private Label tipoAvatar;


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
        infoSucesos.getChildren().addAll(TarjetasSucesos.crearTarjeta(titulo, texto, color));
        scrollSucesos.requestLayout();
        JFXScrollPane.smoothScrolling(scrollSucesos);
        scrollSucesos.setVvalue(1.0);

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

        listaJugadores.getChildren().get(0).getStyleClass().add("boton-jugador-con-turno");
        /* Registro en los sucesos */
        Partida.interprete.registrar(this);
        this.setSubject(Partida.interprete);


        /* Preparado del panel de control */
        this.botonLanzarDados.setText("0 - 0");
        this.nombreJugadorActual.textProperty().bind(Tablero.getPrompt().nombreJugadorPropertyProperty());
        this.dineroJugadorActual.textProperty().bind(Tablero.getPrompt().dineroPropertyProperty());
        this.modDin.textProperty().bind(Tablero.getPrompt().modDIneroPropertyProperty());
        this.tipoAvatar.textProperty().bind(Tablero.getPrompt().tipoAvatarPropertyProperty());
//        JFXDepthManager.setDepth(botonLanzarDados, 1);
//        JFXDepthManager.setDepth(botonPasarTurno, 1);

    }

    /* Mostrado de los sucesos como tarjetas */
    @Override
    public void update() {
        botonPasarTurno.requestFocus();
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        if (suceso.getDeshacer()) {
            infoSucesos.getChildren().remove(suceso.getTarjeta());
            return;
        }

        if (suceso instanceof Guardado) {
            return;
        }

        infoSucesos.getChildren().add(suceso.getTarjeta());
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
            listaJugadores.getChildren().get(Tablero.getTablero().getJugadoresGUI().indexOf(
                    Tablero.getTablero().getJugadorTurno()
            )).getStyleClass().remove("boton-jugador-con-turno");
            Tablero.getTablero().pasarTurno();
            // TODO: Cambiar el color de los circulos de Jugador a la izquierda.
            // El que tiene turno habría que resaltarlo, y poner los demás en blanco.


            listaJugadores.getChildren().get(Tablero.getTablero().getJugadoresGUI().indexOf(
                    Tablero.getTablero().getJugadorTurno()
            )).getStyleClass().add("boton-jugador-con-turno");

            botonNitroso.setSelected(Tablero.getPrompt().getJugador().getAvatar().isNitroso());
        } catch (ExcepcionMonopooly excepcionMonopooly) {
            listaJugadores.getChildren().get(Tablero.getTablero().getJugadoresGUI().indexOf(
                    Tablero.getTablero().getJugadorTurno()
            )).getStyleClass().add("boton-jugador-con-turno");
            excepcionMonopooly.mostrarError();
        }
        Jugador jugador = Tablero.getTablero().getJugadorTurno();
        this.botonLanzarDados.setText(jugador.getDados().getDado1() + " - " + jugador.getDados().getDado2() + "\n" + jugador.getDados().tirada());
    }

    @ActionMethod("lanzarDados")
    public void lanzarDados() {
        try {
            Jugador jugador = Tablero.getTablero().getJugadorTurno();
            Partida.interprete.lanzar(jugador);
            this.botonLanzarDados.setText(jugador.getDados().getDado1() + " - " + jugador.getDados().getDado2() + "\n" + jugador.getDados().tirada());
        } catch (ExcepcionMonopooly excepcionMonopooly) {
            excepcionMonopooly.mostrarError();
        }
    }

    @ActionMethod("modificarNitroso")
    public void modificarNitroso(){
        try{
            Partida.interprete.cambiarModo(Tablero.getTablero().getJugadorTurno().getAvatar());
        }catch(ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.mostrarError();
        }
        this.botonNitroso.setSelected(Tablero.getPrompt().getJugador().getAvatar().isNitroso());

    }

    @ActionMethod("construirCasa")
    public void construirCasa(){
        Casilla casillaConstruir=Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());

            try {
                Partida.interprete.edificar(
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
            Partida.interprete.edificar(
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
            Partida.interprete.edificar(
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
            Partida.interprete.edificar(
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
        System.out.println(id);
    }

    /**
     * Acciones que se hacen cuando se hace click en una casilla
     * @param id String con el número correspondiente a la posición actual de
     *           esa casilla
     */
    private void handleClickCasilla(String id) {
        Casilla casilla = Tablero.getTablero().getCasilla(new Posicion(Integer.parseInt(id)));

        JFXAlert alert = new JFXAlert(Arranque.getMainStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label(casilla.getNombre()));
        layout.setBody(new Label("Info de " + casilla.getNombre()));

        JFXButton botonCerrar = new JFXButton("Cerrar");
        botonCerrar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);
            // Codigo que se deba ejecutar
        });

        JFXButton botonComprar=new JFXButton("Comprar");
        botonComprar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);
            try {
                Partida.interprete.comprar(Tablero.getPrompt().getJugador(), casilla);
            }catch(ExcepcionMonopooly excepcionMonopooly){
                excepcionMonopooly.mostrarError();
            }
        });

        JFXButton botonHipotecar=new JFXButton("Hipotecar");
        botonHipotecar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);

            Partida.interprete.hipotecar(Tablero.getPrompt().getJugador(),casilla);

        });

        JFXButton botonDeshipotecar=new JFXButton("Deshipotecar");
        botonDeshipotecar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);
            try{
                Partida.interprete.deshipotecar(Tablero.getPrompt().getJugador(),casilla);
            }catch(ExcepcionMonopooly excepcionMonopooly){
                excepcionMonopooly.mostrarError();
            }
        });

        JFXButton botonVenderEdificios=new JFXButton("Vender Edificios");
        botonVenderEdificios.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);

            JFXAlert alert2 = new JFXAlert(Arranque.getMainStage());
            alert2.initModality(Modality.APPLICATION_MODAL);
            alert2.setOverlayClose(false);
            JFXDialogLayout layout2 = new JFXDialogLayout();
            layout2.setHeading(new Label("Venta de edificios en "+casilla.getNombre()));
            layout2.setBody(new Label("Elige el tipo de edificio que quieres vender y la cantidad."));

            JFXButton botonClose=new JFXButton("Cerrar");
            botonClose.setOnAction(event1 -> {
                alert2.hideWithAnimation();
                alert2.setHideOnEscape(true);
            });

            JFXComboBox<Edificio.TIPO> listaEdificios=new JFXComboBox();
            listaEdificios.getItems().addAll(Edificio.TIPO.casa, Edificio.TIPO.hotel, Edificio.TIPO.piscina, Edificio.TIPO.deporte);
            listaEdificios.getSelectionModel().selectFirst();

            JFXComboBox<Integer> listaNumeros=new JFXComboBox();
            listaNumeros.getItems().addAll(1,2,3,4);
            listaNumeros.getSelectionModel().selectFirst();

            JFXButton botonVender=new JFXButton("Vender");
            botonVender.setOnAction(event1 -> {
                alert2.hideWithAnimation();
                alert2.setHideOnEscape(true);
                try {
                    Partida.interprete.vender(casilla, listaNumeros.getValue(), listaEdificios.getValue());
                }catch(ExcepcionMonopooly excepcionMonopooly){
                    excepcionMonopooly.mostrarError();
                }
            });


            layout2.getActions().add(listaNumeros);
            layout2.getActions().add(botonVender);
            layout2.getActions().add(botonClose);
            layout2.getActions().add(listaEdificios);
            layout2.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

            alert2.setContent(layout2);
            alert2.showAndWait();
        });

        layout.getActions().add(botonCerrar);
        layout.getActions().add(botonComprar);
        layout.getActions().add(botonHipotecar);
        layout.getActions().add(botonDeshipotecar);
        layout.getActions().add(botonVenderEdificios);

        layout.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));
        alert.setContent(layout);
        alert.showAndWait();
    }
}
