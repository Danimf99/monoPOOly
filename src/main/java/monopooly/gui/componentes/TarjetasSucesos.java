package monopooly.gui.componentes;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.emojione.EmojiOne;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TarjetasSucesos {

    public static StackPane crearTarjeta(String titulo, String texto, String color) {
        return crearTarjeta(titulo, new SimpleStringProperty(texto), color, (JFXButton) null);
    }

    public static StackPane crearTarjeta(String titulo, StringProperty textoProperty, String color, JFXButton boton) {
        StackPane tarjeta = new StackPane();
        StackPane cabeceira = new StackPane();
        StackPane containerMensaje = new StackPane();

        JFXDepthManager.setDepth(tarjeta, 1);

        cabeceira.getChildren().add(new Label(titulo));
        cabeceira.setStyle("-fx-background-color: " + color);
        VBox.setVgrow(cabeceira, Priority.ALWAYS);

        Label labelMensaje = new Label();
        labelMensaje.textProperty().bind(textoProperty);
        containerMensaje.getChildren().add(labelMensaje);
        VBox.setVgrow(containerMensaje, Priority.ALWAYS);

        VBox container = new VBox();
        container.getChildren().addAll(cabeceira, containerMensaje);
        tarjeta.getChildren().add(container);

        /* Aplicado del CSS post-modificaciones previas */
        tarjeta.getStyleClass().add("tarjeta");
        cabeceira.getStyleClass().add("cabecera-tarjeta");
        containerMensaje.getStyleClass().add("cuerpo-tarjeta");

        /* Se añade el boton si tiene */
        if (boton == null) {
            return tarjeta;
        }

        boton.translateYProperty().bind(Bindings.createDoubleBinding(
                () -> cabeceira.getBoundsInParent().getHeight() - boton.getHeight() / 2,
                cabeceira.boundsInParentProperty(),
                boton.heightProperty()
        ));

        StackPane.setMargin(boton, new Insets(0, 12, 0, 0));
        StackPane.setAlignment(boton, Pos.TOP_RIGHT);
        tarjeta.getChildren().add(boton);
        return tarjeta;
    }


    public static StackPane crearTarjeta(String titulo, SimpleStringProperty texto, String color, String idBoton) {
        return crearTarjeta(titulo, texto, color, idBoton, MaterialIcon.EDIT);
    }

    public static StackPane crearTarjeta(String titulo, SimpleStringProperty texto, String color, String idBoton, MaterialIcon emo) {
        JFXButton boton = new JFXButton();
        boton.setId(idBoton);
        boton.setPrefSize(40, 40);
        boton.getStyleClass().add("boton-editar-tarjeta");
        MaterialIconView emoji = new MaterialIconView(emo);

        emoji.getStyleClass().add("icono-editar-tarjeta");
        emoji.setSize("23");
        boton.setGraphic(emoji);
        StackPane tarjeta = crearTarjeta(titulo, texto, color, boton);
        tarjeta.setStyle("-fx-min-width: 120; -fx-max-width: 120");
        JFXDepthManager.setDepth(boton, 1);
        return tarjeta;
    }
}
