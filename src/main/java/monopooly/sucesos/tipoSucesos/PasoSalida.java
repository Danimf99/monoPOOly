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
    }



    public StackPane tarjeta() {
        return TarjetasSucesos.crearTarjeta("Pago Salida", this.toString(), "#FAA916");
    }


    @Override
    public String toString() {
        return "Paso por la salida:\n" +
                "   Jugador  -> " + getJugadorOriginador().getNombre() + ",\n" +
                "   Cantidad -> " + Precios.SALIDA + " " + Precios.MONEDA + "\n" +
                "}";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().quitarDinero(Precios.SALIDA);
    }
}
