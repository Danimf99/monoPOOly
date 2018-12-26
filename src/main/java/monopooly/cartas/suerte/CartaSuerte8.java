package monopooly.cartas.suerte;

import monopooly.Partida;
import monopooly.cartas.Suerte;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.*;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.PagoBanca;

import java.util.HashSet;

public class CartaSuerte8 extends Suerte {
    private static final int DINERO = Precios.SALIDA;
    private static final int DINERO_CASA = 50;
    private static final int DINERO_HOTEL = 100;
    private static final int DINERO_PISCINA = 200;
    private static final int DINERO_PISTA_DEPORTE = 400;
    private static final String MENSAJE = ""
            + "EL impuesto sobre bienes inmuebles afecta a tdas tus propiedades.\n"
            + "Paga " + DINERO_CASA + " " + Precios.MONEDA + " por casa"
            + ""
            + ", " + DINERO_HOTEL + " " + Precios.MONEDA + " por hotel"
            + ""
            + ", " + DINERO_PISCINA + " " + Precios.MONEDA + " por piscina"
            + ""
            + ", " + DINERO_PISTA_DEPORTE + " " + Precios.MONEDA + " por pista de deportes."
            + ""
            + ""
            + "";

    @Override
    public void accion() throws ExcepcionMonopooly {
        Jugador jugador = Tablero.getPrompt().getJugador();
        int pago = 0;
        HashSet<Propiedad> propiedades = new HashSet<>();
        propiedades.addAll(jugador.getPropiedades());
        propiedades.addAll(jugador.getHipotecas());
        for (Propiedad propiedad : propiedades) {
            if (propiedad instanceof Solar) {
                for (Edificio edificio : ((Solar) propiedad).getEdificios()) {
                    if (edificio instanceof Casa) {
                        pago += DINERO_CASA;
                    } else if (edificio instanceof Hotel) {
                        pago += DINERO_HOTEL;
                    } else if (edificio instanceof Piscina) {
                        pago += DINERO_PISCINA;
                    } else if (edificio instanceof PistaDeporte) {
                        pago += DINERO_PISTA_DEPORTE;
                    }
                }
            }
        }
        jugador.quitarDinero(pago);
        Partida.interprete.enviarSuceso(new PagoBanca(jugador, -pago, MENSAJE));

    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        // Se deshace como pago banca
    }


    @Override
    public int modDinero() {
        return 0;
    }
}
