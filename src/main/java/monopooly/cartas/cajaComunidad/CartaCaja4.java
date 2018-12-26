package monopooly.cartas.cajaComunidad;

import monopooly.Partida;
import monopooly.cartas.CajaComunidad;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;
import monopooly.sucesos.tipoSucesos.PagoBanca;

public class CartaCaja4 extends CajaComunidad {
    private static final int DINERO = 50;
    private static final String MENSAJE = ""
            + "Tu compañia de internet obtiene beneficios \n"
            + "cobra " + DINERO + " " + Precios.MONEDA
            + ""
            + "";


    @Override
    public void accion() {
        Jugador jugador = Tablero.getPrompt().getJugador();
        jugador.anhadirDinero(DINERO);
        Partida.interprete.enviarSuceso(new AccionCarta(jugador, this));
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() throws ExcepcionMonopooly {
        Tablero.getPrompt().getJugador().quitarDinero(DINERO);
    }

    @Override
    public int modDinero() {
        return DINERO;
    }
}
