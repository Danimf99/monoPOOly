package monopooly.colocacion.calles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Monopolio {
    private HashSet<Inmueble> calles;

    /* Constructores */
    public Monopolio(HashSet<Inmueble> calles) {
        this.calles = calles;
    }

    public Monopolio(ArrayList<Inmueble> calles) {
        this.calles = new HashSet<>(calles);
    }

    /* Metodos sobre la instancia */

    /**
     * Comprueba si un monopolio esta completo. Prueba ssi todas las calles del grupo son del mismo propietario.
     * Asume que la clase Jugador tiene un Equals bien implementado.
     * @return Devuelve true si el Monopolio esta completo; false en caso contrario.
     */
    public boolean esCompleto() {
//        TODO Implementar la comprobacion de si el Monopolio esta completo. Requiere que Jugador tenga un equals.
        return false;
    }

}
