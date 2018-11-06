package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.entradaSalida.ProcesarComando;
import monopooly.entradaSalida.Turno;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.ArrayList;
import java.util.Scanner;

public class Arranque {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(ReprASCII.ASCII_TITLE);
        int numJugadores = 0;
        while (numJugadores <= 0 || numJugadores > 6) {
            System.out.print(ReprASCII.PROMPT_NUEVA_PARTIDA);
            numJugadores = scanner.nextInt();
        }
        ArrayList<Jugador> jugadoresPartida = new ArrayList<>();
        for (int i = 0; i < numJugadores; i++) {
            jugadoresPartida.add(ProcesarComando.crearJugador());
        }
        Turno.partida(new Tablero(jugadoresPartida));

    }
}
