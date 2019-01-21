package monopooly.gui.componentes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.svg.SVGGlyph;
import de.jensd.fx.glyphs.GlyphsStack;
import de.jensd.fx.glyphs.emojione.EmojiOne;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.Impuesto;
import monopooly.colocacion.tipoCasillas.accion.especiales.Especial;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.player.Jugador;


import java.util.ArrayList;

public class HelperGui {
    public static void colocarCasillas(BorderPane borderPane) {
        Tablero tablero = Tablero.getTablero();
        ArrayList<Casilla> casillas = tablero.getCasillas();


        /* Puede resultar confusa la construccion. Aclarar que las posiciones del monopoly van de derecha a izquierda
        * y en el grid pane de izquierda a derecha por eso hay restas del estilo 10 - i */


        /* Lado Sur 0 - 10 */
        GridPane ladoSur = (GridPane) borderPane.getBottom();
        for (int i = 1; i < 10; i++) {
            ladoSur.addColumn(i);
            ladoSur.add(intPosCasillaToGridPane(10 - i), i, 0);
        }
        // Esquinas
        ladoSur.add(intPosCasillaToGridPane(10), 0, 0);
        ladoSur.add(intPosCasillaToGridPane(0), 10, 0);

        /* Lado Norte 20 - 30 */
        GridPane ladoNorte = (GridPane) borderPane.getTop();
        for (int i = 1; i < 10; i++) {
            ladoNorte.addColumn(i);
            ladoNorte.add(intPosCasillaToGridPane(30 - i), i, 0);
        }

        // Esquinas
        ladoNorte.add(intPosCasillaToGridPane(30), 0, 0);
        ladoNorte.add(intPosCasillaToGridPane(20), 10, 0);


        /* Lado Izquierdo 11 - 19 */
        GridPane ladoIzquierdo = (GridPane) borderPane.getLeft();
        for (int i = 1; i < 10; i++) {
            ladoIzquierdo.addColumn(i);
            ladoIzquierdo.add(intPosCasillaToGridPane(20 - i), i, 0);
        }


        /* Lado derecho 31 - 39 */
        GridPane ladoDerecho = (GridPane) borderPane.getRight();
        for (int i = 1; i < 10; i++) {
            ladoDerecho.addColumn(i);
            ladoDerecho.add(intPosCasillaToGridPane(40 - i), i, 0);
        }



    }

    private static GridPane intPosCasillaToGridPane(int nPosicion) {
        GridPane casilla = casillaToGridPane(Tablero.getTablero().getCasilla(new Posicion(nPosicion)));
        casilla.setId("" + nPosicion);
        return casilla;
    }


    private static GridPane casillaToGridPane(Casilla casilla) {
        GridPane gridPane = new GridPane();
        gridPane.addRow(1);
        gridPane.addRow(2);
        gridPane.getStyleClass().add("casilla");

        // Nombre de la casilla
        StackPane nombreCasilla = new StackPane();
        nombreCasilla.getStyleClass().add("repr-nombre-casilla");
        Label nombre = new Label(casilla.getNombre());
        nombre.getStyleClass().add("texto-nombre-casilla");
        nombreCasilla.getChildren().add(nombre);
        gridPane.add(nombreCasilla, 0, 0);
        if (casilla instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) casilla;
            nombreCasilla.setStyle("-fx-background-color: " + propiedad.getMonopolio().getHexColor() + ";");
        }

        // Avatares

        VBox avataresContainer = new VBox();
        avataresContainer.getStyleClass().add("repr-avatares");
        gridPane.add(avataresContainer, 0,1);
        casilla.setAvs(avataresContainer);

        // Precio si tiene

        StackPane precioContainer = new StackPane();
        precioContainer.getStyleClass().add("repr-precio");
        gridPane.add(precioContainer, 0, 2);
        if (casilla instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) casilla;
            Label labelPrecio = new Label(propiedad.getPrecio() + " " + Precios.MONEDA);
            labelPrecio.getStyleClass().add("texto-precio");
            precioContainer.getChildren().add(labelPrecio);

        } else if (casilla instanceof Impuesto) {
            Label labelPrecio = new Label(Precios.IMPUESTOS + " " + Precios.MONEDA);
            labelPrecio.getStyleClass().add("texto-precio");
            precioContainer.getChildren().add(labelPrecio);
        }

        // Si es una esquina el css es diferente
        gridPane.getStyleClass().add(casilla instanceof Especial ? "casilla-esquina" : "casilla-normal");
        return gridPane;
    }

    public static void colocarJugadores (GridPane gridPane) {
        ArrayList<Jugador> jugadores = Tablero.getTablero().getJugadores();
        for (int i = 0; i < jugadores.size(); i++) {
            gridPane.addRow(i,genBotonJugador(jugadores.get(i)));
        }
    }

    private static JFXButton genBotonJugador(Jugador jugador) {
        JFXButton botonJugador = new JFXButton();
        EmojiOneView emoji = new EmojiOneView(jugador.getAvatar().getRepresentacion());
        emoji.setSize("25");
        emoji.getStyleClass().add("icono-jugador-lateral");
        botonJugador.setGraphic(emoji);
        botonJugador.getStyleClass().add("boton-jugador");
        botonJugador.setId(jugador.getNombre());
        return botonJugador;
    }
}
