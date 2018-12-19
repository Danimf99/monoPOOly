package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import java.util.ArrayList;
import java.util.Arrays;

public class PintadoAscii {
    private static int asciiArtHelper; // Para centrar el logo del tablero


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
        return widear(salida.toString(), longitudDeseada);
    }


    /**
     * Dado un arrayList de strings, devuelve la la longitud de la mayor cadena
     * @param lineas ArrayList de strings que se desean medir
     * @return Longitud mayor
     */
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
}
