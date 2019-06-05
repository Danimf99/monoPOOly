package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class AceptarTrato implements Expresion {
    private String[] comandoIntroducido;

    public AceptarTrato(String[] comandoIntroducido) {
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
                    interprete.aceptarTrato(t);
                    return;
                }
            }
        }
        throw new ExcepcionAccionInvalida("No existe ese trato en tu lista de tratos");
    }
}
