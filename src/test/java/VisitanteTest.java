import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.Visitante;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import org.junit.Before;
import org.junit.Ignore;

public class VisitanteTest {
    private static Casilla casilla1;

    @Before
    public void setUp() throws ExcepcionParametrosInvalidos {
        casilla1 = Tablero.getTablero().getCasilla("Lugo");
    }

    @Ignore
    public void caerSolar() throws ExcepcionMonopooly {
        casilla1.visitar(new Visitante());
        Juego.consola.imprimir("" + ((Propiedad) casilla1).calcularAlquiler(new Visitante()));
    }
}
