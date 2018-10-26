package monopooly.entradaSalida;

import com.sun.deploy.util.StringUtils;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
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


    private static String espacioCentral(int tamTablero) {
        StringBuilder salida = new StringBuilder();
        // i = 1; Para que no me cuente la barra vertical
        for (int i = 1; i < (tamTablero / 11) * 9; i++) {
            salida.append(" ");
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
//                sBuilder.append(ficha.getRepresentacion());
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



        // Seccion central Este/Oeste completa
        for (ArrayList<Posicion> par : Posiciones.posicionesEsteOeste()) {
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

            salida.append(espacioCentral(anchoTablero));

            salida.append(ReprASCII.BARRA_VERTICAL);
            inmuebleAuxiliar = tablero.getCasilla(par.get(0)).getCalle();
            if (inmuebleAuxiliar.getGrupoColor().getTipo() != null) {
                salida.append(ReprASCII.colorMonopolio(inmuebleAuxiliar.getGrupoColor().getTipo()));
            }
            salida.append(' ');
            salida.append(
                    widear(tablero.getCasilla(par.get(1)).getCalle().getNombre(), longitudMax)
            );
            salida.append(' ' + ReprASCII.ANSI_RESET + ReprASCII.BARRA_VERTICAL + "\n");
            // Jugadores y precios actual fila seccion central
            salida.append(reprAvatares(tablero, par, longitudMax, anchoTablero));
            salida.append('\n');
            // Fin Jugadores y precios actual fila seccion central
            salida.append(reprPrecio(tablero, par, longitudMax, anchoTablero));
            // Separacion calles este/oesta
            salida.append(separadorCentral(anchoCasilla));
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
}
