package monopooly.cartas.suerte;

import monopooly.Partida;
import monopooly.cartas.Suerte;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;
import monopooly.sucesos.tipoSucesos.PagoBanca;

public class CartaSuerte3 extends Suerte {
    private static final int DINERO = 150;
    private static final String MENSAJE = ""
            + "Vendes tu billete de avión para Terrasa, recibe " + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    @Override
    public void accion() throws ExcepcionCarta {
        Jugador actual = Tablero.getPrompt().getJugador();
        actual.anhadirDinero(DINERO);
        Partida.interprete.enviarSuceso(new AccionCarta(actual, this));
        Partida.interprete.enviarSuceso(new PagoBanca(actual, DINERO, "Carta de Suerte"));
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        // Los pagos de la banca se deshacen solos
    }


    @Override
    public int modDinero() {
        return DINERO;
    }
}
