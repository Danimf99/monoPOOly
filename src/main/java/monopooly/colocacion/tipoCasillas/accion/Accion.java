package monopooly.colocacion.tipoCasillas.accion;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Imprimible;

/**
 * Esta clase debe ser:
 *  > La clase padre de suerte y caja de comunidad
 *  > La clase de la casilla Parking
 */
public abstract class Accion extends Casilla implements Imprimible {

    public Accion(String nombre) {
        super(nombre);
    }

    @Override
    public String representar(String reprSubclases) {
        String texto = "Lo añade Accion\n" + reprSubclases;
        return super.representar(reprSubclases);
    }
}
