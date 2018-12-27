package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionArgumentosIncorrectos extends ExcepcionComando {
    public ExcepcionArgumentosIncorrectos(String message) {
        super(message);
    }

    public ExcepcionArgumentosIncorrectos() {
        super("No existe el trato introducido");
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "Comando escrito de forma incorrecta"
        );
    }
}
