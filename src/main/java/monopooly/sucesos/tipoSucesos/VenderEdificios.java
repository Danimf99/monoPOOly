package monopooly.sucesos.tipoSucesos;

import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.player.Jugador;
import monopooly.sucesos.Suceso;

public class VenderEdificios extends Suceso {

    private Solar solar;
    private int dineroGanado;
    private Edificio.TIPO edificioVendido;

    public VenderEdificios(Jugador originador, Solar solar, int dineroGanado, Edificio.TIPO edificioVendido){
        super(originador);
        this.solar=solar;
        this.dineroGanado=dineroGanado;
        this.edificioVendido=edificioVendido;
    }

    public Solar getSolar() {
        return solar;
    }

    public int getDineroGanado() {
        return dineroGanado;
    }

    public Edificio.TIPO getEdificioVendido() {
        return edificioVendido;
    }
}
