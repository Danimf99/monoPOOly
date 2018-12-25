package monopooly.player.tiposAvatar;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Pelota extends Avatar {

    public Pelota(Jugador jugador){
        super(jugador);
    }

    @Override
    public void pasarTurno() throws ExcepcionMonopooly {
        if (!this.isNitroso()) {
            super.pasarTurno();
            return;
        }

        if (!Tablero.getPrompt().isUsoMovEspecial()) {
            throw new ExcepcionAccionInvalida("Aun no tiraste este turno");
        }
    }


    @Override
    public TIPO getTipo() {
        return TIPO.pelota;
    }

    @Override
    public void moverBasico() throws ExcepcionMonopooly {
        super.moverBasico();
    }


    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/


    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().isUsoMovEspecial()) {
            throw new ExcepcionAccionInvalida("Ya tiraste este turno");
        }
        Tablero.getPrompt().setUsoMovEspecial(true);
        this.preLanzamiento();
        int tirada = this.getJugador().getDados().tirada();
        boolean avanzar = tirada > 4;

        this.moverAvatar(avanzar ? 5 : -1);
        for (int i = avanzar ? 6 : 2; i <= tirada; i++)
            if (i % 2 != 0) this.moverAvatar(avanzar ? 2 : -2);
            else if (i == tirada) this.moverAvatar(avanzar ? 1 : -1);
    }

    @Override
    public void intentarComprar(Casilla casilla) throws ExcepcionMonopooly {
        if (this.isNitroso()) {
            if (!Tablero.getPrompt().pisoCasilla(casilla)) {
                throw new ExcepcionAccionInvalida(
                        "No pasaste por la casilla '" + casilla.getNombre() + "'\n" +
                                "este turno."
                );
            }
            return;
        }
        super.intentarComprar(casilla);
    }




    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Pelota"+
                super.toString()
                +"\n}");
    }
}
