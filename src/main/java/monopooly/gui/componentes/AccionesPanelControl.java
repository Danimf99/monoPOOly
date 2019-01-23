package monopooly.gui.componentes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXMasonryPane;
import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import monopooly.colocacion.Tablero;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;

import java.util.Objects;

public abstract class AccionesPanelControl {

    public static void pasarTurno(GridPane listaJugadores, JFXToggleButton botonNitroso, BorderPane panelTablero, JFXMasonryPane infoSucesos, JFXButton botonLanzarDados) {
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
        botonLanzarDados.setText(jugador.getDados().getDado1() + " - " + jugador.getDados().getDado2() + "\n" + jugador.getDados().tirada());
    }
}
