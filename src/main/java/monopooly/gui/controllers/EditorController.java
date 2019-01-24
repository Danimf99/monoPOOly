package monopooly.gui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToolbar;
import com.jfoenix.effects.JFXDepthManager;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.container.AnimatedFlowContainer;
import io.datafx.controller.flow.container.ContainerAnimations;
import io.datafx.controller.util.VetoException;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import monopooly.gui.controllers.crearJugadores.NuevaPartidaController;
import monopooly.gui.controllers.editores.DerechaController;
import monopooly.gui.controllers.editores.IzquierdaController;
import monopooly.gui.controllers.editores.NorteController;
import monopooly.gui.controllers.editores.SurController;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/editor/Editor.fxml", title = "MonoPOOly")
public class EditorController {

    @FXML
    @LinkAction(NuevaPartidaController.class)
    private JFXButton inicioPartida;

    @FXML
    @LinkAction(LoginController.class)
    private JFXButton login;


    @FXML
    @ActionTrigger("siguiente")
    private JFXButton botonSiguiente;

    @FXML
    @ActionTrigger("atras")
    private JFXButton botonAtras;


    @FXML
    private StackPane centroEditor;

    @FXML
    private JFXToolbar navEditor;

    private FlowHandler flowHandler;

    @PostConstruct
    public void init() throws FlowException {
        Flow flowInterno = new Flow(SurController.class)
                .withLink(SurController.class, "siguiente", IzquierdaController.class)
                .withLink(IzquierdaController.class, "siguiente", NorteController.class)
                .withLink(NorteController.class, "siguiente", DerechaController.class);

        flowHandler = flowInterno.createHandler();
        centroEditor.getChildren().add(
                flowHandler.start(
                        new AnimatedFlowContainer(
                                Duration.millis(300),
                                ContainerAnimations.FADE
                        )
                )
        );
        botonAtras.setDisable(true);
        JFXDepthManager.setDepth(navEditor, 1);
    }

    @ActionMethod("atras")
    public void pulsarAtras() throws VetoException, FlowException {
        flowHandler.navigateBack();
        if(flowHandler.getCurrentViewControllerClass().equals(SurController.class)) {
            botonAtras.setDisable(true);
        } else {
            botonAtras.setDisable(false);
        }
        botonSiguiente.setDisable(false);
    }

    @ActionMethod("siguiente")
    public void pulsarSiguiente() throws VetoException, FlowException {
        flowHandler.handle("siguiente");
        if(flowHandler.getCurrentViewControllerClass().equals(DerechaController.class)) {
            botonSiguiente.setDisable(true);
        } else {
            botonSiguiente.setDisable(false);
        }
        botonAtras.setDisable(false);
    }
}
