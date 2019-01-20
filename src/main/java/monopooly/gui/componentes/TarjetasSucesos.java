package monopooly.gui.componentes;

import com.jfoenix.effects.JFXDepthManager;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TarjetasSucesos {

    public static StackPane crearTarjeta(String titulo, String texto, String color) {
        StackPane tarjeta = new StackPane();
        StackPane cabeceira = new StackPane();
        StackPane containerMensaje = new StackPane();

        JFXDepthManager.setDepth(tarjeta, 1);

        cabeceira.getChildren().add(new Label(titulo));
        cabeceira.setStyle("-fx-background-color: " + color);
        VBox.setVgrow(cabeceira, Priority.ALWAYS);

        containerMensaje.getChildren().add(new Label(texto));
        VBox.setVgrow(containerMensaje, Priority.ALWAYS);

        VBox container = new VBox();
        container.getChildren().addAll(cabeceira, containerMensaje);
        tarjeta.getChildren().add(container);

        /* Aplicado del CSS post-modificaciones previas */
        tarjeta.getStyleClass().add("tarjeta");
        cabeceira.getStyleClass().add("cabecera-tarjeta");
        containerMensaje.getStyleClass().add("cuerpo-tarjeta");

        return tarjeta;
    }
}
