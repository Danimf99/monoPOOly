package monopooly.cartas.suerte;

import monopooly.cartas.Suerte;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;

public class CartaSuerte4 extends Suerte {
    private static final int DINERO = Precios.SALIDA;
    private static final String NOMBRE_CASILLA = "Salamanca";
    private static final String MENSAJE = ""
            + "Ve a " + NOMBRE_CASILLA + " a buscar al Lazarillo. Si pasas por la casilla de salida \n"
            + "cobra " + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    @Override
    public void accion() throws ExcepcionMonopooly {
        Jugador jugador = Tablero.getPrompt().getJugador();
        jugador.getAvatar().moverAvatar(Tablero.getTablero().getCasilla(NOMBRE_CASILLA).getPosicion());
        /*
         * Ya se encarga mover avatar de mandar los sucesos
         * */

        throw new ExcepcionCarta(MENSAJE, this); // Hay que mandar la excepcion para que los movs especiales
        // sigan sin fallo
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        // Se deshace sola con los eventos de pasar por la salida
    }


    @Override
    public int modDinero() {
        return 0;
    }
}
