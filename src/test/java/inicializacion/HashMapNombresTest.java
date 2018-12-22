package inicializacion;


import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Nombres;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class HashMapNombresTest {
    private static Tablero tablero = Tablero.getTablero();

    @Parameters(name = "{index}: Casilla -> {0}")
    public static Object[] nombres() {
        return Nombres.CALLES;
    }

    @Parameter
    public String nombreCalle;


    @Test
    public void testME() {
        assertNotNull(tablero.getCasilla(nombreCalle));
    }
}
