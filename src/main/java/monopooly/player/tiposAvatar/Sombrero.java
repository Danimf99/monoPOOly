package monopooly.player.tiposAvatar;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.General;
import monopooly.configuracion.Posiciones;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionAcontecimiento;
import monopooly.excepciones.ExcepcionCarta;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Sombrero extends Avatar {

    public Sombrero(Jugador jugador){
        super(jugador);
    }


    private void elegirLadoInicio(int tirada) throws ExcepcionMonopooly {
        int posicion = this.getPosicion().getX();
        if (posicion < Posiciones.PARKING) {
            inicioOeste(tirada);
        } else {
            inicioEste(tirada);
        }
    }


    private void zigzag(int tirada, ArrayList<Posicion> posicionesComienzo, ArrayList<Posicion> posicionesFin) throws ExcepcionMonopooly {
        int comienzo = posicionesComienzo.indexOf(this.getPosicion());
        try {
            while (tirada > 0) {
                tirada--;
                this.moverAvatar(
                        posicionesFin.get(comienzo++)
                );
                if (tirada > 0) {
                    tirada--;
                    this.moverAvatar(
                            posicionesComienzo.get(++comienzo)
                    );
                }
            }
        } catch (IndexOutOfBoundsException e) {
            boolean salirOeste = Stream.of(
                    IntStream.range(Posiciones.PARKING - 3, Posiciones.PARKING +1 ),
                    IntStream.range(Posiciones.VE_A_LA_CARCEL, Posiciones.VE_A_LA_CARCEL + 5)
            ).flatMapToInt(x -> x).anyMatch(x -> x == this.getPosicion().getX());

            if (salirOeste) {
                this.getJugador().pagoSalida();
                this.moverAvatar(new Posicion(Posiciones.CARCEL));
                inicioOeste(tirada);
            } else {
                this.moverAvatar(new Posicion(Posiciones.VE_A_LA_CARCEL));
                inicioEste(tirada);
            }
        } catch (ExcepcionCarta e) {
            e.imprimeInfo();
            elegirLadoInicio(tirada);
        }
    }



    private void inicioOeste(int tirada) throws ExcepcionMonopooly {
        if (tirada == 0) {
            return;
        }
        ArrayList<Posicion> posicionesEste = Posiciones.posicionesEsteFromSalida();
        ArrayList<Posicion> posicionesOeste = Posiciones.posicionesOesteFromCarcel();
        posicionesOeste.remove(posicionesOeste.size() - 1); // No se cae en el Parking
        posicionesEste.remove(posicionesEste.size() -1); // Al llegar al final se vuelve para donde se sale
        posicionesEste.remove(0);  // No se cae en la salida

        if (this.getPosicion().getX() < Posiciones.CARCEL) {
            tirada--;
            this.moverAvatar(new Posicion(Posiciones.CARCEL + 1));
        }
        zigzag(tirada, posicionesOeste, posicionesEste);

    }

    private void inicioEste(int tirada) throws ExcepcionMonopooly {
        if (tirada == 0) {
            return;
        }
        ArrayList<Posicion> posicionesEste = Posiciones.posicionesEsteFromSalida();
        ArrayList<Posicion> posicionesOeste = Posiciones.posicionesOesteFromCarcel();
        posicionesOeste.remove(0);
        Collections.reverse(posicionesOeste);
        posicionesOeste.remove(0);

        posicionesEste.remove(0);
        Collections.reverse(posicionesEste);

        if (this.getPosicion().getX() < Posiciones.VE_A_LA_CARCEL) {
            tirada--;
            this.moverAvatar(new Posicion(Posiciones.VE_A_LA_CARCEL + 1));
        }
        zigzag(tirada, posicionesEste, posicionesOeste);
    }

    @Override
    public void pasarTurno() throws ExcepcionMonopooly {
        if (!this.isNitroso()) {
            super.pasarTurno();
            return;
        }
        int restantes = 3 - Tablero.getPrompt().getLanzamientosDados();
        if (restantes > 0) {
            throw new ExcepcionAccionInvalida("Aun no tiraste suficientes veces este turno.\n Te faltan " +
                            restantes + " lanzamientos."
            );
        }
        this.getSubject().eliminar(this);
    }


    @Override
    public TIPO getTipo() {
        return TIPO.sombrero;
    }

    @Override
    public void intentarComprar(Casilla casilla) throws ExcepcionMonopooly {
        if (this.isNitroso()) {
            Tablero.getPrompt().intentaCompraEspecial(casilla);
            return;
        }
        super.intentarComprar(casilla);
    }

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().getLanzamientosDados() >= 3) {
            throw new ExcepcionAccionInvalida("Ya tiraste 3 veces este turno, no puedes tirar más.");
        }
        this.preLanzamiento();
        if (this.getJugador().getDados().tirada() <= General.TIRADA_ROLLBACK) {
            try {
                this.restore();
                throw new ExcepcionAcontecimiento(
                        "Sacaste menos o igual que " + General.TIRADA_ROLLBACK + ".\n" +
                                "Vuelves atrás en el tiempo.\n"
                );
            } catch (EmptyStackException e) {
                throw new ExcepcionAcontecimiento("No realizaste acciones el anterior turno.\n" +
                        "No viajas en el tiempo");
            }
        }
        this.setOldPosicion(this.getPosicion());
        elegirLadoInicio(this.getJugador().getDados().tirada());
    }



    /*MOVIMIENTO ESPECIAL PARA CADA AVATAR*/

    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Sombrero"+
                super.toString()
                +"\n}");
    }
}
