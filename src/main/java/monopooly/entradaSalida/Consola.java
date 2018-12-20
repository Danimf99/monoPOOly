package monopooly.entradaSalida;

public interface Consola {
    public void detalles(String mensaje);
    public void error(String mensaje);
    public void info(String mensaje);
    public void info(String mensaje, String titulo);
    public void error(String mensaje, String titulo);
    public void detalles(String mensaje, String titulo);
    void imprimir(String mensaje);
    void imprimirln(String mensaje);
    String leer();
}
