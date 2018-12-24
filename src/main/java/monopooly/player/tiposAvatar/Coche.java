package monopooly.player.tiposAvatar;

import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Coche extends Avatar {

    public Coche(Jugador jugador){
        super(jugador);
    }

    @Override
    public TIPO getTipo() {
        return TIPO.coche;
    }

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        Juego.consola.info(
                "El movimiento avanzado no esta disponible en estos momentos.\n" +
                        "Usando el movimiento básico.",
                "Movimiento avanzado no disponible"
        );
        this.moverBasico();
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
