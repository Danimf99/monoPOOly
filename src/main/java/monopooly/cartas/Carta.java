package monopooly.cartas;


import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;

public abstract class Carta {


    public abstract void accion() throws ExcepcionMonopooly;

    public abstract String getMensaje();

    public abstract void deshacer() throws ExcepcionMonopooly;

    public abstract int modDinero();
}
