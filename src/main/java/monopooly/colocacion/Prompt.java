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


    void reset() {
        this.jugador = Tablero.getTablero().getJugadorTurno();
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.compro = false;
        this.posicionesTurno = new ArrayList<>();
    }

    protected Prompt() {
        this.reset();
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
    }

    public int getModDinero() {
        return modDinero;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }
}
