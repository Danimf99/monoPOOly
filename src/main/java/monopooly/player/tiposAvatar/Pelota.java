package monopooly.player.tiposAvatar;

import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;
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

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        Juego.consola.info(
                "El movimiento avanzado no esta disponible en estos momentos.\n" +
                        "Usando el movimiento básico.",
                "Movimiento avanzado no disponible"
        );
        super.moverBasico();
    }


    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Pelota"+
                super.toString()
                +"\n}");
    }
}
