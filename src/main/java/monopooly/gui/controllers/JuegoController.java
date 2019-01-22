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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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
import monopooly.excepciones.*;
import monopooly.gui.componentes.HelperGui;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
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
        interprete.registrar(this);
        this.setSubject(interprete);


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
        private void click() {
            switch (menuEstadisticas.getSelectionModel().getSelectedIndex()) {
                case 0:
                    // Estadisticas globales
                    System.out.println("Estadisticas globales");
                    break;
                case 1:
                    // Insertar comandos
                    System.out.println("InsertarComandos");
                    JFXAlert alert ;

                    alert = new JFXAlert(Arranque.getMainStage());
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.setOverlayClose(false);

                    JFXDialogLayout layout = new JFXDialogLayout();
                    layout.setHeading(new Label("Introduzca el comando"));
                    JFXTextField comando = new JFXTextField();
                    layout.setBody(comando);

                    JFXButton botonCerrar = new JFXButton("Cerrar");
                    botonCerrar.setOnAction(event -> {
                        alert.hideWithAnimation();
                        alert.setHideOnEscape(true);
                    });

                    JFXButton botonComando = new JFXButton("Ejecutar comando");
                    botonComando.setOnAction(event -> {
                        alert.hideWithAnimation();
                        alert.setHideOnEscape(true);

                        /*Funcion con un switch para ver el comando introducido*/
                        realizarAccionComando(comando.getText());

                    });

                    /*Funcion para pulsar enter y que se ejecute la accion introducida en textField */

                    comando.setOnKeyReleased(event -> {
                        if(event.getCode()== KeyCode.ENTER){
                            alert.hideWithAnimation();
                            alert.setHideOnEscape(true);
                            realizarAccionComando(comando.getText());
                        }
                    });

                    layout.getActions().add(botonCerrar);
                    layout.getActions().add(botonComando);

                    layout.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

                    alert.setContent(layout);
                    alert.showAndWait();
                    break;
                case 2:

                    JFXAlert alertJugadores;
                    alertJugadores = new JFXAlert(Arranque.getMainStage());
                    alertJugadores.initModality(Modality.APPLICATION_MODAL);
                    alertJugadores.setOverlayClose(false);

                    JFXDialogLayout layoutJugadores = new JFXDialogLayout();
                    layoutJugadores.setHeading(new Label("Lista de jugadores"));

                    JFXButton botonCerrarJ = new JFXButton("Cerrar");
                    botonCerrarJ.setOnAction(event -> {
                        alertJugadores.hideWithAnimation();
                        alertJugadores.setHideOnEscape(true);
                    });

                    botonCerrarJ.getStyleClass().add("boton-aceptar-dialogo");

                    StringBuilder listarJugadores = new StringBuilder();

                    for(Jugador j: Tablero.getTablero().getJugadores()){
                        listarJugadores.append(j.toString());
                        System.out.println(j.toString());
                    }

                    layoutJugadores.setBody(new Label(listarJugadores.toString()));
                    layoutJugadores.getActions().forEach(action -> action.getStyleClass().add("texto-listar-jugadores"));

                    layoutJugadores.getActions().add(botonCerrarJ);

                    alertJugadores.setContent(layoutJugadores);
                    alertJugadores.showAndWait();

                    break;
                case 3:
                    System.out.println("LISTAR ENVENTA");



                    break;
                default:
                    // Nada
            }
        }
    }

    private static void realizarAccionComando(String comando){
        Expresion exp=null;
        String[] args;

        /*Switch para comprobar el comando introducido*/
        args = comando.split(" ");
        try {
            switch (args[0].toLowerCase()) {  // Classic switch de comandos
                case "comprar":
                case "c":
                    exp = new Comprar(args);
                    break;
                case "aceptar":
                    exp = new AceptarTrato(args);
                    break;
                case "bancarrota":
                    exp = new Bancarrota(args);
                    break;
                case "eliminar":
                    exp = new EliminarTrato(args);
                    break;
                case "estadisticas":
                case "estadísticas":
                    exp = new Estadisticas(args);
                    break;
                case "trato":
                    exp = new HacerTrato(args);
                    break;
                case "edificar":
                    exp = new Edificar(args, false);
                    break;
                case "listar":
                    exp = new Listar(args);
                    break;
                case "salir":
                    exp = new SalirCarcel(args);
                    break;
                case "cambiar":
                    exp = new CambiarModo(args);
                    break;
                case "info":
                case "informacion":
                    exp = new Info(args);
                    break;
                case "hipotecar":
                    exp = new Hipotecar(args);
                    break;
                case "deshipotecar":
                    exp = new Deshipotecar(args);
                    break;
                case "describir":
                    exp = new Describir(args);
                    break;
                case "vender":
                    exp = new Vender(args);
                    break;
                case "acabar":
                case "a":
                    Tablero.getTablero().pasarTurno();
                    Juego.consola.imprimirln(Tablero.getTablero().toString());
                    break;
                case "tratos":
                    exp = new VerTratos(args);
                    break;
                case "lanzar":
                    exp = new Lanzar(args);
                    break;
                case "help":
                case "h":
                    Juego.consola.detalles(String.join("\n", General.LISTA_COMANDOS),
                            "Lista de comandos disponible");
                    break;
                default:
                    throw new ExcepcionComandoInexistente(comando);
            }
        } catch (ExcepcionComandoInexistente e) {
            Tablero.getPrompt().setHelp(true);
            e.imprimeError();
        } catch (ExcepcionMonopooly e) {
            e.imprimeError();
        }

        if (exp != null) {
            try {
                exp.interpretar(interprete);
            } catch (ExcepcionAcontecimiento e) {
                e.imprimeInfo();
            } catch (ExcepcionComando e) {
                Tablero.getPrompt().setHelp(true);
                e.imprimeError();
            } catch (ExcepcionMonopooly e) {
                e.imprimeError();
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


            listaJugadores.getChildren().get(Tablero.getTablero().getJugadoresGUI().indexOf(
                    Tablero.getTablero().getJugadorTurno()
            )).getStyleClass().add("boton-jugador-con-turno");

            botonNitroso.setSelected(Tablero.getPrompt().getJugador().getAvatar().isNitroso());
            panelTablero.getChildren().stream()
                    .filter(Objects::nonNull)
                    .map(node -> (Pane) node)
                    .forEach(lado -> lado.getChildren()
                            .forEach(casilla -> {
                                casilla.getStyleClass().remove("casilla-resaltada");
                            }));
            infoSucesos.getChildren().clear(); // Llega un momento que se llena asi que hay que vaciarlo
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

        try {
            Jugador jugador = Tablero.getTablero().getJugador(id);


            JFXAlert alert = new JFXAlert(Arranque.getMainStage());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("Jugador"));
            layout.setBody(new Label(jugador.toStringGUI()));


            JFXButton botonCerrar = new JFXButton("Cerrar");
            botonCerrar.setOnAction(event -> {
                alert.hideWithAnimation();
                alert.setHideOnEscape(true);
            });

            JFXButton botonTratos=new JFXButton("Tratos");
            botonTratos.setOnAction(event -> {
                alert.hideWithAnimation();
                alert.setHideOnEscape(true);

                JFXAlert alertaT=new JFXAlert(Arranque.getMainStage());
                alertaT.initModality(Modality.APPLICATION_MODAL);
                alertaT.setOverlayClose(false);

                JFXDialogLayout layoutT=new JFXDialogLayout();
                layoutT.setHeading(new Label("Elija el trato que quiere efectuar"));
                //TODO botones para tratos
                JFXComboBox<String> comboTratos = new JFXComboBox();
                comboTratos.setPromptText("Tratos");
                comboTratos.getItems().addAll("Trato 1","Trato 2","Trato 3","Trato 4","Trato 5","Trato 6");
                comboTratos.setValue("");
                comboTratos.getStyleClass().add("boton-aceptar-dialogo");

                layoutT.getBody().add(comboTratos);

                JFXButton botonElegirTrato = new JFXButton("Elegir");
                layoutT.getActions().add(botonElegirTrato);

                botonElegirTrato.setOnAction(event1 -> {

                    switch (comboTratos.getValue()){
                        case "Trato 1":

                            break;
                        case "Trato 2":

                            JFXTextField textoDinero = new JFXTextField();
                            textoDinero.setPromptText("Dinero $");

                            JFXComboBox<String> comboPropiedadesJDestino2 = new JFXComboBox<>();
                            comboPropiedadesJDestino2.setPromptText("Propiedad que quieres recibir");

                            JFXButton botonAceptar2 = new JFXButton("Aceptar");
                            botonAceptar2.setOnAction(event2 -> {
                                alertaT.hideWithAnimation();
                                alertaT.setHideOnEscape(true);

                                if(!comboPropiedadesJDestino2.getSelectionModel().isEmpty() && !textoDinero.getText().trim().isEmpty()){
                                    try {
                                        int dinero= Integer.parseInt(textoDinero.getText());
                                        Casilla pJDestino = Tablero.getTablero().getCasilla(comboPropiedadesJDestino2.getValue());
                                        Partida.interprete.Hacertrato3(Tablero.getPrompt().getJugador(),jugador,dinero,(Propiedad)pJDestino);
                                    }catch (ExcepcionMonopooly excepcionMonopooly){
                                        excepcionMonopooly.mostrarError();
                                    }catch (NumberFormatException numberFormatException){
                                        System.out.println("Not a number!!");
                                    }
                                }
                            });
                            botonAceptar2.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo2 = new HBox(5);
                            cajaCombo2.getChildren().addAll(textoDinero,comboPropiedadesJDestino2);

                            layoutT.setBody(cajaCombo2);

                            for(Propiedad p: jugador.getPropiedades()){
                                comboPropiedadesJDestino2.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar2);
                            break;
                        case "Trato 3":

                            break;
                        case "Trato 4":

                            break;
                        case "Trato 5":

                            break;
                        case "Trato 6":
                            alertaT.setTitle("Elija las propiedades");

                            JFXComboBox<String> comboPropiedadesJOrigen = new JFXComboBox<>();
                            comboPropiedadesJOrigen.setPromptText("Tu propiedad");

                            JFXComboBox<String> comboPropiedadesJDestino = new JFXComboBox<>();
                            comboPropiedadesJDestino.setPromptText("Propiedad que quieres recibir");

                            JFXButton botonAceptar = new JFXButton("Aceptar");
                            if(!comboPropiedadesJDestino.getSelectionModel().isEmpty() && !comboPropiedadesJOrigen.getSelectionModel().isEmpty()) {
                                botonAceptar.setOnAction(event2 -> {
                                    alertaT.hideWithAnimation();
                                    alertaT.setHideOnEscape(true);
                                    try {
                                        Casilla jOrigen = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen.getValue());
                                        Casilla jDestino = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen.getValue());

                                        if (!comboPropiedadesJDestino.getValue().equals("") && !comboPropiedadesJOrigen.getValue().equals("") &&
                                                jDestino != null && jOrigen != null) {
                                            try {
                                                Partida.interprete.Hacertrato1(Tablero.getPrompt().getJugador(), jugador, (Propiedad) jOrigen, (Propiedad) jDestino);
                                            } catch (ExcepcionMonopooly excepcionMonopooly) {
                                                excepcionMonopooly.mostrarError();
                                            }
                                        }

                                    } catch (ExcepcionParametrosInvalidos excepcionMonopooly) {
                                        excepcionMonopooly.mostrarError();
                                    }
                                });
                            }
                            botonAceptar.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo = new HBox(5);
                            cajaCombo.getChildren().addAll(comboPropiedadesJOrigen,comboPropiedadesJDestino);

                            layoutT.setBody(cajaCombo);

                            for(Propiedad p: Tablero.getPrompt().getJugador().getPropiedades()) {
                                comboPropiedadesJOrigen.getItems().add(p.getNombre());
                            }

                            for(Propiedad p: jugador.getPropiedades()){
                                comboPropiedadesJDestino.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar);

                            break;
                        default:
                            break;
                    }
                });


                JFXButton botonCerrarS = new JFXButton("Cerrar");
                botonCerrarS.setOnAction(event1 -> {
                    alertaT.hideWithAnimation();
                    alertaT.setHideOnEscape(true);
                });

                layoutT.getActions().add(botonCerrarS);


                layoutT.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

                alertaT.setContent(layoutT);
                alertaT.showAndWait();
            });

            JFXButton botonStats= new JFXButton("Estadísticas");
            botonStats.setOnAction(event -> {
                alert.hideWithAnimation();
                alert.setHideOnEscape(true);

                JFXAlert alertaStats=new JFXAlert(Arranque.getMainStage());
                alertaStats.initModality(Modality.APPLICATION_MODAL);
                alertaStats.setOverlayClose(false);

                JFXDialogLayout layoutS=new JFXDialogLayout();
                layoutS.setHeading(new Label("Estadísticas de "+jugador.getNombre()));
                layoutS.setBody(new Label(jugador.getEstadisticas().toStringGUI()));

                JFXButton botonCerrarS = new JFXButton("Cerrar");
                botonCerrarS.setOnAction(event1 -> {
                    alertaStats.hideWithAnimation();
                    alertaStats.setHideOnEscape(true);
                });

                layoutS.getActions().add(botonCerrarS);

                layoutS.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

                alertaStats.setContent(layoutS);
                alertaStats.showAndWait();

            });


            layout.getActions().add(botonCerrar);
            if(!id.equals(Tablero.getTablero().getJugadorTurno().getNombre())) {
                layout.getActions().add(botonTratos);
            }
            layout.getActions().add(botonStats);

            layout.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

            alert.setContent(layout);
            alert.showAndWait();

        }catch(ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }

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
        layout.setBody(new Label(casilla.toStringGUI()));

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
                interprete.comprar(Tablero.getPrompt().getJugador(), casilla);
            }catch(ExcepcionMonopooly excepcionMonopooly){
                excepcionMonopooly.mostrarError();
            }
        });

        JFXButton botonHipotecar=new JFXButton("Hipotecar");
        botonHipotecar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);

            interprete.hipotecar(Tablero.getPrompt().getJugador(),casilla);

        });

        JFXButton botonDeshipotecar=new JFXButton("Deshipotecar");
        botonDeshipotecar.setOnAction(event -> {
            alert.hideWithAnimation();
            alert.setHideOnEscape(true);
            try{
                interprete.deshipotecar(Tablero.getPrompt().getJugador(),casilla);
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
                    interprete.vender(casilla, listaNumeros.getValue(), listaEdificios.getValue());
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
        if(casilla instanceof Propiedad) {
            layout.getActions().add(botonComprar);
            layout.getActions().add(botonHipotecar);
            layout.getActions().add(botonDeshipotecar);
        }
        if(casilla instanceof Solar) {
            layout.getActions().add(botonVenderEdificios);
        }

        layout.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));
        alert.setContent(layout);
        alert.showAndWait();
    }
}
