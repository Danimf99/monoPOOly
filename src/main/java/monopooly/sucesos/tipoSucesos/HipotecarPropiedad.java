package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class HipotecarPropiedad extends Suceso {

    private Propiedad propiedad;
    private boolean hipotecar;

    public HipotecarPropiedad(Jugador jugadorOriginador, Propiedad propiedad, Boolean hipotecar) {
        super(jugadorOriginador);
        this.propiedad = propiedad;
        this.hipotecar = hipotecar;
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
        return "HipotecarPropiedad{" +
                "propiedad=" + propiedad.getNombre() +
                ", hipotecar=" + hipotecar +
                '}';
    }
}
