package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Casilla {

    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }
}
