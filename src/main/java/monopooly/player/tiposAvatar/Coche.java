package monopooly.player.tiposAvatar;

import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public class Coche extends Avatar {

    private int cooldown;

    public Coche(Jugador jugador){
        super(jugador);
        cooldown = 0;
    }

    @Override
    public void pasarTurno() throws ExcepcionMonopooly {
        if (!this.isNitroso()) {
            super.pasarTurno();
            return;
        }

        if (cooldown == 0 && Tablero.getPrompt().getLanzamientosDados() < 4) {
            throw new ExcepcionAccionInvalida("Aun no tiró todas las veces permitidas");
        }

        if (cooldown > 0) {
            this.cooldown--;
        }
        this.getSubject().eliminar(this);
    }


    @Override
    public TIPO getTipo() {
        return TIPO.coche;
    }

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        if (cooldown > 0) {
            throw new ExcepcionAccionInvalida("Aun no puedes realizar acciones, tienes\n" +
                    "que esperar " + cooldown + " turnos.");
        }

        if (Tablero.getPrompt().getLanzamientosDados() >= 4) {
            throw new ExcepcionAccionInvalida("Ya tiraste 4 veces este turno, no puedes tirar más.");
        }

        this.preLanzamiento();

        int tirada = getJugador().getDados().tirada();
        if (tirada > 4) {
            this.moverAvatar(tirada);
        } else {
            this.cooldown = 2;
            this.moverAvatar(-tirada);
        }
    }

    @Override
    public void setNitroso(boolean nitroso) throws ExcepcionAccionInvalida {
        if (cooldown > 0) {
            throw new ExcepcionAccionInvalida(
                    "Tienes que esperar " + cooldown + " turnos para volver a jugar"
            );
        }
        if (Tablero.getPrompt().getLanzamientosDados() > 0 || Tablero.getPrompt().getTiradasEspeciales() > 0) {
            throw new ExcepcionAccionInvalida("No puedes cambiar de modo despues de tirar.\n" +
                    "Inténtalo el próximo turno");
        }
        super.setNitroso(nitroso);
    }

    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    @Override
    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Coche"+
                super.toString()
                +"\n}");
    }
}
