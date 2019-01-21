package monopooly.sucesos;

import javafx.scene.layout.StackPane;
import monopooly.Partida;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.HelperGui;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.*;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Almacena informacion sobre un suceso del juego. Toda la necesaria para
 * que se tenga en cuenta en las estadísticas y para revertirla en movimientos
 * especiales como los del sombrero o la esfinge.
 *
 * @author Danimf99
 * @author luastan
 */
public abstract class Suceso {
    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();
    private Integer id;
    private Jugador jugadorOriginador;
    private boolean deshacer;

    public StackPane getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(StackPane tarjeta) {
        this.tarjeta = tarjeta;
    }

    private StackPane tarjeta;

    public Suceso(Jugador jugadorOriginador) {
        this.jugadorOriginador = jugadorOriginador;
        this.id = generadorId.next();
        this.deshacer = false;
    }

    private static void deshacerEnviar(Juego interprete, Suceso suceso) throws ExcepcionMonopooly {
        suceso.deshacer();
        interprete.enviarSuceso(suceso);
    }

    public static void enviarSoloBeneficios(Suceso suceso) throws ExcepcionMonopooly {
        Juego interprete = Partida.interprete;

        if (suceso instanceof AccionCarta) {
            AccionCarta accionCarta = (AccionCarta) suceso;
            if (accionCarta.getCarta().modDinero() > 0) {
                deshacerEnviar(interprete, suceso);
            }
            return;
        }

        if (suceso instanceof Comprar) {
            deshacerEnviar(interprete, suceso);
            return;
        }

        if (suceso instanceof ConseguirBote) {
            deshacerEnviar(interprete, suceso);
            return;
        }

        if (suceso instanceof PagoBanca) {
            if (((PagoBanca) suceso).getCantidad() > 0) {
                deshacerEnviar(interprete, suceso);
            }
        }

    }

    public Jugador getJugadorOriginador() {
        return jugadorOriginador;
    }

    public void setJugadorOriginador(Jugador jugadorOriginador) {
        this.jugadorOriginador = jugadorOriginador;
    }

    public boolean getDeshacer() {
        return deshacer;
    }

    public void setDeshacer(boolean deshacer) {
        this.deshacer = deshacer;
    }

    public void deshacer() throws ExcepcionMonopooly {
        this.deshacer = true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Suceso)) return false;

        Suceso suceso = (Suceso) o;

        return this.id.equals(suceso.id);
    }

    @Override
    public int hashCode() {
        return id;
    }
}
