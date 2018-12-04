package monopooly.cartas;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Comunes {
    public static int darDineroTodos(Prompt prompt, int dinero) {
        // Por cada jugador quitar dinero y darselo a otro
        ArrayList<Jugador> jugadores = prompt.getTablero().getJugadoresTurno();
        Jugador actual = prompt.getJugador();
        int mod_dinero = 0;
        for (Jugador jugador : jugadores) {
            if (!jugador.equals(actual)) {
                jugador.anhadirDinero(dinero);
                jugador.getEstadisticas().sumarInversionesBote(dinero);
                actual.getEstadisticas().sumarTasas(dinero);
                actual.quitarDinero(dinero);
                mod_dinero -= dinero;
            }
        }
        return dinero;
    }

    public static void pagoSalidaDesplazamiento(Prompt prompt, Posicion posicion) {
        Jugador actual = prompt.getJugador();
        if (actual.getAvatar().getPosicion().pasoPorSalida()
                && !posicion.equals(new Posicion(Posiciones.CARCEL))) {
            actual.anhadirDinero(Precios.SALIDA);
            prompt.setModificacionPasta(Precios.SALIDA, "Pasaste por la casilla de salida");
            actual.getEstadisticas().sumarDineroSalida(Precios.SALIDA);
            actual.aumentarVueltas();
        }
    }

    public static void desplazar(Prompt prompt, int desplazamiento) {
        Jugador actual = prompt.getJugador();
        Tablero tablero =prompt.getTablero();
        actual.moverJugador(prompt.getTablero(), desplazamiento);
        tablero.getCasilla(actual.getAvatar().getPosicion()).getCalle().aumentarVecesFrecuentado();
        pagoSalidaDesplazamiento(prompt, actual.getAvatar().getPosicion());
    }

    public static void desplazar(Prompt prompt, Posicion posicion) {
        Jugador actual = prompt.getJugador();
        Tablero tablero =prompt.getTablero();
        actual.moverJugador(prompt.getTablero(), posicion);
        tablero.getCasilla(actual.getAvatar().getPosicion()).getCalle().aumentarVecesFrecuentado();
        pagoSalidaDesplazamiento(prompt, posicion);
    }

    public static void darDinero(Prompt prompt, int dinero) {
        Jugador actual = prompt.getJugador();
        actual.anhadirDinero(dinero);
        actual.getEstadisticas().sumarInversionesBote(dinero);
        prompt.setModificacionPasta(dinero, "Carta especial.");
    }

}
