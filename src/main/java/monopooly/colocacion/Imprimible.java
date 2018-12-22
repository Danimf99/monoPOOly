package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

import java.util.ArrayList;

public interface Imprimible {
    ArrayList<String> representar(ArrayList<String> reprSubclases);
    TipoMonopolio getTipo();

    static String generar(ArrayList<String> lineas) {
        return String.join("\n", lineas);
    }
}
