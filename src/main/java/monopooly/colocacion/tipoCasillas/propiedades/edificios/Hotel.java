package monopooly.colocacion.tipoCasillas.propiedades.edificios;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;

import java.util.Iterator;
import java.util.stream.Stream;

public class Hotel extends Edificio {

    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();

    public Hotel(Solar solar) {
        super(generadorId.next(), solar);
    }
}
