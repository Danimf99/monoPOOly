package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;

import java.util.InputMismatchException;
import java.util.Scanner;

import static monopooly.entradaSalida.PintadoAscii.encuadrar;

public class ConsolaNormal implements Consola {
    private Scanner sc;

    ConsolaNormal() {  // Package-Private
        this.sc = new Scanner(System.in);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @param titulo  Titulo del mensaje
     * @return String con el nuevo formato
     */
    public String genInfo(String mensaje, String titulo) {
        String out = ReprASCII.ANSI_BLUE_BOLD
                + "\n[i] "
                + ReprASCII.ANSI_RESET
                + titulo
                + "\n";
        return out + encuadrar(mensaje);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @return String con el nuevo formato
     */
    public String genInfo(String mensaje) {
        String out = ReprASCII.ANSI_BLUE_BOLD
                + "\n[i] "
                + ReprASCII.ANSI_RESET
                + "\n";
        return out + encuadrar(mensaje);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @return String con el nuevo formato
     */
    public String genError(String mensaje) {
        String out = ReprASCII.ANSI_RED_BOLD
                + "\n[!] Error !\n"
                + ReprASCII.ANSI_RESET;
        return out + encuadrar(mensaje);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @param titulo  Titulo del mensaje
     * @return String con el nuevo formato
     */
    public String genError(String mensaje, String titulo) {
        String out = ReprASCII.ANSI_RED_BOLD
                + "\n[!] Error: "
                + ReprASCII.ANSI_RESET + titulo
                + "\n";
        return out + encuadrar(mensaje);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @return String con el nuevo formato
     */
    public String genDetalles(String mensaje) {
        String out = ReprASCII.ANSI_GREEN_BOLD
                + "\n[+] \n"
                + ReprASCII.ANSI_RESET;
        return out + encuadrar(mensaje);
    }

    /**
     * Genera un mensaje encuadrado
     *
     * @param mensaje Mensaje de texto que aparecera encuadrado
     * @param titulo  Titulo del mensaje
     * @return String con el nuevo formato
     */
    public String genDetalles(String mensaje, String titulo) {
        String out = ReprASCII.ANSI_GREEN_BOLD
                + "\n[+] "
                + ReprASCII.ANSI_RESET + titulo
                + titulo
                + "\n";
        return out + encuadrar(mensaje);
    }

    /**
     * Imprime un mensaje de error
     *
     * @param mensaje Mensaje que se quiere imprimir
     */
    public void error(String mensaje) {
        imprimir(genError(mensaje));
    }

    /**
     * Imprime un mensaje de error con titulo
     *
     * @param mensaje Mensaje que se quiere imprimir
     * @param titulo  Titulo del mensaje
     */
    public void error(String mensaje, String titulo) {
        imprimir(genError(mensaje, titulo));
    }

    /**
     * Imprime un mensaje de informacion
     *
     * @param mensaje Mensaje que se quiere imprimir
     */
    public void info(String mensaje) {
        imprimir(genInfo(mensaje));
    }

    /**
     * Imprime un mensaje de informacion con titulo
     *
     * @param mensaje Mensaje que se quiere imprimir
     * @param titulo  Titulo del mensaje
     */
    public void info(String mensaje, String titulo) {
        imprimir(genInfo(mensaje, titulo));
    }

    /**
     * Imprime un mensaje de informacion
     *
     * @param mensaje Mensaje que se quiere imprimir
     */
    public void detalles(String mensaje) {
        imprimir(genDetalles(mensaje));
    }

    /**
     * Imprime un mensaje de detalles con titulo
     *
     * @param mensaje Mensaje que se quiere imprimir
     * @param titulo  Titulo del mensaje
     */
    public void detalles(String mensaje, String titulo) {
        imprimir(genDetalles(mensaje, titulo));
    }

    /**
     * Muestra por pantalla un mensaje
     *
     * @param mensaje Mensaje mostrado
     */
    public void imprimir(String mensaje) {
        System.out.print(mensaje);
    }

    /**
     * Muestra por pantalla un mensaje. Añade un salto de linea al final
     *
     * @param mensaje Mensaje mostrado.
     */
    public void imprimirln(String mensaje) {
        imprimir(mensaje + '\n');
    }

    /**
     * Lee por stdin un mensaje.
     *
     * @return Siguiente linea que introduzca el usuario
     */
    public String leer() {
        return sc.nextLine();
    }

    /**
     * Lee por stdin un mensaje. Muestra previamente el mensaje pasado como
     * argumento. No añade un salto de linea.
     *
     * @param mensaje Mensaje que se muestra antes de leer la linea
     * @return Siguiente linea que introduzca el usuario
     */
    public String leer(String mensaje) {
        imprimir(mensaje);
        return leer();
    }

    @Override
    public int elegirCarta() {
        System.out.print(ReprASCII.PROMPT_ELECCION_CARTA);
        int eleccion = 0;
        try {
            eleccion = this.sc.nextInt();
            if (sc.hasNextLine()) {
                sc.nextLine();
            }
        } finally {
            if (eleccion < 1 || eleccion > 6) {
                error("Elige un numero entre el 1 y el 6.", "Error eligiendo carta");
                eleccion = elegirCarta();
            }
        }
        return eleccion;
    }
}
