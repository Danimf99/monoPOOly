

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.parsers.Comprar;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.HashSet;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CompraPropiedadesTest {
    private static Tablero tablero = Tablero.getTablero();
    private static Jugador jugador;

    @BeforeClass
    public static void setUpClass() throws ExcepcionMonopooly {
        jugador = new Jugador("Amancio Ortega", Avatar.TIPO.esfinge);
        tablero.meterJugador(jugador);
    }

    @After
    public void setUp() {
        jugador.setDinero(Precios.DINERO_INICIAL);
        jugador.setPropiedades(new HashSet<>());
        Casilla casilla = tablero.getCasilla(posicion);
        ((Propiedad) casilla).setPropietario(Tablero.BANCA);
        Tablero.getPrompt().setCompro(false);
    }

    @Parameters(name = "Comprando {0}")
    public static Object[] posiciones() {
        return tablero.getGrupos().stream()
                .flatMap(grupo -> grupo.getPropiedades().stream())
                .map(propiedad -> tablero.localizarCasilla(propiedad))
                .toArray();
    }

    @Parameterized.Parameter
    public Posicion posicion;

    @Before
    public void setUpPrevio() {
        jugador = tablero.getJugadorTurno();
    }

    @Test
    public void testDineroCompraValida() throws ExcepcionMonopooly {
        Propiedad propiedad = (Propiedad) tablero.getCasilla(posicion);
        assertEquals(Tablero.BANCA, propiedad.getPropietario());
        propiedad.comprar(jugador);

        assertEquals(  // Comprobar la reduccion de pasta
                Precios.DINERO_INICIAL - propiedad.getPrecio(),
                jugador.getDinero()
        );
    }


    @Test
    public void testPropietarioCompraValida() throws ExcepcionMonopooly {
        Propiedad propiedad = (Propiedad) tablero.getCasilla(posicion);
        assertEquals(Tablero.BANCA, propiedad.getPropietario());
        propiedad.comprar(jugador);
        assertEquals(
                propiedad.getPropietario(),
                jugador
        );
    }


    @Test
    public void testHashSetPropiedadesCompraValida() throws ExcepcionMonopooly {
        Propiedad propiedad = (Propiedad) tablero.getCasilla(posicion);
        assertEquals(Tablero.BANCA, propiedad.getPropietario());
        propiedad.comprar(jugador);
        assertTrue(
                jugador.getPropiedades().contains(propiedad)
        );
    }

    @Test
    public void testComandoCompraCasillaValida() throws ExcepcionMonopooly {
        Propiedad propiedad = (Propiedad) tablero.getCasilla(posicion);
        assertEquals(Tablero.BANCA, propiedad.getPropietario());

        jugador.getAvatar().moverAvatar(posicion);

        String comando = "comprar " + propiedad.getNombre();
        new Comprar(comando.split(" ")).interpretar(Partida.interprete);
        assertTrue(
                jugador.getPropiedades().contains(propiedad)
        );
        assertEquals(
                propiedad.getPropietario(),
                jugador
        );

    }
}
