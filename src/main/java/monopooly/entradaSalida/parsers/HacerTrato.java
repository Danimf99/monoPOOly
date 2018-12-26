package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;

public class HacerTrato implements Expresion {
    private String[] comandoIntroducido;

    public HacerTrato(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        String comandoReplace=comandoIntroducido[1].replace(":","");//Variable para quitar : , ( ) del comando introducido

        Jugador receptor=Tablero.getTablero().getJugador(comandoReplace);
        if(receptor==null){
            throw new ExcepcionAccionInvalida("El jugador indicado para el trato no existe");
        }
        //Para coger la Propiedad1 o dinero o lo quesea

        comandoReplace=comandoIntroducido[3].replace("(","");
        comandoReplace=comandoReplace.replace(",","");
    }
}
