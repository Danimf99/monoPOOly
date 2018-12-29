package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class Deshipotecar implements Expresion {
    private String[] comandoIntroducido;

    public Deshipotecar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }
    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {

        if(comandoIntroducido.length<2){
            Juego.consola.error("Error en el comando");
            return;
        }

        //Si el nombre tiene espacios pues hacemos esto
        if (comandoIntroducido.length > 2) {
            for(int i=2;i<comandoIntroducido.length;i++){
                comandoIntroducido[1] = comandoIntroducido[1].concat(" " + comandoIntroducido[i]);
            }
        }

        Casilla deshipotecada;

        deshipotecada=interprete.casillaCorrecta(comandoIntroducido[1]);

        if(!(deshipotecada instanceof Propiedad)){
            throw new ExcepcionArgumentosIncorrectos("La casilla no es una propiedad");
        }
        interprete.deshipotecar(Tablero.getPrompt().getJugador(),deshipotecada);
    }
}
