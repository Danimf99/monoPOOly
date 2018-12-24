package monopooly.colocacion;

import monopooly.Partida;
import monopooly.colocacion.tipoCasillas.Impuesto;
import monopooly.colocacion.tipoCasillas.accion.CajaComunidad;
import monopooly.colocacion.tipoCasillas.accion.Suerte;
import monopooly.colocacion.tipoCasillas.accion.especiales.Especial;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.Caer;
import monopooly.sucesos.tipoSucesos.PagoImpuesto;

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
        this.jugadorVisitante = null;
    }

    private void notificarCaer(Casilla casilla) {
        Partida.interprete.enviarSuceso(new Caer(jugadorVisitante, casilla.getPosicion()));
    }

    @Override
    public int calcularAlquiler(Estacion estacion) {
        return 0;
    }

    @Override
    public int calcularAlquiler(Propiedad propiedad) {
        if (propiedad instanceof Estacion) {
            return calcularAlquiler((Estacion) propiedad);
        }

        if (propiedad instanceof Servicio) {
            return calcularAlquiler((Servicio) propiedad);
        }

        if (propiedad instanceof Solar) {
            return calcularAlquiler((Solar) propiedad);
        }
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

    /*



    Caer en las casillas



    */

    @Override
    public void visitar(Solar solar) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(solar);
    }


    @Override
    public void visitar(Estacion estacion) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(estacion);
    }

    @Override
    public void visitar(Servicio servicio) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(servicio);
    }

    @Override
    public void visitar(Impuesto impuesto) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(impuesto);
        jugadorVisitante.quitarDinero(Precios.IMPUESTOS);
        Partida.interprete.enviarSuceso(new PagoImpuesto(jugadorVisitante));
    }

    @Override
    public void visitar(Especial especial) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(especial);

        especial.getComportamiento().efectuar(this.jugadorVisitante);
    }

    @Override
    public void visitar(CajaComunidad cajaComunidad) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(cajaComunidad);
    }

    @Override
    public void visitar(Suerte suerte) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(suerte);
    }

    @Override
    public void visitar(Propiedad propiedad) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugado para visitarla");
        }
        notificarCaer(propiedad);

        if (propiedad instanceof Estacion) {
            visitar((Estacion) propiedad);
        }

        if (propiedad instanceof Servicio) {
            visitar((Servicio) propiedad);
        }

        if (propiedad instanceof Solar) {
            visitar((Solar) propiedad);
        }
    }
}
