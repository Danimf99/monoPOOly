package monopooly;

import monopooly.colocacion.Tablero;
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
        // Meter los jugadores en el tablero
        pedirJugadores();

        if (Tablero.getTablero().jugadoresRestantes() < 2) {
            // Comprueba que el tablero tiene los jugadores necesarios
            // Tirar excepcion
            return;
        }

        // Iniciar el handler de la partida
        handler();

        // Mostrar ganador / Terminar
        fin();
    }

    /**
     * Pide jugadores por pantalla y los introduce en el tablero
     */
    private void pedirJugadores(){
        Tablero.getTablero().meterJugador(new Jugador("Dani", Avatar.TIPO.esfinge));
        Tablero.getTablero().meterJugador(new Jugador("Saul", Avatar.TIPO.esfinge));
        //Tablero.getTablero().meterJugador(new Jugador("Lola", Avatar.TIPO.esfinge));
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
                        exp=new Edificar(args);
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

    }

    /**
     * Finaliza la partida mostrando el ganador y demas
     */
    private void fin() {

    }
}
