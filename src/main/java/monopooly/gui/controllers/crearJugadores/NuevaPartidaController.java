package monopooly.gui.controllers.crearJugadores;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.ViewController;
import io.datafx.controller.flow.FlowHandler;
import io.datafx.controller.flow.action.ActionMethod;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import io.datafx.controller.flow.context.FXMLViewFlowContext;
import io.datafx.controller.flow.context.ViewFlowContext;
import javafx.fxml.FXML;
import monopooly.colocacion.Tablero;
import monopooly.gui.controllers.JuegoController;
import monopooly.gui.controllers.LoginController;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import javax.annotation.PostConstruct;

@ViewController(value = "/fxml/crearJugadores/NuevaPartida.fxml", title = "MonoPOOly - Nueva Partida")
public class NuevaPartidaController {

    @FXMLViewFlowContext
    private ViewFlowContext context;

    @FXML
    @LinkAction(LoginController.class)
    private JFXButton irLogin;

    @FXML
    @ActionTrigger("inicioPartida")
    private JFXButton iniciarPartida;

    private FlowHandler flowHandler;


    @PostConstruct
    public void init() {

    }

    /**
     * Inicia la partida, Aqui deben crearsee tambien los jugadores
     * @throws Exception Excepcion que puede tirar el .navigateTO
     */
    @ActionMethod("inicioPartida")
    public void iniciarJuego() throws Exception {
        System.out.println("Nueva partida");

        /* Creacion de los Jugadores */
        Tablero tablero = Tablero.getTablero();
        Jugador dani = new Jugador("Dani", Avatar.TIPO.esfinge);
        Jugador saul = new Jugador("Saul", Avatar.TIPO.esfinge);

        tablero.meterJugador(dani);
        tablero.meterJugador(saul);
        /* Se debe comprobar que son suficientes */

        ((FlowHandler) context.getRegisteredObject("flowHandler")).navigateTo(JuegoController.class);
    }
}
