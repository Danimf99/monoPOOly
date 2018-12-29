package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionComando extends ExcepcionMonopooly {
    public ExcepcionComando(String message) {
        super(message);
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "Error ejecutando el comando"
        );
    }
}
