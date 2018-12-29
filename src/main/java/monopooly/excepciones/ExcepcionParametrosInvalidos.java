package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionParametrosInvalidos extends ExcepcionMonopooly {
    public ExcepcionParametrosInvalidos(String message) {
        super(message);
    }
    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "Parametros invalidos"
        );
    }
}
