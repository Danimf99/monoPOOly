package monopooly.entradaSalida;

import java.util.Scanner;

public class ConsolaNormal implements Consola{
    private Scanner sc;

    public ConsolaNormal() {
        this.sc = new Scanner(System.in);
    }

    public void imprimir(String mensaje){
        System.out.print(mensaje);
    }

    public void imprimirln(String mensaje){
        System.out.println(mensaje);
    }

    public String leer(){
        return sc.nextLine();
    }


}
