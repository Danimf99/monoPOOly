package monopooly.player.tiposAvatar;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Posiciones;
import monopooly.entradaSalida.Juego;
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

public class Esfinge extends Avatar {


    public Esfinge(Jugador jugador){
        super(jugador);
    }

    @Override
    public TIPO getTipo() {
        return TIPO.esfinge;
    }

    @Override
    public void pasarTurno() throws ExcepcionMonopooly {
        if (!this.isNitroso()) {
            super.pasarTurno();
            return;
        }

        int restantes = 3 - Tablero.getPrompt().getLanzamientosDados();

        if (restantes > 0) {
            throw new ExcepcionAccionInvalida(
                    "Aun no tiraste suficientes veces este turno.\n" +
                            "Te faltan " + restantes + " lanzamientos."
            );
        }
    }


    private void inicioSur(int tirada) throws ExcepcionMonopooly {
        if (tirada == 0) {
            return;
        }
        ArrayList<Posicion> posicionesNorte = Posiciones.posicionesNorteIzqDer();
        ArrayList<Posicion> posicionesSur = Posiciones.posicionesSurIzqDer();
        posicionesSur.remove(0); // No se cae en la carcel
        posicionesNorte.remove(0); // Si se va al parking en teoria se vuelve para la salida
        Collections.reverse(posicionesNorte);
        Collections.reverse(posicionesSur);
        posicionesNorte.remove(0); // No se cae en ve a la carcel

        if (this.getPosicion().getX() > Posiciones.VE_A_LA_CARCEL) {
            tirada--;
            this.moverAvatar(new Posicion(Posiciones.SALIDA + 1));
        }
        zigzag(tirada, posicionesSur, posicionesNorte);

    }

    private void inicioNorte(int tirada) throws ExcepcionMonopooly {
        if (tirada == 0) {
            return;
        }
        ArrayList<Posicion> posicionesNorte = Posiciones.posicionesNorteIzqDer();
        ArrayList<Posicion> posicionesSur = Posiciones.posicionesSurIzqDer();
        posicionesNorte.remove(posicionesNorte.size() - 1); //  No se cae en ve a la carcel
        posicionesSur.remove(posicionesSur.size() - 1); // Si se cae en la salida realmente se va para el parking
        posicionesSur.remove(0); // No se cae en la carcel
        if (this.getPosicion().getX() < Posiciones.PARKING) {
            tirada--;
            this.moverAvatar(new Posicion(Posiciones.PARKING + 1));
        }
        zigzag(tirada, posicionesNorte, posicionesSur);

    }

    private void zigzag(int tirada, ArrayList<Posicion> posicionesComienzo, ArrayList<Posicion> posicionesFin) throws ExcepcionMonopooly {
        int comienzo = posicionesComienzo.indexOf(this.getPosicion());
        try {
            while (tirada > 0) {
                if (tirada --> 0) this.moverAvatar(
                        posicionesFin.get(comienzo++)
                );
                if (tirada --> 0) this.moverAvatar(
                        posicionesComienzo.get(++comienzo)
                );
            }
        } catch (IndexOutOfBoundsException e) {
            this.getJugador().pagoSalida();
            boolean salirNorte = Stream.of(
                    IntStream.range(Posiciones.VE_A_LA_CARCEL - 5, Posiciones.VE_A_LA_CARCEL),
                    IntStream.range(Posiciones.SALIDA, Posiciones.SALIDA + 5)
            ).flatMapToInt(x -> x).anyMatch(x -> x == this.getPosicion().getX());

            if (salirNorte) {
                this.moverAvatar(new Posicion(Posiciones.PARKING));
                inicioNorte(tirada);
            } else {
                this.moverAvatar(new Posicion(Posiciones.SALIDA));
                inicioSur(tirada);
            }
        } catch (ExcepcionCarta e) {
            e.imprimeInfo();
            elegirLadoInicio(tirada);
        }
    }

    private void elegirLadoInicio(int tirada) throws ExcepcionMonopooly {
        int posicion = this.getPosicion().getX();
        if (posicion >= Posiciones.VE_A_LA_CARCEL || posicion < Posiciones.CARCEL) {
            // Estrictamente si estuviera en ve a la carcel empezaría en el lado sur. Anque nunca habrá un avatar ahi
            inicioSur(tirada);
        } else {
            inicioNorte(tirada);
        }
    }

    @Override
    public void moverAvanzado() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().getLanzamientosDados() >= 3) {
            throw new ExcepcionAccionInvalida("Ya tiraste 3 veces este turno, no puedes tirar más.");
        }

        this.preLanzamiento();

        int tirada = this.getJugador().getDados().tirada();

        if (tirada < 4) {
            try {
                this.restore();
                throw new ExcepcionAcontecimiento(
                        "Sacaste menos de 4.\n" +
                                "Vuelves atrás en el tiempo.\n"
                );
            } catch (EmptyStackException e) {
                throw new ExcepcionAcontecimiento("No realizaste acciones el anterior turno.\n" +
                        "No viajas en el tiempo");
            }
        }
        this.setOldPosicion(this.getPosicion());
        elegirLadoInicio(tirada);
    }

    @Override
    public void moverBasico() throws ExcepcionMonopooly {
        super.moverBasico();
    }

    public String toString(){
        return PintadoAscii.encuadrar("Avatar{\n"+
                "   Tipo: Esfinge"+
                super.toString()
                +"\n}");
    }
}
