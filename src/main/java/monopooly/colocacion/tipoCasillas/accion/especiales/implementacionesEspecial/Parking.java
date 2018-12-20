package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.player.Jugador;

public class Parking implements AccionEspecial {

    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public String describir() {
        return "\nEsto es el Parking\n";
    }
}
