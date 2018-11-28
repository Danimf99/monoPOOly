package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import static monopooly.entradaSalida.PintadoASCII.*;

public class Mensajes {
    public static void error(String mensaje) {
        imprimir(genError(mensaje));
    }

    public static void error(String mensaje, String titulo) {
        imprimir(genError(mensaje, titulo));
    }

    public static void info(String mensaje) {
        imprimir(genInfo(mensaje));
    }


    public static void info(String mensaje, String titulo) {
        imprimir(genInfo(mensaje, titulo));
    }


    public static void detalles(String mensaje) {
        imprimir(genDetalles(mensaje));
    }


    public static void detalles(String mensaje, String titulo) {
        imprimir(genDetalles(mensaje, titulo));
    }

    public static void imprimir(String mensaje) {
        System.out.println(mensaje);
    }
}
