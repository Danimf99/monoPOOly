package monopooly.gui.controllers.editores;

import io.datafx.controller.ViewController;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/EditorIzquierda.fxml", title = "MonoPOOly")
public class IzquierdaController extends LadoController {

    @PostConstruct
    public void init() {
        colocarCasillas(LADO.izquierdo);
    }

}
