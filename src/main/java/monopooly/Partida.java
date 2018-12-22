package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.*;
import monopooly.player.Jugador;

class Partida { // Package Private

    private Juego interprete;

    public Partida(Juego interprete) {
        this.interprete = interprete;
    }

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
            Juego.consola.imprimir(Tablero.getPrompt().toString());
            comandoIntroducido = Juego.consola.leer();
            args = comandoIntroducido.split(" ");

            switch (args[0].toLowerCase()) {  // Classic switch de comandos
                case "comprar":
                case "Comprar":
                case "c":
                    exp = new Comprar(args);
                    exp.interpretar(interprete);
                    break;
                case "info":
                case "informacion":
                    exp=new Info(args);
                    exp.interpretar(interprete);
                    break;
                case "hipotecar":
                case "Hipotecar":
                    exp=new Hipotecar(args);
                    exp.interpretar(interprete);
                    break;
                case "deshipotecar":
                case "Deshipotecar":
                    exp=new Deshipotecar(args);
                    exp.interpretar(interprete);
                    break;

                case "acabar":
                case "a":
                    Tablero.getTablero().pasarTurno();
                    break;
                default:
                    Juego.consola.error(
                            "El coomando introducido es incorrecto",
                            "Expresion desconocida"
                    );
                    Tablero.getPrompt().setHelp(true);
                    break;

            }

            // Se termina el switch y se analiza la expresion

            if (exp != null) {
                exp.interpretar(interprete);
            }
        } while (Tablero.getTablero().jugadoresRestantes() > 1);

    }

    /**
     * Finaliza la partida mostrando el ganador y demas
     */
    private void fin() {

    }
}
