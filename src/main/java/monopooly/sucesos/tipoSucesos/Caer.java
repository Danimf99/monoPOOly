package monopooly.sucesos.tipoSucesos;

import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.gui.componentes.TarjetasSucesos;
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

    @Override
    public StackPane tarjeta() {
        return TarjetasSucesos.crearTarjeta("Caer", this.toString(), "#bc2e54");
    }
    @Override
    public String toString() {
        return Tablero.getTablero().getCasilla(posicion).getNombre();
    }
}
