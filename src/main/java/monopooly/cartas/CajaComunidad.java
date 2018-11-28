package monopooly.cartas;

import monopooly.configuracion.Carta;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class CajaComunidad {
    public enum NumeracionCartas{
        carta1,
        carta2,
        carta3,
        carta4,
        carta5,
        carta6,
        carta7,
        carta8,
        carta9,
        carta10,
    }

    private String mensaje;
    private NumeracionCartas numero;

    public CajaComunidad(String mensaje, NumeracionCartas numero) {
        this.mensaje = mensaje;
        this.numero = numero;
    }


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public NumeracionCartas getNumero() {
        return numero;
    }

    public void setNumero(NumeracionCartas numero) {
        this.numero = numero;
    }

    public void ejecutarCarta(Prompt prompt) {
        switch (numero) {
            case carta1:
                accionCarta1(prompt);
                break;
            case carta2:
                accionCarta2(prompt);
                break;
            case carta3:
                accionCarta3(prompt);
                break;
            case carta4:
                accionCarta4(prompt);
                break;
            case carta5:
                accionCarta5(prompt);
                break;
            case carta6:
                accionCarta6(prompt);
                break;
            case carta7:
                accionCarta7(prompt);
                break;
            case carta8:
                accionCarta8(prompt);
                break;
            case carta9:
                accionCarta9(prompt);
                break;
            case carta10:
                accionCarta10(prompt);
        }
    }


    private void accionCarta1(Prompt prompt) {

    }

    private void accionCarta2(Prompt prompt) {

    }

    private void accionCarta3(Prompt prompt) {

    }

    private void accionCarta4(Prompt prompt) {

    }

    private void accionCarta5(Prompt prompt) {

    }

    private void accionCarta6(Prompt prompt) {

    }

    private void accionCarta7(Prompt prompt) {

    }


    private void accionCarta8(Prompt prompt) {
        // Por cada jugador quitar dinero y darselo a otro
        int dinero = Carta.Comunidad.Carta8.DINERO;
        ArrayList<Jugador> jugadores = prompt.getTablero().getJugadoresTurno();
        Jugador actual = prompt.getJugador();
        int mod_dinero = 0;
        for (Jugador jugador : jugadores) {
            if (!jugador.equals(actual)) {
                jugador.anhadirDinero(dinero);
                actual.quitarDinero(dinero);
                mod_dinero -= dinero;
            }
        }
        // Avisar jugador mediante prompt
        prompt.setModificacionPasta(mod_dinero, "Carta Caja de Comunidad");
    }


    private void accionCarta9(Prompt prompt) {

    }


    private void accionCarta10(Prompt prompt) {

    }


}
