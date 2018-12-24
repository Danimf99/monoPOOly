package monopooly.sucesos.tipoSucesos;

import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class PasoSalida extends Suceso {

    public PasoSalida(Jugador jugadorOriginador) {
        super(jugadorOriginador);
    }

    @Override
    public String toString() {
        return "PasoSalida{}";
    }
}
