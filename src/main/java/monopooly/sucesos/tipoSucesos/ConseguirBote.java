package monopooly.sucesos.tipoSucesos;

import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class ConseguirBote extends Suceso {
    private int cantidadBote;

    public ConseguirBote(Jugador jugadorOriginador, int cantidadBote) {
        super(jugadorOriginador);
        this.cantidadBote = cantidadBote;
    }

    public int getCantidadBote() {
        return cantidadBote;
    }

    public void setCantidadBote(int cantidadBote) {
        this.cantidadBote = cantidadBote;
    }
}
