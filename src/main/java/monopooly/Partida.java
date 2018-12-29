package monopooly;

import javafx.scene.control.Tab;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.General;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.*;
import monopooly.excepciones.ExcepcionAcontecimiento;
import monopooly.excepciones.ExcepcionComando;
import monopooly.excepciones.ExcepcionComandoInexistente;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Partida {

    public static final Juego interprete = new Juego();


    /**
     * Inicia la partida
     */
    void init() { // Package Private

        Juego.consola.imprimirln(ReprASCII.ASCII_TITLE);

        // Meter los jugadores en el tablero
        pedirJugadores();

        if (Tablero.getTablero().jugadoresRestantes() < 2) {
            Juego.consola.error("No hay suficientes jugadores para empezar la partida.", "Error inicializando la partida");
            pedirJugadores();
        }

        Juego.consola.imprimirln("\n\n");

        // Iniciar el handler de la partida
        handler();

        // Mostrar ganador / Terminar
        fin();
    }

    /**
     * Pide jugadores por pantalla y los introduce en el tablero
     */
    private void pedirJugadores(){
        Tablero tablero = Tablero.getTablero();
        String cantidadJugadores = Juego.consola.leer(ReprASCII.PROMPT_NUEVA_PARTIDA);
        int cantidad = 0;
        try {
            cantidad = Integer.parseInt(cantidadJugadores);
        } catch (NumberFormatException e) {
            Juego.consola.error("No se reconoce '" + cantidadJugadores + "'\n" +
                    "como un número.", "Error de entrada");
            pedirJugadores();
            return;
        }
        if (!(cantidad > 0)) {
            Juego.consola.error("La cantidad '" + cantidad + "'\n" +
                    "no es valida.", "Error de entrada");
            pedirJugadores();
            return;
        }
        String nombre;
        String avatar;
        for (int i = 0; i < cantidad; i++) {
            nombre = Juego.consola.leer(ReprASCII.PROMPT_NOMBRE_JUGADOR);
            Avatar.TIPO tipo = null;
            while (tipo == null) {
                Juego.consola.imprimir(ReprASCII.PROMT_AYUDA_TIPO_AVATAR);
                avatar = Juego.consola.leer(ReprASCII.PROMPT_TIPO_AVATAR);
                switch (avatar.toLowerCase()) {
                    case "esfinge":
                        tipo = Avatar.TIPO.esfinge;
                        break;
                    case "sombrero":
                        tipo = Avatar.TIPO.sombrero;
                        break;
                    case "coche":
                        tipo = Avatar.TIPO.coche;
                        break;
                    case "pelota":
                        tipo = Avatar.TIPO.pelota;
                        break;
                    default:
                        Juego.consola.error("Vuelva a introducir el tipo de avatar", "Error de entrada");
                        break;
                }
            }
            tablero.meterJugador(new Jugador(nombre, tipo));
            Juego.consola.imprimir("\n");
        }
    }

    /**
     * Controla el flujo de la partida
     */
    private void handler() {
        String comandoIntroducido;
        String[] args;
        Expresion exp;
        Juego.consola.imprimirln(Tablero.getTablero().toString());
        do { // Bucle de partida
            exp = null;
            Juego.consola.imprimir(Tablero.getPrompt().toString());
            comandoIntroducido = Juego.consola.leer();
            args = comandoIntroducido.split(" ");
            Tablero.getPrompt().setHelp(false);
            try {
                switch (args[0].toLowerCase()) {  // Classic switch de comandos
                    case "comprar":
                    case "c":
                        exp = new Comprar(args);
                        break;
                    case "aceptar":
                        exp=new AceptarTrato(args);
                        break;
                    case "bancarrota":
                        exp=new Bancarrota(args);
                        break;
                    case "eliminar":
                        exp=new EliminarTrato(args);
                        break;
                    case "estadisticas":
                    case "estadísticas":
                        exp=new Estadisticas(args);
                        break;
                    case "trato":
                        exp=new HacerTrato(args);
                        break;
                    case "edificar":
                        exp=new Edificar(args,false);
                        break;
                    case "listar":
                        exp=new Listar(args);
                        break;
                    case "salir":
                        exp=new SalirCarcel(args);
                        break;
                    case "cambiar":
                        exp=new CambiarModo(args);
                        break;
                    case "info":
                    case "informacion":
                        exp=new Info(args);
                        break;
                    case "hipotecar":
                        exp=new Hipotecar(args);
                        break;
                    case "deshipotecar":
                        exp=new Deshipotecar(args);
                        break;
                    case "describir":
                        exp=new Describir(args);
                        break;
                    case "vender":
                        exp=new Vender(args);
                        break;
                    case "ver":
                        exp=new VerTablero(args);
                        break;
                    case "acabar":
                    case "a":
                        Tablero.getTablero().pasarTurno();
                        Juego.consola.imprimirln(Tablero.getTablero().toString());
                        break;
                    case "tratos":
                        exp=new VerTratos(args);
                        break;
                    case "lanzar":
                        exp=new Lanzar(args);
                        break;
                    default:
                        throw new ExcepcionComandoInexistente(comandoIntroducido);
                }
            } catch (ExcepcionComandoInexistente e) {
                Tablero.getPrompt().setHelp(true);
                e.imprimeError();
            } catch (ExcepcionMonopooly e) {
                e.imprimeError();
            }
            // Se termina el switch y se analiza la expresion

            if (exp != null) {
                try {
                    exp.interpretar(interprete);
                } catch (ExcepcionAcontecimiento e) {
                    e.imprimeInfo();
                } catch (ExcepcionComando e) {
                    Tablero.getPrompt().setHelp(true);
                    e.imprimeError();
                } catch (ExcepcionMonopooly e) {
                    e.imprimeError();
                }
            }
        } while (Tablero.getTablero().jugadoresRestantes() > 1);
        fin();
    }

    /**
     * Finaliza la partida mostrando el ganador y demas
     */
    private void fin() {
        Juego.consola.info("El ganador de la partida es "+Tablero.getTablero().getJugadores().get(0),"La partida ha finalizado");
        Juego.consola.imprimir(Tablero.getTablero().getJugadores().get(0).toString());
    }
}
