package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class PagoBanca extends Suceso {

    private int cantidad;
    private String explicacion;

    public PagoBanca(Jugador jugadorOriginador, int cantidad, String explicacion) {
        super(jugadorOriginador);
        this.cantidad = cantidad;
        this.explicacion = explicacion;
        setTarjeta(TarjetasSucesos.crearTarjeta("Pago banca", this.toString(), "#FAA916"));
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getExplicacion() {
        return explicacion;
    }

    public void setExplicacion(String explicacion) {
        this.explicacion = explicacion;
    }


    @Override
    public String toString() {
        return "" +
                "Jugador: " + getJugadorOriginador().getNombre() + ",\n" +
                "Cantidad: " + cantidad + " " + Precios.MONEDA + ",\n" +
                "Concepto: " + explicacion + "";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().anhadirDinero(-cantidad);
    }
}
