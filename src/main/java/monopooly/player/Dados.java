package monopooly.player;

import java.util.Random;

public class Dados {
    private int dado1;
    private int dado2;
    private int dobles;
    private int contador;
    /**
     * Constructor que inicializa los dados a 0
     */
    public Dados() {
        this.dado1 = 0;
        this.dado2 = 0;
        this.dobles=0;
    }

    public Dados(int dado1,int dado2){
        this.dado1=dado1;
        this.dado2=dado2;
        this.dobles=0;
    }

    public int getDado1(){
        return dado1;
    }
    public int getDado2(){
        return dado2;
    }
    public int getDobles(){
        return dobles;
    }
    public int getContador(){return contador;}
    /**
     * Comprueba si la tirada de cada dado es igual
     *
     * @return true: si son iguales, false: si no son iguales
     */
    public boolean sonDobles() {
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
        contador++;
        if(sonDobles()){
            dobles++;
            return;
        }
        dobles=0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dados dados = (Dados) o;
        return dado1 == dados.dado1 &&
                dado2 == dados.dado2;
    }

    //@Override
    //public int hashCode() {
    //    return Objects.hash(dado1, dado2);
    //}
}
