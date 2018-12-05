package monopooly.cartas;

import monopooly.colocacion.Posicion;
import monopooly.configuracion.Carta;
import monopooly.configuracion.Posiciones;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;

import static monopooly.cartas.Comunes.darDinero;
import static monopooly.cartas.Comunes.darDineroTodos;

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

    private static final String MENSAJE_PROMPT = "Carta de Caja de comunidad";

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
        Mensajes.info(this.mensaje, "Carta de Caja de comunidad");
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
                break;
        }

    }


    private void accionCarta1(Prompt prompt) {
        darDinero(prompt, -Carta.Comunidad.Carta1.DINERO);
    }

    private void accionCarta2(Prompt prompt) {
        Jugador actual = prompt.getJugador();
        actual.moverJugador(prompt, new Posicion(Posiciones.CARCEL));
        actual.setEstarEnCarcel(true);
    }

    private void accionCarta3(Prompt prompt) {
        Jugador actual = prompt.getJugador();
        actual.moverJugador(prompt, new Posicion(Posiciones.SALIDA));
    }

    private void accionCarta4(Prompt prompt) {
        int dinero = Carta.Comunidad.Carta4.DINERO;
        Jugador actual = prompt.getJugador();
        darDinero(prompt, dinero);
    }

    private void accionCarta5(Prompt prompt) {
        darDinero(prompt, -Carta.Comunidad.Carta5.DINERO);
    }

    private void accionCarta6(Prompt prompt) {

    }

    private void accionCarta7(Prompt prompt) {

    }


    private void accionCarta8(Prompt prompt) {
        int dinero = Carta.Comunidad.Carta8.DINERO;
        prompt.setModificacionPasta(darDineroTodos(prompt, dinero), MENSAJE_PROMPT);
    }


    private void accionCarta9(Prompt prompt) {

    }


    private void accionCarta10(Prompt prompt) {

    }


}
