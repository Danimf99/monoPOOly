package entradaSalida;

public class PromptImpl implements Prompt {
    private String nombre;


    public PromptImpl(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public void test() {
        System.out.println("Whatever");
    }

}
