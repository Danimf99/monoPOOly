package monopooly.player;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;

import java.util.HashSet;
import java.util.Objects;

public class Jugador {
    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Inmueble> propiedades;
    private Dados dados;
    private HashSet<Inmueble> hipotecas;
    /**
     * Inicizaliza jugador pasandole el nombre, TipoAvatar y Dados, dinero y propiedades siempre se van a inicizalizar al mismo valor
     *
     * @param nombre string con el nombre del jugador
     * @param avatar TipoAvatar para saber si es sombrero,balon...
     */
    public Jugador(String nombre, TipoAvatar avatar) {
        if (avatar != null && nombre != null) {
            this.nombre=nombre;
            this.dados=new Dados();
            dinero= Precios.DINERO_INICIAL;
            propiedades=new HashSet<>();
            this.avatar=new Avatar(avatar,this);
            this.hipotecas=null;
        }
    }

    /**
     * Constructor para el jugador Banca
     * @param propiedades todas las propiedades del tablero
     */
    public Jugador(HashSet<Inmueble> propiedades){
        avatar=null;
        dados=null;
        this.propiedades=propiedades;
        this.nombre=null;
        this.dinero=0;
    }

    public int getDinero() {
        return dinero;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public String getNombre() {
        return nombre;
    }

    public HashSet<Inmueble> getPropiedades() {
        return propiedades;
    }
    public Dados getDados(){
        return dados;
    }
    //LOS SETTERS PARA PROPIEDADES Y DINERO NO HACEN FALTA, YA QUE TENEMOS METODOS PARA IR AÑADIENDO Y QUITANDO TANTO UNO COMO OTRO
    /**
     * Al jugador se le añade una propiedad ya sea porque la compra, la intercambia por otro jugador...
     * @param propiedad propiedad nueva que se le introduce al jugador
     */
    public void anhadirPropiedad(Inmueble propiedad){
        if(propiedad==null){
            Mensajes.error("La propiedad que se quiere añadir es nula");
            return;
        }
        this.propiedades.add(propiedad);
    }

    /**
     * Al jugador se le retira una propiedad de su lista de propiedades ya sea porque la vende, la intercambia....
     * @param propiedad propiedad que se quiere retirar
     */
    public void quitarPropiedad(Inmueble propiedad){
        if(propiedad==null){
            Mensajes.error("La propiedad que se quiere retirar es nula");
            return;
        }
        this.propiedades.remove(propiedad);
    }
    /**
     * Al jguador se le quitar una cantidad de dinero
     *
     * @param cantidad cantidad que se desea quitar al jugador
     */
    public void quitarDinero(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero -= cantidad;
    }

    /**
     * AL jugador se le suma una cantidad de dinero
     *
     * @param cantidad cantidad que se desea sumar al jugador
     */
    public void anhadirDinero(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero += cantidad;
    }
    public void moverJugador(Tablero tablero){
        if(this.dados==null || this.nombre==null || this.avatar==null){
            Mensajes.error("Atributos nulos, no se puede mover al jugador");
            return;
        }
        tablero.getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        dados.lanzar();
        avatar.getPosicion().mover(dados.tirada());
        tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
        //TODO si cae en una casilla de impuestos o propiedad de otro jugador cobrar la cantidad
    }

    /**
     * Comprueba si un jugador está en la carcel
     * @return devuelve true si está en la carcel y false si no lo está
     */
    public boolean estaEnCarcel(){
        return avatar.getPosicion().getX()==Posiciones.CARCEL;
    }

    @Override
    public String toString() {
        HashSet<Inmueble> solares;
        return "Jugador{" +
                "\tNombre: " + nombre +
                "\tFortuna: " + dinero +
                "\tAvatar: " + avatar+
                "\tPropiedades: " + propiedades
                +"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dinero == jugador.dinero &&
                Objects.equals(nombre, jugador.nombre);

    }
//
    //@Override
    //public int hashCode() {
    //    return Objects.hash(nombre, dinero, avatar, propiedades, dados);
    //}
}
