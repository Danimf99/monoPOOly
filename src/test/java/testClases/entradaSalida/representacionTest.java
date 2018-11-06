package testClases.entradaSalida;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.entradaSalida.Prompt;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;
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
        monguer = new Jugador("pepe", TipoAvatar.sombrero);
        ArrayList<Jugador> listaJugadores = new ArrayList<>();
        listaJugadores.add(monguer);
        listaJugadores.add(new Jugador("manolo", TipoAvatar.esfinge));
        listaJugadores.add(new Jugador("federico", TipoAvatar.esfinge));
        listaJugadores.add(new Jugador("jaime", TipoAvatar.esfinge));
        listaJugadores.add(new Jugador("dani", TipoAvatar.esfinge));
        listaJugadores.add(new Jugador("saul", TipoAvatar.esfinge));
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

    @Test
    public void mensajesMultiLinea() {
        Mensajes.error("Primera linea\nSegunda linea.");
        Mensajes.info("Primera linea\nSegunda linea.");
        Mensajes.detalles("Primera linea\nSegunda linea.");
    }

    @Test
    public void imprimePropiedades() {
        System.out.println(tableroPrueba.getCasilla(new Posicion(8)).getCalle());
    }
}
