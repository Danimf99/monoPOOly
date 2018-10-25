package monopooly.colocacion.calles;

import monopooly.player.Avatar;

import java.util.ArrayList;

public class Casilla {
    private Inmueble calle;
    private ArrayList<Avatar> avatares;


    /* Constructores */

    public Casilla(Inmueble calle) {
//        TODO Comprobacion de errores en el constructor
        this.calle = calle;
        this.avatares = new ArrayList<>();
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
    public ArrayList<Avatar> getAvatares() {
        return new ArrayList<>(this.avatares);
    }

    /**
     * Elimina un avatar dado de la casilla actual. ASUME QUE EXISTE @Override de EQUALS EN AVATAR.
     * Ayuda para el metodo mover Avatar
     * @param figura Avatar que se desea eliminar.
     */
    private void eliminarAvatar(Avatar figura) {
//        TODO implementar eliminacion comprobando que el @override del equal esta presente
    }


    /**
     * Añade un avatar a la casilla.
     * Ayuda para el metodo mover Avatar
     * @param figura Avatar que se quiere añadir.
     */
    public void insertarAvatar(Avatar figura) {
//        TODO comprobacion de errores
        this.avatares.add(figura);
    }

    /**
     * Elimina un avatar de la casilla actual y lo inserta en su nueva posicion
     * @param figura Avatar que se desea mover
     * @param nuevaCasilla Casilla en la que se desea colocar al avatar
     */
    public void moverAvatarA(Avatar figura, Casilla nuevaCasilla) {
//        TODO Comprobacion de errores
        this.avatares.remove(figura);
        nuevaCasilla.insertarAvatar(figura);
    }



}
