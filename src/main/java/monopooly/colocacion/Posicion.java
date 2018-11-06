package monopooly.colocacion;

import monopooly.configuracion.Posiciones;

import java.util.ArrayList;
import java.util.Objects;

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

    /* Metodos sobre la instancia */

    /**
     * Modifica la posicion para que sea la misma que la de la carcel
     */
    public void irCarcel(){
        this.x = Posiciones.CARCEL;
    }


    /**
     * Determina si la posicion es la de ir a la carcel. Ayuda a decidir si debe ir a la carcel.
     * @return falso si no coincide con ir a la carcel, verdadero en caso de coincidir.
     */
    public boolean esIrCarcel(){
        return x == Posiciones.VE_A_LA_CARCEL;
    }


    /**
     * Permite modificar una posicion. Tanto hacia alante como hacia atras.
     * @param desplazamiento Número de casillas que se avanzan o se retroceden. Admite numeros negativos.
     */
    public void mover(int desplazamiento){
        this.x += desplazamiento;
        this.x %= Posiciones.TOTAL;
        this.historialPosiciones.add(new Posicion(this.x));
//        TODO implementar Comprobaciones

    }


    /**
     * Realiza uno de los movimientos especiales. Esto es para la parte 2
     */
    public void movimientoEspecial() {
//        TODO implementar movimiento especial
    }


    /**
     * Cuenta el numero de veces que el jugador pasó por una posicion
     * @param p Posicion que se desea comprobar
     * @return Repeticiones de una posicion
     */
    public int contarRepeticiones(Posicion p) {
        int cantidad = 0;
        for (Posicion pos :
                this.historialPosiciones) {
            if (pos.equals(p)) {
                cantidad++;
            }
        }
        return cantidad;
    }




    /* Overrides */

    @Override
    public String toString() {
        return "Posicion{" +
                "x=" + x +
                ", historialPosiciones=" + historialPosiciones +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        // No tiene en cuenta el historial de posiciones a propsito
        if (this == o) return true;
        if (!(o instanceof Posicion)) return false;
        Posicion posicion = (Posicion) o;
        return x == posicion.x;
    }


    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
