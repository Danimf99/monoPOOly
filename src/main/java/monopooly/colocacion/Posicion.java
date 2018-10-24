package monopooly.colocacion;

import java.util.ArrayList;
import java.util.Objects;

public class Posicion {
    private int x;
    private ArrayList<Posicion> posicionesEsteTurno;

    public void mover(){
        // TODO implementar movimiento
    }


    public void movimientoEspecial() {
        // TODO implementar movimiento especial
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Posicion)) return false;
        Posicion posicion = (Posicion) o;
        return x == posicion.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x);
    }
}
