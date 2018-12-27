package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;

public class Hipotecar implements Expresion {
    private String[] comandoIntroducido;

    public Hipotecar(String[] comandoIntroducido) {
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
            Tablero.getPrompt().setHelp(true);
            return;
        }
        //Si el nombre tiene espacios pues hacemos esto
        if (comandoIntroducido.length > 2) {
            for(int i=2;i<comandoIntroducido.length;i++){
                comandoIntroducido[1] = comandoIntroducido[1].concat(" " + comandoIntroducido[i]);
            }
        }
        Casilla hipotecada;
        hipotecada=interprete.casillaCorrecta(comandoIntroducido[1]);

        if(hipotecada==null || !(hipotecada instanceof Propiedad)){
            Juego.consola.error("Esa propiedad no existe.");
            return;
        }

        interprete.hipotecar(Tablero.getPrompt().getJugador(),hipotecada);
    }
}
