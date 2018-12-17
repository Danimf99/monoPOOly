package monopooly;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.parsers.Comprar;
import monopooly.entradaSalida.parsers.Expresion;

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
        Juego.consola.imprimir("caracola");
        pedirJugadores();

//        if (Tablero.getTablero().jugadoresRestantes() < 2) {
//            // Comprueba que el tablero tiene los jugadores necesarios
//            // Tirar excepcion
//            return;
//        }

        // Iniciar el handler de la partida
        handler();

        // Mostrar ganador / Terminar
        fin();
    }

    /**
     * Pide jugadores por pantalla y los introduce en el tablero
     */
    private void pedirJugadores(){

    }

    /**
     * Controla el flujo de la partida
     */
    private void handler() {
        Juego interprete = new Juego();
        String comandoIntroducido;
        String[] args;
        Expresion exp = null;

        do { // Bucle de partida
            do { // Bucle de turno
                comandoIntroducido = Juego.consola.leer();
                args = comandoIntroducido.split(" ");

                switch (args[0].toLowerCase()) {  // Classic switch de comandos
                    case "comprar":
                        exp = new Comprar(comandoIntroducido);
                        break;

                    default:
                        Tablero.getPrompt().setHelp(true);
                        break;

                }

                // Se termina el switch y se analiza la expresion

                if (exp != null) {
                    exp.interpretar(interprete);
                }


                Tablero.getTablero().pasarTurno();
            } while (true);
        } while (Tablero.getTablero().jugadoresRestantes() > 1);

    }

    /**
     * Finaliza la partida mostrando el ganador y demas
     */
    private void fin() {

    }
}
