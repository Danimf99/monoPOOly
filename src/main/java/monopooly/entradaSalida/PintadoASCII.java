package monopooly.entradaSalida;

import monopooly.configuracion.ReprASCII;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
}
