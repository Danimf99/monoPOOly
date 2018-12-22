package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;

public class Hipotecar implements Expresion {
    private String[] comandoIntroducido;

    public Hipotecar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        comandoIntroducido=new String[comandoIntroducido.length];

        for(int i=0;i<comandoIntroducido.length;i++){
            this.comandoIntroducido[i] = comandoIntroducido[i];
        }
    }

    @Override
    public void interpretar(Juego interprete) {
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
        if(!((Propiedad) hipotecada).getPropietario().equals(Tablero.getPrompt().getJugador())){
            Juego.consola.error("Esa propiedad pertenece a otro jugador");
            return;
        }
        if(((Propiedad) hipotecada).getHipotecado()){
            Juego.consola.info("La propiedad "+hipotecada.getNombre()+" ya está hipotecada.");
            return;
        }
    }
}
