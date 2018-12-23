package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.*;
import monopooly.excepciones.*;
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
        Tablero.getTablero().meterJugador(new Jugador("Dani", "Coche"));
        Tablero.getTablero().meterJugador(new Jugador("Saul", "Pelota"));
        Tablero.getTablero().meterJugador(new Jugador("Lola", "Coche"));
    }

    /**
     * Controla el flujo de la partida
     */
    private void handler() {
        String comandoIntroducido;
        String[] args;
        Expresion exp;

        do { // Bucle de partida
            exp = null;
            Juego.consola.imprimirln(Tablero.getTablero().toString());
            Juego.consola.imprimir(Tablero.getPrompt().toString());
            comandoIntroducido = Juego.consola.leer();
            args = comandoIntroducido.split(" ");
            try {
                switch (args[0].toLowerCase()) {  // Classic switch de comandos
                    case "comprar":
                    case "c":
                        exp = new Comprar(args);
                        break;
                    case "Bancarrota":
                        exp=new Bancarrota(args);
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
                    case "ver":
                        exp=new VerTablero(args);
                        break;
                    case "acabar":
                    case "a":
                        if(Tablero.getPrompt().getJugador().getDados().getContador()==1){
                            Tablero.getPrompt().getJugador().getDados().setContador(0);
                            Tablero.getTablero().pasarTurno();
                        }
                        break;
                    case "lanzar":
                        exp=new Lanzar(args);
                        break;
                    default:
                        throw new ExcepcionComandoInexistente(comandoIntroducido);
                }
            } catch (ExcepcionComandoInexistente e) {
                Tablero.getPrompt().setHelp(true);
                Juego.consola.error(
                        e.getMessage(),
                        "El comando no existe"
                );
            }


            // Se termina el switch y se analiza la expresion

            if (exp != null) {
                try {
                    exp.interpretar(interprete);
                } catch (ExcepcionAccionInvalida e) {
                    Juego.consola.error(
                            e.getMessage(),
                            "Accion invalida"
                    );
                } catch (ExcepcionArgumentosIncorrectos e) {
                    Juego.consola.error(
                            e.getMessage(),
                            "Argumentos del mensaje incorrectos"
                    );
                } catch (ExcepcionComando excepcionComando) {
                    Juego.consola.error(
                            excepcionComando.getMessage(),
                            "Error interpretando el comando"
                    );
                } catch (ExcepcionParametrosInvalidos e) {
                    Juego.consola.error(
                            e.getMessage(),
                            "Parametros invalidos"
                    );

                } catch (ExcepcionMonopooly excepcionMonopooly) {
                    Juego.consola.error(excepcionMonopooly.getMessage());
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
