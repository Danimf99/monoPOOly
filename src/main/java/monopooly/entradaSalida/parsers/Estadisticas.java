package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.player.Jugador;

public class Estadisticas implements Expresion{
    private String[] comandoIntroducido;

    public Estadisticas(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length==2){
            Jugador jugador=Tablero.getTablero().getJugador(comandoIntroducido[1]);
            if(jugador==null){
                throw new ExcepcionParametrosInvalidos("El jugador no se encuentra en la partida");
            }
            interprete.estadisticasJugador(jugador);
            return;
        }
        if(comandoIntroducido.length==1){
            interprete.estadistiscasGlobales();
            return;
        }
        throw new ExcepcionArgumentosIncorrectos("Numero de argumentos incorrectos");
    }
}
