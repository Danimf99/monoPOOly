package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.entradaSalida.Mensajes;

public class Avatar {
    private tipoAvatar tipo;
    private char representacion;
    private Jugador jugador;
    private Posicion posicion;

    /**
     * Inicializa avatar pasandole la representacion ASCII, el tipoAvatar que va a ser y el Jugador asociado a el
     * @param representacion char para representar en el tablero
     * @param tipo tipoAvatar para saber balon,esfinge...
     * @param jugador Jugador poseedor del avatar
     */
    public Avatar(char representacion,tipoAvatar tipo,Jugador jugador){
        if(representacion=='\0' || tipo==null || jugador==null){
            Mensajes.error("Error al crear avatar, atributos nulos");
            return;
        }
        this.representacion=representacion;
        this.tipo=tipo;
        this.jugador=jugador;
        posicion=new Posicion();
    }

    public char getRepresentacion() {
        return representacion;
    }
    public tipoAvatar getTipo(){
        return tipo;
    }
    public Jugador getJugador(){
        return jugador;
    }
    public Posicion getPosicion(){
        return posicion;
    }
}
