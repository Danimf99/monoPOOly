package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionMonopooly extends Exception {

    public ExcepcionMonopooly(String message) {
        super(message);
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage()
        );
    }
}
