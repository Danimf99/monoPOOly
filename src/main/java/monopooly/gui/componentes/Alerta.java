package monopooly.gui.componentes;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private VBox content;

    public JFXDialogLayout getLayout() {
        return layout;
    }

    public Alerta() {
        alert = new JFXAlert(Arranque.getMainStage());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        layout = new JFXDialogLayout();
        content = new VBox();
        layout.getBody().add(content);
        alert.setContent(layout);
    }

    public static Alerta nuevaAlerta() {
        return new Alerta();
    }

    /**
     * Añade Paneles al cuerpo de la alerta
     * @param node Nodo que se quiere añadir
     * @return instancia actual
     */
    public Alerta meterEnCuerpo(Node node) {
        content.getChildren().add(node);
        return this;
    }

    /**
     * Añade Paneles al cuerpo de la alerta
     * @param elementos Nodos que se quieren añadir
     * @return instancia actual
     */
    public Alerta meterEnCuerpoTodos(Node... elementos) {
        content.getChildren().addAll(elementos);
        return this;
    }

    /**
     * Añade un boton para cerrar
     * @return Instancia actual
     */
    public Alerta meterBotonCerrar() {
        JFXButton botonCerrar = new JFXButton("Cerrar");
        botonCerrar.setOnAction(event -> {
            alert.setHideOnEscape(true);
            alert.hideWithAnimation();
        });
        botonCerrar.getStyleClass().add("boton-aceptar-dialogo");
        return meterBotones(botonCerrar);
    }

    /**
     * Añade botones a las acciones
     * @param botones Botones a añadir
     * @return Instancia actual de accion
     */
    public Alerta meterBotones(JFXButton... botones) {
        layout.getActions().addAll(botones);
        Arrays.stream(botones).forEach(boton -> boton.getStyleClass().add("boton-aceptar-dialogo"));
        return this;
    }

    /**
     * Define un heading para la alerta a partir de un Nodo
     * @param node Nodo que se coloca en el heading
     * @return Instancia actual de alerta
     */
    public Alerta ponerHeading(Node node) {
        layout.getHeading().add(node);
        return this;
    }

    /**
     * Define un heading para la alerta a partir de un String
     * @param titulo Titulo que se desea poner
     * @return Instancia actual de Alerta
     */
    public Alerta ponerHeading(String titulo) {
        layout.getHeading().add(new Label(titulo));
        return this;
    }

    /**
     * JFXalert
     * @return alerta del JFX
     */
    public JFXAlert getAlert() {
        return alert;
    }

    /**
     * Añade una linea de texto al cuerpo de la alerta
     * @param titulo Texto que saldra en negrita al principio de la linea
     * @param texto Texto normal que se coloca tras el texto en negrita
     * @return Instancia actual
     */
    public Alerta meterTexto(String titulo, String texto) {
        HBox linea = new HBox();
        linea.setStyle("-fx-spacing: 10px");
        Label negrita = new Label(titulo);
        Label textoNormal = new Label(texto);

        negrita.getStyleClass().add("titulo-panel-control");
        textoNormal.getStyleClass().add("texto-panel-control");

        linea.getChildren().addAll(negrita, textoNormal);

        return this.meterEnCuerpo(linea);
    }

    /**
     * Muestra la alerta y espera
     */
    public void mostrar() {
        alert.showAndWait();
    }
}
