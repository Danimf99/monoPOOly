package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class CajaComunidad extends Accion {


    public CajaComunidad(String nombre) {
        super(nombre);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        return super.representar("CajaComunidad{}");
    }
}
