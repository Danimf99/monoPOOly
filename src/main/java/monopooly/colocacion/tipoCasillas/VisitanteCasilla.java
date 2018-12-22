package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.tipoCasillas.accion.CajaComunidad;
import monopooly.colocacion.tipoCasillas.accion.Suerte;
import monopooly.colocacion.tipoCasillas.accion.especiales.Especial;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;

public interface VisitanteCasilla {

    int calcularAlquiler(Estacion estacion);

    int calcularAlquiler(Propiedad propiedad);

    int calcularAlquiler(Servicio servicio);

    int calcularAlquiler(Solar solar);

    void visitar(Solar solar);

    void visitar(Estacion estacion);

    void visitar(Servicio servicio);

    void visitar(Impuesto impuesto);

    void visitar(Especial especial);

    void visitar(CajaComunidad cajaComunidad);

    void visitar(Suerte suerte);

    void visitar(Propiedad propiedad);
}
