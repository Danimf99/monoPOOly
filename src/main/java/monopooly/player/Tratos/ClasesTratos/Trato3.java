package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class Trato3 extends Trato {
    private Propiedad propiedadR;
    private int dinero;

    public Trato3(Jugador originador, Jugador receptor,int dinero, Propiedad propiedadR ){
        super(originador,receptor);
        this.propiedadR=propiedadR;
        this.dinero=dinero;
    }

    public Propiedad getPropiedadR() {
        return propiedadR;
    }

    public int getDinero() {
        return dinero;
    }

    @Override
    public String toString() {
        return super.toString()+"\n"
                +"Cambiar "+dinero+ Precios.MONEDA+" por "+propiedadR.getNombre();
    }

}
