package monopooly.colocacion;

import monopooly.configuracion.Posiciones;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Objects;

public class Posicion {
    private int x;
    private ArrayList<Posicion> posicionesEsteTurno;

    /* Constructores */

    public Posicion() {
        this.x = 0;
        this.posicionesEsteTurno = new ArrayList<>();
    }

    public Posicion(int x) {
        this.x = x;
        this.posicionesEsteTurno = new ArrayList<>();
    }

    /* Getters & Setters*/

    public ArrayList<Posicion> getPosicionesEsteTurno() {
        return posicionesEsteTurno;
    }

    public void setPosicionesEsteTurno(ArrayList<Posicion> posicionesEsteTurno) {
        this.posicionesEsteTurno = posicionesEsteTurno;
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
//        TODO implementar Comprobaciones

    }


    /**
     * Realiza uno de los movimientos especiales. Esto es para la parte 2
     */
    public void movimientoEspecial() {
//        TODO implementar movimiento especial
    }


    // TODO Calculo del precio en funcion de la posicion

    /**
     * Para la posicion actual determina el precio que deberia tener el solar de la posicion actual
     * @return precio inicial que deberia tener el Inmueble
     */
    public int precioCompra() {
        return 0;
    }






//    TODO implementar mas metodos estaticos útiles.
    /*
    * Probablemente devolver arrayLists que ayuden al pintado ASCII del tablero
    * */



    /* Overrides */

    @Override
    public String toString() {
        return "Posicion{" +
                "x=" + x +
                ", posicionesEsteTurno=" + posicionesEsteTurno +
                '}';
    }

    @Override
    public boolean equals(Object o) {
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
