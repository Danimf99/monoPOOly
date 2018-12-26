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
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.Alquiler;
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
        return ((Precios.SALIDA/4) * estacion.getMonopolio().cantidadPropiedades(estacion.getPropietario()));
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
        return Precios.FACTOR_SERVICIOS * jugadorVisitante.getDados().tirada();
    }

    @Override
    public int calcularAlquiler(Solar solar) {
        int dineroAlquiler=0;
        if(solar.calcularNumDeportes()>0){
            dineroAlquiler=(int) (solar.getMonopolio().getPrecio())*25*solar.calcularNumDeportes();
        }
        else if(solar.calcularNumPiscinas()>0){
            dineroAlquiler+=25*solar.calcularNumPiscinas()*(int) (solar.getMonopolio().getPrecio());
        }
        else if(solar.calcularNumHoteles()>0){
            dineroAlquiler+=70*dineroAlquiler*(int) (solar.getMonopolio().getPrecio());
        }
        else if(solar.calcularNumCasas()>0){
            if(solar.calcularNumCasas()==4){
                dineroAlquiler=50*(int) (solar.getMonopolio().getPrecio());
            }
            if(solar.calcularNumCasas()==3){
                dineroAlquiler+=35*(int) (solar.getMonopolio().getPrecio());
            }
            if (solar.calcularNumCasas()==2){
                dineroAlquiler+=15*(int) (solar.getMonopolio().getPrecio());
            }
            else{
                dineroAlquiler+=5*(int) (solar.getMonopolio().getPrecio());
            }
        }
        else{
            dineroAlquiler=(int)(solar.getMonopolio().getPrecio()*0.1);
        }

        // Habria que tener en cuenta las casas pero de momento no hay
        if (solar.getMonopolio().esCompleto()) {
            dineroAlquiler *= 2;
        }
        return dineroAlquiler;
    }

    /*



    Caer en las casillas



    */



    @Override
    public void visitar(Impuesto impuesto) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugador para visitarla");
        }
        notificarCaer(impuesto);
        jugadorVisitante.quitarDinero(Precios.IMPUESTOS);
        Partida.interprete.enviarSuceso(new PagoImpuesto(jugadorVisitante));
    }

    @Override
    public void visitar(Especial especial) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugador para visitarla");
        }
        notificarCaer(especial);

        especial.getComportamiento().efectuar(this.jugadorVisitante);
    }

    @Override
    public void visitar(CajaComunidad cajaComunidad) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugador para visitarla");
        }
        notificarCaer(cajaComunidad);
    }

    @Override
    public void visitar(Suerte suerte) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugador para visitarla");
        }
        notificarCaer(suerte);
    }

    @Override
    public void visitar(Propiedad propiedad) throws ExcepcionMonopooly {
        if (jugadorVisitante == null) {
            throw new ExcepcionMonopooly("No se puede visitar una casilla sin jugador para visitarla");
        }
        notificarCaer(propiedad);

        if(!jugadorVisitante.equals(propiedad.getPropietario()) && !propiedad.getPropietario().equals(Tablero.BANCA)) {
            jugadorVisitante.quitarDinero(calcularAlquiler(propiedad));
            propiedad.getPropietario().anhadirDinero(calcularAlquiler(propiedad));
            Partida.interprete.enviarSuceso(new Alquiler(jugadorVisitante, calcularAlquiler(propiedad), propiedad, propiedad.getPropietario()));
        }
    }
}
