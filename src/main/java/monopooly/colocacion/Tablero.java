package monopooly.colocacion;

import monopooly.cartas.Carta;
import monopooly.cartas.FabricaCartas;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Esta clase permite devolver el tablero para la partida actual.
 *
 * <p>La instanciación del tablero se hace automaticamente. Mas info en:
 * https://es.wikipedia.org/wiki/Singleton
 *
 * @author luastan
 * @author Danimf99
 */
public class Tablero {
    private static FabricaCasillas fabricaCasillas = new FabricaCasillas();

    private static Tablero instanciaTablero;
    private static Prompt prompt;
    public static final Jugador BANCA = new Jugador();

    private int bote;
    private ArrayList<Jugador> jugadoresTurno;
    private ArrayList<Carta> cartasSuerte;
    private ArrayList<Carta> cartasCajaComunidad;
    private ArrayList<Casilla> casillas;

    private HashMap<Posicion, Casilla> casillasPosicion;
    private HashMap<String, Casilla> casillasNombre;
    private HashMap<String, Jugador> jugadores;


    private Tablero() {
        /* Constructor de tablero */
        prompt = null;
        this.jugadoresTurno = new ArrayList<>();
        this.casillas = fabricaCasillas.genCasillas();
        this.cartasSuerte = FabricaCartas.cartasSuerte();
        this.cartasCajaComunidad = FabricaCartas.cartasCaja();

        this.bote = 0;

        this.casillasNombre = new HashMap<>();
        this.jugadores = new HashMap<>();
        this.casillasPosicion = new HashMap<>();

        casillas.forEach(casilla -> {
            casillasPosicion.put(new Posicion(casillas.indexOf(casilla)), casilla);
            casillasNombre.put(casilla.getNombre().toLowerCase(), casilla);
        });
    }


    // Inicializacion estatica de una instancia del tablero para el control de errores
    static {
        try {
            instanciaTablero = new Tablero();
        } catch (Exception e) {
            throw new RuntimeException("Error inicializando el tablero");
        }
    }

    /**
     * Añade un jugador a la lista de jugadores de la partida. Si el jugador ya
     * se añadió no se realiza ninguna acción.
     *
     * @param jugador instancia del nuevo jugador añadido
     */
    public void meterJugador(Jugador jugador) {
        if (jugadoresTurno.stream().anyMatch(j -> j.equals(jugador))) {
            // El jugador ya existe
            return;
        }
        this.jugadoresTurno.add(jugador);
        this.jugadores.put(jugador.getNombre().toLowerCase(), jugador);
    }


    /**
     *
     * @param jugadores Jugadores que se quieren añadir a la partida
     */
    public void meterJugadores(ArrayList<Jugador> jugadores) {
        jugadores.forEach(this::meterJugador);
    }


    /**
     * Jugador que actualmente tiene el turno
     *
     * @return Instancia del jugador que posee el turno en este momento
     */
    public Jugador getJugadorTurno() {
        return this.jugadoresTurno.get(0);
    }


    /**
     * Pasa turno. Actualiza las posiciones en el array de jugadores y resetea la prompt
     *
     */
    public void pasarTurno() {
        this.jugadoresTurno.add(this.getJugadorTurno());
        this.jugadoresTurno.remove(0);
        Tablero.getPrompt().reset();  // Resetea la prompt al pasar turno
    }

    /**
     * Devuelve los jugadores de la partida actual
     *
     * @return Arraylist de los jugadores de la partida. La instancia de este
     * arraylist no es la misma que la guardada dentro del tablero. Es una copia.
     * Las instancias de los jugadores si son las mismas. Para modificar este
     * arraylist deben usarse los metodos meterJugador() y eliminarJugador()
     */
    public ArrayList<Jugador> getJugadores() {
        return new ArrayList<>(this.jugadoresTurno);
    }

    /**
     *
     * @return Numero de jugadores restantes en la partida
     */
    public int jugadoresRestantes() {
        return this.jugadoresTurno.size();
    }

    /**
     * Permite obtener la instancia actual del tablero
     *
     * @return Instancia del tablero para la partida actual
     */
    public static Tablero getTablero() {
        return instanciaTablero;
    }

    /**
     * Devuelve la instancia prompt correspondiente al turno actual
     * Hay que controlar que tenga jugadores el tablero
     *
     * @return instancia actual del prompt
     */
    public static Prompt getPrompt() {
        if (prompt == null) { // Necesario la primera vez que se ejecuta
            if (instanciaTablero.jugadoresRestantes() < 1) {
                // Hay que meter una excepcion aqui
                Juego.consola.error("No quedan jugadores en la partida actual",
                        "Error inicializando la prompt");
            }
            prompt = new Prompt();
        }
        return prompt;
    }


    /**
     * Devuelve la posicion en la que se encuentra una casilla
     *
     * @param casilla Casilla que se busca
     * @return Posicion de la casilla
     */
    public Posicion posicionCasilla(Casilla casilla) {
        return new Posicion(casillas.indexOf(casilla));
    }


    public Casilla getCasilla(Posicion posicion) {
        return this.casillasPosicion.get(posicion);
    }

    public Casilla getCasilla(String nombre) {
        return this.casillasNombre.get(nombre.toLowerCase());
    }

    public int getBote() {
        return bote;
    }

    public void setBote(int bote) {
        this.bote = bote;
    }

    @Override
    public String toString() {
        return PintadoAscii.genTablero();
    }
}
