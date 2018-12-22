package monopooly.colocacion;

import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

public interface Imprimible {
    public String representar(String reprSubclases);
    public TipoMonopolio getTipo();
}
