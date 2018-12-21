package monopooly.player.tiposAvatar;

import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Esfinge extends Avatar {

    public Esfinge(Jugador jugador){
        super(jugador);
    }

    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Esfinge"+
                super.toString()
                +"\n}");
    }
}
