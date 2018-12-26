package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class VerTratos implements Expresion {
    private String[] comandoIntroducido;

    public VerTratos(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length!=1){
            throw new ExcepcionArgumentosIncorrectos("Numero de argumentos incorrecto");
        }
        interprete.verTratos(Tablero.getPrompt().getJugador());
    }
}
