package monopooly.colocacion.tipoCasillas.accion.especiales;

import monopooly.player.Jugador;

/**
 * Metodos que todo tipo de casilla especial debe tener.
 * Refleja el comportamiento de las otras casillas basicamente.
 * Es solo necesaria para que el visitante sepa en que tipo de casilla especial
 * cayó
 */
public interface AccionEspecial {
    void efectuar(Jugador jugador);
    String describir();
}
