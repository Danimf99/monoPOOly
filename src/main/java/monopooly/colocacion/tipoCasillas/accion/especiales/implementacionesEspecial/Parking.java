package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

public class Parking implements AccionEspecial {
    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.parking;
    }

    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public String describir() {
        return "\nEsto es el Parking\n";
    }
}
