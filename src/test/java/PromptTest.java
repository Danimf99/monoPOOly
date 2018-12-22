import monopooly.colocacion.Prompt;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PromptTest {

    @BeforeClass
    public static void setUp() {
        Tablero.getTablero().meterJugador(new Jugador("pepe", "coche"));
        Tablero.getTablero().meterJugador(new Jugador("manolo", "sombrero"));
    }

    @Test
    public void basicPrompt() {
        Juego.consola.imprimir(Tablero.getPrompt().toString());
    }
}
