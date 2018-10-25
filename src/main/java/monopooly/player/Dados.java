package monopooly.player;

import monopooly.entradaSalida.Mensajes;

import java.util.Random;

public class Dados {
    private int dado1;
    private int dado2;

    /**
     * Constructor que inicializa los dados a 0
     */
    public Dados(){
        this.dado1=0;
        this.dado2=0;
    }
    /**
     * Comprueba si la tirada de cada dado es igual
     * @return true: si son iguales, false: si no son iguales
     */
    public boolean isDobles(){
        if(this!=null) {
            if (this.dado1 == this.dado2) {
                return true;
            } else {
                return false;
            }
        }
        Mensajes.error("Dados nulos");
        return false;
    }

    /**
     * Hace una tirada de dados otorgandoles un valor aleatorio entre 1 y 6
     */
    public void lanzar(){
        if(this!=null){
            Random tirada1=new Random();
            Random tirada2=new Random();
            this.dado1=tirada1.nextInt(6)+1;
            this.dado2=tirada2.nextInt(6)+1;
        }
    }

    /**
     * Devuelve el valor de la suma de los dos dados
     * @return dado1+dado2
     */
    public int tirada(){
        if(this!=null) {
            if (this.dado1 > 0 && this.dado2 > 0) {
                return this.dado1 + this.dado2;
            } else {
                //Valor incorrecto en los dados
                return 0;
            }
        }
        return 0;
    }
}
