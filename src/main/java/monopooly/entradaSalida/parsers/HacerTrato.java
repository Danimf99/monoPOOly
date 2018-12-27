package monopooly.entradaSalida.parsers;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionArgumentosIncorrectos;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HacerTrato implements Expresion {

    private static Pattern[] PATRONES = {
        Pattern.compile("trato (\\w+): cambiar \\((\\d+), (\\w+)\\)"),
            // Trato 0  - trato Luis: cambiar (2343243, Solar21)
        Pattern.compile("trato (\\w+): cambiar \\((\\w+), (\\d+)\\)"),
            // Trato 1  - trato Luis: cambiar (Solar14, 3234234)
        Pattern.compile("trato (\\w+): cambiar \\((\\w+), (\\w+)\\) y noalquiler\\((\\w+), (\\d+)\\)"),
            // Trato 2  - trato Luis: cambiar (Solar14, Solar10) y noalquiler(solar3, 4)
        Pattern.compile("trato (\\w+): cambiar \\((\\w+), (\\w+) y (\\d+)\\)"),
            // Trato 3  - trato Luis: cambiar (Solar14, Solar10 y 30000)
        Pattern.compile("trato (\\w+): cambiar \\((\\w+) y (\\d+), (\\w+)\\)"),
            // Trato 4  - trato Luis: cambiar (Solar14 y 34534543, Solar34)
        Pattern.compile("trato (\\w+): cambiar \\((\\w+), (\\w+)\\)")
            // Trato 5  - trato Luis: cambiar (Solar14, Solar10)
    };

    private ArrayList<Matcher> matchers;

    private int str2int(String string) throws ExcepcionArgumentosIncorrectos {
        try {
            return Integer.parseInt(string);
        } catch (NumberFormatException e) {
            throw new ExcepcionArgumentosIncorrectos("La cantidad '" + string + "',\n" +
                    "no es un número.");
        }
    }

    public HacerTrato(String[] comandoIntroducido) throws ExcepcionMonopooly {
        if (comandoIntroducido == null)
            throw new ExcepcionParametrosInvalidos("Parametros inválidos para el comando 'trato'");
        if (comandoIntroducido.length < 3)
            throw new ExcepcionArgumentosIncorrectos("Insuficientes argumentos para el comando 'trato'");

        this.matchers = new ArrayList<>();
        Arrays.stream(PATRONES).forEachOrdered(pattern -> matchers.add(pattern.matcher(String.join(" ", comandoIntroducido))));
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        Matcher m = matchers.stream().filter(Matcher::matches).findFirst().orElseThrow(ExcepcionArgumentosIncorrectos::new);
        Jugador jOrigen = Tablero.getPrompt().getJugador();
        Jugador jDestino = Tablero.getTablero().getJugador(m.group(0));
        Casilla propiedad1;
        Casilla propiedad2;

        switch (matchers.indexOf(m)) {
            case 0:
                break;
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            default:
                throw new ExcepcionArgumentosIncorrectos("No existe el trato introducido");
        }

    }
}
