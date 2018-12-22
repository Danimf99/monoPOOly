package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

public class Suerte extends Accion {

    public Suerte(String nombre) {
        super(nombre);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Suerte{");
        sb.append('}');
        return sb.toString();
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.suerte;
    }
}
