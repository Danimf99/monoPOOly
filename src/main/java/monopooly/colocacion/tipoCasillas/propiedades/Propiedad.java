package monopooly.colocacion.tipoCasillas.propiedades;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Imprimible;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.VisitanteCasilla;
import monopooly.entradaSalida.Juego;
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
public abstract class Propiedad extends Casilla implements Monopolio, Imprimible {
    private Jugador propietario;
    private Grupo monopolio;
    private int precio;
    private boolean hipotecado;

    public Propiedad(Grupo monopolio, String nombre) {
        super(nombre);
        this.monopolio = monopolio;
        this.hipotecado=false;
        monopolio.addPropiedad(this);
    }



    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }

    public boolean isHipotecado() {
        return hipotecado;
    }

    public void setHipotecado(boolean hipotecado) {
        this.hipotecado = hipotecado;
    }
    public boolean getHipotecado(){
        return this.hipotecado;
    }
    public Grupo getMonopolio() {
        return monopolio;
    }

    public void setMonopolio(Grupo monopolio) {
        this.monopolio = monopolio;
    }

    /**
     * Dado un visitante calcula el alquiler
     * @param visitante Visitante
     * @return cantidad del alquiler
     */
    public abstract int calcularAlquiler(VisitanteCasilla visitante);

    /**
     * Calculo por defecto del alquiler
     * @return Calcula el alquiler de una casilla
     */
    public abstract int calcularAlquiler();


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
        return this.monopolio.getTipoMonopolio();
    }

    @Override
    public void setTipoMonopolio(TipoMonopolio tipo) {
        this.monopolio.setTipoMonopolio(tipo);
    }

    @Override
    public boolean compartenPropietario() {
        return this.monopolio.compartenPropietario();
    }

    public void compra(Jugador deudor) {
        if (deudor == null) {
            Juego.consola.error("Un jugador null no puede comprar una propiedad");
            return;
        }
        this.propietario.anhadirDinero(this.precio);
        this.propietario.quitarPropiedad(this);
        deudor.quitarDinero(this.precio);
        deudor.anhadirPropiedad(this);
        this.propietario = deudor;
    }
    @Override
    public String representar(String reprSubclases) {
        String salida = "Lo añade propiedad\n" + reprSubclases;
        return super.representar(salida);
    }
}
