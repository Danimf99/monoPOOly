package monopooly.gui.controllers.crearJugadores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowException;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import monopooly.colocacion.Tablero;
import monopooly.gui.componentes.Alerta;
import monopooly.gui.controllers.JuegoController;
import monopooly.gui.controllers.LoginController;
import monopooly.gui.controllers.editores.LadoController;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import javax.annotation.PostConstruct;
import java.util.ArrayList;

@ViewController(value = "/fxml/crearJugadores/NuevaPartida.fxml", title = "MonoPOOly - Nueva Partida")
public class NuevaPartidaController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    private FlowPane nuevosJugadores;

    @FXML
    @LinkAction(LoginController.class)
    private JFXButton irLogin;

    @FXML
    @ActionTrigger("inicioPartida")
    private JFXButton iniciarPartida;

    private FlowHandler flowHandler;

    private static ArrayList<Jugador> lobby;



    @PostConstruct
    public void init() throws FlowException {
        lobby = new ArrayList<>();
        JFXButton meterJugador = new JFXButton("Añadir Jugador");
        meterJugador.getStyleClass().add("boton-meter-jugador");
        meterJugador.setOnAction(event -> meterCreacionJugador());
        generarCuadro(meterJugador);

        meterCreacionJugador();
        meterCreacionJugador();
    }

    /**
     * Inicia la partida, Aqui deben crearsee tambien los jugadores
     * @throws Exception Excepcion que puede tirar el .navigateTO
     */
    @ActionMethod("inicioPartida")
    public void iniciarJuego() throws Exception {

        // Nombres iguales o de longitud 0
        if (lobby.stream()
                .map(Jugador::getNombre).distinct()
                .filter(nombre -> nombre.length() != 0)
                .count() != lobby.size()) {
           Alerta.nuevaAlerta()
                   .meterBotonCerrar()
                   .ponerHeading(new Label("Nombres invalidos"))
                   .meterEnCuerpoTodos(new Label("Varios jugadores poseen el mismo nombre o no tienen."))
                   .mostrar();
            return;
        }

        lobby.stream().map(Jugador::getAvatar).forEach(Avatar::elegirRepresentacion);
        Tablero tablero = Tablero.getTablero();
        lobby.forEach(tablero::meterJugador);

        ((FlowHandler) context.getRegisteredObject("flowHandler")).navigateTo(JuegoController.class);
    }


    private void generarCuadro(Node... nodes) {
        VBox cuadro = new VBox();
        cuadro.getStyleClass().addAll("contenedor-crear-jugadores");
        cuadro.getChildren().addAll(nodes);
        int tam = nuevosJugadores.getChildren().size();
        if (tam > 0) {
            if (tam == 6) {
                nuevosJugadores.getChildren().remove(tam - 1);
                nuevosJugadores.getChildren().add(cuadro);
            } else {
                nuevosJugadores.getChildren().add(tam - 1, cuadro);
            }
        } else {
            nuevosJugadores.getChildren().add(cuadro);
        }
    }

    private void meterCreacionJugador() {
        Jugador jugador = new Jugador("", Avatar.TIPO.esfinge);
        JFXTextField nombre = new JFXTextField();
        nombre.setLabelFloat(true);
        nombre.setPromptText("Nombre");
        nombre.getStyleClass().add("nombre-jugador");
        nombre.textProperty().bindBidirectional(jugador.nombreProperty());

        /* Validacion */
        LadoController.requerirCampo(nombre);


        JFXComboBox<Avatar.TIPO> tipoAvatar = new JFXComboBox<>();
        tipoAvatar.getStyleClass().add("avatar-jugador");
        tipoAvatar.getItems().addAll(Avatar.TIPO.values());
        tipoAvatar.setValue(Avatar.TIPO.esfinge);
        tipoAvatar.setOnAction(event -> {
            jugador.genAvatar(tipoAvatar.getValue());
        });

        lobby.add(jugador);
        generarCuadro(nombre, tipoAvatar);
    }

}
