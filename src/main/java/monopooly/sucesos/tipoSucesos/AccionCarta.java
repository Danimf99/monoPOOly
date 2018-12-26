package monopooly.sucesos.tipoSucesos;

import monopooly.cartas.Carta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

import static java.lang.Math.abs;


public class AccionCarta extends Suceso {
    private Carta carta;

    public AccionCarta(Jugador jugadorOriginador, Carta carta) {
        super(jugadorOriginador);
        this.carta = carta;
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
        return "Carta {\n" +
                "" +
                "" +
                "" + carta.getMensaje() + "\n}";
    }
}
