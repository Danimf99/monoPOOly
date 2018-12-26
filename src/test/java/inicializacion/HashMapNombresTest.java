package inicializacion;


import monopooly.colocacion.Tablero;
import monopooly.configuracion.General;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.util.Arrays;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class HashMapNombresTest {
    private static Tablero tablero = Tablero.getTablero();

    @Parameters(name = "Buscando casilla -> {0}")
    public static Object[] nombres() {
        return Arrays.stream(General.CALLES).distinct().toArray();
    }

    @Parameter
    public String nombreCalle;


    @Test
    public void testGetCasilla() {
        assertNotNull(tablero.getCasilla(nombreCalle));
    }
}
