package monopooly.colocacion.tipoCasillas.tiposPropiedad;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.Propiedad;
import monopooly.colocacion.tipoCasillas.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Solar extends Propiedad {

    public Solar(TipoMonopolio tipoMonopolio, Grupo monopolio) {
        super(tipoMonopolio, monopolio);
    }

    @Override
    public int calcularAlquiler(VisitanteCasilla visitante) {
        return visitante.calcularAlquiler(this);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }
}
