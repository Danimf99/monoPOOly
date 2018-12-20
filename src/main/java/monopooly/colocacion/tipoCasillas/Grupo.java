package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.Monopolio;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.player.Jugador;

import java.util.HashSet;

/**
 * Grupo tiene que ser una agregacion lo cual es un poco...
 *
 *              ( * it be like that sometimes * )
 *
 * pero no pasa ni media. La idea es sacarle provecho con metodos que tenga
 * sentido usar en todas las casillas que componen un grupo a la vez.
 * Por ejemplo incrementar el precio cuando pasan X turnos
 *
 */
public class Grupo extends Casilla implements Monopolio {

    private int precio;
    private TipoMonopolio tipoMonopolio;
    private HashSet<Propiedad> propiedades;


    public Grupo(TipoMonopolio tipoMonopolio) {
        super(genNombre(tipoMonopolio));
        this.tipoMonopolio = tipoMonopolio;
        this.propiedades = new HashSet<>();
    }

    public Grupo(String nombre, int precio, TipoMonopolio tipoMonopolio) {
        super(nombre);
        this.precio = precio;
        this.tipoMonopolio = tipoMonopolio;
    }

    /**
     * Genera el nombre del grupo / monopolio en funcion de su tipo
     *
     * @param tipoMonopolio Tipo de la instancia del grupo
     * @return String con el nombre
     */
    private static String genNombre(TipoMonopolio tipoMonopolio) {
        String monopolio = tipoMonopolio.toString();
        return "Monopolio " +
                "" + monopolio.substring(0, 1).toUpperCase() + monopolio;
    }

    public void addPropiedad(Propiedad propiedad) {
        this.propiedades.add(propiedad);
    }


    @Override
    public TipoMonopolio getTipoMonopolio() {
        return tipoMonopolio;
    }

    @Override
    public void setTipoMonopolio(TipoMonopolio tipoMonopolio) {
        this.tipoMonopolio = tipoMonopolio;
    }

    @Override
    public boolean compartenPropietario() {
        HashSet<Jugador> propietarios = new HashSet<>();
        propiedades.forEach(propiedad -> propietarios.add(propiedad.getPropietario()));
        return propietarios.size() == 1;
    }

    @Override
    public void incrementarPrecio() {
        propiedades.forEach(Propiedad::incrementarPrecio);
    }

    @Override
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public int getPrecio() {
        return this.precio;
    }

    @Override
    public void visitar(VisitanteCasilla visitante) {
        // Un grupo no se visita =P
    }


}
