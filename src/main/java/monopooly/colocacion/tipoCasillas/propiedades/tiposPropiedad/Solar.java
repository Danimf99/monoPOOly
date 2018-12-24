package monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.Visitante;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;

import java.util.ArrayList;
import java.util.HashSet;

public class Solar extends Propiedad {

    public HashSet<Edificio> getEdificios() {
        return new HashSet<>(edificios);
    }

    public void setEdificios(HashSet<Edificio> edificios) {
        this.edificios = edificios;
    }

    private HashSet<Edificio> edificios;

    public Solar(Grupo monopolio, String nombre) {
        super(monopolio, nombre);
        this.edificios = new HashSet<>();
    }

    public void edificar(Edificio edificio) {
        edificios.add(edificio);
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
