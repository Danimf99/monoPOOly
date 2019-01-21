package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class PasoSalida extends Suceso {

    public PasoSalida(Jugador jugadorOriginador) {
        super(jugadorOriginador);
        setTarjeta(TarjetasSucesos.crearTarjeta("Pago Salida", this.toString(), "#FAA916"));
    }


    @Override
    public String toString() {
        return getJugadorOriginador().getNombre();
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().quitarDinero(Precios.SALIDA);
    }
}
