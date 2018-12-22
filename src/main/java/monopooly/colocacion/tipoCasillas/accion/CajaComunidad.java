package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

public class CajaComunidad extends Accion {


    public CajaComunidad(String nombre) {
        super(nombre);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.caja_comunidad;
    }

    @Override
    public String toString() {
        return super.representar("CajaComunidad{}");
    }
}
