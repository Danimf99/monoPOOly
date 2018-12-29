package monopooly.entradaSalida;

public interface Consola {
    void detalles(String mensaje);
    void error(String mensaje);
    void info(String mensaje);
    void info(String mensaje, String titulo);
    void error(String mensaje, String titulo);
    void detalles(String mensaje, String titulo);
    void imprimir(String mensaje);
    void imprimirln(String mensaje);
    String leer();
    public String leer(String mensaje);
    int elegirCarta();
}
