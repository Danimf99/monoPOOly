package monopooly;

import monopooly.colocacion.Prompt;
import monopooly.colocacion.Tablero;
import monopooly.player.Jugador;

public class Arranque {
    public static void main(String[] args) {
        Tablero t = Tablero.getTablero(); // No hace falta new para tablero se hace solo en cuanto se carga la clase
        t.meterJugador(new Jugador());

        Prompt p1 = Tablero.getPrompt(); // Esto se haace sin new ni nada; lo hace automatico en cuanto pasa turno.
        Prompt p2 = Tablero.getPrompt();

        System.out.println("\np1 == p2 --> " + p1.equals(p2));
        System.out.println("\n !! El equals no esta ni implementado;\n" +
                "    las direcciones de memoria son exactamente las mismas.");

        System.out.println("\nDinero: \n\tp1 -> " + p1.getModDinero());
        System.out.println("\tp2 -> " + p2.getModDinero());
        System.out.println("\n ! Se modifica el modDinero en p2\n");
        p2.setModDinero(400);
        System.out.println("Dinero: \n\tp1 -> " + p1.getModDinero());
        System.out.println("\tp2 -> " + p2.getModDinero());

        System.out.println("\n\nSe pasa turno ! \n");
        t.pasarTurno();  // Pasar turno resetea la prompt

        Prompt p3 = Tablero.getPrompt();
        System.out.println("p1 == p3 --> " + p1.equals(p3));
        System.out.println(" --- ");
        System.out.println("p1 == p2 --> " + p1.equals(p2));

        System.out.println("\n !! No debe guardarse la prompt en ningun lado. \n" +
                "    Siempre que se necesite hay que pillarla de la clase tablero");

        System.out.println("\n !! Asi se asegura uno de que siempre tiene la instancia correcta");
        System.out.println("\nDinero: \n\tp1 -> " + p1.getModDinero());
        System.out.println("\tp2 -> " + p2.getModDinero());
        System.out.println("\tp3 -> " + p3.getModDinero());

        System.out.println("\n\n Esto es la clave madre de dios");
    }
}
