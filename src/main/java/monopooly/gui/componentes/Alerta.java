package monopooly.gui.componentes;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.stage.Modality;
import monopooly.Arranque;

import java.util.Arrays;

/**
 * Wrapper de la clase JFXAlert para no repetir mucho codigo.
 * Es un builder para que se vea mejor
 */
public class Alerta {
    private JFXAlert alert;
    private JFXDialogLayout layout;

    public Alerta() {
        alert = new JFXAlert(Arranque.getMainStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        layout = new JFXDialogLayout();
        alert.setContent(layout);
    }

    public Alerta meterEnCuerpo(Node node) {
        layout.getHeading().add(node);
        return this;
    }

    public Alerta meterEnCuerpoTodos(Node... elementos) {
        layout.getBody().addAll(elementos);
        return this;
    }

    public Alerta meterBotonCerrar() {
        JFXButton botonCerrar = new JFXButton("Cerrar");
        botonCerrar.setOnAction(event -> {
            alert.setHideOnEscape(true);
            alert.hideWithAnimation();
        });
        return meterBotones(botonCerrar);
    }

    public Alerta meterBotones(JFXButton... botones) {
        layout.getActions().addAll(botones);
        Arrays.stream(botones).forEach(boton -> boton.getStyleClass().add("boton-aceptar-dialogo"));
        return this;
    }

    public Alerta ponerHeading(Node node) {
        layout.getHeading().add(node);
        return this;
    }

    public JFXAlert getAlert() {
        return alert;
    }

    public void mostrar() {
        alert.showAndWait();
    }
}
