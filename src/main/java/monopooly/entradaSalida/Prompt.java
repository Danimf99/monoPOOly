package monopooly.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.player.Dados;
import monopooly.player.Jugador;

public class Prompt {
    private Dados dadosInicio;
    private Tablero tablero;
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;

    public Prompt(Tablero tablero, Jugador jugador) {
        this.tablero = tablero;
        this.jugador = jugador;
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.dadosInicio = new Dados();
    }
}
