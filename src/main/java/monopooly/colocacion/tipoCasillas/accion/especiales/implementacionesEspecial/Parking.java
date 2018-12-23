package monopooly.colocacion.tipoCasillas.accion.especiales.implementacionesEspecial;

import monopooly.Partida;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.accion.especiales.AccionEspecial;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;
import monopooly.sucesos.tipoSucesos.ConseguirBote;

import java.util.ArrayList;

public class Parking implements AccionEspecial {
    @Override
    public TipoMonopolio getTipo() {
        return TipoMonopolio.parking;
    }

    @Override
    public void efectuar(Jugador jugador) {
        int cantidad = Tablero.getTablero().getBote();
        jugador.anhadirDinero(cantidad);
        Tablero.getPrompt().setModDinero(cantidad,"Bote del Parking");
        Tablero.getTablero().setBote(0);
        Partida.interprete.enviarSuceso(new ConseguirBote(jugador, cantidad));
    }

    @Override
    public ArrayList<String> describir() {
        ArrayList<String> lineas = new ArrayList<>();
        lineas.add("");
        lineas.add("Bote: " + PintadoAscii.stringDinero(Tablero.getTablero().getBote()));
        return lineas;
    }
}
