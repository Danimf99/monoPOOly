package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.Visitante;
import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.configuracion.Posiciones;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.CaerCarcel;

import java.util.ArrayList;

/**
 * Hay que mandar al jugador a la carcel
 */
public class IrCarcel implements AccionEspecial {


    @Override
    public void efectuar(Jugador jugador) throws ExcepcionMonopooly {
        Posicion posCarcel = new Posicion(Posiciones.CARCEL);
        Casilla carcel = Tablero.getTablero().getCasilla(posCarcel);
        jugador.setEstarEnCarcel(true);
        Tablero.getTablero().recolocar(jugador, new Posicion(Posiciones.CARCEL));
        jugador.getAvatar().getPosicion().setX(Posiciones.CARCEL);
        carcel.visitar(new Visitante(jugador));
        Partida.interprete.enviarSuceso(new CaerCarcel(jugador));
    }

    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.ir_carcel;
    }

    @Override
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        return lineas;
    }
}
