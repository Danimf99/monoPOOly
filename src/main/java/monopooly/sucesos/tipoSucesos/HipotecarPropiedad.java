package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class HipotecarPropiedad extends Suceso {

    private Propiedad propiedad;
    private boolean hipotecar;

    public HipotecarPropiedad(Jugador jugadorOriginador, Propiedad propiedad, Boolean hipotecar) {
        super(jugadorOriginador);
        this.propiedad = propiedad;
        this.hipotecar = hipotecar;
        setTarjeta(TarjetasSucesos.crearTarjeta("Hipoteca", this.toString(),
                this.propiedad.getMonopolio().getHexColor()));
    }

    public int getDinero() {
        return hipotecar ? propiedad.hipoteca() : -(int)(propiedad.getMonopolio().getPrecio()*1.1);
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public boolean isHipotecar() {
        return hipotecar;
    }

    public boolean isDeshipotecar() {
        return !hipotecar;
    }

    public void setHipotecar(boolean hipotecar) {
        this.hipotecar = hipotecar;
    }



    @Override
    public String toString() {
        return "" +
                "jugador: " + getJugadorOriginador().getNombre() + ",\n" +
                "cantidad: " + getDinero() + " " + Precios.MONEDA + ",\n" +
                "propiedad: " + propiedad.getNombre() + "";
    }


    @Override
    public void deshacer() throws ExcepcionMonopooly {
        super.deshacer();
        propiedad.setHipotecado(!hipotecar);
        getJugadorOriginador().anhadirDinero(-getDinero());
        getJugadorOriginador().getHipotecas().remove(propiedad);
        getJugadorOriginador().anhadirPropiedad(propiedad);
    }
}
