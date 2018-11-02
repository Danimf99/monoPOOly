package testClases.entradaSalida;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.Monopolio;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;
import monopooly.player.tipoAvatar;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

public class representacionTest {
    private Inmueble solarEjemplo;
    private static Tablero tableroPrueba;
    private static Jugador monguer;
    private static Prompt promptTest;

    @BeforeClass
    public static void preSetUp() {
        monguer = new Jugador("pepe", tipoAvatar.sombrero);
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(monguer);
        listaJugadores.add(new Jugador("manolo", tipoAvatar.esfinge));
        listaJugadores.add(new Jugador("federico", tipoAvatar.esfinge));
        listaJugadores.add(new Jugador("jaime", tipoAvatar.esfinge));
        listaJugadores.add(new Jugador("dani", tipoAvatar.esfinge));
        listaJugadores.add(new Jugador("saul", tipoAvatar.esfinge));
        tableroPrueba = new Tablero(listaJugadores);
    }

    @Before
    public void setUp() {
        promptTest = new Prompt(tableroPrueba, monguer);
    }

    @Ignore
    public void asciiArt() {
        for (String linea : ReprASCII.ASCII_TITLE_ARRAY) {
            System.out.println(linea + " " + linea.length());
        }
    }

    @Test
    public void imprimeTablero() {
        System.out.println(PintadoASCII.genTablero(tableroPrueba));
    }

    @Test
    public void moverJugadorRepresentar() {
        monguer.moverJugador(tableroPrueba);
        System.out.println(PintadoASCII.genTablero(tableroPrueba));
    }


}
