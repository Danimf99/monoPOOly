package monopooly.configuracion;

/*
               A
       ┌───────┬───────┐
       │       │       │
       │   1   │   2   │
       │       │       │
     D ├───────┼───────┤ B
       │       │       │
       │   4   │   3   │
       │       │       │
       └───────┴───────┘
               C

* */


import monopooly.colocacion.calles.TipoMonopolio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ReprASCII {

    public static final String APP_NAME = "MonoPOOly";

    /* Representacion para AVATARES*/
    public static List<Character> AVATARES= new ArrayList<>(Arrays.asList('K','L','M','P','T','R', 'Q', 'J', 'S', 'D'));
    // Mellor que sobren que que falten
    /* BARRITAS */

    public static final String ESQUINA_1 = "┌";
    public static final String ESQUINA_2 = "┐";
    public static final String ESQUINA_3 = "┘";
    public static final String ESQUINA_4 = "└";

    public static final String T_LADO_A = "┬";
    public static final String T_LADO_B = "┤";
    public static final String T_LADO_C = "┴";
    public static final String T_LADO_D = "├";

    public static final String CRUZ = "┼";
    public static final String BARRA_HORIZONTAL = "─";
    public static final String BARRA_VERTICAL = "│";

    public static final String FLECHA = "►";

    /* ANSI COLOR CODES*/

    public static final String ANSI_RESET = "\u001B[0m";

//    Color texto normal
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

//    Color negrita
    public static final String ANSI_BLACK_BOLD = "\u001B[1;30m";
    public static final String ANSI_RED_BOLD = "\u001B[1;31m";
    public static final String ANSI_GREEN_BOLD = "\u001B[1;32m";
    public static final String ANSI_YELLOW_BOLD = "\u001B[1;33m";
    public static final String ANSI_BLUE_BOLD = "\u001B[1;34m";
    public static final String ANSI_PURPLE_BOLD = "\u001B[1;35m";
    public static final String ANSI_CYAN_BOLD = "\u001B[1;36m";
    public static final String ANSI_WHITE_BOLD = "\u001B[1;37m";

//    Color subrayado
    public static final String ANSI_BLACK_UNDERLINE = "\u001B[4;30m";
    public static final String ANSI_RED_UNDERLINE = "\u001B[4;31m";
    public static final String ANSI_GREEN_UNDERLINE = "\u001B[4;32m";
    public static final String ANSI_YELLOW_UNDERLINE = "\u001B[4;33m";
    public static final String ANSI_BLUE_UNDERLINE = "\u001B[4;34m";
    public static final String ANSI_PURPLE_UNDERLINE = "\u001B[4;35m";
    public static final String ANSI_CYAN_UNDERLINE = "\u001B[4;36m";
    public static final String ANSI_WHITE_UNDERLINE = "\u001B[4;37m";

//    Color fondo del texto
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_HIGH_YELLOW_BACKGROUND = "\u001B[0;103m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_HIGH_WHITE_BACKGROUND = "\u001B[0;107m";


    //    PROMPT
    public static final String PROMPT_TOP_OPENER = " ┌─";
    public static final String PROMPT_BOT_OPENER = " └─────► ";
    public static final String PROMPT_ELM_SEPARATOR = "──";
    public static final String PROMPT_ELM_LEFT = "[ ";
    public static final String PROMPT_ELM_RIGHT = " ]";
    public static final String PROMPT_ELM_OUTTER_SEP = PROMPT_ELM_RIGHT + PROMPT_ELM_SEPARATOR + PROMPT_ELM_LEFT;
    public static final String PROMPT_LOG_HELP = "Escribe h para optener ayuda";
    public static final String PROMPT_LOG_DINERO = "Dinero: ";
    public static final String PROMPT_LOG_DINERO_UP = "⇡";
    public static final String PROMPT_LOG_DINERO_DOWN = "⇣";
    public static final String PROMPT_DADOS = "\uD83C\uDFB2 Dados: ";
    public static final String PROMPT_LOGO = ANSI_RED_BOLD + APP_NAME + ANSI_RESET;
    public static final String PROMPT_NUEVA_PARTIDA = PROMPT_TOP_OPENER +
            "[ Nueva partida ]" +
            PROMPT_ELM_SEPARATOR +
            PROMPT_ELM_LEFT +
            PROMPT_LOGO +
            PROMPT_ELM_RIGHT +
            PROMPT_ELM_SEPARATOR + "[ Introduca el numero de jugadores ]" +
            "\n" +
            PROMPT_BOT_OPENER;
    public static final String PROMPT_NOMBRE_JUGADOR = "\n[ ? ]──[ Nombre del nuevo jugador: ]───► ";
    // TODO Actualizar la descripcion de los tipos de avatares
    public static final String PROMT_AYUDA_TIPO_AVATAR = ""
            + ANSI_BLUE_BOLD + "\n[i]" + ANSI_RESET + " Avatares disponibles: \n"
            + "\t > " + ANSI_RED_BOLD + "Sombrero" + ANSI_RESET + ":\n"
            + "\t\t Se desplaza en diagonal.\n"
            + "\t > " + ANSI_RED_BOLD + "Esfinge" + ANSI_RESET + ":\n"
            + "\t\t Lo que haga este tipo de avatar\n"
            + "\t > " + ANSI_RED_BOLD + "Pelota" + ANSI_RESET + ":\n"
            + "\t\t Se desplaza hacia delante si el valor de los dados es mayor que 4.\n"
            +" \t\t Mientras que si el valor es menor que 4, retrocederás.\n"
            + "\t\t En ambos casos se parará en las casillas impares mayores o menores que 4, dependiendo de si avanza o retrocede.\n"
            + "\t > " + ANSI_RED_BOLD + "Coche" + ANSI_RESET + ":\n"
            + "\t\t Se desplaza hacia delante si el valor de los dados es mayor que 4.\n"
            + "\t\t Y podrá seguir tirando los dados 3 veces más.\n"
            + "\t\t Si el valor es menor que 4, retrocedes tantas casillas como número haya salido \n"
            + "\t\t y no podrás lanzar los dados en los siguientes dos turnos.\n"
            + ""
            + "";
    public static final String PROMPT_TIPO_AVATAR = "\n[ ? ]──[ Tipo (sombrero/pelota/coche/esfinge): ]───► ";
    public static final String PROMPT_CARCEL = "⛓ " + ANSI_HIGH_WHITE_BACKGROUND + " Estás en la carcel " + ANSI_RESET;

    public static final String OFF = ANSI_HIGH_WHITE_BACKGROUND + " OFF " + ANSI_RESET;
    public static final String ON = ANSI_GREEN_BACKGROUND + " ON " + ANSI_RESET;



    public static final String[] ASCII_TITLE_ARRAY = {
            "                                 ____    _____   _____    ___",
            " /'\\_/`\\                        /\\  _`\\ /\\  __`\\/\\  __`\\ /\\_ \\",
            "/\\      \\    ___     ___     ___\\ \\ \\L\\ \\ \\ \\/\\ \\ \\ \\/\\ \\\\//\\ \\    __  __",
            "\\ \\ \\__\\ \\  / __`\\ /' _ `\\  / __`\\ \\ ,__/\\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\  /\\ \\/\\ \\",
            " \\ \\ \\_/\\ \\/\\ \\L\\ \\/\\ \\/\\ \\/\\ \\L\\ \\ \\ \\/  \\ \\ \\_\\ \\ \\ \\_\\ \\ \\_\\ \\_\\ \\ \\_\\ \\",
            "  \\ \\_\\\\ \\_\\ \\____/\\ \\_\\ \\_\\ \\____/\\ \\_\\   \\ \\_____\\ \\_____\\/\\____\\\\/`____ \\",
            "   \\/_/ \\/_/\\/___/  \\/_/\\/_/\\/___/  \\/_/    \\/_____/\\/_____/\\/____/ `/___/> \\",
            "                                                                       /\\___/",
            "                                                                       \\/__/"
    };

    public static final String ASCII_TITLE = "\n" +
            ANSI_RED_BOLD +
            "\t                                 ____    _____   _____    ___                \n" +
            "\t /'\\_/`\\                        /\\  _`\\ /\\  __`\\/\\  __`\\ /\\_ \\               \n" +
            "\t/\\      \\    ___     ___     ___\\ \\ \\L\\ \\ \\ \\/\\ \\ \\ \\/\\ \\\\//\\ \\    __  __    \n" +
            "\t\\ \\ \\__\\ \\  / __`\\ /' _ `\\  / __`\\ \\ ,__/\\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\  /\\ \\/\\ \\   \n" +
            "\t \\ \\ \\_/\\ \\/\\ \\L\\ \\/\\ \\/\\ \\/\\ \\L\\ \\ \\ \\/  \\ \\ \\_\\ \\ \\ \\_\\ \\ \\_\\ \\_\\ \\ \\_\\ \\  \n" +
            "\t  \\ \\_\\\\ \\_\\ \\____/\\ \\_\\ \\_\\ \\____/\\ \\_\\   \\ \\_____\\ \\_____\\/\\____\\\\/`____ \\ \n" +
            "\t   \\/_/ \\/_/\\/___/  \\/_/\\/_/\\/___/  \\/_/    \\/_____/\\/_____/\\/____/ `/___/> \\\n" +
            "\t                                                                       /\\___/\n" +
            "\t                                                                       \\/__/ \n" +
            ANSI_RESET;


    public static String colorMonopolio(TipoMonopolio tipo) {
        switch (tipo) {
            case rojo:
                return ANSI_RED_BACKGROUND ;
            case verde:
                return ANSI_GREEN_BACKGROUND;
            case marron:
                return ANSI_BLACK_BACKGROUND + ANSI_WHITE;
            case naranja:
                return ANSI_HIGH_YELLOW_BACKGROUND;
            case violeta:
                return ANSI_PURPLE_BACKGROUND;
            case amarillo:
                return ANSI_YELLOW_BACKGROUND;
            case estacion:
                return ANSI_HIGH_WHITE_BACKGROUND;
            case servicio:
                return ANSI_BLACK_BOLD;
            case azul_claro:
                return ANSI_CYAN_BACKGROUND;
            case azul_marino:
                return ANSI_BLUE_BACKGROUND + ANSI_WHITE_BOLD;
            case none:
                return ANSI_BLACK_BOLD;
            case parking:
                return ANSI_BLACK_BOLD;
            case suerte:
                return ANSI_RED_BOLD;
            case caja_comunidad:
                return ANSI_BLUE_BOLD;
            case impuesto:
                return ANSI_BLACK_BOLD;
            default:
                return ANSI_BLACK;

        }
    }

}
