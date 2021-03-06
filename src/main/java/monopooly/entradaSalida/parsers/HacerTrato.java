package monopooly.entradaSalida.parsers;

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
        Pattern.compile("trato (.*): cambiar \\((.*), (.*)\\) y noalquiler\\((.*), (\\d+)\\)"),
            // Trato 0  - trato Dani: cambiar (Solar14, Solar10) y noalquiler(solar3, 4)
        Pattern.compile("trato (.*): cambiar \\((\\d+), (.*)\\)"),
            // Trato 1  - trato Dani: cambiar (2343243, Solar21)
        Pattern.compile("trato (.*): cambiar \\((.*), (\\d+)\\)"),
            // Trato 2  - trato Saul: cambiar (Solar14, 3234234)
        Pattern.compile("trato (.*): cambiar \\((.*), (.*) y (\\d+)\\)"),
            // Trato 3  - trato Saul: cambiar (Solar14, Solar10 y 30000)
        Pattern.compile("trato (.*): cambiar \\((.*) y (\\d+), (.*)\\)"),
            // Trato 4  - trato Dani: cambiar (Solar14 y 34534543, Solar34)
        Pattern.compile("trato (.*): cambiar \\((.*), (.*)\\)")
            // Trato 5  - trato Saul: cambiar (a coruña, Lugo)
    };

    private ArrayList<Matcher> matchers;
    private Matcher matcher;

    private static int str2int(String string) throws ExcepcionArgumentosIncorrectos {
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

        Arrays.stream(PATRONES)
                .forEachOrdered(
                        pattern -> matchers.add(pattern.matcher(String.join(" ", comandoIntroducido)))
                );

        this.matcher = matchers.stream()
                .filter(Matcher::matches)
                .findFirst().orElseThrow(ExcepcionArgumentosIncorrectos::new);
    }

    @Override
    public void interpretar(Juego interprete) throws ExcepcionMonopooly {
        Jugador jOrigen = Tablero.getPrompt().getJugador();
        Jugador jDestino = Tablero.getTablero().getJugador(matcher.group(1));
        Tablero t = Tablero.getTablero();

        switch (matchers.indexOf(matcher)) {
            case 0:
                interprete.hacerTrato6(jOrigen,jDestino,
                        t.getPropiedad(matcher.group(2)),
                        t.getPropiedad(matcher.group(3)),
                        t.getPropiedad(matcher.group(4)),
                        str2int(matcher.group(5)));
                break;
            case 1:
                interprete.Hacertrato3(jOrigen, jDestino,
                        str2int(matcher.group(2)),
                        t.getPropiedad(matcher.group(3))
                );
                break;
            case 2:
                interprete.Hacertrato2(jOrigen, jDestino,
                        t.getPropiedad(matcher.group(2)),
                        str2int(matcher.group(3))
                );
                break;
            case 3:
                interprete.hacerTrato5(jOrigen, jDestino,
                        t.getPropiedad(matcher.group(2)),
                        str2int(matcher.group(3)),
                        t.getPropiedad(matcher.group(4))
                );
                break;
            case 4:
                interprete.hacerTrato4(jOrigen, jDestino,
                        t.getPropiedad(matcher.group(2)),
                        str2int(matcher.group(3)),
                        t.getPropiedad(matcher.group(4))
                        );
                break;
            case 5:
                interprete.Hacertrato1(jOrigen, jDestino,
                        t.getPropiedad(matcher.group(2)),
                        t.getPropiedad(matcher.group(3))
                        );
                break;
            default:
                throw new ExcepcionArgumentosIncorrectos("No existe el trato introducido");
        }
    }
}
