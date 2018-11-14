package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import static monopooly.entradaSalida.PintadoASCII.encuadrar;

public class Mensajes {
    public static void error(String mensaje) {
        String out = ReprASCII.ANSI_RED_BOLD
                + "\n[!] Error !\n"
                + ReprASCII.ANSI_RESET;
        System.out.println(out + encuadrar(mensaje));
    }

    public static void info(String mensaje) {
        String out = ReprASCII.ANSI_BLUE_BOLD
                + "\n[i] \n"
                + ReprASCII.ANSI_RESET;
        System.out.println(out + encuadrar(mensaje));
    }

    public static void detalles(String mensaje) {
        String out = ReprASCII.ANSI_GREEN_BOLD
                + "\n[+] \n"
                + ReprASCII.ANSI_RESET;
        System.out.println(out + encuadrar(mensaje));
    }
}
