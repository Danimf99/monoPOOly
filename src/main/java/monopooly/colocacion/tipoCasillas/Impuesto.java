package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Imprimible;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.PintadoAscii;

import java.util.ArrayList;

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
    public TipoMonopolio getTipo() {
        return TipoMonopolio.impuesto;
    }

    @Override
    public String toString() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Impuestos");
        lineas.add("");
        lineas.add("Cantidad:");
        lineas.add(PintadoAscii.stringDinero(cantidad));
        return PintadoAscii.genCasilla(super.representar(lineas), this);
    }
}
