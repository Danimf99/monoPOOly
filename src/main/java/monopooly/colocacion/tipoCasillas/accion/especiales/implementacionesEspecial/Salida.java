package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Salida implements AccionEspecial {
    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.salida;
    }

    @Override
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("");
        return lineas;
    }
}
