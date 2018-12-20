package monopooly.colocacion;

import java.util.ArrayList;

/**
 * Esta clase se encarga de cargar la configuracion de la colocacion y nombre de las casillas y
 * devuelve un ArrayList con todas las casillas del tablero por orden, para que el tablero solo
 * tenga que llamar a esta clase para generar el tablero con todas sus casillas
 *
 * @author Danimf99
 * @author luastan
 */
public interface FabricaCasillas {
    public static ArrayList<Casilla> crearCasillas() {

        return new ArrayList<>();
    }
}
