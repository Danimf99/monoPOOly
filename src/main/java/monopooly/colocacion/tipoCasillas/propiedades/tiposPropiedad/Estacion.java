package monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.Visitante;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.VisitanteCasilla;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;

import java.util.ArrayList;

public class Estacion extends Propiedad {

    public Estacion(Grupo monopolio, String nombre) {
        super(monopolio, nombre);
    }


    @Override
    public int calcularAlquiler(VisitanteCasilla visitante) {
        return visitante.calcularAlquiler(this);
    }

    @Override
    public int calcularAlquiler() {
        return new Visitante().calcularAlquiler(this);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) throws ExcepcionMonopooly {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        ArrayList<String> lineas = new ArrayList<>();
        return PintadoAscii.genCasilla(super.representar(lineas), this);
    }
}
