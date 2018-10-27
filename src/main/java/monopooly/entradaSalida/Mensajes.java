package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import static monopooly.entradaSalida.PintadoASCII.encuadrar;

public class Mensajes {
    public static void error(String mensaje) {
        String out = ReprASCII.ANSI_RED
                + "[!] " // +4 de longitud al mensaje
                + ReprASCII.ANSI_RESET
                + ReprASCII.ANSI_BLACK_UNDERLINE
                + mensaje
                + ReprASCII.ANSI_RESET;
        System.out.println(encuadrar(out, mensaje.length() + 4));
    }

    public static void info(String mensaje) {
        String out = ReprASCII.ANSI_BLUE
                + "[i] " // +4 de longitud al mensaje
                + ReprASCII.ANSI_RESET
                + mensaje;
        System.out.println(encuadrar(out, mensaje.length() + 4));
    }

    public static void detalles(String mensaje) {
        String out = ReprASCII.ANSI_GREEN
                + "[+] " // +4 de longitud al mensaje
                + ReprASCII.ANSI_RESET
                + mensaje;
        System.out.println(encuadrar(out, mensaje.length() + 4));
    }
}
