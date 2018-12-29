package monopooly.colocacion.tipoCasillas.propiedades;


/**
 * Esta interfaz representa todos aquellos metodos que deben tener tanto las
 * las instancias de grupo como las instancias de Propiedades. Si se aplica
 * a una se debe aplicar a las demas.
 *
 */
public interface Monopolio {

    public TipoMonopolio getTipoMonopolio();

    public void setTipoMonopolio(TipoMonopolio tipo);

    public boolean compartenPropietario();

    public void incrementarPrecio();

    public void setPrecio(int precio);

    public int getPrecio();
}
