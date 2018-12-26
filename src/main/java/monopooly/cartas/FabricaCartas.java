package monopooly.cartas;

import monopooly.cartas.cajaComunidad.*;
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
        cartas.add(new CartaCaja2());
        cartas.add(new CartaCaja3());
        cartas.add(new CartaCaja4());
        cartas.add(new CartaCaja5());
        cartas.add(new CartaCaja8());
        return cartas;
    }
}
