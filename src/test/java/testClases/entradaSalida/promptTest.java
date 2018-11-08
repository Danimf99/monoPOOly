package testClases.entradaSalida;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.entradaSalida.ProcesarComando;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Scanner;

public class promptTest {
    private Inmueble solarEjemplo;
    private static Tablero tableroPrueba;
    private static Jugador monguer;
    private static Prompt promptTest;

    @BeforeClass
    public static void preSetUp() {
        monguer = new Jugador("pepe", TipoAvatar.sombrero);
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

    @Test
    public void pruebaComandoMover() {
        String mensaje = "lanzar dados";

        tableroPrueba.getCasilla(new Posicion(8)).getCalle().setPropietario(new Jugador("Propietario", TipoAvatar.sombrero));
        int cantidadPruebas = 7;
        for (int i = 0; i < cantidadPruebas; i++) {
            promptTest = new Prompt(promptTest.getTablero(), promptTest.getJugador());
            ProcesarComando.lanzarDados(mensaje.split(" "), promptTest);
            System.out.println(promptTest);
        }

    }


    @Test
    public void listarEnVenta() {
        String mensaje = "listar enventa";

        tableroPrueba.getCasilla(new Posicion(8)).getCalle().setPropietario(new Jugador("Propietario", TipoAvatar.sombrero));
        promptTest = new Prompt(promptTest.getTablero(), promptTest.getJugador());
        ProcesarComando.listar(mensaje.split(" "), promptTest);
        System.out.println(promptTest);

    }

    @Test
    public void representacionCarcel() {
        promptTest.getJugador().setEstarEnCarcel(true);
        System.out.println(promptTest);
        String mensaje = "lanzar dados";
        ProcesarComando.lanzarDados(mensaje.split(" "), promptTest);
    }
}
