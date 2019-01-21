package monopooly.gui.controllers.editores;

import io.datafx.controller.ViewController;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/EditorDerecha.fxml", title = "MonoPOOly")
public class DerechaController extends LadoController {

    @PostConstruct
    public void init() {
        colocarCasillas(LADO.derecho);
    }

}
