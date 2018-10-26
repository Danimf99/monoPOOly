package testClases.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.Monopolio;
import monopooly.colocacion.calles.TipoInmueble;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Dados;
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
        Jugador monguer = new Jugador("pepe", tipoAvatar.sombrero, new Dados());
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(monguer);
        tableroPrueba = new Tablero(listaJugadores);
    }

    @Before
    public void setUp() {
        Monopolio monopolioEjemplo = new Monopolio(TipoMonopolio.rojo);
        solarEjemplo = new Inmueble("Calle Chunga", 500, TipoInmueble.solarEdificable, monopolioEjemplo);
    }

    @Ignore
    public void probarCasillaUnica() {
        System.out.println(solarEjemplo);
    }

    @Test
    public void imprimeTablero() {
        System.out.println(PintadoASCII.genTablero(tableroPrueba));
    }
}
