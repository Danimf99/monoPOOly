package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.player.Jugador;

import java.util.ArrayList;
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
public class Grupo extends Casilla implements Monopolio{

    private int precio;
    private TipoMonopolio tipoMonopolio;
    private HashSet<Propiedad> propiedades;


    public Grupo(TipoMonopolio tipoMonopolio) {
        this.tipoMonopolio = tipoMonopolio;
        this.propiedades = new HashSet<>();
    }

    void addPropiedad(Propiedad propiedad) { // Package-Private
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
        for (Propiedad propiedad : this.propiedades) {
            propietarios.add(propiedad.getPropietario());
        }
        return propietarios.size() == 1;
    }

    @Override
    public void incrementarPrecio() {
        for (Propiedad propiedad : this.propiedades) {
            propiedad.incrementarPrecio();
            /* Aqui es donde se le saca partido a la agregacion */
        }
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
