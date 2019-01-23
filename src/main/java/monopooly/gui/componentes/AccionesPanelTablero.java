package monopooly.gui.componentes;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import monopooly.Arranque;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.excepciones.ExcepcionMonopooly;

import static monopooly.Partida.interprete;

public abstract class AccionesPanelTablero {
    public static void dibujarTablero(BorderPane panelTablero) {
        HelperGui.colocarCasillas(panelTablero);
        asignarAccionCasillas((GridPane) panelTablero.getTop());
        asignarAccionCasillas((GridPane) panelTablero.getBottom());
        asignarAccionCasillas((GridPane) panelTablero.getLeft());
        asignarAccionCasillas((GridPane) panelTablero.getRight());
    }

    /**
     * Asigna la accion predeterminada a todas las casillas de un lado del tablero
     * @param lado Gridpane con las casillas
     */
    private static void asignarAccionCasillas(GridPane lado) {
        lado.getChildren().forEach(
                child -> child.setOnMouseClicked(
                        event -> handleClickCasilla(((GridPane) event.getSource()).getId())
                )
        );
    }

    /**
     * Acciones que se hacen cuando se hace click en una casilla
     * @param id String con el número correspondiente a la posición actual de
     *           esa casilla
     */
    private static void handleClickCasilla(String id) {
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
