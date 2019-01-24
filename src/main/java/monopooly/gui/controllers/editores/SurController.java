package monopooly.gui.controllers.editores;

import io.datafx.controller.ViewController;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/EditorSur.fxml", title = "MonoPOOly")
public class SurController extends LadoController {

    @PostConstruct
    public void init() {
        colocarCasillas(LADO.sur);
    }

}
