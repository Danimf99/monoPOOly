package monopooly.player;

import monopooly.colocacion.Posicion;

public class MementoAvatar {
    private Posicion posicion;
    private MementoAvatar mementoPrevio;

    public MementoAvatar(Avatar avatar, MementoAvatar mementoPrevio) {
        this.posicion = avatar.getPosicion();
        this.mementoPrevio = mementoPrevio;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public MementoAvatar getMementoPrevio() {
        return mementoPrevio;
    }

    public void setMementoPrevio(MementoAvatar mementoPrevio) {
        this.mementoPrevio = mementoPrevio;
    }
}
