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



public class ReprASCII {
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
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";



    public static final String ASCII_ART_TITLE = "\n" +
            ANSI_RED_BOLD +
            "                                 ____    _____   _____    ___                \n" +
            " /'\\_/`\\                        /\\  _`\\ /\\  __`\\/\\  __`\\ /\\_ \\               \n" +
            "/\\      \\    ___     ___     ___\\ \\ \\L\\ \\ \\ \\/\\ \\ \\ \\/\\ \\\\//\\ \\    __  __    \n" +
            "\\ \\ \\__\\ \\  / __`\\ /' _ `\\  / __`\\ \\ ,__/\\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\ \\  /\\ \\/\\ \\   \n" +
            " \\ \\ \\_/\\ \\/\\ \\L\\ \\/\\ \\/\\ \\/\\ \\L\\ \\ \\ \\/  \\ \\ \\_\\ \\ \\ \\_\\ \\ \\_\\ \\_\\ \\ \\_\\ \\  \n" +
            "  \\ \\_\\\\ \\_\\ \\____/\\ \\_\\ \\_\\ \\____/\\ \\_\\   \\ \\_____\\ \\_____\\/\\____\\\\/`____ \\ \n" +
            "   \\/_/ \\/_/\\/___/  \\/_/\\/_/\\/___/  \\/_/    \\/_____/\\/_____/\\/____/ `/___/> \\\n" +
            "                                                                       /\\___/\n" +
            "                                                                       \\/__/ " +
            ANSI_RESET;

}
