package monopooly.excepciones;

import monopooly.entradaSalida.Juego;

public class ExcepcionComandoInexistente extends ExcepcionComando {
    public ExcepcionComandoInexistente(String message) {
        super("No se reconoce el comando: \n'" + message + "'");
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage(),
                "El comando no existe"
        );
    }
}
