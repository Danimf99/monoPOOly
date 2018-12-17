package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;

public class Hipotecar implements Expresion {
    private String comandoIntroducido;

    public Hipotecar(String comandoIntroducido) {
        this.comandoIntroducido = comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) {
        // Truco: Con ctrl + i el intellij te mete aqui lo de la interfaz todo
    }
}
