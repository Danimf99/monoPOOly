package monopooly.cartas;

import monopooly.cartas.cajaComunidad.CartaCaja1;
import monopooly.cartas.suerte.CartaSuerte1;

import java.util.ArrayList;

public abstract class FabricaCartas {

    public static ArrayList<Carta> cartasSuerte() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(new CartaSuerte1());
        return cartas;
    }

    public static ArrayList<Carta> cartasCaja() {
        ArrayList<Carta> cartas = new ArrayList<>();
        cartas.add(new CartaCaja1());
        return cartas;
    }
}
