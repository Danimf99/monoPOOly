package monopooly.gui.controllers;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.ActionHandler;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

import javax.annotation.PostConstruct;
import java.util.Objects;

@ViewController(value = "/fxml/juego/Login.fxml", title = "MonoPOOly")
public class LoginController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private StackPane root;

    @FXML
    @LinkAction(NuevaPartidaController.class)
    private JFXButton nuevaPartida;

    @FXML
    @LinkAction(EditorController.class)
    private JFXButton irEditor;

    @PostConstruct
    public void init() {

    }
}
