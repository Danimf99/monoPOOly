package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.player.Avatar;
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
                if(comandoIntroducido.length!=3){
                    Juego.consola.error("Error en el comando.");
                    Tablero.getPrompt().setHelp(true);
                    return;
                }
                Jugador jugador= Tablero.getTablero().getJugador(comandoIntroducido[2]);
                if(jugador==null){
                    Juego.consola.info("El jugador "+comandoIntroducido[2]+" no existe.");
                    return;
                }
                interprete.describirJugador(jugador);
                break;
            case"avatar":
                if(comandoIntroducido.length!=3){
                    Juego.consola.error("Error en el comando.");
                    Tablero.getPrompt().setHelp(true);
                    return;
                }
                Avatar avatar=null;
                for(Jugador jugador2:Tablero.getTablero().getJugadores()){
                    if(jugador2.getAvatar().getRepresentacion()==comandoIntroducido[2].charAt(0)){
                        avatar=jugador2.getAvatar();
                    }
                }
                if(avatar==null){
                    Juego.consola.info("No hay ningún avatar con esa representación");
                    return;
                }
                interprete.describirAvatar(avatar);
                break;
            default:
                break;
        }
    }
}
