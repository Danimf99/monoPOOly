package monopooly;

import monopooly.entradaSalida.Juego;

public class Arranque {
    public static void main(String[] args) {
        Juego.consola.imprimir("We back !");
        new Partida(new Juego()).init();
    }
}
