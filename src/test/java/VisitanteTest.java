import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.Propiedad;
import monopooly.colocacion.tipoCasillas.Visitante;
import monopooly.colocacion.tipoCasillas.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;
import org.junit.Before;
import org.junit.Test;

public class VisitanteTest {
    private static Solar casilla1;

    @Before
    public void setUp() {
        casilla1 = new Solar();
    }

    @Test
    public void caerSolar() {
        casilla1.visitar(new Visitante());
        Juego.consola.imprimir("" + ((Propiedad) casilla1).calcularAlquiler(new Visitante()));
    }
}
