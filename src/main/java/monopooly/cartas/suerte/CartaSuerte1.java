package monopooly.cartas.suerte;

import monopooly.cartas.Suerte;
import monopooly.configuracion.Precios;

public class CartaSuerte1 extends Suerte {
    private static final int DINERO = Precios.SALIDA;
    private static final String MENSAJE = ""
            + "Ve a Invierno a coger un catarro. Si pasas por la casilla de salida \n"
            + "cobra " + DINERO + " " + Precios.MONEDA
            + ""
            + "";

    @Override
    public void accion() {
        // Accion de la carta 1
    }

    @Override
    public String getMensaje() {
        return MENSAJE;
    }

    @Override
    public void deshacer() {
        // Deshacer
    }

    @Override
    public int cantidadBanca() {
        return 0;
    }

    @Override
    public int modDinero() {
        return 0;
    }
}
