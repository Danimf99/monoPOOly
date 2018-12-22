package monopooly.player;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Juego;
import monopooly.estadisticas.EstadisticasJugador;
import monopooly.player.tiposAvatar.Coche;
import monopooly.player.tiposAvatar.Esfinge;
import monopooly.player.tiposAvatar.Pelota;
import monopooly.player.tiposAvatar.Sombrero;

import java.util.HashSet;
import java.util.Objects;

public class Jugador {

    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Propiedad> propiedades;
    private Dados dados;
    private HashSet<Propiedad> hipotecas;
    private boolean estarEnCarcel;
    private int turnosEnCarcel;
    private EstadisticasJugador estadisticas;
    private int vueltas;
    private int vecesDados;
    private int cooldown;

    /*-------------------------*/
    /* CONSTRUCTORES PARA JUGADOR */
    /*-------------------------*/

    public Jugador(String nombre,String avatar) {
        this.nombre = nombre;
        this.dinero= Precios.DINERO_INICIAL;
        this.dados=new Dados();
        this.propiedades=new HashSet<>();
        this.hipotecas=new HashSet<>();
        this.estarEnCarcel=false;
        this.turnosEnCarcel=0;
        this.estadisticas=new EstadisticasJugador();
        this.vueltas=0;
        this.vecesDados=0;
        this.cooldown=0;

        switch(avatar){
            case "coche":
            case "Coche":
                this.avatar=new Coche(this);
                break;
            case "Esfinge":
            case "esfinge":
                this.avatar=new Esfinge(this);
                break;
            case "Sombrero":
            case "sombrero":
                this.avatar=new Sombrero(this);
                break;
            case "Pelota":
            case "pelota":
                this.avatar=new Pelota(this);
                break;
        }
    }

    public Jugador() {
        avatar=new Coche(this);
        dados=new Dados();
        this.propiedades=new HashSet<>();
        this.nombre="Banca";
        this.dinero=0;
        this.estarEnCarcel=false;
        this.hipotecas = new HashSet<>();
        this.estarEnCarcel = false;
        this.turnosEnCarcel=0;
        this.vueltas=0;
        this.vecesDados=0;
        estadisticas=new EstadisticasJugador();
    }

    /*-------------------------*/
    /* SETTERS Y GETTERS*/
    /*-------------------------*/

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDinero(){
        return dinero;
    }

    public void setDinero(int dinero){
        this.dinero=dinero;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public HashSet<Propiedad> getPropiedades() {
        return propiedades;
    }

    public void setPropiedades(HashSet<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    public HashSet<Propiedad> getHipotecas() {
        return hipotecas;
    }

    public void setHipotecas(HashSet<Propiedad> hipotecas) {
        this.hipotecas = hipotecas;
    }

    public boolean isEstarEnCarcel() {
        return estarEnCarcel;
    }

    public void setEstarEnCarcel(boolean estarEnCarcel) {
        this.estarEnCarcel = estarEnCarcel;
    }

    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public void setTurnosEnCarcel(int turnosEnCarcel) {
        this.turnosEnCarcel = turnosEnCarcel;
    }

    public EstadisticasJugador getEstadisticas() {
        return estadisticas;
    }

    public void setEstadisticas(EstadisticasJugador estadisticas) {
        this.estadisticas = estadisticas;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    public int getVecesDados() {
        return vecesDados;
    }

    public void setVecesDados(int vecesDados) {
        this.vecesDados = vecesDados;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    /*-------------------------*/
    /* METODOS PARA JUGADOR */
    /*-------------------------*/
    public boolean puedeComprar(Casilla casilla) {
        if (Tablero.getPrompt().isCompro()) {
            return false;
        }
        for (Posicion posicion : Tablero.getPrompt().getPosicionesTurno()) {
            if (casilla.equals(Tablero.getTablero().getCasilla(posicion))) {
                return true;
            }
        }
        return !Tablero.getPrompt().isCompro();
    }

    public void hipotecar(Propiedad inmueble){
        if(inmueble==null){
            Juego.consola.error("Inmueble null, no se puede hipotecar");
            return;
        }
        hipotecas.add(inmueble);
        anhadirDinero(inmueble.getMonopolio().getPrecio()/2);
        quitarPropiedad(inmueble);
        inmueble.setHipotecado(true);
    }
    public void deshipotecar(Propiedad inmueble){
        if(inmueble==null){
            Juego.consola.error("Inmueble null, no se puedes deshipotecar");
            return;
        }
        this.anhadirPropiedad(inmueble);
        inmueble.setHipotecado(false);
        this.hipotecas.remove(inmueble);
        this.quitarDinero((int)(inmueble.getMonopolio().getPrecio()*1.1));
    }
    /**
     * AL jugador se le suma una cantidad de dinero
     *
     * @param cantidad cantidad que se desea sumar al jugador
     */
    public void anhadirDinero(int cantidad) {
        this.dinero += cantidad;
    }
    public void quitarDinero(int cantidad) {
        if (cantidad < 0) {
            Juego.consola.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero -= cantidad;
    }

    /**
     * Al jugador se le añade una propiedad ya sea porque la compra, la intercambia por otro jugador...
     * @param propiedad propiedad nueva que se le introduce al jugador
     */
    public void anhadirPropiedad(Propiedad propiedad){
        if(propiedad==null){
            Juego.consola.error("La propiedad que se quiere añadir es nula");
            return;
        }
        this.propiedades.add(propiedad);
    }

    /**
     * Al jugador se le retira una propiedad de su lista de propiedades ya sea porque la vende, la intercambia....
     * @param propiedad propiedad que se quiere retirar
     */
    public void quitarPropiedad(Propiedad propiedad){
        if(propiedad==null){
            Juego.consola.error("La propiedad que se quiere retirar es nula");
            return;
        }
        this.propiedades.remove(propiedad);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dinero == jugador.dinero &&
                Objects.equals(nombre, jugador.nombre);

    }
}
