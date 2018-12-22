import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class RepresentacionTest {
    private static Tablero tablero = Tablero.getTablero();
    private static Object target;

    private static Casilla getCasillaAt(int posicion) {
        return tablero.getCasilla(new Posicion(posicion));
    }

    @After
    public void imprimirTarget() {
        assertNotNull(target);
        assertNotNull(target.toString());
    }

    @Test
    public void testEstacion() {
        target = getCasillaAt(5);
    }

    @Test
    public void testSalida() {
        target = getCasillaAt(0);
    }

    @Test
    public void testCarcel() {
        target = getCasillaAt(10);
    }

    @Test
    public void testParking() {
        target = getCasillaAt(20);
    }

    @Test
    public void testIrCarcel() {
        target = getCasillaAt(30);
    }

    @Test
    public void testSolar() {
        target = getCasillaAt(18);
    }

    @Test
    public void testImpuesto() {
        target = getCasillaAt(4);
    }

    @Test
    public void testServicio() {
        target = getCasillaAt(12);
    }

    @Test
    public void testTablero() {
        target = Tablero.getTablero();
    }
}
