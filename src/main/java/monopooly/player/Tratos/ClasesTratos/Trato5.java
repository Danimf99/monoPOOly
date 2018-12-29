package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class Trato5 extends Trato {
    private Propiedad propiedadO;
    private Propiedad propiedadReceptor;
    private  int dineroReceptor;

    public Trato5(Jugador originador, Jugador receptor, Propiedad propiedadO, Propiedad propiedadReceptor, int dineroReceptor){
        super(originador,receptor);
        this.propiedadO=propiedadO;
        this.propiedadReceptor=propiedadReceptor;
        this.dineroReceptor=dineroReceptor;
    }

    public Propiedad getPropiedadO() {
        return propiedadO;
    }

    public Propiedad getPropiedadReceptor() {
        return propiedadReceptor;
    }

    public int getDineroReceptor() {
        return dineroReceptor;
    }

    @Override
    public String toString() {
        return super.toString()+"\n"
                +"Cambiar "+propiedadO.getNombre() +" por "+propiedadReceptor.getNombre()+" y "+dineroReceptor+ Precios.MONEDA;
    }
}
