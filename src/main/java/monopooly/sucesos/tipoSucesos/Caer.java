package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.Posicion;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Caer extends Suceso {
    private Posicion posicion;


    public Caer(Jugador jugadorOriginador, Posicion posicion) {
        super(jugadorOriginador);
        this.posicion = posicion;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }
}
