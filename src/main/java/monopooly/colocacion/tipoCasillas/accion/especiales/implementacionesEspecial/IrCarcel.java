package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.player.Jugador;

/**
 * Hay que mandar al jugador a la carcel
 */
public class IrCarcel implements AccionEspecial {


    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public String describir() {
        return "\nEsto es ir a la carsel\n";
    }
}
