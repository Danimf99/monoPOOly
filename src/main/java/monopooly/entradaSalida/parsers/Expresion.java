package monopooly.entradaSalida.parsers;

import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionComando;
import monopooly.excepciones.ExcepcionMonopooly;


public interface Expresion {

    /**
     * Procesa la linea que se le pasa
     * @param interprete Clase que imprementa los comandos necesarios.
     */
    void interpretar(Juego interprete) throws ExcepcionMonopooly;
}
