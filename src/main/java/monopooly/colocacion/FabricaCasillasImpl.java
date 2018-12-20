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
public class FabricaCasillasImpl {


    private ArrayList<Casilla> ladoSur() {

        return new ArrayList<>();
    }

    private ArrayList<Casilla> ladoOeste() {
        return new ArrayList<>();
    }

    private ArrayList<Casilla> ladoNorte() {
        return new ArrayList<>();
    }

    private ArrayList<Casilla> ladoEste() {
        return new ArrayList<>();
    }

    public ArrayList<Casilla> crearCasillas() {
        ArrayList<Casilla> casillasPartida = new ArrayList<>();
        casillasPartida.addAll(ladoSur());
        casillasPartida.addAll(ladoOeste());
        casillasPartida.addAll(ladoNorte());
        casillasPartida.addAll(ladoEste());
        return casillasPartida;
    }
}
