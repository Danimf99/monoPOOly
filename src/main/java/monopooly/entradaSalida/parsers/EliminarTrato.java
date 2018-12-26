package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class EliminarTrato implements Expresion {
    private String[] comandoIntroducido;

    public EliminarTrato(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length!=2){
            throw new ExcepcionArgumentosIncorrectos("Numero de argumentos incorrecto");
        }
        for(Jugador j: Tablero.getTablero().getJugadores()){
            for(Trato t: j.getTratos()) {
                if(t.getId().equals(comandoIntroducido[1])) {
                    interprete.eliminarTrato(j, t);
                    return;
                }
            }
        }
        throw new ExcepcionAccionInvalida("El trato con id "+comandoIntroducido[1]+" no existe");
    }
}
