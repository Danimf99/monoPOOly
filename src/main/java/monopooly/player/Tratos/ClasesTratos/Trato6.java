package monopooly.player.Tratos.ClasesTratos;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.player.Tratos.Trato;

public class Trato6 extends Trato {
    private Propiedad propiedadOrigina;
    private Propiedad propiedadReceptor;
    private Propiedad propiedadNoAlquiler;
    private int turnosNoAlquiler;


    public Trato6(Jugador originador, Jugador receptor, Propiedad propiedadOrigina, Propiedad propiedadReceptor, Propiedad propiedadNoAlquiler, int turnosNoAlquiler){
        super(originador,receptor);
        this.propiedadOrigina=propiedadOrigina;
        this.propiedadReceptor=propiedadReceptor;
        this.propiedadNoAlquiler=propiedadNoAlquiler;
        this.turnosNoAlquiler=turnosNoAlquiler;
    }

    public Propiedad getPropiedadOrigina() {
        return propiedadOrigina;
    }

    public Propiedad getPropiedadReceptor() {
        return propiedadReceptor;
    }

    public Propiedad getPropiedadNoAlquiler() {
        return propiedadNoAlquiler;
    }

    public int getTurnosNoAlquiler() {
        return turnosNoAlquiler;
    }

    public String toString(){
        return super.toString()+"\n"
                +"Cambiar "+propiedadOrigina.getNombre()+" por "+propiedadReceptor.getNombre()+"\n"
                +"y no pagar alquiler en "+propiedadNoAlquiler.getNombre()+" durante "+turnosNoAlquiler+" turnos";
    }
}
