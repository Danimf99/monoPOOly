package monopooly.player;

import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import java.util.HashSet;

public class Jugador {
    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Inmueble> propiedades;
    private Dados dados;

    /**
     * Inicizaliza jugador pasandole el nombre, tipoAvatar y Dados, dinero y propiedades siempre se van a inicizalizar al mismo valor
     *
     * @param nombre string con el nombre del jugador
     * @param avatar tipoAvatar para saber si es sombrero,balon...
     * @param dados  Dados para que los lanze
     */
    public Jugador(String nombre, tipoAvatar avatar, Dados dados) {
        if (avatar != null && dados != null && nombre != null) {
            this.nombre=nombre;
            this.dados=dados;
            dinero= Precios.DINERO_INICIAL;
            propiedades=new HashSet<>();
            //TODO pensar en una manera mejor de hacer el constructor para avatar
            this.avatar=new Avatar('k',avatar,this);
        }
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

    public HashSet getPropiedades() {
        return propiedades;
    }
    public Dados getDados(){
        return dados;
    }
    public void setPropiedades(Inmueble propiedades){

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
    public void ganarDinero(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero += cantidad;
    }

    @Override
    public String toString() {
        HashSet<Inmueble> solares;
        return "Jugador{" +
                "Nombre: " + nombre +
                "Fortuna: " + dinero +
                "Avatar: " + avatar.getTipo() +
                "Propiedades: " + propiedades;
    }
}
