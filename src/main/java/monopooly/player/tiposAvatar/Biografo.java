package monopooly.player.tiposAvatar;

import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;

import java.util.EmptyStackException;
import java.util.Stack;

public class Biografo {
    private Stack<Object> historial;

    public Biografo() {
        historial = new Stack<>();
    }

    public void guardar(Avatar avatar) {
        this.historial.push(avatar.guardar());
    }

    public void deshacer(Avatar avatar) throws EmptyStackException, ExcepcionMonopooly {
        avatar.deshacer(this.historial.pop());
    }
}
