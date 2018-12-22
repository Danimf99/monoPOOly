package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

public class Impuesto extends Casilla {
    private int cantidad;

    public Impuesto(String nombre, int cantidad) {
        super(nombre);
        this.cantidad = cantidad;
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Impuesto{");
        sb.append('}');
        return super.representar(sb.toString());
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.impuesto;
    }
}
