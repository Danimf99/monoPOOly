package testClases.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.Monopolio;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Jugador;
import monopooly.player.tipoAvatar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

public class representacionTest {
    private Inmueble solarEjemplo;
    private static Tablero tableroPrueba;

    @BeforeClass
    public static void preSetUp() {
        Jugador monguer = new Jugador("pepe", tipoAvatar.sombrero);
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(monguer);
        tableroPrueba = new Tablero(listaJugadores);
    }

    @Before
    public void setUp() {
        Monopolio monopolioEjemplo = new Monopolio(TipoMonopolio.rojo);
        solarEjemplo = new Inmueble("Calle Chunga", 500, monopolioEjemplo);
    }

    @Ignore
    public void asciiArt() {
        for (String linea : ReprASCII.ASCII_TITLE_ARRAY) {
            System.out.println(linea + " " + linea.length());
        }
    }

    @Test
    public void imprimeTablero() {
        System.out.println(PintadoASCII.genTablero(tableroPrueba));
    }

    @Test
    public void testPromptBasic() {
        
    }

    @Test
    public void moverJugadorRepresentar() {

    }
}
