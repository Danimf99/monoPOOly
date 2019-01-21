package monopooly;

import com.jfoenix.controls.JFXDecorator;
import io.datafx.controller.flow.Flow;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.container.DefaultFlowContainer;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import monopooly.gui.controllers.JuegoController;
import monopooly.gui.controllers.LoginController;

public class Arranque extends Application {

    private static Stage mainStage;

    public static Stage getMainStage() {
        return mainStage;
    }

    @FXMLViewFlowContext
    private ViewFlowContext flowContext;

    public static void main(String[] args) {
//        new Partida().init();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /* Configuracion del stage*/
        mainStage = stage;
        stage.setTitle("MonoPOOly");
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResource("/imagenes/logo_sombrero.png").toExternalForm()));

        /* Flow container */
        Flow flow = new Flow(JuegoController.class);
        DefaultFlowContainer container = new DefaultFlowContainer();
        flowContext = new ViewFlowContext();
        flowContext.register("Stage", stage);
        FlowHandler flowHandler = flow.createHandler(flowContext);
        flowHandler.start(container);
        flowContext.register("flow", flow);
        flowContext.register("flowHandler", flowHandler);

        /* Preparación del decorator */
        JFXDecorator decorator = new JFXDecorator(stage, container.getView(), false, false, true);
        ImageView logoDecorator = new ImageView(getClass().getResource("/imagenes/logo_sombrero.png").toExternalForm());
        logoDecorator.setPreserveRatio(true);
        logoDecorator.setFitHeight(30);
        decorator.setGraphic(logoDecorator);

        /* Preparacion de la escena principal */
        double ANCHO = 1400;
        double ALTO = 848;
        Scene escenaPrincipal = new Scene(decorator, ANCHO, ALTO);
        escenaPrincipal.getStylesheets().addAll(
                getClass().getResource("/css/global.css").toExternalForm(),
                getClass().getResource("/css/editor.css").toExternalForm(),
                getClass().getResource("/css/juego.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-design.css").toExternalForm()
        );

        /* Inicio de la app */
        stage.setScene(escenaPrincipal);
        stage.show();
    }
}
