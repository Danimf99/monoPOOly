package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;

public class Lanzar implements Expresion{
    private String[] comandoIntroducido;

    public Lanzar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) {

    }
}
