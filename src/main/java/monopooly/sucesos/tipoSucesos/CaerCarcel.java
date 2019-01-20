package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class CaerCarcel extends Suceso {
    private boolean encarcelado;

    public CaerCarcel(Jugador jugador) {
        super(jugador);
        this.encarcelado = true;
    }

    public boolean saleDeCarcel() {
        return !encarcelado;
    }

    public boolean entraEnCarcel() {
        return encarcelado;
    }


    @Override
    public StackPane tarjeta() {
        return TarjetasSucesos.crearTarjeta("Caer en la carcel", this.toString(), "#bc2e54");
    }

    @Override
    public String toString() {
        return "El jugador " + getJugadorOriginador().getNombre() + " está en la carcel (" + encarcelado + ")";
    }

    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().setEstarEnCarcel(!encarcelado);
    }
}
