package testClases.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;
import monopooly.player.tipoAvatar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class promptTest {
    private Inmueble solarEjemplo;
    private static Tablero tableroPrueba;
    private static Jugador monguer;
    private static Prompt promptTest;

    @BeforeClass
    public static void preSetUp() {
        monguer = new Jugador("pepe", tipoAvatar.sombrero);
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(monguer);
        tableroPrueba = new Tablero(listaJugadores);
    }

    @Before
    public void setUp() {
        promptTest = new Prompt(tableroPrueba, monguer);
    }


    @Test
    public void testPromptBasic() {
        System.out.println(promptTest);
    }

    @Test
    public void testPromptHelp() {
        promptTest.setHelp(true);
        System.out.println(promptTest);
    }

    @Test
    public void testPromptSuma() {
        promptTest.setModificacionPasta(500, "Alquiler");
        System.out.println(promptTest);

    }

    @Test
    public void testPromptResta() {
        promptTest.setModificacionPasta(-500, "Alquiler");
        System.out.println(promptTest);
    }


    @Test
    public void testPromptCarcelDados() {
        promptTest.setModificacionPasta(-500, "Alquiler");
        promptTest.getJugador().moverJugador(tableroPrueba);
        System.out.println(promptTest);
    }
}
