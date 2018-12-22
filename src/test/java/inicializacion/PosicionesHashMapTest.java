package inicializacion;


import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Posiciones;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class PosicionesHashMapTest {
    private static Tablero tablero = Tablero.getTablero();

    @Parameters(name = "{0}")
    public static Object[] posiciones() {
        return IntStream.range(0, Posiciones.TOTAL).mapToObj(Posicion::new).toArray(Posicion[]::new);
    }

    @Parameter
    public Posicion posicion;


    @Test
    public void testME() {
        assertNotNull(tablero.getCasilla(posicion));
    }
}
