package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.Impuesto;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Stream;

public abstract class Casilla implements Imprimible {
    private String nombre;
    private HashSet<Avatar> avatares;
    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();
    private Integer id;

    public Casilla(String nombre) {
        this.nombre = nombre;
        this.avatares = new HashSet<>();
        this.id = generadorId.next();
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

    public ArrayList<String> representar(ArrayList<String> reprSubclases) {
        reprSubclases.add(1, this.nombre);
        return reprSubclases;
    }

    public void meterJugador(Jugador jugador) {
        this.meterJugador(jugador.getAvatar());
    }

    public void meterJugador(Avatar avatar) {
        this.avatares.add(avatar);
    }

    public void quitarJugador(Jugador jugador) {
        this.quitarJugador(jugador.getAvatar());
    }

    public void quitarJugador(Avatar avatar) {
        this.avatares.remove(avatar);
    }

    public boolean estaAvatar(Avatar avatar) {
        return this.avatares.contains(avatar);
    }

    public boolean estaJugador(Jugador jugador) {
        return this.estaAvatar(jugador.getAvatar());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casilla)) return false;

        Casilla casilla = (Casilla) o;

        return nombre.equals(casilla.nombre)
                && id.equals(casilla.id);
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre='" + nombre + '\'' +
                ", avatares=" + avatares +
                '}';
    }
}
