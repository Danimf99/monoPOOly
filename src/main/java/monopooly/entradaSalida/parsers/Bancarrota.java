package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;

public class Bancarrota implements Expresion {
    private String[] comandoIntroducido;

    public Bancarrota(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) {
        if(comandoIntroducido.length!=1){
            Juego.consola.info("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }
        interprete.bancarrota(Tablero.getPrompt().getJugador());
    }
}
