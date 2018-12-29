package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionAccionInvalida extends ExcepcionMonopooly {
    public ExcepcionAccionInvalida(String message) {
        super(message);
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "Accion invalida"
        );
    }
}
