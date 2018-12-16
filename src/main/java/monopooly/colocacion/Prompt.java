package monopooly.colocacion;

import monopooly.player.Jugador;

import java.util.ArrayList;

public class Prompt {
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;
    private boolean compro;
    private ArrayList<Posicion> posicionesTurno;

    protected Prompt(Jugador jugador) {
        this.jugador = jugador;
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.compro = false;
        this.posicionesTurno = new ArrayList<>();
    }

    public Prompt() {
        /*  Prompt vacio para inicializar la partida  */
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
    }

    public int getModDinero() {
        return modDinero;
    }
}
