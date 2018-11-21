package monopooly.colocacion.calles;

import monopooly.entradaSalida.Mensajes;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.HashSet;

public class Monopolio {
    private HashSet<Inmueble> calles;
    private TipoMonopolio tipo;

    /* Constructores */
    public Monopolio(TipoMonopolio tipo) {
        this.calles = new HashSet<>();
        this.tipo = tipo;
    }

    public Monopolio() {
        this.calles = new HashSet<>();
        this.tipo = TipoMonopolio.none;
    }

    public Monopolio(TipoMonopolio tipo, ArrayList<Inmueble> calles) {
        this.calles = new HashSet<>(calles);
        this.tipo = tipo;
    }

    public TipoMonopolio getTipo() {
        return tipo;
    }

    public HashSet<Inmueble> getCalles(){
        return calles;
    }

    /* Metodos sobre la instancia */


    /**
     * Añade un inmueble a un Monopoloo/grupo de color. Se usa en el constructor de Inmueble para añadirlo directamente.
     * @param solarEdificable Inmueble que se desea añadir
     */
    public void insertarInmueble(Inmueble solarEdificable) {
        if (solarEdificable == null) {
            Mensajes.error("No se puede insertar el inmueble en el monopolio. solarEdificable == null");
            return;
        }
        calles.add(solarEdificable);
    }

    /**
     * Comprueba si un monopolio esta completo. Prueba ssi todas las calles del grupo son del mismo propietario.
     * Asume que la clase Jugador tiene un Equals bien implementado.
     * @return Devuelve true si el Monopolio esta completo; false en caso contrario.
     */
    public boolean esCompleto() {
//        TODO Cuidado con la funcion hash del jugador
        HashSet<Jugador> propietariosCalles = new HashSet<>();
        for (Inmueble calle : calles) {
            propietariosCalles.add(calle.getPropietario());
        }
        return propietariosCalles.size() == 1;
    }


    /**
     * Determina que cantidad de las propiedades del monopolio pertenece a un jugador dado. Util para las estaciones
     * @param player Jugador que se quiere consultar
     * @return cantidad de propiedades que posee
     */
    public int cantidadPropiedades(Jugador player) {
        int total = 0;
        for (Inmueble calle : calles) {
            if (calle.getPropietario().equals(player)) {
                total++;
            }
        }
        return total;
    }

}
