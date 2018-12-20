package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.player.Avatar;

import java.util.HashSet;

public abstract class Casilla implements Imprimible {
    private String nombre;
    private HashSet<Avatar> avatares;

    public Casilla(String nombre) {
        this.nombre = nombre;
        this.avatares = new HashSet<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public HashSet<Avatar> getAvatares() {
        return new HashSet<>(this.avatares);
    }

    public void setAvatares(HashSet<Avatar> avatares) {
        this.avatares = avatares;
    }

    /* Metodos para trabajar con el conjunto de avatares y la posicion de la casilla */

    /**
     *
     * @return Posicion de la casilla
     */
    public Posicion getPosicion() {
        return Tablero.getTablero().posicionCasilla(this);
    }

    public abstract void visitar(VisitanteCasilla visitante);

    public String representar(String reprSubclases) {

        return "Lo añade casilla \n" + reprSubclases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casilla)) return false;

        Casilla casilla = (Casilla) o;

        return getNombre().equals(casilla.getNombre());
    }


}
