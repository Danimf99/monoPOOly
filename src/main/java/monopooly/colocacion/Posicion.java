package monopooly.colocacion;

import monopooly.configuracion.Posiciones;
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
        this.x = Posiciones.CARCEL;
    }


    /**
     * Determina si la posicion es la de ir a la carcel. Ayuda a decidir si debe ir a la carcel.
     * @return falso si no coincide con ir a la carcel, verdadero en caso de coincidir.
     */
    public boolean esIrCarcel(){
        return x == Posiciones.VE_A_LA_CARCELZ;
    }


    /**
     * Permite modificar una posicion. Tanto hacia alante como hacia atras.
     * @param desplazamiento Número de casillas que se avanzan o se retroceden. Admite numeros negativos.
     */
    public void mover(int desplazamiento){
        this.x += desplazamiento;
        this.x %= Posiciones.TOTAL;
//        TODO implementar Comprobaciones

        /* Aun teniendo tarjeta de librarse de la carcel, a la carcel hay que mandarlo */
        if (this.esIrCarcel()) {
            this.irCarcel();
        }
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
    public static ArrayList<Posicion> posicionesPosibles(){
        ArrayList<Posicion> conjunto = new ArrayList<>();
        for (int i = 0; i < Posiciones.TOTAL; i++) {
            conjunto.add(new Posicion(i));
        }
        return conjunto;
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
