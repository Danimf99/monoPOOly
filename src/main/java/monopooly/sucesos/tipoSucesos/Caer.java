package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.ReprASCII;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Caer extends Suceso {
    private Posicion posicion;


    public Caer(Jugador jugadorOriginador, Posicion posicion) {
        super(jugadorOriginador);
        this.posicion = posicion;
        Casilla casillaCaer=Tablero.getTablero().getCasilla(this.posicion);
        setTarjeta(TarjetasSucesos.crearTarjeta("Caer", this.toString(),
                casillaCaer instanceof Propiedad ? ((Propiedad) casillaCaer).getMonopolio().getHexColor() : "#9a0003"));
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    @Override
    public String toString() {
        return Tablero.getTablero().getCasilla(posicion).getNombre();
    }
}
