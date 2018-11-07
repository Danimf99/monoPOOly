package monopooly.player;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;

import java.util.HashSet;
import java.util.Objects;

public class Jugador {
    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Inmueble> propiedades;
    private Dados dados;
    private HashSet<Inmueble> hipotecas;
    private boolean estarEnCarcel;
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
            this.hipotecas=new HashSet<>();
            this.estarEnCarcel=false;
        }
    }

    /**
     * Constructor para el jugador Banca
     * @param propiedades todas las propiedades del tablero
     */
    public Jugador(HashSet<Inmueble> propiedades){
        avatar=new Avatar(TipoAvatar.sombrero,this);
        dados=new Dados();
        this.propiedades=propiedades;
        this.nombre="Banca";
        this.dinero=0;
        this.estarEnCarcel=false;
        this.hipotecas = new HashSet<>();
        this.estarEnCarcel = false;
    }

    public int getDinero() {
        return dinero;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public HashSet<Inmueble> getHipotecas(){
        return hipotecas;
    }

    public String getNombre() {
        return nombre;
    }
    //Con el get ya comprobamos si esta en la carcel o no
    public boolean getEstarEnCarcel(){
        return estarEnCarcel;
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

    public void hipotecar(Inmueble inmueble){
        if(inmueble==null){
            Mensajes.error("Inmueble null, no se puede hipotecar");
            return;
        }
        if(!propiedades.contains(inmueble)){
            Mensajes.error("No eres propietario de ese inmueble");
            return;
        }
        hipotecas.add(inmueble);
        anhadirDinero(inmueble.getPrecio()/2);
        quitarPropiedad(inmueble);
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
        if(tablero==null){
            Mensajes.error("Tablero nulo, no se puede mover al jugador");
            return;
        }
        if(this.estarEnCarcel){
            Mensajes.info("Estás en la cárcel, no puedes tirar");
            return;
        }
        tablero.getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        dados.lanzar();
        if(this.dados.getDobles()==3) {
            Mensajes.info("No puede seguir tirando, 3 dobles seguidos, va a la carcel");
            this.estarEnCarcel = true;
            this.getAvatar().setPosicion(Posiciones.CARCEL);
            tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
            return;
        }
        avatar.getPosicion().mover(dados.tirada());
        tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
    }

    @Override
    public String toString() {
        return PintadoASCII.encuadrar("Jugador{\n" +
                "   Nombre: " + nombre +
                "\n   Fortuna: " + dinero +
                "\n   Avatar: " + getAvatar().getRepresentacion()+
                "\n   Propiedades: " + propiedades+
                "\n   Hipotecadas: " + hipotecas
                +"\n}");
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
