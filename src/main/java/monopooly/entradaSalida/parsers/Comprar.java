package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;

public class Comprar implements Expresion {
    private String[] comandoIntroducido;

    public Comprar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido=comandoIntroducido;
    }


    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        /*
         * Este metodo es el que se encarga de decidir que es lo que se hace
         * con un comando comprar Mira si se quiere
         * comprar una casilla y luego invoca uno de los comandos de intereprete.
         *
         *
         *
         * Ejemplo:
         *  0. Miro si el comando es de comprar casilla
         *  1. Determina que casilla la busca y comprueba que existe
         *  2. interprete.comprar(jugadorQueCompra, casillaQueCompra);
         *  3. return; rico - La vieja confiable
         *
         *
         * ¿ Ventajas de hacerlo así a parte de que queda mejor organizado ?
         *
         * Luego con la interfaz solo se necesita el codigo que está dentro de
         * la clase Juego. El de estas clases que miran lineas de codigo no es
         * necesario. Pulsar un boton de comprar una casilla necesita que la
         * implementacion de la comprar y la implementación de mirar que hace
         * el comando sean distintas.
         *
         */
        Casilla comprar;

        if (comandoIntroducido.length <2) {
            throw new ExcepcionArgumentosIncorrectos("El numero de argumentos introducido es insuficiente");
        }
        //Si el nombre tiene espacios pues hacemos esto
        if (comandoIntroducido.length > 2) {
            for(int i=2;i<comandoIntroducido.length;i++){
                comandoIntroducido[1] = comandoIntroducido[1].concat(" " + comandoIntroducido[i]);
            }
        }
        comprar=interprete.casillaCorrecta(comandoIntroducido[1]);

        if(!(comprar instanceof Propiedad)){
            throw new ExcepcionArgumentosIncorrectos("La casilla introducida no es una propiedad");
        }

        interprete.comprar(Tablero.getPrompt().getJugador(),comprar);
    }
}
