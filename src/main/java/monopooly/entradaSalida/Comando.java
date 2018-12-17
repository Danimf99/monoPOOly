package monopooly.entradaSalida;

import monopooly.colocacion.Casilla;
import monopooly.player.Jugador;

public interface Comando {
    /**
     * Permite a un jugador comprar una casilla. Se hacen las comprobaciones de si puede comprarla
     * @param jugador Jugador que compra
     * @param casilla Casilla que se quiere comprar
     */
    void comprar(Jugador jugador, Casilla casilla);
}
