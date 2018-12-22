import monopooly.colocacion.Prompt;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Dados;
import monopooly.player.Jugador;
import org.junit.*;

public class PromptTest {
    private static Prompt prompt;
    private static Jugador testPlayer;
    private static Jugador testPlayer2;

    @BeforeClass
    public static void setUp() {
        testPlayer = new Jugador("Saul", "pelota");
        testPlayer2 = new Jugador("Dani", "sombrero");
        Tablero.getTablero().meterJugador(testPlayer);
        Tablero.getTablero().meterJugador(testPlayer2);
        //Tablero.getTablero().meterJugador(new Jugador("manolo", "sombrero"));
        testPlayer.setDados(new Dados(1, 2));
        testPlayer2.setDados(new Dados(6, 5));
        prompt = Tablero.getPrompt();
    }

    @After
    public void imprimir() {
        Juego.consola.imprimir(prompt.toString());
        testPlayer.setEstarEnCarcel(false);
        testPlayer.setDados(new Dados(1, 3));
        testPlayer.getAvatar().setNitroso(false);
        testPlayer2.setEstarEnCarcel(false);
        testPlayer2.setDados(new Dados(6, 5));
        testPlayer2.getAvatar().setNitroso(false);
        prompt.setHelp(false);
    }

    @Test
    public void testBasicPrompt() {
        //nothing
    }

    @Test
    public void testMenosDineroPrompt() {
        prompt.setModDinero(-32, "Impuesto trangalleiro");
    }

    @Test
    public void testMasDineroPrompt() {
        prompt.setModDinero(+42, "devolusion de movidas");
    }

    @Test
    public void testEstarEnCarcel() {
        prompt.getJugador().setEstarEnCarcel(true);
    }

    @Test
    public void testMovimientoEspecial() {
        prompt.getJugador().getAvatar().setNitroso(true);
    }

    @Test
    public void testDadosDobles() {
        prompt.getJugador().setDados(new Dados(4, 4));
    }

    @Test
    public void testHelp() {
        prompt.setHelp(true);
    }

    @Test
    public void testPasoTurno() {
        Tablero.getTablero().pasarTurno();
    }
}
