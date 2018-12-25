package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Comprar extends Suceso {
    private Object objetoComprado;
    private int precioPagado;
    private Jugador cuentaIngreso;

    public Comprar(Jugador comprador, Object objetoComprado, int precioPagado, Jugador cuentaIngreso) {
        super(comprador);
        this.objetoComprado = objetoComprado;
        this.precioPagado = precioPagado;
        this.cuentaIngreso = cuentaIngreso;
    }

    public Comprar(Jugador comprador, Object objetoComprado, int precioPagado) {
        super(comprador);
        this.objetoComprado = objetoComprado;
        this.precioPagado = precioPagado;
        this.cuentaIngreso = Tablero.BANCA;
    }

    public Object getObjetoComprado() {
        return objetoComprado;
    }

    public void setObjetoComprado(Object objetoComprado) {
        this.objetoComprado = objetoComprado;
    }

    public int getPrecioPagado() {
        return precioPagado;
    }

    public void setPrecioPagado(int precioPagado) {
        this.precioPagado = precioPagado;
    }

    public Jugador getCuentaIngreso() {
        return cuentaIngreso;
    }

    public void setCuentaIngreso(Jugador cuentaIngreso) {
        this.cuentaIngreso = cuentaIngreso;
    }

    @Override
    public String toString() {
        return "Comprar{" +
                "Clase objetoComprado=" + objetoComprado.getClass().getSimpleName() +
                ", precioPagado=" + precioPagado +
                ", cuentaIngreso=" + cuentaIngreso.getNombre() +
                '}';
    }

    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        getJugadorOriginador().anhadirDinero(precioPagado);
        cuentaIngreso.quitarDinero(precioPagado);

        if (objetoComprado instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) objetoComprado;
            getJugadorOriginador().quitarPropiedad(propiedad);
            cuentaIngreso.anhadirPropiedad(propiedad);
            propiedad.setPropietario(cuentaIngreso);
            return;
        }
        if (objetoComprado instanceof Edificio) {
            Edificio edificio = (Edificio) objetoComprado;
            //edificio.getSolar().quitarEdificio(edificio);
        }
    }
}
