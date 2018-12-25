package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionCarta extends ExcepcionMonopooly {
    public ExcepcionCarta(String message) {
        super(message);
    }

    public void imprimeInfo() {
        Juego.consola.info(
                this.getMessage()
        );
    }
}
