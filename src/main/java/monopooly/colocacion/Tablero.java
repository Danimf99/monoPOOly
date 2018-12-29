package monopooly.colocacion;

import monopooly.Partida;
import monopooly.cartas.Carta;
import monopooly.cartas.FabricaCartas;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.estadisticas.StatsGlobales;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.AccionCarta;
import monopooly.sucesos.tipoSucesos.PagoBanca;
import monopooly.sucesos.tipoSucesos.PagoImpuesto;
import monopooly.sucesos.tipoSucesos.PasoSalida;

import java.util.ArrayList;
import java.util.Collections;
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
public class Tablero implements Observador {
    private static FabricaCasillas fabricaCasillas = new FabricaCasillas();
    private static StatsGlobales ESTADISTICAS;

    private static Tablero instanciaTablero;
    private static Prompt prompt;
    public static final Jugador BANCA = new Jugador();

    private int bote;
    private ArrayList<Jugador> jugadoresTurno;
    private ArrayList<Carta> cartasSuerte;
    private ArrayList<Carta> cartasCajaComunidad;
    private ArrayList<Casilla> casillas;
    private ArrayList<Grupo> grupos;

    private HashMap<Posicion, Casilla> casillasPosicion;
    private HashMap<String, Casilla> casillasNombre;
    private HashMap<String, Jugador> jugadores;

    private Subject subject;


    private Tablero() {
        /* Constructor de tablero */
        prompt = null;
        this.jugadoresTurno = new ArrayList<>();
        this.casillas = new ArrayList<>(fabricaCasillas.genCasillas());
        this.grupos = new ArrayList<>(fabricaCasillas.getGrupos());
        this.cartasSuerte = new ArrayList<>(FabricaCartas.cartasSuerte());
        this.cartasCajaComunidad = new ArrayList<>(FabricaCartas.cartasCaja());

        this.bote = 0;

        this.casillasNombre = new HashMap<>();
        this.jugadores = new HashMap<>();
        this.casillasPosicion = new HashMap<>();

        casillas.forEach(casilla -> {
            casillasPosicion.put(new Posicion(casillas.indexOf(casilla)), casilla);
            casillasNombre.put(casilla.getNombre().toLowerCase(), casilla);
        });

        this.subject = Partida.interprete;
        this.subject.registrar(this);
    }

    public Grupo getTipoGrupo(TipoMonopolio tipo){
        for(Grupo g:grupos){
            if(g.getTipo().equals(tipo)){
                return g;
            }
        }
        return null;
    }

    // Inicializacion estatica de una instancia del tablero para el control de errores
    static {
        try {
            instanciaTablero = new Tablero();
            ESTADISTICAS = new StatsGlobales(Partida.interprete);
        } catch (Exception e) {
            throw new RuntimeException("No se pudo inicializar el tablero");
        }
    }

    public void eliminarJugador(Jugador jugador){
        instanciaTablero.getCasilla(instanciaTablero.getJugadorTurno().getAvatar().getPosicion()).quitarJugador(jugador);
        this.jugadoresTurno.remove(jugador);
        this.jugadores.remove(jugador.getNombre());
        prompt.reset();
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
        this.casillasPosicion.get(jugador.getAvatar().getPosicion()).meterJugador(jugador);
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

    public Jugador getJugador(String nombre) throws ExcepcionArgumentosIncorrectos {
        Jugador jugador = jugadores.get(nombre.toLowerCase());
        if (jugador == null) {
            throw new ExcepcionArgumentosIncorrectos("El jugador '" + nombre + "'\n no existe.");
        }
        return jugador;
    }
    /**
     * Pasa turno. Actualiza las posiciones en el array de jugadores y resetea la prompt
     *
     */
    public void pasarTurno() throws ExcepcionMonopooly {
        this.getJugadorTurno().pasarTurno();
        this.jugadoresTurno.add(this.getJugadorTurno());
        this.jugadoresTurno.remove(0);
        this.subject.registrar(this.jugadoresTurno.get(0).getAvatar());
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
     * Cambia a un jugador de posicion
     * @param jugador Jugador que se desea mover
     * @param posicion Posicion que tendra
     */
    public void recolocar(Jugador jugador, Posicion posicion) throws ExcepcionMonopooly {
        this.casillasPosicion.get(jugador.getAvatar().getPosicion()).quitarJugador(jugador);
        Casilla siguiente = this.casillasPosicion.get(posicion);
        siguiente.meterJugador(jugador);
        jugador.getAvatar().getPosicion().setX(posicion.getX());
        siguiente.visitar(new Visitante(jugador));
    }

    /**
     * Cambia a un jugador de posicion
     * @param avatar Jugador que se desea mover
     * @param posicion Posicion que tendra
     */
    public void recolocar(Avatar avatar, Posicion posicion) throws ExcepcionMonopooly {
        this.casillasPosicion.get(avatar.getPosicion()).quitarJugador(avatar);
        Casilla siguienteCasilla = this.casillasPosicion.get(posicion);
        siguienteCasilla.meterJugador(avatar);
        avatar.getPosicion().setX(posicion.getX());
        siguienteCasilla.visitar(new Visitante(avatar.getJugador()));
    }

    /**
     * Cambia a un jugador de posicion sin generar sucesos ni visitar la casilla
     * @param avatar avatar que se mueve
     * @param posicion Posicion nueva
     */
    public void recolocacionSimple(Avatar avatar, Posicion posicion) {
        this.casillasPosicion.get(avatar.getPosicion()).quitarJugador(avatar);
        Casilla siguienteCasilla = this.casillasPosicion.get(posicion);
        siguienteCasilla.meterJugador(avatar);
        avatar.setPosicion(posicion);
    }

    public void cartaComunidad(int eleccion) throws ExcepcionMonopooly {
        Collections.shuffle(this.cartasCajaComunidad);
        Carta carta = cartasCajaComunidad.get(eleccion);
        Juego.consola.info(carta.getMensaje(), "Carta de Caja de comunidad");
        carta.accion();
    }

    public void cartaSuerte(int eleccion) throws ExcepcionMonopooly {
        Collections.shuffle(this.cartasSuerte);
        Carta carta = cartasSuerte.get(eleccion);
        Juego.consola.info(carta.getMensaje(), "Carta de Suerte");
        carta.accion();
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

    public Casilla getCasilla(String nombre) throws ExcepcionParametrosInvalidos {
        Casilla casilla = this.casillasNombre.get(nombre.toLowerCase());
        if (casilla == null) {
            throw new ExcepcionParametrosInvalidos("La casilla '" + nombre + "'\n" +
                    "no existe.");
        }
        return casilla;
    }

    public Propiedad getPropiedad(String nombre) throws ExcepcionMonopooly {
        Casilla casilla = getCasilla(nombre);
        if (!(casilla instanceof Propiedad)) {
            throw new ExcepcionParametrosInvalidos("La casilla '" + nombre + "'\n" +
                    "no es una propiedad.");
        }
        return (Propiedad) casilla;
    }

    public int getBote() {
        return bote;
    }

    public void setBote(int bote) {
        this.bote = bote;
    }

    public ArrayList<Grupo> getGrupos() {
        return new ArrayList<>(grupos);
    }

    public void setGrupos(ArrayList<Grupo> grupos) {
        this.grupos = grupos;
    }

    public ArrayList<Casilla> getCasillas() {
        return new ArrayList<>(casillas);
    }

    public void setCasillas(ArrayList<Casilla> casillas) {
        this.casillas = casillas;
    }

    public Posicion localizarCasilla(Casilla casilla) {
        return new Posicion(casillas.indexOf(casilla));
    }

    public static StatsGlobales getStatsGlobales() {
        return ESTADISTICAS;
    }

    @Override
    public String toString() {
        return PintadoAscii.genTablero();
    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }

        if (suceso instanceof PagoImpuesto) {
            int pago = ((PagoImpuesto) suceso).getCantidad();
            this.bote += suceso.getDeshacer() ? - pago : + pago;
        }

        if (suceso instanceof PagoBanca) {
            int pago = ((PagoBanca) suceso).getCantidad();
            if (suceso.getDeshacer()) {
                if (pago > 0) {
                    this.bote -= pago;
                }
            } else {
                if (pago < 0) {
                    this.bote += -pago;
                }
            }
        }

        if (suceso instanceof PasoSalida) {
            if (suceso.getJugadorOriginador().getEstadisticas().getVueltas() % 4 != 0) {
                return;
            }
            Jugador jugador = jugadoresTurno.get(0);
            for (Jugador j : this.jugadoresTurno) {
                if (j.getEstadisticas().getVueltas() < jugador.getEstadisticas().getVueltas()) {
                    jugador = j;
                }
            }
            if (suceso.getJugadorOriginador().equals(jugador)) {
                this.grupos.forEach(Grupo::incrementarPrecio);
            }
        }

    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
