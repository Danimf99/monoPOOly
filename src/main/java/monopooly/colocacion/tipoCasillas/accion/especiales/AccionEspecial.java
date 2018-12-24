package monopooly.colocacion.tipoCasillas.accion.especiales;

import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;

import java.util.ArrayList;

/**
 * Metodos que todo tipo de casilla especial debe tener.
 * Refleja el comportamiento de las otras casillas basicamente.
 * Es solo necesaria para que el visitante sepa en que tipo de casilla especial
 * cayó
 */
public interface AccionEspecial {
    void efectuar(Jugador jugador) throws ExcepcionMonopooly;
    TipoMonopolio getTipo();
    ArrayList<String> describir();
}
