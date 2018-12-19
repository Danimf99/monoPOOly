package monopooly.colocacion.tipoCasillas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.tiposEspecial.Tarot;
import monopooly.player.Jugador;

/**
 *
 * Como hay que manejar dos precios en las casillas. El inicial y luego el que
 * van ganando las casillas con el paso de los turnos. Las instancias de
 * propiedad tendrán el precio actualizado pa imprimirlo guay, y los grupos
 * tendran el precio inicial. Va bien asi porque todas las propiedades de
 * un grupo tienen que tener el mismo precio
 *
 * @author Danimf99
 * @author luastan
 */
public abstract class Propiedad extends Casilla implements Monopolio {
    private TipoMonopolio tipoMonopolio;
    private Jugador propietario;
    private Grupo monopolio;
    private int precio;

    public Propiedad(TipoMonopolio tipoMonopolio, Grupo monopolio) {
        this.tipoMonopolio = tipoMonopolio;
        this.monopolio = monopolio;
        monopolio.addPropiedad(this);
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    @Override
    public void incrementarPrecio() {
        if (!this.propietario.equals(Tablero.BANCA)) {
            // Solo hay que incrementar el precio de las que no fueron compradas =)
            // TODO: Las movidas pertinentes
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
    public TipoMonopolio getTipoMonopolio() {
        return tipoMonopolio;
    }

    @Override
    public void setTipoMonopolio(TipoMonopolio tipo) {
        this.tipoMonopolio = tipo;
    }

    @Override
    public boolean compartenPropietario() {
        return this.monopolio.compartenPropietario();
    }

    public abstract int calcularAlquiler(VisitanteCasilla visitante);
}
