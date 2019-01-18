package monopooly.gui.componentes;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;

import java.util.ArrayList;

public class DibujoTableroHelper {
    public static void colocarCasillas(BorderPane borderPane) {
        Tablero tablero = Tablero.getTablero();
        ArrayList<Casilla> casillas = tablero.getCasillas();


        /* Lado Sur 0 - 10 */
        GridPane ladoSur = (GridPane) borderPane.getTop();
        for (int i = 1; i < 10; i++) {
            ladoSur.addColumn(i);
        }

    }
}
