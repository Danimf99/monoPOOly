package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.ArrayList;

public class Arranque {
    public static void main(String[] args) {
        ArrayList<Jugador> Jugadores=new ArrayList<>();
        Jugadores.add(new Jugador("daniel", TipoAvatar.sombrero));
        Jugadores.add(new Jugador("pepe", TipoAvatar.sombrero));
        Jugadores.add(new Jugador("juan", TipoAvatar.sombrero));
        Tablero tablero=new Tablero(Jugadores);
        System.out.print(PintadoASCII.genTablero(tablero));
        Jugadores.get(0).moverJugador(tablero);
        System.out.print("\n"+PintadoASCII.genTablero(tablero)+"\n");
        Mensajes.error("Mensaje de prueba. Porque null pointer exception");
        Mensajes.info("Mensaje de prueba. Informacion de un suceso");
        Mensajes.detalles("Mensaje de informacion sobre un objeto");
    }
}
