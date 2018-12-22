import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.Visitante;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;
import org.junit.Before;
import org.junit.Test;

public class VisitanteTest {
    private static Casilla casilla1;

    @Before
    public void setUp() {
        casilla1 = Tablero.getTablero().getCasilla("Lugo");
    }

    @Test
    public void caerSolar() {
        casilla1.visitar(new Visitante());
        Juego.consola.imprimir("" + ((Propiedad) casilla1).calcularAlquiler(new Visitante()));
    }
}
