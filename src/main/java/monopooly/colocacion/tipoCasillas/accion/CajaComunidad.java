package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;

import java.util.ArrayList;

public class CajaComunidad extends Accion {

    public CajaComunidad(String nombre) {
        super(nombre);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) throws ExcepcionMonopooly {
        visitante.visitar(this);
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.caja_comunidad;
    }

    @Override
    public String toString() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Caja de Comunidad");
        lineas.add("");
        return PintadoAscii.genCasilla(super.representar(lineas), this);
    }
}
