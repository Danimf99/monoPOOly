

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Posiciones;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runners.Parameterized.Parameter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CompraPropiedadesTest {
    private static Tablero tablero = Tablero.getTablero();

    @Parameters(name = "Comprando {0}")
    public static Posicion[] posiciones() {
        ArrayList<Posicion> posiciones = new ArrayList<>();
        return null;
    }

}
