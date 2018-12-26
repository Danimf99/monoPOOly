package monopooly.cartas.cajaComunidad;

import javafx.scene.control.Tab;
import monopooly.Partida;
import monopooly.cartas.CajaComunidad;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial.IrCarcel;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.AccionCarta;

public class CartaCaja2 extends CajaComunidad {
    private static final int DINERO = 0;
    private static final String MENSAJE = ""
            + "\"Ve a la carcel sin cobrar por la casilla de salida\\n\""
            + "";


    @Override
    public void accion() throws ExcepcionMonopooly {
        Jugador jugador = Tablero.getPrompt().getJugador();
        Partida.interprete.enviarSuceso(new AccionCarta(jugador, this));
        new IrCarcel().efectuar(jugador);
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        /* No es necesario el suceso generado al ir a la carcel es el que se deshace */
    }

    @Override
    public int modDinero() {
        return DINERO;
    }
}
