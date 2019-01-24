package monopooly.excepciones;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import monopooly.Arranque;
import monopooly.entradaSalida.Juego;

public class ExcepcionMonopooly extends Exception {

    public ExcepcionMonopooly(String message) {
        super(message);
    }

    public void imprimeError() {
        Juego.consola.error(
                this.getMessage()
        );
    }

    public void mostrarError() {
        if (this instanceof ExcepcionCarta) {
            return;
        }
        JFXSnackbar snackbar = new JFXSnackbar((Pane) Arranque.getMainStage().getScene().getRoot());
        snackbar.setPrefWidth(350);
            snackbar.fireEvent(new JFXSnackbar.SnackbarEvent(
                    new JFXSnackbarLayout(this.getMessage(), "!", action -> {
//                        snackbar.close();
//                        snackbar.getCurrentEvent().consume();
                    }),
                    Duration.millis(3500), null
            ));
    }
}
