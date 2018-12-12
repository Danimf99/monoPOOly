import entradaSalida.Prompt;
import entradaSalida.PromptImpl;

public class Arranque {
    public static void main(String[] args) {
        System.out.println("We back");
        Prompt prompt = new PromptImpl("was");
        prompt.test();

    }
}
