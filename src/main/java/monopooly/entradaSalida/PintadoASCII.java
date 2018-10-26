package monopooly.entradaSalida;

import com.sun.deploy.util.StringUtils;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Avatar;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;

public class PintadoASCII {

    @Contract(pure = true)
    @NotNull
    public static String encuadrar(String mensaje, int ancho) {
        // Se necesita el ancho por que los modificadores de color
        //  cuentan como caracter
        String out = "| " + mensaje + " |\n";
        ancho += 2; // Las dos barras
        String topBar = ReprASCII.ESQUINA_1;
        String bottomBar = ReprASCII.ESQUINA_4;
        for (int i = 0; i < ancho; i++) {
            topBar = topBar.concat(ReprASCII.BARRA_HORIZONTAL);
            bottomBar = bottomBar.concat(ReprASCII.BARRA_HORIZONTAL);
        }
        topBar += ReprASCII.ESQUINA_2 + "\n";
        bottomBar += ReprASCII.ESQUINA_3 + "\n";

        return topBar + out + bottomBar;
    }

    /**
     * Añade espacios a un texto
     * @param texto texto que se desea modificar
     * @param longitudDeseada longitud que debe tener el texto añadiendole espacios al final
     * @return texto deseado
     */
    private static String widear(String texto, int longitudDeseada) {
        StringBuilder salida = new StringBuilder(texto);
        while (salida.length() < longitudDeseada) {
            salida.append(" ");
        }
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

        int longitudMax = Nombres.maxLen();
        StringBuilder salida = new StringBuilder();

        // Fila nombre calles NORTE
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posicion.posicionesNorteIzqDer()) {
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(pos).getCalle().getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.BARRA_VERTICAL);
        }
        // Calculo de cuan ancho es el tablero
        int anchoTablero = salida.length();
        salida.append("\n");
        // Fin fila nombre calles NORTE


        // Barra Superior del tablero
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ESQUINA_1);
        for (int i = 1; i <= anchoTablero - 2; i++) { // -2 para las esquinas
            if ((i % (anchoTablero/11)) == 0) {
                sBuilder.append(ReprASCII.T_LADO_A);
            } else {
                sBuilder.append(ReprASCII.BARRA_HORIZONTAL);
            }
        }
        sBuilder.append(ReprASCII.ESQUINA_2);
        sBuilder.append("\n");
        salida.insert(0, sBuilder);
        // Fin Barra Superior del tablero

        
        
        // Jugadores de la zona NORTE
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posicion.posicionesNorteIzqDer()) {
            salida.append(' ');
            sBuilder = new StringBuilder();

            for (Avatar ficha : tablero.getCasilla(pos).getAvatares()) {
                sBuilder.append("\uD83C\uDFA9");
//                sBuilder.append(ficha.getRepresentacion());
                sBuilder.append(' ');
            }

            salida.append(
                    widear(sBuilder.toString(), longitudMax)
            );
            salida.append(' ' + ReprASCII.BARRA_VERTICAL);
        }
        salida.append("\n");
        // Fin Jugadores de la zona norte




        // Seccion central Este/Oeste completa
        for (ArrayList<Posicion> par : Posicion.posicionesEsteOeste()) {
            salida.append(ReprASCII.BARRA_VERTICAL + " ");
            salida.append(
                    widear(tablero.getCasilla(par.get(0)).getCalle().getNombre(), longitudMax)
            );
            salida.append(" " + ReprASCII.BARRA_VERTICAL);

            for (int i = 1; i < (anchoTablero / 11) * 9; i++) {
                salida.append(" ");
            }
            salida.append(ReprASCII.BARRA_VERTICAL + ' ');
           salida.append(
                    widear(tablero.getCasilla(par.get(0)).getCalle().getNombre(), longitudMax)
            );
            salida.append(" " + ReprASCII.BARRA_VERTICAL + "\n");
            // Jugadores actual fila seccion central

            // Fin Jugadores actual fila seccion central
        }
//        salida.append('\n');
        // Fin seccion central


        // Fila nombres lado SUR
        salida.append(ReprASCII.BARRA_VERTICAL);
        for (Posicion pos : Posicion.posicionesSurIzqDer()) {
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(pos).getCalle().getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.BARRA_VERTICAL);
        }
        salida.append("\n");
        // Fin fila de nombres del lado SUR

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

}
