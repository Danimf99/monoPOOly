package monopooly.colocacion;

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

  private Tablero() {

    /* Constructor de tablero */

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

  public static Tablero getInstanciaTablero() {
    return INSTANCIA_TABLERO;
  }
}
