package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;

public class Trato implements Expresion {
    private String[] comandoIntroducido;

    public Trato(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {

    }
}
