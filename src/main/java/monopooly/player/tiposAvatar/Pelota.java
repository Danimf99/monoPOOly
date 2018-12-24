package monopooly.player.tiposAvatar;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.Visitante;
import monopooly.configuracion.Posiciones;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionAcontecimiento;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador){
        super(jugador);
    }


    @Override
    public TIPO getTipo() {
        return TIPO.pelota;
    }

    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/


    private void avanzar(int tirada) throws ExcepcionMonopooly {
        this.moverAvatar(5);
        for (int i = 6; i <= tirada; i++) {
            if (i % 2 != 0) {
                this.moverAvatar(2);
            } else if (i == tirada) {
                this.moverAvatar(1);
            }
        }
    }

    private void retroceder(int tirada) throws ExcepcionMonopooly {
        this.moverAvatar(-2);
        for (int i = 0; i <= tirada; i++) {
            if (i % 2 != 0) {
                this.moverAvatar(-2);
            } else if (i == tirada) {
                this.moverAvatar(-1);
            }
        }
    }

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        int dados = this.getJugador().getDados().tirada();
        if (dados > 4) {
            avanzar(dados);
        } else {
            retroceder(dados);
        }
    }




    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Pelota"+
                super.toString()
                +"\n}");
    }
}
