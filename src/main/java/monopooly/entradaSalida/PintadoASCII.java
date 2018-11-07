package monopooly.entradaSalida;

import com.sun.istack.internal.NotNull;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Avatar;
import monopooly.player.Dados;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.ArrayList;
import java.util.Arrays;

public class PintadoASCII {
    private static int asciiArtHelper;



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


    private static int tamMaxArrayString(ArrayList<String> lineas) {
        int tamMax = 0;
        for (String linea :
                lineas) {
            if (linea.length() > tamMax) {
                tamMax = linea.length();
            }
        }
        return tamMax;
    }

    public static String encuadrar(String mensaje) {
        // Se necesita el ancho por que los modificadores de color
        //  cuentan como caracter
        StringBuilder sBuilder = new StringBuilder();
        String[] lins = mensaje.split("\n");
        ArrayList<String> lineas = new ArrayList<>(Arrays.asList(lins));

        int ancho = tamMaxArrayString(lineas);
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
            for (int i = 1; i < (tamHueco - 77) / 2; i++) {
                salida.append(" ");
            }
            salida.append(lineaAsciiArt);
            // +6; Para Ajustar el aumento de longitud por el ANSI_Code
            while (salida.length() < tamHueco + 6) {
                salida.append(" ");
            }
            salida.append(ReprASCII.ANSI_BLACK);
        } else {
            // i = 1; Para que no me cuente la barra vertical
            for (int i = 1; i < tamHueco; i++) {
                salida.append(" ");
            }
        }
        return salida.toString();
    }

    private static String reprAvatares(Tablero tablero, ArrayList<Posicion> posiciones, int longMaxima, int tamTablero) {
        StringBuilder salida = new StringBuilder();
        StringBuilder sBuilder;
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : posiciones) {
            salida.append(' ');
            sBuilder = new StringBuilder();

            for (Avatar ficha : tablero.getCasilla(pos).getAvatares()) {
                sBuilder.append(ficha.getRepresentacion());
                sBuilder.append(' ');
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
        int precio;
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : posiciones) {
            salida.append(' ');
            sBuilder = new StringBuilder();
            precio = tablero.getCasilla(pos).getCalle().getPrecio();
            if (precio != 0) {
                sBuilder.append(precio);
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
        for (int i = 0; i < anchoCasilla - 1; i++) {
            salida.append(ReprASCII.BARRA_HORIZONTAL);
        }
        salida.append(ReprASCII.CRUZ); // Esquina
        for (int i = 1; i < anchoCasilla * 9; i++) {
            if ((i % anchoCasilla) == 0) {
                salida.append(separador); // ReprASCII.T_LADO_C
            } else {
                salida.append(ReprASCII.BARRA_HORIZONTAL);
            }
        }
        salida.append(ReprASCII.CRUZ); // Esquina
        for (int i = 0; i < anchoCasilla - 1; i++) {
            salida.append(ReprASCII.BARRA_HORIZONTAL);
        }
        salida.append(ReprASCII.T_LADO_B + '\n');
        return salida.toString();
    }

    private static String separadorCentral(int anchoCasilla) {
        StringBuilder salida = new StringBuilder();
        salida.append(ReprASCII.T_LADO_D);
        for (int i = 1; i < anchoCasilla; i++) {
            salida.append(ReprASCII.BARRA_HORIZONTAL);
        }
        salida.append(ReprASCII.T_LADO_B);
        salida.append(espacioCentral(anchoCasilla * 11));
        salida.append(ReprASCII.T_LADO_D);
        for (int i = 1; i < anchoCasilla; i++) {
            salida.append(ReprASCII.BARRA_HORIZONTAL);
        }
        salida.append(ReprASCII.T_LADO_B);
        salida.append("\n");
        return salida.toString();
    }


    /**
     * Devuelve una representacion ASCII de un tablero.
     * @param tablero Tablero que se desea representar
     * @return String de la representacion
     */
    public static String genTablero(Tablero tablero) {
        if (tablero == null) {
            Mensajes.error("Error pintando el tablero. El tablero es null");
            return "";
        }
        Inmueble inmuebleAuxiliar; // NO MODIFICAR
        int longitudMax = Nombres.maxLen();
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
            if ((i % anchoCasilla) == 0) {
                sBuilder.append(ReprASCII.T_LADO_A);
            } else {
                sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
            }
        }
        sBuilder.append(ReprASCII.ESQUINA_2);
        sBuilder.append("\n");
        salida.append(sBuilder);
        // Fin Barra Superior del tablero


        // Fila nombre calles NORTE
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posiciones.posicionesNorteIzqDer()) {
            inmuebleAuxiliar = tablero.getCasilla(pos).getCalle();
            if (inmuebleAuxiliar.getGrupoColor().getTipo() != null) {
                salida.append(ReprASCII.colorMonopolio(inmuebleAuxiliar.getGrupoColor().getTipo()));
            }
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(pos).getCalle().getNombre(), longitudMax)
            );
//            salida.append(ReprASCII.ANSI_RESET);
            salida.append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);
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
            inmuebleAuxiliar = tablero.getCasilla(par.get(0)).getCalle();
            if (inmuebleAuxiliar.getGrupoColor().getTipo() != null) {
                salida.append(ReprASCII.colorMonopolio(inmuebleAuxiliar.getGrupoColor().getTipo()));
            }
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(par.get(0)).getCalle().getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);

            // Espacio central
            salida.append(espacioCentral(anchoTablero));

            // Lado derecho
            salida.append(ReprASCII.BARRA_VERTICAL);
            inmuebleAuxiliar = tablero.getCasilla(par.get(1)).getCalle();
            if (inmuebleAuxiliar.getGrupoColor().getTipo() != null) {
                salida.append(ReprASCII.colorMonopolio(inmuebleAuxiliar.getGrupoColor().getTipo()));
            }
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(par.get(1)).getCalle().getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL + "\n");

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
//        salida.append('\n');
        // Fin seccion central



        // Barra separadora inferior zona SUR
        salida.append(barraInterna(ReprASCII.T_LADO_A, anchoCasilla));
        // Fin Barra separadora inferior zona SUR


        // Fila nombres lado SUR
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posiciones.posicionesSurIzqDer()) {
            inmuebleAuxiliar = tablero.getCasilla(pos).getCalle();
            if (inmuebleAuxiliar.getGrupoColor().getTipo() != null) {
                salida.append(ReprASCII.colorMonopolio(inmuebleAuxiliar.getGrupoColor().getTipo()));
            }
            salida.append(' ');
            salida.append(
                    widear(inmuebleAuxiliar.getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL);
        }
        salida.append("\n");
        // Fin fila de nombres del lado SUR


        // Jugadores y precios de la zona SUR
        salida.append(reprAvatares(tablero, Posiciones.posicionesSurIzqDer(), longitudMax, anchoTablero));
        salida.append("\n");
        salida.append(reprPrecio(tablero, Posiciones.posicionesSurIzqDer(), longitudMax, anchoTablero));
//        salida.append("\n");
        // Fin Jugadores y precios de la zona SUR


        // Barra inferior SUR
        sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ESQUINA_4);
        for (int i = 1; i <= anchoTablero - 2; i++) { // -2 para las esquinas
            if ((i % (anchoTablero/11)) == 0) {
                sBuilder.append(ReprASCII.T_LADO_C);
            } else {
                sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
            }

        }
        sBuilder.append(ReprASCII.ESQUINA_3);
        salida.append(sBuilder);
        // Fin Barra inferior Sur

        return salida.toString();
    }

    /* FIN Representacion del tablero
    *
    *
    *
    * */


    /* Representacion de un inmueble */
    /* Ejemplo:

    ┌───────────────────────┐
    │  Titulo de propiedad  │
    │        BADALONA       │
    ├───────────────────────┤
    │     Alquiler 260 $    │
    │  Valor Hipoteca 130 $ │
    │                       │
    │  Propietario: manolo  │
    │                       │
    └────── MonoPOOly ──────┘

    * */

    /**
     * Devuelve la representacion de una propiedad
     * @param propiedad inmueble que se debe mostrar por pantalla
     * @return string para imprimir
     */
    public static String genPropiedad(Inmueble propiedad) {
        // Para añadir cosas en la tarjeta que se muestra, basta con meterlas en este Array
        String mensajeHipoteca = "Valor Hipoteca " + propiedad.calcularHipoteca() + Precios.MONEDA;
        if (propiedad.getHipotecado()) {
            mensajeHipoteca = "Propiedad Hipotecada";
        }
        ArrayList<String> lineas = new ArrayList<>();

        switch (propiedad.getGrupoColor().getTipo()) {
            case none:
            case caja_comunidad:
            case suerte:
                lineas.add("Casilla especial");
                lineas.add(propiedad.getNombre().toUpperCase());
                // Padding
                lineas.add("");
                lineas.add("");
                lineas.add("");
                lineas.add("");
                break;
            case impuesto:
                lineas.add("Impuesto");
                lineas.add(propiedad.getNombre().toUpperCase());
                // Padding
                lineas.add("");
                lineas.add("Cantidad: " + propiedad.getPrecio_inicial());
                lineas.add("");
                lineas.add("");
                break;
            case parking:
                lineas.add(propiedad.getNombre().toUpperCase());
                lineas.add("");
                // Padding
                lineas.add("");
                lineas.add("");
                lineas.add("");
                lineas.add("");
                break;
                default: // TODO Cuando haya edificios hay que modificar esto
                    lineas.add("Titulo de propiedad");
                    lineas.add(propiedad.getNombre().toUpperCase());
                    lineas.add("Alquiler " + propiedad.calcularAlquiler(propiedad.getPropietario()) + " " + Precios.MONEDA);
                    lineas.add(mensajeHipoteca);
                    lineas.add("");
                    lineas.add("Propietario: " + propiedad.getPropietario().getNombre());
                    break;
        }

        ArrayList<String> lin = new ArrayList<>();

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
        for (int i = 0; i < anchoRequerido + 2; i++) { // +2 por el margen de los espacios
            sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
        }
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
                sBuilder.append(ReprASCII.colorMonopolio(propiedad.getGrupoColor().getTipo()));
            }
            sBuilder.append(" "); // Margen de un espacio entre la barra y el texto
            sBuilder.append(widear(lineas.get(i), anchoRequerido)); // TODO Hacer que quede centrado
            sBuilder.append(" " + ReprASCII.ANSI_RESET);
            sBuilder.append(ReprASCII.BARRA_VERTICAL + "\n");
        }

        // Barra inferior
        sBuilder.append(ReprASCII.ESQUINA_4);
        // -2 por los espacios +1 por el margen
        int anchoInferior = ((anchoRequerido - ReprASCII.APP_NAME.length() - 2)/ 2) + 1;
        for (int i = 0; i < anchoInferior; i++) {
            sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
        }
        sBuilder.append(" " + ReprASCII.ANSI_RED_BOLD + ReprASCII.APP_NAME + ReprASCII.ANSI_RESET + " ");


        for (int i = 0; i < anchoInferior; i++) {
            sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
        }

        sBuilder.append(ReprASCII.ESQUINA_3 + "\n");
        return sBuilder.toString();
    }

    /**
     * Dado un jugador imprime informacion suya
     * @param jugador Jugador del que se desea mostrar informacion
     * @return string para imprimir
     */
    public static String genJugador(Jugador jugador) {
        return "";
    }

}
