package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

import java.util.Iterator;
import java.util.stream.Stream;

public class TratoPropiedad extends Trato {

    private Propiedad propiedadOrigina;
    private Propiedad propiedadReceptor;



    public TratoPropiedad(Jugador originador, Jugador receptor,Propiedad propiedadOrigina,Propiedad propiedadReceptor){
        super(originador,receptor);
        this.propiedadOrigina=propiedadOrigina;
        this.propiedadReceptor=propiedadReceptor;
    }

    public Propiedad getPropiedadOrigina() {
        return propiedadOrigina;
    }

    public Propiedad getPropiedadReceptor() {
        return propiedadReceptor;
    }

    public String toString(){
        return super.toString()+"\n"
                +"Cambiar "+propiedadOrigina.getNombre()+" por "+propiedadReceptor.getNombre();
    }
}
