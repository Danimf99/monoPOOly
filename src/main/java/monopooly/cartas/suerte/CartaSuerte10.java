package monopooly.cartas.suerte;

import monopooly.Partida;
import monopooly.cartas.Suerte;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;

public class CartaSuerte10 extends Suerte {
    private static final int DINERO = 150;
    private static final String MENSAJE = ""
            + "Has sido elegido presidente de la junta directiva. Paga a cada jugador "
            + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    private int pago() {
        return (Tablero.getTablero().getJugadores().size() - 1) * DINERO;
    }

    @Override
    public void accion() throws ExcepcionMonopooly {
        Jugador actual = Tablero.getPrompt().getJugador();
        actual.quitarDinero(pago());
        Tablero.getTablero().getJugadores().stream()
                .filter(jugador -> !jugador.equals(actual)).forEach(
                    jugador -> jugador.anhadirDinero(DINERO)
            );

        Partida.interprete.enviarSuceso(new AccionCarta(actual, this));
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() throws ExcepcionMonopooly {
        Jugador actual = Tablero.getPrompt().getJugador();
        actual.quitarDinero(pago());
        Tablero.getTablero().getJugadores().stream().filter(jugador -> !jugador.equals(actual)).forEach(
                jugador -> jugador.anhadirDinero(-DINERO)
        );
    }


    @Override
    public int modDinero() {
        return -pago();
    }
}
