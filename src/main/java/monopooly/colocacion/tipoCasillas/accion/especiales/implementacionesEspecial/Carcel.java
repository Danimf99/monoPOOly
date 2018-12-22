package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

import java.util.ArrayList;

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
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Jugadores encarcelados: ");

        Tablero.getTablero().getJugadores().stream()  // Sweet
                .filter(Jugador::isEstarEnCarcel)
                .map(Jugador::getNombre)
                .forEach(lineas::add);

        return lineas;
    }
}
