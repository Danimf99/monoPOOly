package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class TratoDinero extends Trato {

    private Propiedad propiedadO;
    private int dinero;

    public TratoDinero(Jugador originador, Jugador receptor, Propiedad propiedadOrigina,int dinero){
        super(originador,receptor);
        this.propiedadO=propiedadOrigina;
        this.dinero=dinero;
    }

    public Propiedad getPropiedadO() {
        return propiedadO;
    }

    public int getDinero() {
        return dinero;
    }

    @Override
    public String toString() {
        return super.toString()+"\n"
                +"Cambiar "+propiedadO.getNombre()+" por "+dinero+ Precios.MONEDA;
    }
}
