package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.tiposEspecial.IrCarcel;
import monopooly.colocacion.tipoCasillas.tiposEspecial.Parking;
import monopooly.colocacion.tipoCasillas.tiposEspecial.Tarot;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Estacion;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Servicio;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Solar;

public interface VisitanteCasilla {

    int calcularAlquiler(Estacion estacion);

    int calcularAlquiler(Servicio servicio);

    int calcularAlquiler(Solar solar);

    void visitar(Propiedad propiedad);

    void visitar(Tarot tarot);

    void visitar(IrCarcel irCarcel);

    void visitar(Parking parking);

    void visitar(Casilla casilla);
}
