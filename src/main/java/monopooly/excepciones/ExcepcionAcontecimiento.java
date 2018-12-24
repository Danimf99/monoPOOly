package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionAcontecimiento extends ExcepcionMonopooly {
    public ExcepcionAcontecimiento(String message) {
        super(message);
    }

    public void imprimeInfo() {
        Juego.consola.info(
                this.getMessage()
        );
    }
}
