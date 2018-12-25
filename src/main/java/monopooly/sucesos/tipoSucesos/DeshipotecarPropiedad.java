package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class DeshipotecarPropiedad extends Suceso {

    private Propiedad propiedad;
    private int precioDeshipotecar;

    public DeshipotecarPropiedad(Jugador originador, Propiedad propiedad,int precioDeshipotecar){
        super(originador);
        this.propiedad=propiedad;
        this.precioDeshipotecar=precioDeshipotecar;
    }

    public Propiedad getPropiedad() {
        return propiedad;
    }

    public int getPrecioDeshipotecar() {
        return precioDeshipotecar;
    }

    public void setPrecioDeshipotecar(int precioDeshipotecar) {
        this.precioDeshipotecar = precioDeshipotecar;
    }

    @Override
    public String toString() {
        return "DeshipotecarPropiedad{" +
                "propiedad=" + propiedad.getNombre() +"\n"+
                "dineroRecibido="+precioDeshipotecar+
                '}';
    }
}
