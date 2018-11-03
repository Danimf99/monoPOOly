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
        boolean salir = false;
        while (!salir) {
            System.out.println(prompt);
            prompt.setHelp(false);
            String comando = reader.nextLine();
            String[] arrayComando = comando.split(" ");
            switch (arrayComando[0].toLowerCase()) {
                case "":
                    break;
                case "lanzar":
                    //ProcesarComando.lanzarDados(arrayComando,prompt);
                    break;
                case "listar":
                case "l":
                    //ProcesarComando.listar(arrayComando,prompt);
                    break;

                case "describir":
                case "d":
                    //ProcesarComando.describir(arrayComando,prompt);
                    break;

                case "jugador":
                case "j":
                    //ProcesarComando.infoJugador(arrayComando,prompt);
                    break;

                case "acabar":
                case "at":
                    salir = true;
                    //salir = ProcesarComando.acabarTurno(arrayComando,prompt);
                    break;

                case "salir":
                case "s":
                    salir = true;
                    //ProcesarComando.salirCarcel(arrayComando);
                    break;
                case "ver":
                    //ProcesarComando.verTablero(arrayComando,prompt);
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
}
