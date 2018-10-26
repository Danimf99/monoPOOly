package testClases.entradaSalida;

import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.Monopolio;
import monopooly.colocacion.calles.TipoInmueble;
import monopooly.colocacion.calles.TipoMonopolio;
import org.junit.Before;
import org.junit.Test;

public class representacionTest {
    private Inmueble solarEjemplo;

    @Before
    public void setUp() {
        Monopolio monopolioEjemplo = new Monopolio(TipoMonopolio.rojo);
        solarEjemplo = new Inmueble("Calle Chunga", 500, TipoInmueble.solarEdificable, monopolioEjemplo);
    }

    @Test
    public void probarCasillaUnica() {
        System.out.println(solarEjemplo);
    }
}
