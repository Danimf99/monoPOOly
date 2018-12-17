package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;

/**
 * Aqui copias y pegas y luego haces find & replace para andar antes
 */
public class SampleCopyPaste implements Expresion {
    private String comandoIntroducido;

    public SampleCopyPaste(String comandoIntroducido) {
        if (comandoIntroducido == null) {

            return;
        }
        this.comandoIntroducido = comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) {

    }
}
