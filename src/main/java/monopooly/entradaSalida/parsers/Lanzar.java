package monopooly.entradaSalida.parsers;

public class Lanzar {
    private String[] comandoIntroducido;

    public Lanzar(String[] comandoIntroducido) {
        if (comandoIntroducido == null) {
            // Hay que meter excepciones aqui
            return;
        }
        comandoIntroducido=new String[comandoIntroducido.length];

        for(int i=0;i<comandoIntroducido.length;i++){
            this.comandoIntroducido[i] = comandoIntroducido[i];
        }
    }

}
