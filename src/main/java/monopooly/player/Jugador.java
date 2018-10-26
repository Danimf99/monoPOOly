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
     * @param nombre string con el nombre del jugador
     * @param avatar tipoAvatar para saber si es sombrero,balon...
     * @param dados Dados para que los lanze
     */
    public Jugador(String nombre,tipoAvatar avatar,Dados dados){
        if(avatar!=null && dados!=null && nombre!=null){
            //TODO Inicializar los atributos de jugador
        }
    }

    public int getDinero(){
            return this.dinero;
    }
}
