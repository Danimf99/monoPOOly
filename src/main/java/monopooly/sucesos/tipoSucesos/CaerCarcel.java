package monopooly.sucesos.tipoSucesos;

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
    public String toString() {
        return "CaerCarcel{" +
                "encarcelado=" + encarcelado +
                '}';
    }
}
