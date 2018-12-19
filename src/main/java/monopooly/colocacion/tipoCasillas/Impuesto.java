package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;

public class Impuesto extends Casilla {
    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }
}
