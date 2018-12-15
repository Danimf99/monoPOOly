package monopooly.colocacion;

/**
 * Esta clase permite devolver el tablero para la partida actual.
 *
 * <p>La instanciación del tablero se hace al arrancar el programa. Mas info en:
 * https://es.wikipedia.org/wiki/Singleton
 *
 * @author luastan
 * @author Danimf99
 */
public class Tablero {
  private static Tablero instanciaTablero;

  private Tablero() {

    /* Constructor de tablero */




  }

  // Inicializacion estatica de una instancia del tablero
  static {
    try {
      instanciaTablero = new Tablero();
    } catch (Exception e) {
      throw new RuntimeException("Error inicializando el tablero");
    }
  }

  public static Tablero getInstanciaTablero() {
    return instanciaTablero;
  }
}
