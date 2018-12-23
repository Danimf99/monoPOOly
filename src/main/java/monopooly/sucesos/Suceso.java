package monopooly.sucesos;

import monopooly.player.Jugador;

import java.util.Iterator;
import java.util.stream.Stream;

/**
 * Almacena informacion sobre un suceso del juego. Toda la necesaria para
 * que se tenga en cuenta en las estadísticas y para revertirla en movimientos
 * especiales como los del sombrero o la esfinge
 *
 * @author Danimf99
 * @author luastan
 */
public abstract class Suceso {
    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();
    private Integer id;
    private Jugador jugadorOriginador;
    private boolean deshacer;

    public Suceso(Jugador jugadorOriginador) {
        this.jugadorOriginador = jugadorOriginador;
        this.id = generadorId.next();
        this.deshacer = false;
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
