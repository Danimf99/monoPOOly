package monopooly.excepciones;

import monopooly.cartas.Carta;
import monopooly.cartas.Suerte;
import monopooly.entradaSalida.Juego;

public class ExcepcionCarta extends ExcepcionMonopooly {
    private Carta carta;
    public ExcepcionCarta(String message, Carta carta) {
        super(message);
        this.carta = carta;
    }

    public void imprimeInfo() {
        String titulo = "Carta de Caja de Comunidad";
        if (carta instanceof Suerte) {
            titulo = "Carta de Suerte";
        }
        Juego.consola.info(
                this.getMessage(),
                titulo
        );
    }
}
