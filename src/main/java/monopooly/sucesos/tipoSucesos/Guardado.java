package monopooly.sucesos.tipoSucesos;

import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Guardado extends Suceso {

    public Guardado(Jugador jugadorOriginador) {
        super(jugadorOriginador);
    }

    @Override
    public String toString() {
        return "\n  > Guardado{}\n";
    }
}
