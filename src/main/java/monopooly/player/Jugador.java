package monopooly.player;

import monopooly.colocacion.calles.Inmueble;
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
     * @param nombre
     * @param avatar
     * @param dados
     */
    public Jugador(String nombre,tipoAvatar avatar,Dados dados){
        if(avatar!=null && dados!=null && nombre!="\0"){
            //TODO Inicializar los atributos de jugador
        }
    }

    public int getDinero(){
        if(this!=null){
            return this.dinero;
        }
        Mensajes.error("Jugador no creado");
        return 0;
    }
}
