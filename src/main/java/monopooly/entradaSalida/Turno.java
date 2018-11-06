package monopooly.entradaSalida;

import monopooly.colocacion.Tablero;

import java.util.Scanner;

public class Turno {

    /**
     * Muestra el prompt, lee comandos y los ejecuta. Pasa turno en el tablero al salir
     * @param tablero Tablero de la partida
     */
    public static void run(Tablero tablero) {
        Prompt prompt = new Prompt(tablero, tablero.getJugadorTurno());
        Scanner reader = new Scanner(System.in);
        boolean terminarTurno = false;
        while (!terminarTurno) {
            System.out.println(prompt);
            prompt.setHelp(false);
            String comando = reader.nextLine();
            String[] arrayComando = comando.split(" ");
            switch (arrayComando[0].toLowerCase()) {
                case "":
                    break;

                case "bancarrota":
                case "b":
                    // TODO implementar comando de bancarrota
                    tablero.getJugadoresTurno().remove(prompt.getJugador());
                    break;

                case "lanzar":
                    ProcesarComando.lanzarDados(arrayComando, prompt);
                    break;

                case "listar":
                case "l":
                    ProcesarComando.listar(arrayComando, prompt);
                    break;

                case "describir":
                case "d":
                    ProcesarComando.describir(arrayComando, prompt);
                    break;

                case "jugador":
                case "j":
                    ProcesarComando.infoJugador(arrayComando, prompt);
                    break;

                case "acabar":
                case "at":
                    terminarTurno = true;
                    terminarTurno = ProcesarComando.acabarTurno(arrayComando);
                    break;

                case "salir":
                case "s":
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
        tablero.pasarTurno();
    }

    public static void partida(Tablero tablero) {
        boolean alive = true;
        // Cuando los jugadores van entrando en bancarrota se van sacando del array de jugadoresTurno
        while (tablero.getJugadoresTurno().size() > 1) {
            Turno.run(tablero);
        }
        System.out.println("Ganador: " + tablero.getJugadoresTurno().get(0));
        // TODO imprimir el ganador de la partida mas decentemente
    }
}
