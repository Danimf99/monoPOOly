package monopooly.colocacion;

public interface IPosicion {
    void mover(int desplazamiento);
    boolean esIrCarcel();
    void irCarcel();
    int contarRepeticiones(Posicion p);
}
