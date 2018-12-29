package monopooly.colocacion.tipoCasillas.propiedades;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Imprimible;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.Visitante;
import monopooly.colocacion.VisitanteCasilla;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.Comprar;

import java.util.ArrayList;

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
        this.propietario = Tablero.BANCA;
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

    public int alquiler() {
        VisitanteCasilla visitante = new Visitante();
        visitante.calcularAlquiler(this);

        return 0;
    }

    /**
     *
     * @return Dinero a cambio de hipotecar la propiedad
     */
    public int hipoteca() {
        return (int) (this.getMonopolio().getPrecio() * Precios.HIPOTECA);
    }

    public boolean perteneceAJugador(Jugador jugador){
        return jugador.equals(this.propietario);
    }

    public void comprar(Jugador deudor) throws ExcepcionMonopooly {
        if (deudor == null) {
            throw new ExcepcionParametrosInvalidos("Un jugador null no puede comprar una propiedad");
        }

        if (deudor.getDinero() < precio) {
            throw new ExcepcionAccionInvalida("No tienes suficiente dinero para comprar la propiedad");
        }

        this.propietario.anhadirDinero(this.precio);
        deudor.quitarDinero(this.precio);
        deudor.anhadirPropiedad(this);
        this.propietario = deudor;

        Partida.interprete.enviarSuceso(new Comprar(deudor, this, precio));
    }

    @Override
    public void incrementarPrecio() {
        if (!this.propietario.equals(Tablero.BANCA)) {
            // Solo hay que incrementar el precio de las que no fueron compradas =)
            // TODO: Las movidas pertinentes
        }
    }

    @Override
    public TipoMonopolio getTipo() {
        return this.getTipoMonopolio();
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



    @Override
    public ArrayList<String> representar(ArrayList<String> reprSubclases) {
        reprSubclases.add("Titulo de propiedad");
        reprSubclases.add("");
        reprSubclases.add("Precio: "+this.precio+" "+Precios.MONEDA);
        reprSubclases.add("");
        reprSubclases.add("Alquiler: " + new Visitante().calcularAlquiler(this) + " " + Precios.MONEDA);
        reprSubclases.add("");
        reprSubclases.add("Hipoteca: " + this.hipoteca() + " " + Precios.MONEDA);
        reprSubclases.add("");
        reprSubclases.add("Propietario: " + this.propietario.getNombre());
        if(this instanceof Solar) {
            reprSubclases.add(ReprASCII.BARRA_HORIZONTAL + ReprASCII.BARRA_HORIZONTAL + ReprASCII.BARRA_HORIZONTAL);
            reprSubclases.add("Edificaciones:");
            reprSubclases.add("");
            for (Edificio edificio : ((Solar) this).getEdificios()) {
                reprSubclases.add(" > " + edificio.getNombre());
                reprSubclases.add("Tipo: " + edificio.getClass().getSimpleName());
                reprSubclases.add("Precio: " + edificio.getPrecio() + " " + Precios.MONEDA);
                reprSubclases.add("");
            }
        }
        return super.representar(reprSubclases);
    }


}
