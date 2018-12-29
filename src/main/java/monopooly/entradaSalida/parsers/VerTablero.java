package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;

public class VerTablero implements Expresion {
    private String[] comandoIntroducido;

    public VerTablero(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) {

        if(comandoIntroducido.length!=2){
            Juego.consola.info("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }
        if(comandoIntroducido[1].equals("tablero")||comandoIntroducido[1].equals("Tablero")){
            interprete.verTablero();
            return;
        }
        Juego.consola.info("Error en el segundo argumento");
    }
}
