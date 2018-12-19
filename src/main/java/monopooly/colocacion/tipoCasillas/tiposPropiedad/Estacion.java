package monopooly.colocacion.tipoCasillas.tiposPropiedad;

import monopooly.colocacion.tipoCasillas.Propiedad;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Estacion extends Propiedad {


    @Override
    public int calcularAlquiler(VisitanteCasilla visitante) {
        return visitante.calcularAlquiler(this);
    }
}
