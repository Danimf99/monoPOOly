package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

import static java.lang.Math.abs;

public class PagoImpuesto extends Suceso {
    private int cantidad;

    public PagoImpuesto(Jugador jugadorOriginador) {
        super(jugadorOriginador);
        this.cantidad = Precios.IMPUESTOS;
    }

    public PagoImpuesto(Jugador jugadorOriginador, int cantidad) {
        super(jugadorOriginador);
        this.cantidad = abs(cantidad);
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public StackPane tarjeta() {
        return TarjetasSucesos.crearTarjeta("Pago impuesto", this.toString(), "#FAA916");
    }

    @Override
    public String toString() {
        return "Pago de impuesto: \n" +
                "   Jugador  -> " + getJugadorOriginador().getNombre() + ",\n" +
                "   Cantidad -> " + -cantidad + " " + Precios.MONEDA + "\n";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().anhadirDinero(cantidad);
    }
}
