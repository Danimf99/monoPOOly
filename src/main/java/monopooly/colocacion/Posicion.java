package monopooly.colocacion;

import monopooly.configuracion.Posiciones;

import java.util.ArrayList;

public class Posicion {
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
        this.x += desplazamiento;
        this.x %= Posiciones.TOTAL;
        if (this.x < 0) {
            this.x += Posiciones.TOTAL;
        }
        this.historialPosiciones.add(new Posicion(this.x));
    }


    public boolean esIrCarcel(){
        return x == Posiciones.VE_A_LA_CARCEL;
    }


    public void irCarcel(){
        this.x = Posiciones.CARCEL;
        this.historialPosiciones.add(new Posicion(this.x));
    }


    public int contarRepeticiones(Posicion p){
        int cantidad = 0;
        for (Posicion pos :
                this.historialPosiciones) {
            if (pos.equals(p)) {
                cantidad++;
            }
        }
        return cantidad;
    }

}
