package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.Comprar;
import monopooly.entradaSalida.parsers.Expresion;
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
            Juego.consola.error("No hay jugadores suficientes para iniciar la partida",
                    "No se pudo iniciar la partida");
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
            Juego.consola.imprimir(Tablero.getPrompt().toString());
            exp = null;
            comandoIntroducido = Juego.consola.leer();
            args = comandoIntroducido.split(" ");

            switch (args[0].toLowerCase()) {  // Classic switch de comandos
                case "comprar":
                case "c":
                    exp = new Comprar(args);
                    exp.interpretar(this.interprete);
                    break;

                case "pasar":
                case "p":
                    Tablero.getTablero().pasarTurno();
                    break;

                default:
                    Juego.consola.error(
                            "El comando introducido es incorrecto",
                            "Expresion desconodida"
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
