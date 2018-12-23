package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;

public class Describir implements Expresion {
    private String[] comandoIntroducido;

    public Describir(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) {
        switch(comandoIntroducido[1].toLowerCase()){
            case "jugador":
                Jugador jugador= Tablero.getTablero().getJugador(comandoIntroducido[2]);
                interprete.describirJugador(jugador);
                break;
            case"avatar":
                break;
            default:
                break;
        }
    }
}
