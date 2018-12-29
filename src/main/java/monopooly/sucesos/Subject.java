package monopooly.sucesos;

public interface Subject {

    /**
     *
     * @param observador Observador para añadir a la lista
     */
    public void registrar(Observador observador);

    /**
     *
     * @param observador Observador que se elimina de la lista
     */
    public void eliminar(Observador observador);

    /**
     * Notifica de actualizaciones a todos los observadores
     */
    public void notificarObservadores();

    /**
     * Devuelve informacion sobre lo que ha ocurrido
     *
     * @param observador Observador que pide la info
     * @return Objeto correspondiente a la informacion
     */
    public Object getUpdate(Observador observador);
}
