package monopooly.cartas.suerte;

import monopooly.cartas.Carta;
import monopooly.configuracion.Precios;

public class CartaSuerte1 implements Carta {
    private static final int DINERO = Precios.SALIDA;
    private static final String MENSAJE = ""
            + "Ve a Invierno a coger un catarro. Si pasas por la casilla de salida \n"
            + "cobra " + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    public void aplicar() {
        // Accion de la carta 1
    }

    @Override
    public void deshacer() {
        // Deshacer
    }

    @Override
    public int cantidadBanca() {
        return 0;
    }
}
