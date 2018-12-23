package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Jugador;

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
        if(comandoIntroducido.length!=2){
            Juego.consola.error("Error en el comando");
            Tablero.getPrompt().setHelp(true);
            return;
        }
        Jugador jugador= Tablero.getPrompt().getJugador();

        if(!jugador.getAvatar().isNitroso()){
            interprete.lanzar(jugador);
        }
    }
}
