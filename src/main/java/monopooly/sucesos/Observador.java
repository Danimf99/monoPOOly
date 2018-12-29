package monopooly.sucesos;

public interface Observador {
    /**
     * Actualiza al observador. Lo usa Subject
     */
    public void update();


    public void setSubject(Subject subject);
}
