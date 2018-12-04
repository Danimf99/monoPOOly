package monopooly.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.ArrayList;
import java.util.Scanner;

public class Juego {

    /**
     * Muestra el prompt, lee comandos y los ejecuta. Pasa turno en el tablero al salir
     * @param tablero Tablero de la partida
     */
    public static void turno(Tablero tablero) {

        System.out.println(tablero);

        Prompt prompt = new Prompt(tablero, tablero.getJugadorTurno());
        tablero.getJugadorTurno().getDados().setContador(0);
        Scanner reader = new Scanner(System.in);
        boolean terminarTurno = false;
        //Esto es para probar comprar y vender edificios de momento dejalo por si hay que probar mas cosas
       //tablero.getCalle("Plasencia").compra(prompt.getJugador());
       //tablero.getCalle("Almería").compra(prompt.getJugador());
       //tablero.getCalle("Ciudad Real").compra(prompt.getJugador());
        while (!terminarTurno) {
            System.out.print(prompt);
            prompt.setHelp(false);
            tablero.getEstadisticas().calcularEstadisticas();
            String comando = reader.nextLine();
            String[] arrayComando = comando.split(" ");
            switch (arrayComando[0].toLowerCase()) {
                case "":
                    break;
                case "bancarrota":
                case "b":
                    ProcesarComando.bancarrota(arrayComando,prompt);
                    terminarTurno=true;
                    break;
                case "edificar":
                case "e":
                    if(prompt.getJugador().getDinero()<0){
                        Mensajes.info("Estás en bancarrota no puedes edificar.");
                        return;
                    }
                    ProcesarComando.edificar(arrayComando,prompt);
                    break;
                case "estadisticas":
                case "estadísticas":
                    ProcesarComando.estadisticas(arrayComando,prompt);
                    break;
                case "lanzar":
                    ProcesarComando.lanzarDados(arrayComando, prompt);
                    break;
                case "hipotecar":
                    ProcesarComando.hipotecar(arrayComando,prompt);
                    break;
                case "listar":
                case "l":
                    ProcesarComando.listar(arrayComando, prompt);
                    break;
                case "comprar":
                case "c":
                    ProcesarComando.comprar(arrayComando,prompt);
                    break;
                case "deshipotecar":
                    if(prompt.getJugador().getDinero()<0){
                        Mensajes.info("Estás en bancarrota no puedes deshipotecar propiedades.");
                        return;
                    }
                    ProcesarComando.deshipotecar(arrayComando,prompt);
                    break;
                case "describir":
                case "d":
                    ProcesarComando.describir(arrayComando, prompt);
                    break;

                case "jugador":
                case "j":
                    ProcesarComando.infoJugador(arrayComando, prompt);
                    break;
                case "vender":
                case "v":
                    ProcesarComando.venderEdificios(arrayComando,prompt);
                    break;
                case "acabar":
                case "at":
                    if(prompt.getJugador().getDinero()<0){
                        Mensajes.info("Tienes que declararte en bancarrota. No puedes pasar turno.");
                        return;
                    }
                    if(prompt.getJugador().getDados().getContador()!=1) {
                        Mensajes.info("Aún no lanzaste dados este turno!!");
                        return;
                    }
                    terminarTurno = true;
                    terminarTurno = ProcesarComando.acabarTurno(arrayComando);
                    break;
                case "cambiar":
                    ProcesarComando.cambiarModo(arrayComando,prompt);
                    break;
                case "salir":
                case "s":
                    ProcesarComando.salirCarcel(arrayComando,prompt);
                    break;
                case "ver":
                    ProcesarComando.verTablero(arrayComando,prompt);
                    break;
                case "help":
                case "h":
                case "ayuda":
                case "a":
                    ProcesarComando.ayuda();
                    break;

                case "terminar":
                case "t":
                    Mensajes.error("El usuario decidio terminar la partida");
                    System.exit(0);
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
            Juego.turno(tablero);
        }
        System.out.println("Ganador: \n" + tablero.getJugadoresTurno().get(0));
        // TODO imprimir el ganador de la partida mas decentemente
    }

    public static void partidaRapida() {
        ArrayList<Jugador> jugadores = new ArrayList<>();
        jugadores.add(new Jugador("Saul", TipoAvatar.coche));
        jugadores.add(new Jugador("Dani", TipoAvatar.coche));
        Tablero tablero = new Tablero(jugadores);
        partida(tablero);
    }
}
