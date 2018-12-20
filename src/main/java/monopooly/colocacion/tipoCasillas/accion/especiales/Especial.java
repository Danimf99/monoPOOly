package monopooly.colocacion.tipoCasillas.accion.especiales;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;

public class Especial extends Casilla {

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
    public void visitar(VisitanteCasilla visitante) {
        visitante.visitar(this);
    }

    @Override
    public String toString() {
        return "Especial{" +
                "comportamiento=" + comportamiento +
                '}';
    }
}
