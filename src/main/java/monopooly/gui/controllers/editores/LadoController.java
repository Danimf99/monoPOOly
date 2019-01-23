package monopooly.gui.controllers.editores;

import com.jfoenix.controls.JFXButton;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.gui.componentes.TarjetasSucesos;
import monopooly.gui.controllers.LoginController;
import monopooly.gui.controllers.crearJugadores.NuevaPartidaController;

public class LadoController {

    @FXML
    @ActionTrigger("siguiente")
    private  JFXButton botonSiguiente;

    @FXML
    @ActionTrigger("atras")
    private  JFXButton botonAtras;

    @FXML
    @LinkAction(NuevaPartidaController.class)
    private JFXButton inicioPartida;

    @FXML
    @LinkAction(LoginController.class)
    private JFXButton login;

    @FXML
    private HBox casillasLado;

    @FXML
    private StackPane centroEditor;

    private StackPane casillaSeleccionada;

    public void colocarCasillas(LADO lado) {
        casillaSeleccionada = null;
        casillasLado.getChildren().clear();
        int inicial = 0;
        int posFinal = 0;
        switch (lado) {
            case sur:
                inicial = 1;
                posFinal = 10;
                break;
            case norte:
                inicial = 21;
                posFinal = 30;
                break;
            case izquierdo:
                inicial = 11;
                posFinal = 20;
                break;
            case derecho:
                inicial = 31;
                posFinal = 40;
        }

        for (int i = inicial; i < posFinal; i++) {
            Casilla casilla = Tablero.getTablero().getCasilla(new Posicion(i));
            StackPane tarjeta = TarjetasSucesos.crearTarjeta(
                    "" + i,
                    casilla.nombrePropertyProperty(),
                    new Grupo(casilla.getTipo()).getHexColor(),
                    "" + i
            );
            tarjeta.getStyleClass().add("tarjeta-seleccionable");
            tarjeta.setId("" + i);
            casillasLado.getChildren().add(tarjeta);
            tarjeta.setOnMouseClicked(event -> clickCasilla((StackPane) event.getSource(), lado));
            tarjeta.getChildren().stream()
                    .filter(node -> node instanceof JFXButton)
                    .forEach(node -> {
                        node.setOnMouseClicked(event -> {
                            clickEditarCasilla(((JFXButton) event.getSource()).getId());
                        });
                    });
        }

    }

    public static enum LADO {
        sur,
        izquierdo,
        norte,
        derecho
    }

    public StackPane getCentroEditor() {
        return centroEditor;
    }

    public void setCentroEditor(StackPane centroEditor) {
        this.centroEditor = centroEditor;
    }

    public JFXButton getBotonSiguiente() {
        return botonSiguiente;
    }

    public void setBotonSiguiente(JFXButton botonSiguiente) {
        this.botonSiguiente = botonSiguiente;
    }

    public JFXButton getBotonAtras() {
        return botonAtras;
    }

    public void setBotonAtras(JFXButton botonAtras) {
        this.botonAtras = botonAtras;
    }

    public JFXButton getInicioPartida() {
        return inicioPartida;
    }

    public void setInicioPartida(JFXButton inicioPartida) {
        this.inicioPartida = inicioPartida;
    }

    public JFXButton getLogin() {
        return login;
    }

    public void setLogin(JFXButton login) {
        this.login = login;
    }

    public HBox getCasillasLado() {
        return casillasLado;
    }

    public void setCasillasLado(HBox casillasLado) {
        this.casillasLado = casillasLado;
    }


    public void clickCasilla(StackPane tarjeta, LADO lado) {
        if (this.casillaSeleccionada == null) {
            casillaSeleccionada = tarjeta;
            tarjeta.getStyleClass().add("tarjeta-seleccionada");
            return;
        }

        if (casillaSeleccionada == tarjeta) {
            tarjeta.getStyleClass().remove("tarjeta-seleccionada");
            casillaSeleccionada = null;
            return;
        }

        Tablero.getTablero().intercambiarCasillas(
                Integer.parseInt(casillaSeleccionada.getId()),
                Integer.parseInt(tarjeta.getId())
            );

        colocarCasillas(lado);
        this.casillaSeleccionada = null;
    }

    public void clickEditarCasilla(String id) {
        System.out.println("Cambio de nombre y tal " + id);


//        Hace falta recargarlo para que actualice los nombres del hashMap
        Tablero.getTablero().reloadColocacion();
    }
}
