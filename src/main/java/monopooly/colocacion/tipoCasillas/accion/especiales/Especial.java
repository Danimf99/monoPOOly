package monopooly.colocacion.tipoCasillas.accion.especiales;

import monopooly.colocacion.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.accion.Accion;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;

import java.util.ArrayList;

public class Especial extends Accion {

    private AccionEspecial comportamiento;


    public Especial(String nombre, AccionEspecial comportamiento) {
        super(nombre);
        this.comportamiento = comportamiento;
    }


    public AccionEspecial getComportamiento() {
        return comportamiento;
    }

    public void setComportamiento(AccionEspecial comportamiento) {
        this.comportamiento = comportamiento;
    }

    @Override
    public void visitar(VisitanteCasilla visitante) throws ExcepcionMonopooly {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("Casilla especial");
        lineas.addAll(comportamiento.describir());
        return PintadoAscii.genCasilla(super.representar(lineas), this);
    }

    @Override
    public TipoMonopolio getTipo() {
        return comportamiento.getTipo();
    }
}
