package monopooly.excepciones;

public class ExcepcionComandoInexistente extends ExcepcionComando {
    public ExcepcionComandoInexistente(String message) {
        super("No se reconoce el comando: \n'" + message + "'");
    }
}
