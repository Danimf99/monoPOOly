package monopooly.colocacion.calles;

import monopooly.entradaSalida.Mensajes;
import monopooly.player.Avatar;

import java.util.ArrayList;
import java.util.HashSet;

public class Casilla {
    private Inmueble calle;
    private HashSet<Avatar> avatares;


    /* Constructores */

    public Casilla(Inmueble calle) {
        if (calle == null) {
            Mensajes.error("No se pudo inicializar la casilla. Calle == Null");
            return;
        }
        this.calle = calle;
        this.avatares = new HashSet<>();
    }

    /* Getters CON ALIASING */

    /* Llevan a propósito aliasing para poder hacer cosas del estilo:
    *   - casilla1.getCalle().cambiarPropietario(jugador1)
    *   */

    public Inmueble getCalle() {
        return calle;
    }

    /* Getters SIN ALIASING en los atributos de CASILLA */

    /* No hay una razon por la que se deba poder modificar directamente la lista de avatares
    * de una casilla */
    public HashSet<Avatar> getAvatares() {
        return new HashSet<>(this.avatares);
    }

    /**
     * Elimina un avatar dado de la casilla actual. ASUME QUE EXISTE @Override de EQUALS EN AVATAR.
     * Ayuda para el metodo mover Avatar
     * @param figura Avatar que se desea eliminar.
     */
    private void eliminarAvatar(Avatar figura) {
        this.avatares.remove(figura);
    }


    /**
     * Añade un avatar a la casilla.
     * Ayuda para el metodo mover Avatar
     * @param figura Avatar que se quiere añadir.
     */
    public void insertarAvatar(Avatar figura) {
        if (figura != null) {
            this.avatares.add(figura);
        } else {
            Mensajes.error("No se puede insertar un jugador null en la casll");
        }

    }

    /**
     * Elimina un avatar de la casilla actual y lo inserta en su nueva posicion
     * @param figura Avatar que se desea mover
     * @param nuevaCasilla Casilla en la que se desea colocar al avatar
     */
    public void moverAvatarA(Avatar figura, Casilla nuevaCasilla) {
        if (figura == null || !this.avatares.contains(figura)) {
            Mensajes.error("Ese avatar no se encuentra en esta casilla.");
            return;
        }

        if (nuevaCasilla == null) {
            Mensajes.error("No se puede mover un jugador a una casilla nula");
            return;
        }

        this.avatares.remove(figura);
        nuevaCasilla.insertarAvatar(figura);
    }



}
