package monopooly.gui.controllers.editores;

import io.datafx.controller.ViewController;
import monopooly.gui.controllers.EditorController;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/EditorNorte.fxml", title = "MonoPOOly")
public class NorteController extends LadoController {

    @PostConstruct
    public void init() {
        colocarCasillas(LADO.norte);
    }

}
