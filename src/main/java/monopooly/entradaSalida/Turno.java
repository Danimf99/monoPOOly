package monopooly.entradaSalida;

import monopooly.colocacion.Tablero;

import java.util.Scanner;

public class Turno {

    /**
     * Muestra el prompt, lee comandos y los ejecuta
     * @param prompt Prompt del jugador que tiene el turno actualmente
     * @param tablero Tablero de la partida
     */
    public static void run(Prompt prompt, Tablero tablero) {
        Scanner reader = new Scanner(System.in);
        System.out.println(prompt);
        prompt.setHelp(false);
        String comando = reader.nextLine();
        String[] arrayComando = comando.split(" ");
        switch (arrayComando[0].toLowerCase()) {
            case "":
                break;

            case "listar":
            case "l":
                //ProcesarComando.listar();
                break;

            case "describir":
            case "d":
                break;

            case "acabar":
            case "at":
                break;

            case "help":
            case "h":
            case "ayuda":
            case "a":
                break;
            default:
                Mensajes.error("No existe ese comando");
                prompt.setHelp(true);
        }
    }
}
