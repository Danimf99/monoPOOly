package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;

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
    public void interpretar(Juego interprete) {

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

        if(deshipotecada==null|| !(deshipotecada instanceof Propiedad)){
            Juego.consola.error("Esa propiedad no existe.");
            return;
        }
        interprete.deshipotecar(Tablero.getPrompt().getJugador(),deshipotecada);

        return;
    }
}
