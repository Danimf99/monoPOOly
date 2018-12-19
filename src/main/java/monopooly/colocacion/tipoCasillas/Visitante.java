package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.tiposEspecial.IrCarcel;
import monopooly.colocacion.tipoCasillas.tiposEspecial.Parking;
import monopooly.colocacion.tipoCasillas.tiposEspecial.Tarot;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;

/**
 * Esta clase es la que visita las cassillas y sabe cuanto se debe pagar por el
 * alquiler y que se debe de hacer cuando un jugador cae en una casilla concreta
 *
 * @author luastan
 * @author Danimf99
 */
public class Visitante implements VisitanteCasilla {

    private Jugador jugadorVisitante;

    public Visitante(Jugador jugadorVisitante) {
        this.jugadorVisitante = jugadorVisitante;
    }

    public Visitante() {
    }

    @Override
    public int calcularAlquiler(Estacion estacion) {
        return 0;
    }

    @Override
    public int calcularAlquiler(Servicio servicio) {
        return 0;
    }

    @Override
    public int calcularAlquiler(Solar solar) {
        return 123;
    }

    /*  Caer en las casillas  */

    @Override
    public void visitar(Solar solar) {
        Juego.consola.imprimirln("Visito una propiedad");
    }

    @Override
    public void visitar(Tarot tarot) {

    }

    @Override
    public void visitar(IrCarcel irCarcel) {

    }

    @Override
    public void visitar(Parking parking) {

    }

    @Override
    public void visitar(Estacion estacion) {

    }

    @Override
    public void visitar(Servicio servicio) {

    }

    @Override
    public void visitar(Impuesto impuesto) {

    }
}
