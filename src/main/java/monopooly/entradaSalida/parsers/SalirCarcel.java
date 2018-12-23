package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;

public class SalirCarcel implements Expresion {
    private String[] comandoIntroducido;

    public SalirCarcel(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) {
        if(comandoIntroducido.length!=2){
            Juego.consola.error("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }
        switch (comandoIntroducido[1]){
            case "carcel":
            case "cárcel":
            case "Carcel":
            case "Cárcel":
                break;
            default:
                Juego.consola.error("El segundo argumento es incorrecto");
                Tablero.getPrompt().setHelp(true);
                return;
        }
        interprete.salirCarcel(Tablero.getPrompt().getJugador());
    }
}
