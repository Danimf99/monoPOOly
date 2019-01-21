package monopooly.colocacion;

import com.jfoenix.effects.JFXDepthManager;
import de.jensd.fx.glyphs.GlyphsStack;
import de.jensd.fx.glyphs.emojione.EmojiOne;
import de.jensd.fx.glyphs.emojione.EmojiOneView;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class Casilla implements Imprimible {
    private String nombre;
    private HashSet<Avatar> avatares;
    private final static Iterator<Integer> generadorId = Stream.iterate(0, i -> i + 1).iterator();
    private Integer id;
    private StringProperty reprAvatares = new SimpleStringProperty(this, "reprAvatares", "");
    private VBox avs;

    private SimpleStringProperty nombreProperty = new SimpleStringProperty(this, "nombre", "");


    public String getReprAvatares() {
        return reprAvatares.get();
    }

    public StringProperty reprAvataresProperty() {
        return reprAvatares;
    }

    public void setReprAvatares(String reprAvatares) {
        this.reprAvatares.set(reprAvatares);
    }

    public Casilla(String nombre) {
        this.nombre = nombre;
        this.avatares = new HashSet<>();
        this.id = generadorId.next();
        this.avs = new VBox();
        this.nombreProperty.setValue(nombre);
    }

    public String getNombreProperty() {
        return nombreProperty.get();
    }

    public SimpleStringProperty nombrePropertyProperty() {
        return nombreProperty;
    }

    public void setNombreProperty(String nombreProperty) {
        this.nombreProperty.set(nombreProperty);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreProperty.setValue(nombre);
    }

    public HashSet<Avatar> getAvatares() {
        return new HashSet<>(this.avatares);
    }

    public void setAvatares(HashSet<Avatar> avatares) {
        this.avatares = avatares;
    }

    /* Metodos para trabajar con el conjunto de avatares y la posicion de la casilla */

    /**
     *
     * @return Posicion de la casilla
     */
    public Posicion getPosicion() {
        return Tablero.getTablero().posicionCasilla(this);
    }


    public int frecuenciaVisita(){
        return Tablero.getStatsGlobales().getStatsCasilla(this).getFrecuenciaVisita();
    }
    public abstract void visitar(VisitanteCasilla visitante) throws ExcepcionMonopooly;

    public ArrayList<String> representar(ArrayList<String> reprSubclases) {
        reprSubclases.add(1, this.nombre);
        return reprSubclases;
    }

    public void meterJugador(Jugador jugador) {
        this.meterJugador(jugador.getAvatar());
    }

    public void meterJugador(Avatar avatar) {
        this.avatares.add(avatar);
        actualizarRepresentacion();
        fillAvs();
    }

    public void quitarJugador(Jugador jugador) {
        this.quitarJugador(jugador.getAvatar());
    }

    public void quitarJugador(Avatar avatar) {
        this.avatares.remove(avatar);
        actualizarRepresentacion();
        fillAvs();
    }

    private void actualizarRepresentacion() {
        String sb = this.avatares.stream()
                .map(av -> String.valueOf(av.getRepresentacion()) + ' ')
                .collect(Collectors.joining());
        this.setReprAvatares(sb);
    }

    public boolean estaAvatar(Avatar avatar) {
        return this.avatares.contains(avatar);
    }

    public boolean estaJugador(Jugador jugador) {
        return this.estaAvatar(jugador.getAvatar());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Casilla)) return false;

        Casilla casilla = (Casilla) o;

        return nombre.equals(casilla.nombre)
                && id.equals(casilla.id);
    }

    @Override
    public String toString() {
        return "Casilla{" +
                "nombre='" + nombre + '\'' +
                ", avatares=" + avatares +
                '}';
    }


    private void fillAvs() {
        avs.getChildren().clear();
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(5);
        flowPane.setVgap(5);
        avs.getChildren().add(flowPane);
        this.avatares.forEach(avatar -> {
            EmojiOneView emoji = new EmojiOneView(avatar.getRepresentacion());
            emoji.setSize("20");
            JFXDepthManager.setDepth(emoji, 1);
            flowPane.getChildren().add(emoji);
        });
    }

    public VBox getAvs() {
        return avs;
    }

    public void setAvs(VBox avs) {
        this.avs = avs;
        fillAvs();
    }
}
