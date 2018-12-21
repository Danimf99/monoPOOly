package monopooly;

import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.entradaSalida.Juego;

public class Arranque {
    public static void main(String[] args) {
        Juego.consola.imprimir("We back !\n");
        Grupo monop = new Grupo(TipoMonopolio.naranja);
        Solar solar1 = new Solar(monop, "Nombre de la casilla");
        System.out.println(solar1);
    }
}
