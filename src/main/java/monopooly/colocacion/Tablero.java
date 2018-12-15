package monopooly.colocacion;

import monopooly.Prompt;
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
  private Prompt prompt;
  private ArrayList<Jugador> jugadoresTurno;

  private Tablero() {
    /* Constructor de tablero */
    this.prompt = null;
    this.jugadoresTurno = new ArrayList<>();

    System.out.println("Construccion del tablero");
  }


  // Inicializacion estatica de una instancia del tablero
  static {
    try {
      INSTANCIA_TABLERO = new Tablero();
    } catch (Exception e) {
      throw new RuntimeException("Error inicializando el tablero");
    }
  }


  public static Tablero getTablero() {
    return INSTANCIA_TABLERO;
  }


  public Jugador getJugadorTurno() {
    return this.jugadoresTurno.get(0);
  }


  public void pasarTurno() {
    this.prompt = null; // Resetea la prompt en cada turno =)

    this.jugadoresTurno.add(this.getJugadorTurno());
    this.jugadoresTurno.remove(0);
  }


  public Prompt getPrompt() {
    if (prompt == null) {
      this.prompt = new Prompt(this.getJugadorTurno());
    }

    return this.prompt;
  }
}
