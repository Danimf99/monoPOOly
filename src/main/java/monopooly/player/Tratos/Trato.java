package monopooly.player.Tratos;

import monopooly.player.Jugador;

import java.util.Iterator;
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

    public String toString(){
        return "Trato: "+this.id+"\n"+
                "Jugador que propone el trato: "+originador.getNombre();
    }
}
