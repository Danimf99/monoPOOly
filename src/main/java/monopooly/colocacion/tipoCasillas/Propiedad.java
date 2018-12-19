package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;

/**
 *
 * @author Danimf99
 * @author luastan
 */
public abstract class Propiedad extends Casilla {

    public abstract int calcularAlquiler(VisitanteCasilla visitante);
}
