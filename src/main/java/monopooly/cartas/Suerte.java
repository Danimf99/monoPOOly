package monopooly.cartas;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.calles.Edificaciones;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Carta;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;

import static monopooly.cartas.Comunes.*;

public class Suerte {
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
        carta11,
        carta12,
        carta13,
        carta14
    }

    private static final String MENSAJE_PROMPT = "Carta de Suerte";

    private String mensaje;
    private Suerte.NumeracionCartas numero;

    public Suerte(String mensaje, Suerte.NumeracionCartas numero) {
        this.mensaje = mensaje;
        this.numero = numero;
    }


    public void ejecutarCarta(Prompt prompt) {
        switch (numero) {
            case carta1:
                this.accionCarta1(prompt);
                break;
            case carta2:
                this.accionCarta2(prompt);
                break;
            case carta3:
                this.accionCarta3(prompt);
                break;
            case carta4:
                this.accionCarta4(prompt);
                break;
            case carta5:
                this.accionCarta5(prompt);
                break;
            case carta6:
                this.accionCarta6(prompt);
                break;
            case carta7:
                this.accionCarta7(prompt);
                break;
            case carta8:
                this.accionCarta8(prompt);
                break;
            case carta9:
                this.accionCarta9(prompt);
                break;
            case carta10:
                this.accionCarta10(prompt);
                break;
            case carta11:
                this.accionCarta11(prompt);
                break;
            case carta12:
                this.accionCarta12(prompt);
                break;
            case carta13:
                this.accionCarta13(prompt);
                break;
            case carta14:
                this.accionCarta14(prompt);
                break;
        }
        Mensajes.info(this.mensaje, "Carta de Suerte");
    }


    private void accionCarta1(Prompt prompt) {
        desplazar(prompt, new Posicion(5));
    }

    private void accionCarta2(Prompt prompt) {
        prompt.getJugador().moverJugador(prompt, new Posicion(18));
    }

    private void accionCarta3(Prompt prompt) {
        darDinero(prompt, Carta.Suerte.Carta3.DINERO);
    }

    private void accionCarta4(Prompt prompt) {
        desplazar(prompt, new Posicion(21));
    }

    private void accionCarta5(Prompt prompt) {

    }

    private void accionCarta6(Prompt prompt) {

    }

    private void accionCarta7(Prompt prompt) {

    }


    private void accionCarta8(Prompt prompt) {
        Jugador actual = prompt.getJugador();
        int modDinero = 0;
        for (Inmueble propiedad : actual.getPropiedades()) {
            for (Edificaciones edificio : propiedad.getEdificios()) {
                switch (edificio.getTipo()) {
                    case casa:
                        modDinero -= Carta.Suerte.Carta8.DINERO_CASA;
                        break;
                    case hotel:
                        modDinero -= Carta.Suerte.Carta8.DINERO_HOTEL;
                        break;
                    case deporte:
                        modDinero -= Carta.Suerte.Carta8.DINERO_PISCINA;
                        break;
                    case piscina:
                        modDinero -= Carta.Suerte.Carta8.DINERO_PISTA_DEPORTE;
                        break;
                }
            }
        }
        actual.getEstadisticas().sumarTasas(-modDinero);
        actual.quitarDinero(-modDinero);
        prompt.setModificacionPasta(modDinero, MENSAJE_PROMPT);
    }

    private void accionCarta9(Prompt prompt) {

    }

    private void accionCarta10(Prompt prompt) {
        int dinero = Carta.Suerte.Carta10.DINERO;
        prompt.setModificacionPasta(darDineroTodos(prompt, dinero), MENSAJE_PROMPT);
    }

    private void accionCarta11(Prompt prompt) {

    }

    private void accionCarta12(Prompt prompt) {

    }

    private void accionCarta13(Prompt prompt) {

    }

    private void accionCarta14(Prompt prompt) {

    }

}
