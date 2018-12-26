package monopooly.cartas.cajaComunidad;

import monopooly.Partida;
import monopooly.cartas.CajaComunidad;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;

public class CartaCaja8 extends CajaComunidad {
    private static final int DINERO = 50;
    private static final String MENSAJE = ""
            + "Alquilas a tus compañeros una villa en Cannes durante una semana.\n"
            + "Paga " + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    private int pago() {
        return (Tablero.getTablero().getJugadores().size() - 1) * DINERO;
    }

    @Override
    public void accion() throws ExcepcionMonopooly {
        Jugador actual = Tablero.getPrompt().getJugador();
        Tablero.getTablero().getJugadores().stream().filter(jugador -> !jugador.equals(actual)).forEach(
                jugador -> jugador.anhadirDinero(DINERO)
        );
        actual.quitarDinero(pago());
        Partida.interprete.enviarSuceso(new AccionCarta(actual, this));
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() throws ExcepcionMonopooly {
        Jugador actual = Tablero.getPrompt().getJugador();
        Tablero.getTablero().getJugadores().stream().filter(jugador -> !jugador.equals(actual)).forEach(
                jugador -> jugador.anhadirDinero(-DINERO)
        );
        actual.quitarDinero(pago());
    }


    @Override
    public int modDinero() {
        return -pago();
    }
}
