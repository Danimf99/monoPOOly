package monopooly.colocacion;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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


    /* Metodos sobre la instancia */

    /**
     * Modifica la posicion para que sea la misma que la de la carcel
     */
    public void irCarcel(){
//        TODO implementar ir a la carcel.
    }


    /**
     * Determina si la posicion es la de ir a la carcel. Ayuda a decidir si debe ir a la carcel.
     * @return falso si no coincide con ir a la carcel, verdadero en caso de coincidir.
     */
    public boolean esCarcel(){
//        TODO Comprobacion para saber si está en la casilla de ve a la carcel
        return false;
    }


    /**
     * Permite modificar una posicion. Tanto hacia alante como hacia atras.
     * @param desplazamiento Número de casillas que se avanzan o se retroceden. Admite numeros negativos.
     */
    public void mover(int desplazamiento){
//        TODO implementar movimiento
    }


    /**
     * Realiza uno de los movimientos especiales. Esto es para la parte 2
     */
    public void movimientoEspecial() {
//        TODO implementar movimiento especial
    }



    /* Metodos estaticos */


    /**
     * Devuelve las posiciones del tablero por orden.
     * @return ArrayList ordenado de las 40 posiciones del tablero
     */
    @NotNull
    @Contract(value = " -> new", pure = true)
    public static ArrayList<Posicion> posicionesPosibles(){

        return new ArrayList<>();
    }

//    TODO implementar mas metodos estaticos útiles.
    /*
    * Probablemente devolver arrayLists que ayuden al pintado ASCII del tablero
    * */



    /* Overrides */

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
