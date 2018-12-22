package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.Imprimible;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.PintadoAscii;

import java.util.ArrayList;

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
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Suerte");
        lineas.add("");
        return PintadoAscii.genCasilla(super.representar(lineas), this);
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.suerte;
    }
}
