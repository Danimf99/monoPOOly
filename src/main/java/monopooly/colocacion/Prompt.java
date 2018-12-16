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

    /**
     * En caso de no estar uno seguro de si la instancia actual corresponde la
     * de la clase tablero, puede usarse este método que copia todos los
     * atributos.
     */
    void update() {
        Prompt actual = Tablero.getPrompt();
        this.jugador = actual.jugador;
        this.modDinero = actual.modDinero;
        this.motivoPago = actual.motivoPago;
        this.help = actual.help;
        this.compro = actual.compro;
        this.posicionesTurno = actual.posicionesTurno;
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
    }

    public int getModDinero() {
        return modDinero;
    }
}
