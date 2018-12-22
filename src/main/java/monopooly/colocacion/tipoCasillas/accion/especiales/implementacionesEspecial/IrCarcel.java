package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

import java.util.ArrayList;

/**
 * Hay que mandar al jugador a la carcel
 */
public class IrCarcel implements AccionEspecial {


    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.ir_carcel;
    }

    @Override
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        return lineas;
    }
}
