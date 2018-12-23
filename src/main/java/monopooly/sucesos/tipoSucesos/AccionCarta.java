package monopooly.sucesos.tipoSucesos;

import monopooly.cartas.Carta;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;


public class AccionCarta extends Suceso {
    private Carta carta;

    public AccionCarta(Jugador jugadorOriginador, monopooly.cartas.Carta carta) {
        super(jugadorOriginador);
        this.carta = carta;
    }

    public Carta getCarta() {
        return carta;
    }

    public void setCarta(Carta carta) {
        this.carta = carta;
    }

    public int pagoBanca() {
        return this.carta.cantidadBanca();
    }


}
