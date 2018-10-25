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

}
