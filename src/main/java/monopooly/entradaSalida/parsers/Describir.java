package monopooly.entradaSalida.parsers;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
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
    public void interpretar(Juego interprete) throws ExcepcionArgumentosIncorrectos {
        if(comandoIntroducido.length<2){
            throw new ExcepcionArgumentosIncorrectos("Describir requiere más argumentos");
        }
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
            case "finanzas":
                Partida.interprete.describirFinanzas(Tablero.getTablero().getJugadorTurno().getAvatar());
                break;
            default:
                if(comandoIntroducido.length>2){
                    for(int i=2;i<comandoIntroducido.length;i++){
                        comandoIntroducido[1] = comandoIntroducido[1].concat(" " + comandoIntroducido[i]);
                    }
                }
                Casilla casilla= Tablero.getTablero().getCasilla(comandoIntroducido[1]);
                if(casilla==null){
                    Juego.consola.info("La casilla "+comandoIntroducido[1]+" no existe.");
                    return;
                }
                interprete.describirCasilla(casilla);
                break;
        }
    }
}
