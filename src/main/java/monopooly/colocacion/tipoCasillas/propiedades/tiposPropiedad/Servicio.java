package monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.Visitante;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Servicio extends Propiedad {


    public Servicio( Grupo monopolio, String nombre) {
        super(monopolio, nombre);
    }

    @Override
    public int calcularAlquiler(VisitanteCasilla visitante) {
        return visitante.calcularAlquiler(this);
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public int calcularAlquiler() {
        return new Visitante().calcularAlquiler(this);
    }

    @Override
    public String toString() {
        String texto = "servicio";
        return "Servicio{}";
    }
}


