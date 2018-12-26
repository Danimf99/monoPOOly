package monopooly.sucesos.tipoSucesos;

import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class PasoSalida extends Suceso {

    public PasoSalida(Jugador jugadorOriginador) {
        super(jugadorOriginador);
    }

    @Override
    public String toString() {
        return "Paso por la salida {\n" +
                "   Cantidad -> " + Precios.SALIDA + " " + Precios.MONEDA + "\n" +
                "}";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().quitarDinero(Precios.SALIDA);
    }
}
