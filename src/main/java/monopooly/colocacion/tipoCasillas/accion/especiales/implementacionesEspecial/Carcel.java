package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

public class Carcel implements AccionEspecial {


    @Override
    public void efectuar(Jugador jugador) {
        /* Nada nada */
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.carcel;
    }

    @Override
    public String describir() {
        return "\nEsto es la carsel\n";
    }
}
