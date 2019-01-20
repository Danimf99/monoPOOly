package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Guardado extends Suceso {

    public Guardado(Jugador jugadorOriginador) {
        super(jugadorOriginador);
    }


    @Override
    public StackPane tarjeta() {
        return TarjetasSucesos.crearTarjeta("Guardado", "Guardado", "#385785");
    }

    @Override
    public String toString() {
        return "\n  > Guardado{}\n";
    }
}
