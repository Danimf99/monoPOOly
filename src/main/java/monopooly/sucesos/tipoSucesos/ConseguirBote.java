package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class ConseguirBote extends Suceso {
    private int cantidadBote;

    public ConseguirBote(Jugador jugadorOriginador, int cantidadBote) {
        super(jugadorOriginador);
        this.cantidadBote = cantidadBote;
        setTarjeta(TarjetasSucesos.crearTarjeta("Bote", this.toString(), "#FAA916"));
    }

    public int getCantidadBote() {
        return cantidadBote;
    }

    public void setCantidadBote(int cantidadBote) {
        this.cantidadBote = cantidadBote;
    }


    @Override
    public String toString() {
        return "" +
                "Premiado: " + getJugadorOriginador().getNombre() + "\n" +
                "Cantidad: " + cantidadBote + "" + Precios.MONEDA + "";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().quitarDinero(cantidadBote);
    }
}
