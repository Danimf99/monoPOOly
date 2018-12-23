package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class Alquiler extends Suceso {
    private int cantidad;
    private Propiedad propiedad;
    private Jugador propietario;

    public Alquiler(Jugador jugadorOriginador, int cantidad, Propiedad propiedad, Jugador propietario) {
        super(jugadorOriginador);
        this.cantidad = cantidad;
        this.propiedad = propiedad;
        this.propietario = propietario;
    }


    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(Propiedad propiedad) {
        this.propiedad = propiedad;
    }

    public Jugador getPropietario() {
        return propietario;
    }

    public void setPropietario(Jugador propietario) {
        this.propietario = propietario;
    }
}
