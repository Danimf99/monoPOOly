package monopooly.colocacion;

import java.util.ArrayList;

public class Posicion implements IPosicion{
    private int x;
    private ArrayList<Posicion> historialPosiciones;


    /* Constructores */

    public Posicion() {
        this.x = 0;
        this.historialPosiciones = new ArrayList<>();
        this.historialPosiciones.add(new Posicion(this.x));
    }

    public Posicion(int x) {
        this.x = x;
        this.historialPosiciones = new ArrayList<>();
    }

    /* Getters & Setters*/

    public ArrayList<Posicion> getHistorialPosiciones() {
        return historialPosiciones;
    }

    public void setHistorialPosiciones(ArrayList<Posicion> historialPosiciones) {
        this.historialPosiciones = historialPosiciones;
    }

    public int getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
        this.historialPosiciones.add(new Posicion(this.x));
    }

    public void mover(int desplazamiento){

    }

    public boolean esIrCarcel(){
        return false;
    }

    public void irCarcel(){

    }

    public int contarRepeticiones(Posicion p){
        return 0;
    }

}
