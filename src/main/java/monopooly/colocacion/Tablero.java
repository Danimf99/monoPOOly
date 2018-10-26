package monopooly.colocacion;

import monopooly.colocacion.calles.Casilla;
import monopooly.colocacion.calles.Inmueble;
import monopooly.entradaSalida.Mensajes;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    private HashMap<Posicion, Casilla> casillas;
    private HashMap<String, Inmueble> calles;
    private HashMap<String, Jugador> jugadores;


    /* Constructores */

    public Tablero(ArrayList<Jugador> jugadores) {
        if (jugadores == null) {
            Mensajes.error("Lista de jugadores NULL");
            return;
        }
        this.casillas = new HashMap<>();
        this.calles = new HashMap<>();
        this.jugadores = new HashMap<>();
        for (Jugador player: jugadores) {
            //this.jugadores.put(player.)
        }

//        TODO Implementar constructor del tablero
    }


    /* Getters && Setters */

    /**
     * Getter de casillas. Su objetivo es dar info a las clases de pintado ASCII
     * @return HashMap de posicion->casilla
     */
    public HashMap<Posicion, Casilla> getCasillas() {
//        TODO comprobaciones de errores en el getter de casillas
        return casillas;
    }


    /* Metodos sobre la instancia */

    /**
     * Dada una posicion el tablero devuelve su casilla correspondiente.
     * @param pos Posicion que se precisa
     * @return Casilla correspondiente a esa posicion
     */
    public Casilla getCasilla(Posicion pos) {
//        TODO Comprobacion de errores en getCasilla
        return casillas.get(pos);
    }


    /**
     * Dado el nombre de una calle se devuelve informacion correspondiente
     * @param nombre Nombre de la calle
     * @return instancia del Inmueble correspondiente
     */
    public Inmueble getCalle(String nombre) {
//        TODO Comprobacion de errores en getCalle
        return calles.get(nombre);
    }


    /**
     * Dado el nombre de un jugador se devuelve informacion correspondiente
     * @param nombre Nombre del jugador
     * @return instancia del Jugador correspondiente
     */
    public Jugador getJugador(String nombre) {
//        TODO Comprobacion de errores en getJugador
        return jugadores.get(nombre);
    }



}
