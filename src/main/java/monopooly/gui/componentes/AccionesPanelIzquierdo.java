package monopooly.gui.componentes;

import com.jfoenix.controls.*;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import monopooly.Arranque;
import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public abstract class AccionesPanelIzquierdo {

    public static void mostrarEstadisticasGlobales() {
        System.out.println("Estadisticas globales");
    }


    public static void insertarComando() {
        Alerta alertaComandos = new Alerta();
        JFXAlert alert = alertaComandos.getAlert();

        JFXTextField comando = new JFXTextField();
        /*Funcion para pulsar enter y que se ejecute la accion introducida en textField */
        comando.setOnKeyReleased(event -> {
            if(event.getCode()== KeyCode.ENTER){
                alert.hideWithAnimation();
                Partida.ejecutarComando(comando.getText());
            }
        });

        JFXButton botonComando = new JFXButton("Ejecutar comando");
        botonComando.setOnAction(event -> {
            alert.hideWithAnimation();
            Partida.ejecutarComando(comando.getText());
        });

        alertaComandos.ponerHeading(new Label("Introduzca el comando"))
                .meterBotonCerrar()
                .meterBotones(botonComando)
                .meterEnCuerpoTodos(comando)
                .mostrar();
    }

    public static void listarJugadores() {

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
    }

    /**
     * Accion a realizar cuando se hace click en los jugadores de la izquierda
     * @param id Id del boton en el que se hace click
     */
    public static void clickJugador(String id) {
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
                            layoutT.setHeading(new Label("Cambiar Propiedad 1 por Propiedad 2 y no \n " +
                                    "alquiler en Propiedad 3 durante X turnos"));

                            JFXComboBox<String> comboPropiedadesJOrigen1 = new JFXComboBox<>();
                            comboPropiedadesJOrigen1.setPromptText("Propiedad 1");

                            JFXComboBox<String> comboPropiedadesJDestino1 = new JFXComboBox<>();
                            comboPropiedadesJDestino1.setPromptText("Propiedad 2");

                            JFXComboBox<String> comboPropiedadesNoAlquiler = new JFXComboBox<>();
                            comboPropiedadesNoAlquiler.setPromptText("Propiedad 3");

                            JFXTextField textoTurnosNoAlquiler = new JFXTextField();
                            textoTurnosNoAlquiler.setPromptText("Nº de turnos");

                            JFXButton botonAceptar1 = new JFXButton("Aceptar");
                            if(!comboPropiedadesJDestino1.getSelectionModel().isEmpty() && !comboPropiedadesJOrigen1.getSelectionModel().isEmpty()
                                    && !comboPropiedadesNoAlquiler.getSelectionModel().isEmpty() && !textoTurnosNoAlquiler.getText().trim().isEmpty()) {
                                botonAceptar1.setOnAction(event2 -> {
                                    alertaT.hideWithAnimation();
                                    alertaT.setHideOnEscape(true);
                                    try {
                                        Casilla jOrigen = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen1.getValue());
                                        Casilla jDestino = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen1.getValue());
                                        Casilla noAlquiler = Tablero.getTablero().getCasilla(comboPropiedadesNoAlquiler.getValue());

                                        int turnos = Integer.parseInt(textoTurnosNoAlquiler.getText());

                                        Partida.interprete.hacerTrato6(Tablero.getPrompt().getJugador(),jugador,
                                                (Propiedad)jOrigen,(Propiedad)jDestino,(Propiedad)noAlquiler,turnos);

                                    } catch (ExcepcionMonopooly excepcionMonopooly) {
                                        excepcionMonopooly.mostrarError();
                                    }catch (NumberFormatException numberFormatException){
                                        System.out.println("Not a number!");
                                    }
                                });
                            }
                            botonAceptar1.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo = new HBox(5);
                            cajaCombo.getChildren().addAll(comboPropiedadesJOrigen1,comboPropiedadesJDestino1);

                            HBox cajaComboTexto = new HBox(5);
                            cajaComboTexto.getChildren().addAll(comboPropiedadesNoAlquiler,textoTurnosNoAlquiler);

                            VBox cajaContenedor =new VBox(8);
                            cajaContenedor.getChildren().addAll(cajaCombo,cajaComboTexto);

                            layoutT.setBody(cajaContenedor);

                            for(Propiedad p: Tablero.getPrompt().getJugador().getPropiedades()) {
                                comboPropiedadesJOrigen1.getItems().add(p.getNombre());
                            }

                            for(Propiedad p: jugador.getPropiedades()){
                                comboPropiedadesJDestino1.getItems().add(p.getNombre());
                                comboPropiedadesNoAlquiler.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar1);
                            break;
                        case "Trato 2":
                            layoutT.setHeading(new Label("Cambiar cantidad de dinero por Propiedad 1"));
                            JFXTextField textoDinero = new JFXTextField();
                            textoDinero.setPromptText("Dinero $");

                            JFXComboBox<String> comboPropiedadesJDestino2 = new JFXComboBox<>();
                            comboPropiedadesJDestino2.setPromptText("Propiedad 1");

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
                            layoutT.setHeading(new Label("Cambiar Propiedad 1 por cantidad de dinero"));

                            JFXTextField textoDinero3 = new JFXTextField();
                            textoDinero3.setPromptText("Dinero a recibir");

                            JFXComboBox<String> comboPropiedadesJOrigen3 = new JFXComboBox<>();
                            comboPropiedadesJOrigen3.setPromptText("Propiedad 1");

                            JFXButton botonAceptar3 = new JFXButton("Aceptar");
                            botonAceptar3.setOnAction(event3 -> {
                                alertaT.hideWithAnimation();
                                alertaT.setHideOnEscape(true);

                                if(!comboPropiedadesJOrigen3.getSelectionModel().isEmpty() && !textoDinero3.getText().trim().isEmpty()){
                                    try {
                                        int dinero= Integer.parseInt(textoDinero3.getText());
                                        Casilla casillaJDestino = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen3.getValue());

                                        Partida.interprete.Hacertrato2(Tablero.getPrompt().getJugador(),jugador,(Propiedad)casillaJDestino,dinero);
                                    }catch (ExcepcionMonopooly excepcionMonopooly){
                                        excepcionMonopooly.mostrarError();
                                    }catch (NumberFormatException numberFormatException){
                                        System.out.println("Not a number!!");
                                    }
                                }
                            });
                            botonAceptar3.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo3 = new HBox(5);
                            cajaCombo3.getChildren().addAll(textoDinero3,comboPropiedadesJOrigen3);

                            layoutT.setBody(cajaCombo3);

                            for(Propiedad p: Tablero.getPrompt().getJugador().getPropiedades()){
                                comboPropiedadesJOrigen3.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar3);
                            break;
                        case "Trato 4":
                            layoutT.setHeading(new Label("Cambiar Propiedad 1 por Propiedad 2 \n" +
                                    "y cantidad de dinero"));

                            JFXComboBox<String> comboPropiedadesJOrigen4 = new JFXComboBox<>();
                            comboPropiedadesJOrigen4.setPromptText("Propiedad 1");

                            JFXComboBox<String> comboPropiedadesJDestino4 = new JFXComboBox<>();
                            comboPropiedadesJDestino4.setPromptText("Propiedad 2");

                            JFXTextField textoDinero4 = new JFXTextField();
                            textoDinero4.setPromptText("Dinero a recibir");

                            JFXButton botonAceptar4 = new JFXButton("Aceptar");
                            if(!comboPropiedadesJDestino4.getSelectionModel().isEmpty() && !comboPropiedadesJOrigen4.getSelectionModel().isEmpty()) {
                                botonAceptar4.setOnAction(event4 -> {
                                    alertaT.hideWithAnimation();
                                    alertaT.setHideOnEscape(true);
                                    try {
                                        Casilla jOrigen = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen4.getValue());
                                        Casilla jDestino = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen4.getValue());

                                        int dinero= Integer.parseInt(textoDinero4.getText());

                                        Partida.interprete.hacerTrato5(Tablero.getPrompt().getJugador(),jugador,(Propiedad)jOrigen,dinero,(Propiedad)jDestino);
                                    }catch (ExcepcionMonopooly excepcionMonopooly) {
                                        excepcionMonopooly.mostrarError();
                                    }catch (NumberFormatException numberFormatException){
                                        System.out.println("NOt a number!");
                                    }
                                } );
                            }
                            botonAceptar4.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo4 = new HBox(5);
                            cajaCombo4.getChildren().addAll(comboPropiedadesJOrigen4,comboPropiedadesJDestino4);

                            VBox cajaContenedor4 = new VBox(8);
                            cajaContenedor4.getChildren().addAll(cajaCombo4,textoDinero4);

                            layoutT.setBody(cajaContenedor4);

                            for(Propiedad p: Tablero.getPrompt().getJugador().getPropiedades()) {
                                comboPropiedadesJOrigen4.getItems().add(p.getNombre());
                            }

                            for(Propiedad p: jugador.getPropiedades()){
                                comboPropiedadesJDestino4.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar4);
                            break;
                        case "Trato 5":
                            layoutT.setHeading(new Label("Cambiar Propiedad 1 y cantidad de dinero \n" +
                                    "por Propiedad 2"));

                            JFXComboBox<String> comboPropiedadesJOrigen5 = new JFXComboBox<>();
                            comboPropiedadesJOrigen5.setPromptText("Tu propiedad 1");

                            JFXComboBox<String> comboPropiedadesJDestino5 = new JFXComboBox<>();
                            comboPropiedadesJDestino5.setPromptText("Propiedad 2");

                            JFXTextField textoDinero5 = new JFXTextField();
                            textoDinero5.setPromptText("Dinero a dar");

                            JFXButton botonAceptar5 = new JFXButton("Aceptar");
                            if(!comboPropiedadesJDestino5.getSelectionModel().isEmpty() && !comboPropiedadesJOrigen5.getSelectionModel().isEmpty()) {
                                botonAceptar5.setOnAction(event4 -> {
                                    alertaT.hideWithAnimation();
                                    alertaT.setHideOnEscape(true);
                                    try {
                                        Casilla jOrigen = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen5.getValue());
                                        Casilla jDestino = Tablero.getTablero().getCasilla(comboPropiedadesJOrigen5.getValue());

                                        int dinero= Integer.parseInt(textoDinero5.getText());

                                        Partida.interprete.hacerTrato4(Tablero.getPrompt().getJugador(),jugador,(Propiedad)jOrigen,dinero,(Propiedad)jDestino);
                                    }catch (ExcepcionMonopooly excepcionMonopooly) {
                                        excepcionMonopooly.mostrarError();
                                    }catch (NumberFormatException numberFormatException){
                                        System.out.println("NOt a number!");
                                    }
                                } );
                            }
                            botonAceptar5.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo5 = new HBox(5);
                            cajaCombo5.getChildren().addAll(comboPropiedadesJOrigen5,comboPropiedadesJDestino5);

                            VBox cajaContenedor5 = new VBox(8);
                            cajaContenedor5.getChildren().addAll(cajaCombo5,textoDinero5);

                            layoutT.setBody(cajaContenedor5);

                            for(Propiedad p: Tablero.getPrompt().getJugador().getPropiedades()) {
                                comboPropiedadesJOrigen5.getItems().add(p.getNombre());
                            }

                            for(Propiedad p: jugador.getPropiedades()){
                                comboPropiedadesJDestino5.getItems().add(p.getNombre());
                            }

                            layoutT.getActions().removeAll(botonElegirTrato);
                            layoutT.getActions().add(botonAceptar5);
                            break;
                        case "Trato 6":
                            layoutT.setHeading(new Label("Cambiar Propiedad 1 por Propiedad 2"));

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

                                        Partida.interprete.Hacertrato1(Tablero.getPrompt().getJugador(), jugador, (Propiedad) jOrigen, (Propiedad) jDestino);
                                    } catch (ExcepcionMonopooly excepcionMonopooly) {
                                        excepcionMonopooly.mostrarError();
                                    }
                                });
                            }
                            botonAceptar.getStyleClass().add("boton-aceptar-dialogo");

                            HBox cajaCombo6 = new HBox(5);
                            cajaCombo6.getChildren().addAll(comboPropiedadesJOrigen,comboPropiedadesJDestino);

                            layoutT.setBody(cajaCombo6);

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

            JFXButton botonVerTratos = new JFXButton("Ver Tratos");
            botonVerTratos.setOnAction(event -> {
                alert.hideWithAnimation();
                alert.setHideOnEscape(true);

                alertaVerTratos(jugador);
            });

            layout.getActions().add(botonCerrar);
            if(!id.equals(Tablero.getTablero().getJugadorTurno().getNombre())) {
                layout.getActions().add(botonTratos);
            }else{
                layout.getActions().add(botonVerTratos);
            }
            layout.getActions().add(botonStats);

            layout.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

            alert.setContent(layout);
            alert.showAndWait();

        }catch(ExcepcionMonopooly excepcionMonopooly){
            excepcionMonopooly.imprimeError();
        }

    }

    private static void alertaVerTratos(Jugador jugador){
        JFXAlert alertaTratos = new JFXAlert(Arranque.getMainStage());
        alertaTratos.initModality(Modality.APPLICATION_MODAL);
        alertaTratos.setOverlayClose(false);

        JFXDialogLayout layoutTratos = new JFXDialogLayout();
        layoutTratos.setHeading(new Label("Tratos de "+jugador.getNombre()));
        layoutTratos.setBody(new Label(jugador.imprimirTratos()));

        JFXButton cerrarTratos = new JFXButton("Cerrar");
        cerrarTratos.setOnAction(event1 -> {
            alertaTratos.hideWithAnimation();
            alertaTratos.setHideOnEscape(true);
        });

        JFXComboBox comboAceptarTrato = new JFXComboBox();
        comboAceptarTrato.setPromptText("Trato a aceptar");
        for(Trato t: jugador.getTratos()){
            comboAceptarTrato.getItems().add(t.getId());
        }

        JFXButton botonAceptarTrato = new JFXButton("Aceptar");
        botonAceptarTrato.setOnAction(event1 -> {

            if(!comboAceptarTrato.getSelectionModel().isEmpty()){
                alertaTratos.hideWithAnimation();
                alertaTratos.setHideOnEscape(true);

                try{
                    Partida.interprete.aceptarTrato(jugador.getTrato(comboAceptarTrato.getValue().toString()));

                }catch (ExcepcionMonopooly excepcionMonopooly){
                    excepcionMonopooly.mostrarError();
                }
            }
        });


        layoutTratos.getActions().add(comboAceptarTrato);
        layoutTratos.getActions().add(botonAceptarTrato);
        layoutTratos.getActions().add(cerrarTratos);

        layoutTratos.getActions().forEach(action -> action.getStyleClass().add("boton-aceptar-dialogo"));

        alertaTratos.setContent(layoutTratos);
        alertaTratos.showAndWait();
    }
}
