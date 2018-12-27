package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class Trato4 extends Trato {

    private Propiedad propiedadO;
    private Propiedad propiedadReceptor;
    private  int dineroO;

    public Trato4(Jugador originador,Jugador receptor, Propiedad propiedadO,Propiedad propiedadReceptor,int dineroO){
        super(originador,receptor);
        this.propiedadO=propiedadO;
        this.propiedadReceptor=propiedadReceptor;
        this.dineroO=dineroO;
    }

    public Propiedad getPropiedadO() {
        return propiedadO;
    }

    public Propiedad getPropiedadReceptor() {
        return propiedadReceptor;
    }

    public int getDineroO() {
        return dineroO;
    }

    @Override
    public String toString() {
        return super.toString()+"\n"
                +"Cambiar "+propiedadO.getNombre()+" y "+dineroO + Precios.MONEDA+" por "+propiedadReceptor.getNombre();
    }
}
