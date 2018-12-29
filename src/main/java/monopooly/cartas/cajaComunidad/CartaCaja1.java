package monopooly.cartas.cajaComunidad;

import monopooly.Partida;
import monopooly.cartas.CajaComunidad;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;
import monopooly.sucesos.tipoSucesos.PagoBanca;

public class CartaCaja1 extends CajaComunidad {
    private static final int DINERO = -150;
    private static final String MENSAJE = ""
            + "Vas a un balneario de 5 estrellas \n"
            + "paga " + -DINERO + " " + Precios.MONEDA
            + ""
            + "";


    @Override
    public void accion() throws ExcepcionCarta {
        Jugador jugador = Tablero.getPrompt().getJugador();
        jugador.anhadirDinero(DINERO);
        Partida.interprete.enviarSuceso(new AccionCarta(jugador, this));
        Partida.interprete.enviarSuceso( // Necesario enviarlo para que el parking lo pille =)
                new PagoBanca(jugador, DINERO, "Carta de Caja de Comunidad")
        );
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        /* Los pagos de la banca se deshacen solos */
    }

    @Override
    public int modDinero() {
        return -DINERO;
    }
}
