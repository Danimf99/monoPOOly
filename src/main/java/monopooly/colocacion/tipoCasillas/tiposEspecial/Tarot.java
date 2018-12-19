package monopooly.colocacion.tipoCasillas.tiposEspecial;

import monopooly.colocacion.tipoCasillas.Especial;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Tarot extends Especial {
    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }
}
