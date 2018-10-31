package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Jugador;
import monopooly.player.tipoAvatar;

import java.util.ArrayList;

public class Arranque {
    public static void main(String[] args) {
        ArrayList<Jugador> Jugadores=new ArrayList<>();
        Jugadores.add(new Jugador("daniel", tipoAvatar.sombrero));
        Tablero tablero=new Tablero(Jugadores);
        System.out.print(PintadoASCII.genTablero(tablero));
        System.out.println(ReprASCII.ASCII_TITLE);
        Mensajes.error("Mensaje de prueba. Porque null pointer exception");
        Mensajes.info("Mensaje de prueba. Informacion de un suceso");
        Mensajes.detalles("Mensaje de informacion sobre un objeto");
    }
}
