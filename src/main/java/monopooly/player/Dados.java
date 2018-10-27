package monopooly.player;

import java.util.Random;

public class Dados {
    private int dado1;
    private int dado2;

    /**
     * Constructor que inicializa los dados a 0
     */
    public Dados() {
        this.dado1 = 0;
        this.dado2 = 0;
    }

    public int getDado1(){
        return dado1;
    }
    public int getDado2(){
        return dado2;
    }
    /**
     * Comprueba si la tirada de cada dado es igual
     *
     * @return true: si son iguales, false: si no son iguales
     */
    public boolean isDobles() {
        return this.dado1 == this.dado2;
    }

    /**
     * Hace una tirada de dados otorgandoles un valor aleatorio entre 1 y 6
     */
    public void lanzar() {
        Random tirada1 = new Random();
        Random tirada2 = new Random();
        this.dado1 = tirada1.nextInt(6) + 1;
        this.dado2 = tirada2.nextInt(6) + 1;
    }

    /**
     * Devuelve el valor de la suma de los dos dados
     *
     * @return dado1+dado2
     */
    public int tirada() {
        if (this.dado1 > 0 && this.dado2 > 0) {
            return this.dado1 + this.dado2;
        }
        return 0;
    }
}
