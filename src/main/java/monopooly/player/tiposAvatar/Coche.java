package monopooly.player.tiposAvatar;

import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Coche extends Avatar {

    public Coche(Jugador jugador){
        super(jugador);
    }

    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    @Override
    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Coche"+
                super.toString()
                +"\n}");
    }
}
