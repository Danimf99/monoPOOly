package monopooly.player.tiposAvatar;

import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador){
        super(jugador);
    }


    @Override
    public TIPO getTipo() {
        return TIPO.pelota;
    }


    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Pelota"+
                super.toString()
                +"\n}");
    }
}
