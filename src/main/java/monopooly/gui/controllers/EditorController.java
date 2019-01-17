package monopooly.gui.controllers;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/Editor.fxml", title = "MonoPOOly")
public class EditorController {



    @FXML
    @ActionTrigger("accionCojonuda")
    JFXButton botonCojonudo;

    @FXML
    @LinkAction(NuevaPartidaController.class)
    JFXButton inicioPartida;

    @FXML
    @LinkAction(LoginController.class)
    JFXButton login;

    @PostConstruct
    public void init() {


    }

    @ActionMethod("accionCojonuda")
    public void handlerAccionCojonuda() {
        System.out.println("Pasaron cosas cojonudas");
    }
}
