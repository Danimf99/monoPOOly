package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class Edificar implements Expresion {
    private String[] comandoIntroducido;

    public Edificar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        if(comandoIntroducido.length!=2){
            throw new ExcepcionArgumentosIncorrectos("El numero de argumentos introducido es incorrecto");
        }

        Casilla casilla= Tablero.getTablero().getCasilla(Tablero.getPrompt().getJugador().getAvatar().getPosicion());
        if(!(casilla instanceof Solar)){
            throw new ExcepcionArgumentosIncorrectos("La casilla en la que se quiere edificar no es un solar");
        }

        switch (comandoIntroducido[1].toLowerCase()){
            case "casa":
            case "hotel":
            case "deporte":
            case "piscina":
                break;
            default:
                throw new ExcepcionArgumentosIncorrectos("No se identifica el tipo de edificio");
        }
        interprete.edificar((Propiedad)casilla, Edificio.TIPO.valueOf(comandoIntroducido[1]));
    }
}
