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

    public int calcularNumCasas(){
        int cont=0;
        for(Edificio e: this.edificios){
            if(e.getNombre().contains("Casa")){
                cont++;
            }
        }
        return cont;
    }
   // public Edificio getEdificio(Edificio.TIPO tipo){
   //     for(Edificio e:edificios){
   //         if(e=tipo){
   //             return e;
   //         }
   //     }
   //     return null;
   // }
    public int calcularNumHoteles(){
        int cont=0;
        for(Edificio e: edificios){
            if(e.getNombre().contains("Hotel")){
                cont++;
            }
        }
        return cont;
    }
    public int calcularNumPiscinas(){
        int cont=0;
        for(Edificio e: edificios){
            if(e.getNombre().contains("Piscina")){
                cont++;
            }
        }
        return cont;
    }
    public int calcularNumDeportes(){
        int cont=0;
        for(Edificio e: edificios){
            if(e.getNombre().contains("PistaDeporte")){
                cont++;
            }
        }
        return cont;
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
