package monopooly.player.tiposAvatar;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.Visitante;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Posiciones;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionAccionInvalida;
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


    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        int tirada = this.getJugador().getDados().tirada();
        boolean avanzar = tirada > 4;

        this.moverAvatar(avanzar ? 5 : -1);
        for (int i = avanzar ? 6 : 2; i <= tirada; i++)
            if (i % 2 != 0) this.moverAvatar(avanzar ? 2 : -2);
            else if (i == tirada) this.moverAvatar(avanzar ? 1 : -1);
    }

    @Override
    public void intentarComprar(Casilla casilla) throws ExcepcionMonopooly {
        if (this.isNitroso()) {
            if (!Tablero.getPrompt().pisoCasilla(casilla)) {
                throw new ExcepcionAccionInvalida(
                        "No pasaste por la casilla '" + casilla.getNombre() + "'\n" +
                                "este turno."
                );
            }
            return;
        }

        super.intentarComprar(casilla);
    }




    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Pelota"+
                super.toString()
                +"\n}");
    }
}
