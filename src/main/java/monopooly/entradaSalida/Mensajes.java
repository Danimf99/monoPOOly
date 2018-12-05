package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import java.util.Scanner;

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

    public static int elegirCarta() {
        Scanner reader = new Scanner(System.in);
        System.out.print(ReprASCII.PROMPT_ELECCION_CARTA);
        int eleccion = reader.nextInt();
        if (eleccion < 1 || eleccion > 6) {
            Mensajes.error("Elige un numero entre el 1 y el 6.", "Error eligiendo la carta");
            return -1;
        }

        return eleccion;
    }
}
