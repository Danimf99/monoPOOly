package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;

public class Comprar implements Expresion {
    private String comandoIntroducido;

    public Comprar(String comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        this.comandoIntroducido = comandoIntroducido;
    }


    @Override
    public void interpretar(Juego interprete) {
        /*
         * Este metodo es el que se encarga de decidir que es lo que se hace
         * con un comando comprar Mira si se quiere comprar una casa si se quiere
         * comprar una casilla y luego invoca uno de los comandos de intereprete.
         *
         * todo lo que introduzca el usuario y empiece por comprar vendra aqui.
         *
         * Ejemplo:
         *  0. Miro si el comando es de comprar casilla
         *  1. Determina que casilla la busca y comprueba que existe
         *  2. interprete.comprar(jugadorQueCompra, casillaQueCompra);
         *  3. return; rico - La vieja confiable
         *  4. Miro si el comando es de comprar una casa
         *  5. interprete.comprar(casa....... etc
         *
         *
         * ¿ Ventajas de hacerlo así a parte de que queda mejor organizado ?
         *
         * Luego con la interfaz solo se necesita el codigo que está dentro de
         * la clase Juego. El de estas clases que miran lineas de codigo no es
         * necesario. Pulsar un boton de comprar una casilla necesita que la
         * implementacion de la compra y la implementación de mirar que hace
         * el comando sean distintas.
         *
         */
    }
}
