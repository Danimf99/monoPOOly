package monopooly.colocacion;

import monopooly.player.Jugador;

import java.util.ArrayList;

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
  private static Tablero INSTANCIA_TABLERO;
  private static Prompt PROMPT;
  private ArrayList<Jugador> jugadoresTurno;

  private Tablero() {
    /* Constructor de tablero */
    PROMPT = null;
    this.jugadoresTurno = new ArrayList<>();
  }


  // Inicializacion estatica de una instancia del tablero para el control de errores
  static {
    try {
      INSTANCIA_TABLERO = new Tablero();
    } catch (Exception e) {
      throw new RuntimeException("Error inicializando el tablero");
    }
  }

  /**
   * Añade un jugador a la lista de jugadores de la partida. Si el jugador ya
   * se añadió no se realiza ninguna acción.
   * @param jugador instancia del nuevo jugador añadido
   */
  public void meterJugador(Jugador jugador) {
    for (Jugador j : this.jugadoresTurno) {
      if (j.equals(jugador)) {
        return;
      }
    }
    this.jugadoresTurno.add(jugador);
  }

  /**
   * Jugador que actualmente tiene el turno
   * @return Instancia del jugador que posee el turno en este momento
   */
  public Jugador getJugadorTurno() {
    return this.jugadoresTurno.get(0);
  }


  /**
   * Pasa turno. Actualiza las posiciones en el array de jugadores y resetea la prompt
   */
  public void pasarTurno() {
    PROMPT = null; // Resetea la prompt en cada turno =)

    this.jugadoresTurno.add(this.getJugadorTurno());
    this.jugadoresTurno.remove(0);
  }


  /**
   * Permite obtener la instancia actual del tablero
   * @return Instancia del tablero para la partida actual
   */
  public static Tablero getTablero() {
    return INSTANCIA_TABLERO;
  }

  /**
   * Devuelve la instancia prompt correspondiente al turno actual
   * @return instancia actual del prompt
   */
  public static Prompt getPrompt() {
    if (PROMPT == null) {
      PROMPT = new Prompt(getTablero().getJugadorTurno());
    }
    return PROMPT;
  }
}
