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
        setTarjeta(TarjetasSucesos.crearTarjeta("Pago impuesto", this.toString(), "#FAA916"));
    }

    public PagoImpuesto(Jugador jugadorOriginador, int cantidad) {
        super(jugadorOriginador);
        this.cantidad = abs(cantidad);
        setTarjeta(TarjetasSucesos.crearTarjeta("Pago impuesto", this.toString(), "#FAA916"));
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }


    @Override
    public String toString() {
        return "" +
                "Jugador: " + getJugadorOriginador().getNombre() + ",\n" +
                "Cantidad: " + -cantidad + " " + Precios.MONEDA + "";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().anhadirDinero(cantidad);
    }
}
