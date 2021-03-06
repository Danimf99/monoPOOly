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
import javafx.scene.layout.Pane;
import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.Carcel;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.*;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Caer;
import monopooly.sucesos.tipoSucesos.CaerCarcel;
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
    @ActionTrigger("declararBancarrota")
    private JFXButton botonBancarrota;

    @FXML
    @ActionTrigger("salirCarcel")
    private JFXButton botonSalirCarcel;

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

        dineroJugadorActual.textProperty().addListener((observable, oldValue, newValue) -> {
            botonBancarrota.setVisible(newValue.charAt(0) == '-');
        });
    }

    /* Mostrado de los sucesos como tarjetas */
    @Override
    public void update() {
        botonPasarTurno.requestFocus();
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }

        if (suceso instanceof CaerCarcel) {
            botonSalirCarcel.setVisible(!suceso.getDeshacer());
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
        botonSalirCarcel.setVisible(Tablero.getPrompt().getJugador().isEstarEnCarcel());
        botonBancarrota.setVisible(Tablero.getPrompt().getJugador().getDinero() < 0);
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

        GridPane casillasSur = (GridPane) panelTablero.getBottom();
        GridPane casillasOeste = (GridPane) panelTablero.getLeft();
        GridPane casillasNorte = (GridPane) panelTablero.getTop();
        GridPane casillasEste = (GridPane) panelTablero.getRight();

        if(botonEnVenta.isSelected()){
            cambiarEstiloGridPane(casillasSur,0,11);
            cambiarEstiloGridPane(casillasOeste,0,9);
            cambiarEstiloGridPane(casillasNorte,0,11);
            cambiarEstiloGridPane(casillasEste,0,9);
        }else{
            reiniciarEstiloGridPane(casillasEste,0,9);
            reiniciarEstiloGridPane(casillasNorte,0,11);
            reiniciarEstiloGridPane(casillasOeste,0,9);
            reiniciarEstiloGridPane(casillasSur,0,11);
        }
    }

    /*Recorre las casillas del tablero para resaltar las que están en venta*/
    private void cambiarEstiloGridPane(GridPane gridPane, int inicio, int max){
        for(int i=inicio;i<max;i++) {
            Casilla casilla = Tablero.getTablero().getCasilla(new Posicion(Integer.parseInt(gridPane.getChildren().get(i).getId())));

            if(casilla instanceof Propiedad && ((Propiedad) casilla).getPropietario().getNombre().equals("Banca")){
                gridPane.getChildren().get(i).getStyleClass().add("casilla-en-venta");
                gridPane.getChildren().get(i).setVisible(true);
            }else{
//                gridPane.getChildren().get(i).getStyleClass().add("casilla-no-en-venta");
                gridPane.getChildren().get(i).setVisible(false);
            }
        }
    }

    private void reiniciarEstiloGridPane(GridPane gridPane,int inicio,int max){
        for( int i= inicio; i<max; i++){
            gridPane.getChildren().get(i).getStyleClass().remove("casilla-en-venta");
//            gridPane.getChildren().get(i).getStyleClass().remove("casilla-no-en-venta");
            gridPane.getChildren().get(i).setVisible(true);
        }
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

    @ActionMethod("declararBancarrota")
    public void bancarrota() {
        try {
            Jugador brokeAf = Tablero.getPrompt().getJugador();
            interprete.bancarrota(Tablero.getTablero().getJugadorTurno());
            Node node = listaJugadores.getChildren().get(Tablero.getTablero().getJugadoresGUI().indexOf(brokeAf));
            node.getStyleClass().remove("boton-jugador-con-turno");
            node.getStyleClass().add("boton-jugador-con-turno");

        } catch (ExcepcionMonopooly excepcionMonopooly) {
            excepcionMonopooly.mostrarError();
        }
    }

    @ActionMethod("salirCarcel")
    public void salirCarcel() {
        try {
            interprete.salirCarcel(Tablero.getTablero().getJugadorTurno());
            botonSalirCarcel.setVisible(false);
        } catch (ExcepcionMonopooly excepcionMonopooly) {
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
