package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;

public class Info implements Expresion {
    private String[] comandoIntroducido;

    public Info(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        comandoIntroducido=new String[comandoIntroducido.length];

        for(int i=0;i<comandoIntroducido.length;i++){
            this.comandoIntroducido[i] = comandoIntroducido[i];
        }
    }
    @Override
    public void interpretar(Juego interprete) {
        if(comandoIntroducido.length!=1){
            Juego.consola.error("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }
        interprete.info(Tablero.getPrompt().getJugador());

        return;
    }
}
