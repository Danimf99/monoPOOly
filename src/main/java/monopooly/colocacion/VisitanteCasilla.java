package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.Impuesto;
import monopooly.colocacion.tipoCasillas.accion.CajaComunidad;
import monopooly.colocacion.tipoCasillas.accion.Suerte;
import monopooly.colocacion.tipoCasillas.accion.especiales.Especial;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.excepciones.ExcepcionMonopooly;

public interface VisitanteCasilla {

    int calcularAlquiler(Estacion estacion);

    int calcularAlquiler(Propiedad propiedad);

    int calcularAlquiler(Servicio servicio);

    int calcularAlquiler(Solar solar);

    void visitar(Impuesto impuesto) throws ExcepcionMonopooly;

    void visitar(Especial especial) throws ExcepcionMonopooly;

    void visitar(CajaComunidad cajaComunidad) throws ExcepcionMonopooly;

    void visitar(Suerte suerte) throws ExcepcionMonopooly;

    void visitar(Propiedad propiedad) throws ExcepcionMonopooly;
}
