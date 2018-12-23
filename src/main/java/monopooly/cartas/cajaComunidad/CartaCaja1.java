package monopooly.cartas.cajaComunidad;

import monopooly.cartas.Carta;
import monopooly.configuracion.Precios;

public class CartaCaja1 implements Carta {
    private static final int DINERO = 150;
    private static final String MENSAJE = ""
            + "Vas a un balneario de 5 estrellas \n"
            + "paga " + DINERO + " " + Precios.MONEDA
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
        return DINERO;
    }
}
