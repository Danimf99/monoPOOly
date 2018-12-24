package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;

public class CambiarModo implements Expresion {
    private String[] comandoIntroducido;

    public CambiarModo(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length!=2){
            Juego.consola.info("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }

        if(comandoIntroducido[1].equals("modo")||comandoIntroducido[1].equals("Modo")){
            interprete.cambiarModo(Tablero.getPrompt().getJugador().getAvatar());
            return;
        }
        Juego.consola.info("Error en el segundo argumento");
    }
}
