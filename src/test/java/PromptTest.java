import monopooly.colocacion.Prompt;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;
import org.junit.*;

public class PromptTest {
    private static Prompt prompt;

    @BeforeClass
    public static void setUp() {
        Tablero.getTablero().meterJugador(new Jugador("pepe", "coche"));
        Tablero.getTablero().meterJugador(new Jugador("manolo", "sombrero"));
        prompt = Tablero.getPrompt();
    }

    @After
    public void imprimir() {
        Juego.consola.imprimir(prompt.toString());
    }

    @Test
    public void basicPrompt() {
        //nothing
    }

    @Test
    public void dineroPrompt() {
        prompt.setModDinero(-32);
        prompt.setMotivoPago("Amarillo");
    }
}
