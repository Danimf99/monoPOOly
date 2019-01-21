package monopooly.sucesos.tipoSucesos;

import javafx.scene.layout.StackPane;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.gui.componentes.TarjetasSucesos;
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
        setTarjeta(TarjetasSucesos.crearTarjeta("Venta de edificios", this.toString(), this.solar.getMonopolio().getHexColor()));
    }


    @Override
    public String toString() {
        return "" +
                "Jugador: " + getJugadorOriginador().getNombre() + ",\n" +
                "Tipo:" + edificioVendido.toString() + ",\n" +
                "Beneficio: " + dineroGanado + "" + Precios.MONEDA + ",\n" +
                "localizacion: " + solar.getNombre() + "";
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
