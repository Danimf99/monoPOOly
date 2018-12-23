package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionRecursosInsuficientes extends ExcepcionAccionInvalida {
    public ExcepcionRecursosInsuficientes(String message) {
        super(message);
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "Recursos insuficientes"
        );
    }
}
