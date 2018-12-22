package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Parking implements AccionEspecial {
    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.parking;
    }

    @Override
    public void efectuar(Jugador jugador) {

    }

    @Override
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("");
        lineas.add("Bote: " + PintadoAscii.stringDinero(Tablero.getTablero().getBote()));
        return lineas;
    }
}
