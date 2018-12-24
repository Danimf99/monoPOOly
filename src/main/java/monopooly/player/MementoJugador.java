package monopooly.player;

import monopooly.sucesos.Suceso;

import java.util.ArrayList;

public class MementoJugador {
    private ArrayList<Suceso> sucesosTurno;
    private MementoJugador mementoPrevio;

    public MementoJugador(ArrayList<Suceso> sucesosTurno, MementoJugador mementoPrevio) {
        this.sucesosTurno = sucesosTurno;
        this.mementoPrevio = mementoPrevio;
    }

    public ArrayList<Suceso> getSucesosTurno() {
        return sucesosTurno;
    }

    public void setSucesosTurno(ArrayList<Suceso> sucesosTurno) {
        this.sucesosTurno = sucesosTurno;
    }

    public MementoJugador getMementoPrevio() {
        return mementoPrevio;
    }

    public void setMementoPrevio(MementoJugador mementoPrevio) {
        this.mementoPrevio = mementoPrevio;
    }
}
