package monopooly;

import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;

public class Arranque {
    public static void main(String[] args) {
        System.out.println(ReprASCII.ASCII_TITLE);

        Mensajes.error("Mensaje de prueba. Porque null pointer exception");
        Mensajes.info("Mensaje de prueba. Informacion de un suceso");
        Mensajes.detalles("Mensaje de informacion sobre un objeto");
    }
}
