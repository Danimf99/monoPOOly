package monopooly.entradaSalida;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PintadoAscii {
    private static int asciiArtHelper; // Para centrar el logo del tablero


    public static String stringDinero(int cantidad) {
        return cantidad + " " + Precios.MONEDA;
    }

    /**
     * Añade espacios a un texto
     * @param texto texto que se desea modificar
     * @param longitudDeseada longitud que debe tener el texto añadiendole espacios al final
     * @return texto deseado
     */
    private static String widear(String texto, int longitudDeseada) {
        StringBuilder salida = new StringBuilder(texto);
        if (texto.contains(ReprASCII.ANSI_RESET)) {
            longitudDeseada += 2; // el color y el ansi reset
        }
        while (salida.length() < longitudDeseada) {
            salida.append(" ");
        }
        return salida.toString();
    }


    /**
     * Añade espacios a un texto
     * @param texto texto que se desea modificar
     * @param longitudDeseada longitud que debe tener el texto añadiendole espacios al final
     * @return texto deseado
     */
    private static String widearCentrado(String texto, int longitudDeseada) {
        StringBuilder salida = new StringBuilder();
        while (salida.length() < (longitudDeseada - texto.length()) / 2) {
            salida.append(" ");
        }
        salida.append(texto);
        return widear(salida.toString(), longitudDeseada);
    }


    /**
     * Dado un arrayList de strings, devuelve la la longitud de la mayor cadena
     *
     * @param lineas ArrayList de strings que se desean medir
     * @return Longitud mayor
     */
    private static int tamMaxArrayString(ArrayList<String> lineas) {
        return lineas.stream().mapToInt(String::length).max().orElse(0);
    }

    /**
     * Dibuja un marco al rededor de un texto. Respeta los saltos de linea
     * @param mensaje Mensaje que se desea enmarcar
     * @return Mensaje enmarcado
     */
    public static String encuadrar(String mensaje) {
        // Se necesita el ancho por que los modificadores de color
        //  cuentan como caracter
        StringBuilder sBuilder = new StringBuilder();
        String[] lins = mensaje.split("\n");
        ArrayList<String> lineas = new ArrayList<>(Arrays.asList(lins));

        int ancho = tamMaxArrayString(lineas);
        if (ancho < 8) {
            ancho = 9;
        }
        if (ancho % 2 == 0) { // Se asegura de que sea impar para que quede centrado
            ancho++;
        }

        for (String linea : lineas) {
            sBuilder.append(ReprASCII.BARRA_VERTICAL + " ");
            sBuilder.append(widear(linea, ancho));
            sBuilder.append(" "
                    + ReprASCII.BARRA_VERTICAL + "\n");
        }

        String out = sBuilder.toString();
        String topBar = ReprASCII.ESQUINA_1;
        String bottomBar = ReprASCII.ESQUINA_4;
        for (int i = 0; i < ancho + 2; i++) {  // Los espacios de margen
            topBar = topBar.concat(ReprASCII.BARRA_HORIZONTAL);
        }
        int anchoInferior = ((ancho - ReprASCII.APP_NAME.length() - 2)/ 2) + 1; // -2 por los espacios +1 por el margen
        for (int i = 0; i < anchoInferior; i++) {
            bottomBar = bottomBar.concat(ReprASCII.BARRA_HORIZONTAL);
        }
        bottomBar += " " + ReprASCII.ANSI_RED_BOLD + ReprASCII.APP_NAME + ReprASCII.ANSI_RESET + " ";


        for (int i = 0; i < anchoInferior; i++) {
            bottomBar = bottomBar.concat(ReprASCII.BARRA_HORIZONTAL);
        }

        topBar += ReprASCII.ESQUINA_2 + "\n";
        bottomBar += ReprASCII.ESQUINA_3 + "\n";

        return topBar + out + bottomBar;
    }

    public static String genEdificaciones(Solar calle) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Edificaciones en ");
        sBuilder.append(calle.getNombre());
        sBuilder.append(":\n");
        sBuilder.append(" \n");
        for (Edificio edificio : calle.getEdificios()) {
            sBuilder.append(PintadoAscii.genEdificio(edificio));
            sBuilder.append("\n");
        }
        return sBuilder.toString();
    }

    public static String genInfo(String mensaje, String titulo) {
        String out = ReprASCII.ANSI_BLUE_BOLD
                + "\n[i] "
                + ReprASCII.ANSI_RESET
                + titulo
                + "\n";
        return out + encuadrar(mensaje);
    }

    public static String genEdificio(Edificio edificio) {
        StringBuilder sBuilder = new StringBuilder();
        Solar inmuebleActual = edificio.getSolar();
        Jugador propietario = inmuebleActual.getPropietario();
        int maxLen = " Propietario: ".length();

        sBuilder.append(widear(" Id:", maxLen));
        sBuilder.append(edificio.getNombre());
        sBuilder.append("\n");

        sBuilder.append(widear(" Grupo:", maxLen));
        sBuilder.append(edificio.getSolar().getMonopolio().getTipo());
        sBuilder.append("\n");

        sBuilder.append(widear(" Tipo:", maxLen));
        sBuilder.append(edificio.getClass().getSimpleName());
        sBuilder.append("\n");

        sBuilder.append(widear(" Precio:", maxLen));
        sBuilder.append(edificio.getPrecio());
        sBuilder.append("\n");

        sBuilder.append(widear(" Propietario:", maxLen));
        sBuilder.append(propietario.getNombre());
        sBuilder.append("\n");

        sBuilder.append(widear(" Posicion:", maxLen));
        sBuilder.append(inmuebleActual.getNombre());
        sBuilder.append("\n");

        return sBuilder.toString();
    }
    public static String genCasilla(ArrayList<String> lineas, Casilla casilla) {
        lineas.add(""); // MArgen para que quede mejor
        int anchoRequerido = tamMaxArrayString(lineas) + 2; // +2 para que quede algo espaciado y bien colocado
        if (anchoRequerido < ReprASCII.APP_NAME.length()) {
            anchoRequerido = ReprASCII.APP_NAME.length();
        }
        if (anchoRequerido % 2 == 0) {
            anchoRequerido++; // Para que sea un numero impar y quede MonoPOOly bien centradito
        }
        StringBuilder sBuilder = new StringBuilder();

        // Barra superior
        sBuilder.append(ReprASCII.ESQUINA_1);

        // +2 por el margen de los espacios
        IntStream.range(0, anchoRequerido + 2).mapToObj(i -> ReprASCII.BARRA_HORIZONTAL).forEach(sBuilder::append);
        sBuilder.append(ReprASCII.ESQUINA_2 + "\n");

        // Informacion central
        for (int i = 0; i < lineas.size(); i++) {
            if (i == 2) { // Separador titulo e informacion calle
                sBuilder.append(ReprASCII.T_LADO_D);
                for (int j = 0; j < anchoRequerido + 2; j++) { // +2 por el margen de los espacios
                    sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
                }
                sBuilder.append(ReprASCII.T_LADO_B + "\n");
            }
            sBuilder.append(ReprASCII.BARRA_VERTICAL);
            if (i < 2) { // Color para el titulo
                sBuilder.append(ReprASCII.colorMonopolio(casilla.getTipo()));
            }
            sBuilder.append(" "); // Margen de un espacio entre la barra y el texto
            sBuilder.append(widearCentrado(lineas.get(i), anchoRequerido));
            sBuilder.append(" " + ReprASCII.ANSI_RESET);
            sBuilder.append(ReprASCII.BARRA_VERTICAL + "\n");
        }
        // Barra inferior
        sBuilder.append(ReprASCII.ESQUINA_4);

        // -2 por los espacios +1 por el margen
        int anchoInferior = ((anchoRequerido - ReprASCII.APP_NAME.length() - 2)/ 2) + 1;

        IntStream.range(0, anchoInferior).mapToObj(i -> ReprASCII.BARRA_HORIZONTAL).forEach(sBuilder::append);
        sBuilder.append(" " + ReprASCII.ANSI_RED_BOLD + ReprASCII.APP_NAME + ReprASCII.ANSI_RESET + " ");
        IntStream.range(0, anchoInferior).mapToObj(i -> ReprASCII.BARRA_HORIZONTAL).forEach(sBuilder::append);

        sBuilder.append(ReprASCII.ESQUINA_3 + "\n");

        return sBuilder.toString();
    }

    private static String espacioCentral(int tamTablero) {
        StringBuilder salida = new StringBuilder();
        int tamHueco = (tamTablero / 11) * 9;
        // 13 y 21 son la primera y ultima linea centrales donde va el ASCII ART
        if (asciiArtHelper >= 13 && asciiArtHelper <= 21){
            // ASCII ART
            int numLineaAsciiArt = asciiArtHelper - 13;
            String lineaAsciiArt = ReprASCII.ASCII_TITLE_ARRAY[numLineaAsciiArt];
            salida.append(ReprASCII.ANSI_RED_BOLD);
            // i = 1; Para que no me cuente la barra vertical
            IntStream.range(1, (tamHueco - 77) / 2).mapToObj(i -> " ").forEach(salida::append);
            salida.append(lineaAsciiArt);
            // +6; Para Ajustar el aumento de longitud por el ANSI_Code
            while (salida.length() < tamHueco + 6) {
                salida.append(" ");
            }
            salida.append(ReprASCII.ANSI_RESET);
        } else {
            // i = 1; Para que no me cuente la barra vertical
            IntStream.range(1, tamHueco).mapToObj(i -> " ").forEach(salida::append);
        }
        return salida.toString();
    }

    private static String reprAvatares(Tablero tablero, ArrayList<Posicion> posiciones, int longMaxima, int tamTablero) {
        StringBuilder salida = new StringBuilder();
        StringBuilder sBuilder;
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : posiciones) {
            salida.append(' ');//        salida.append("\n");
            sBuilder = new StringBuilder();

            for (Avatar ficha : tablero.getCasilla(pos).getAvatares()) {
                sBuilder.append(ficha.getRepresentacion()).append(" ");
            }
            salida.append(
                    widear(sBuilder.toString(), longMaxima)
            );
            salida.append(' ' + ReprASCII.BARRA_VERTICAL);
        }
        if (posiciones.size() == 2) {
            salida.insert(tamTablero/11 + 1, espacioCentral(tamTablero) + ReprASCII.BARRA_VERTICAL);
        }
        return salida.toString();
    }

    private static String reprPrecio(Tablero tablero, ArrayList<Posicion> posiciones, int longMaxima, int tamTablero) {
        StringBuilder salida = new StringBuilder();
        StringBuilder sBuilder;
        Casilla casilla;
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : posiciones) {
            salida.append(' ');
            sBuilder = new StringBuilder();
            casilla = tablero.getCasilla(pos);
            if (casilla instanceof Propiedad) {
                sBuilder.append(((Propiedad) casilla).getPrecio());
                sBuilder.append(' ' + Precios.MONEDA);
            }
            salida.append(
                    widear(sBuilder.toString(), longMaxima)
            );
            salida.append(' ' + ReprASCII.BARRA_VERTICAL);
        }
        if (posiciones.size() == 2) {
            salida.insert(tamTablero/11 + 1, espacioCentral(tamTablero) + ReprASCII.BARRA_VERTICAL);
        }
        salida.append('\n');
        return salida.toString();
    }

    private static String barraInterna(String separador, int anchoCasilla) {
        StringBuilder salida = new StringBuilder();
        salida.append(ReprASCII.T_LADO_D);
        IntStream.range(0, anchoCasilla - 1).mapToObj(i -> ReprASCII.BARRA_HORIZONTAL).forEach(salida::append);
        salida.append(ReprASCII.CRUZ); // Esquina
        IntStream.range(1, anchoCasilla * 9).forEach(i -> {
            salida.append((i % anchoCasilla) == 0 ? separador : ReprASCII.BARRA_HORIZONTAL); // ReprASCII.T_LADO_C
        });
        salida.append(ReprASCII.CRUZ); // Esquina
        IntStream.range(0, anchoCasilla - 1).mapToObj(i -> ReprASCII.BARRA_HORIZONTAL).forEach(salida::append);
        salida.append(ReprASCII.T_LADO_B + '\n');
        return salida.toString();
    }

    private static String separadorCentral(int anchoCasilla) {
        StringBuilder salida = new StringBuilder();
        genTlatetal(anchoCasilla, salida);
        salida.append(espacioCentral(anchoCasilla * 11));
        genTlatetal(anchoCasilla, salida);
        salida.append("\n");
        return salida.toString();
    }


    private static void genTlatetal(int anchoCasilla, StringBuilder salida) {
        salida.append(IntStream.range(1, anchoCasilla)
                .mapToObj(i -> ReprASCII.BARRA_HORIZONTAL)
                .collect(Collectors.joining("", ReprASCII.T_LADO_D, ReprASCII.T_LADO_B)));
    }


    private static Casilla getCasillaRepr(Tablero tablero, StringBuilder salida, Posicion pos) {
        Casilla casilla = tablero.getCasilla(pos);
        if (casilla.getTipo() != null) salida.append(ReprASCII.colorMonopolio(casilla.getTipo()));
        salida.append(' ');
        return casilla;
    }

    /**
     * Devuelve una representacion ASCII de un tablero.
     * @return String de la representacion
     */
    public static String genTablero() {
        Tablero tablero = Tablero.getTablero();

        Casilla casilla; // NO MODIFICAR
        int longitudMax = tamMaxArrayString(new ArrayList<>(Arrays.asList(Nombres.CALLES)));
        if (longitudMax < 5 * 2 + 1) { // 5 * 2 + 1 es lo minimo para representar 6 avatares separados con un espacio
            longitudMax = 5 * 2 +1;
        }
        int anchoCasilla = longitudMax + 3; // +2 por los espacios de margen y 1 por la barra vertical
        int anchoTablero = anchoCasilla * 11 + 1; // +1 por la barra vertical de la derecha / izq
        StringBuilder salida = new StringBuilder();

        // Barra Superior del tablero
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ESQUINA_1);
        for (int i = 1; i <= anchoTablero - 2; i++) { // -2 para las esquinas
            sBuilder.append((i % anchoCasilla) == 0 ? ReprASCII.T_LADO_A : ReprASCII.BARRA_HORIZONTAL);
        }
        sBuilder.append(ReprASCII.ESQUINA_2).append("\n");
        salida.append(sBuilder);
        // Fin Barra Superior del tablero

        // Fila nombre calles NORTE
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posiciones.posicionesNorteIzqDer()) {
            casilla = getCasillaRepr(tablero, salida, pos);
            salida.append(
                    widear(casilla.getNombre(), longitudMax)
            ).append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);
        }
        // Calculo de cuan ancho es el tablero
        salida.append("\n");
        // Fin fila nombre calles NORTE

        // Jugadores y precios de la zona NORTE
        salida.append(reprAvatares(tablero, Posiciones.posicionesNorteIzqDer(), longitudMax, anchoTablero));
        salida.append('\n');
        salida.append(reprPrecio(tablero, Posiciones.posicionesNorteIzqDer(), longitudMax, anchoTablero));
        // Fin Jugadores y precios de la zona norte

        // Barra separadora inferior zona NORTE
        salida.append(barraInterna(ReprASCII.T_LADO_C, anchoCasilla));
        // Fin Barra separadora inferior zona NORTE

        asciiArtHelper = 0; // Para saber como colocar el ASCII art en el centro
        // Seccion central Este/Oeste
        for (ArrayList<Posicion> par : Posiciones.posicionesEsteOeste()) {
            // Lado izquierdo
            salida.append(ReprASCII.BARRA_VERTICAL);
            casilla = tablero.getCasilla(par.get(0));
            if (casilla.getTipo() != null) salida.append(ReprASCII.colorMonopolio(casilla.getTipo()));
            salida.append(' ').append(
                    widear(tablero.getCasilla(par.get(0)).getNombre(), longitudMax)
            ).append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);

            // Espacio central
            salida.append(espacioCentral(anchoTablero));

            // Lado derecho
            salida.append(ReprASCII.BARRA_VERTICAL);
            casilla = tablero.getCasilla(par.get(1));
            if (casilla.getTipo() != null) salida.append(ReprASCII.colorMonopolio(casilla.getTipo()));
            salida.append(' ').append(
                    widear(tablero.getCasilla(par.get(1)).getNombre(), longitudMax)
            ).append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL + "\n");

            asciiArtHelper++;

            // Jugadores y precios actual fila seccion central
            salida.append(reprAvatares(tablero, par, longitudMax, anchoTablero));
            salida.append('\n');
            asciiArtHelper++;
            // Fin Jugadores y precios actual fila seccion central
            salida.append(reprPrecio(tablero, par, longitudMax, anchoTablero));
            asciiArtHelper++;
            // Separacion calles este/oesta
            salida.append(separadorCentral(anchoCasilla));
            asciiArtHelper++;
        }
        // Toca borrar la ultima separacion porque sobra
        salida.delete(salida.length() - separadorCentral(anchoCasilla).length() - 1, salida.length() - 1);
        // Fin seccion central

        // Barra separadora inferior zona SUR
        salida.append(barraInterna(ReprASCII.T_LADO_A, anchoCasilla));
        // Fin Barra separadora inferior zona SUR

        // Fila nombres lado SUR
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posiciones.posicionesSurIzqDer()) {
            casilla = getCasillaRepr(tablero, salida, pos);
            salida.append(
                    widear(casilla.getNombre(), longitudMax)
            ).append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);
        }
        salida.append("\n");
        // Fin fila de nombres del lado SUR

        // Jugadores y precios de la zona SUR
        salida.append(reprAvatares(tablero, Posiciones.posicionesSurIzqDer(), longitudMax, anchoTablero));
        salida.append("\n");
        salida.append(reprPrecio(tablero, Posiciones.posicionesSurIzqDer(), longitudMax, anchoTablero));
        // Fin Jugadores y precios de la zona SUR

        // Barra inferior SUR
        sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ESQUINA_4);
        for (int i = 1; i <= anchoTablero - 2; i++) { // -2 para las esquinas
            sBuilder.append((i % (anchoTablero / 11)) == 0 ? ReprASCII.T_LADO_C : ReprASCII.BARRA_HORIZONTAL);
        }
        sBuilder.append(ReprASCII.ESQUINA_3);
        salida.append(sBuilder);
        // Fin Barra inferior Sur

        return salida.toString();
    }
}
