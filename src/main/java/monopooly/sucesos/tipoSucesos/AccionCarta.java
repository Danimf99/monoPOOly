package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.cartas.CajaComunidad;
import monopooly.cartas.Carta;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

import static java.lang.Math.abs;


public class AccionCarta extends Suceso {
    private Carta carta;

    public AccionCarta(Jugador jugadorOriginador, Carta carta) {
        super(jugadorOriginador);
        this.carta = carta;
        setTarjeta(TarjetasSucesos.crearTarjeta("Carta", carta.getMensaje(), "#23C9FF"));
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        carta.deshacer();
    }


    @Override
    public String toString() {
        String salida = "Carta {\n";
        if (carta.modDinero() != 0) {
            salida += "   Cantidad -> " + carta.modDinero() + " " + Precios.MONEDA + "\n";
        }
        return salida += (carta instanceof CajaComunidad) ? "   Caja de Comunidad" + "\n}" : "   Suerte" + "\n}";

    }
}
