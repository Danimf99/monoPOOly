package monopooly.player.Tratos;

import monopooly.player.Jugador;

import java.util.Iterator;
import java.util.Objects;
import java.util.stream.Stream;

public abstract class Trato {

    private Jugador originador;
    private Jugador receptor;
    private String id;

    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();

    public Trato(Jugador originador,Jugador receptor){
        this.originador=originador;
        this.receptor=receptor;
        this.id="trato"+generadorId.next();
    }

    public Jugador getOriginador() {
        return originador;
    }

    public Jugador getReceptor() {
        return receptor;
    }

    public String getId() {
        return id;
    }

    public String toString(){
        return "Trato: "+this.id+"\n"+
                "Jugador que propone el trato: "+originador.getNombre();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trato trato = (Trato) o;
        return Objects.equals(id, trato.getId());

    }
}
