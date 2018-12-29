package monopooly;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.FabricaCasillas;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;

import java.util.ArrayList;

public class Arranque {
    public static void main(String[] args) {
        new Partida().init();

    }
}
