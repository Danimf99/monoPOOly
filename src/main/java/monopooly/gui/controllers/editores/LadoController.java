package monopooly.gui.controllers.editores;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import io.datafx.controller.flow.action.ActionTrigger;
import io.datafx.controller.flow.action.LinkAction;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.Grupo;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.gui.componentes.Alerta;
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
                    "Posición " + i,
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
                            tarjeta.getStyleClass().remove("tarjeta-seleccionada");
                            casillaSeleccionada = null;
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
        Alerta alerta = new Alerta();
        Casilla casilla = Tablero.getTablero().getCasilla(new Posicion(Integer.parseInt(id)));

        JFXTextField nombre = new JFXTextField();
        nombre.setLabelFloat(true);
        nombre.setPromptText("Nombre de la casilla");
        nombre.getStyleClass().add("nombre-jugador");
        nombre.setText(casilla.getNombre());
        requerirCampo(nombre);

        JFXTextField precio = new JFXTextField();
        if (casilla instanceof Propiedad) {
            Propiedad propiedad = (Propiedad) casilla;
            precio.setLabelFloat(true);
            precio.setPromptText("Precio de la casilla");
            precio.getStyleClass().add("nombre-jugador");
            precio.setText(propiedad.getPrecio() + "");
            requerirCampo(precio);
            alerta.getLayout().getBody().get(0).setStyle("-fx-spacing: 40");
            alerta.meterEnCuerpo(precio);
        }



        JFXButton botonMod = new JFXButton("Modificar");
        botonMod.setOnAction(event -> {
            alerta.getAlert().hideWithAnimation();
            if (nombre.getText().length() == 0) {
                return;
            }

            if (casilla instanceof Propiedad) {
                if (precio.getText().length() == 0) {
                    return;
                }
                int valor = Integer.parseInt(precio.getText());
                if (valor <= 0) {
                    return;
                }
                ((Propiedad) casilla).setPrecio(valor);
            }
            casilla.setNombre(nombre.getText());
        });


        alerta.meterBotonCerrar()
                .ponerHeading("Editar " + id)
                .meterEnCuerpo(nombre)
                .meterBotones(botonMod)
                .mostrar();

        Tablero.getTablero().reloadColocacion();
    }

    public static void requerirCampo(JFXTextField nombre) {
        RequiredFieldValidator validator = new RequiredFieldValidator();
        validator.setMessage("Campo requerido");
        validator.setIcon(new FontAwesomeIconView(FontAwesomeIcon.WARNING));
        nombre.getValidators().add(validator);
        nombre.focusedProperty().addListener((o,oldVal,newVal)->{
            if(!newVal) nombre.validate();
        });
    }
}
