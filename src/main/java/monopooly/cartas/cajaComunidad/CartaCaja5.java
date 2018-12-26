package monopooly.cartas.cajaComunidad;

import monopooly.Partida;
import monopooly.cartas.CajaComunidad;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.PagoBanca;

public class CartaCaja5 extends CajaComunidad {
    private static final int DINERO = -150;
    private static final String MENSAJE = ""
            + "Invitas a todos tus amigos a un viaje a Leon.\n"
            + "paga " + -DINERO + " " + Precios.MONEDA
            + ""
            + "";


    @Override
    public void accion() throws ExcepcionCarta {
        Jugador jugador = Tablero.getPrompt().getJugador();
        jugador.anhadirDinero(DINERO);
        Partida.interprete.enviarSuceso(new PagoBanca(jugador, -DINERO, MENSAJE));
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        /* Va por pago de Banca entonces se deshace solo */
    }


    @Override
    public int modDinero() {
        return DINERO;
    }
}
