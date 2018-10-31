package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;

import java.util.List;
import java.util.Random;

public class Avatar {
    private tipoAvatar tipo;
    private char representacion;
    private Jugador jugador;
    private Posicion posicion;

    /**
     * Inicializa avatar pasandole la representacion ASCII, el tipoAvatar que va a ser y el Jugador asociado a el
     * @param tipo tipoAvatar para saber balon,esfinge...
     * @param jugador Jugador poseedor del avatar
     */
    public Avatar(tipoAvatar tipo,Jugador jugador){
        if(tipo==null || jugador==null){
            Mensajes.error("Error al crear avatar, atributos nulos");
            return;
        }
        this.representacion= sorteoAvatar(ReprASCII.AVATARES);
        this.tipo=tipo;
        this.jugador=jugador;
        posicion=new Posicion();
    }
    public char sorteoAvatar(List<Character> AVATARES){
        Random avatar=new Random();
        int n=avatar.nextInt(AVATARES.size());
        char ascii;

        ascii=ReprASCII.AVATARES.get(n);
        ReprASCII.AVATARES.remove(n);
        return ascii;
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
    public void setPosicion(Posicion nuevaPosicion){
        if(nuevaPosicion==null){
            Mensajes.error("Error en la nueva posicion");
            return;
        }
        this.posicion=nuevaPosicion;
    }
    //Con setPosicion creo que llega, no hace falta hacer un set a los demas atributos
    @Override
    public String toString(){
        return "Avatar{"+
                "\tTipo: "+tipo+
                "\tRepresentación: "+representacion+
                "\tJugador: "+jugador.getNombre()+
                "\tPosicion: "+posicion.getX()
                +"}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return representacion == avatar.representacion;
    }

    //@Override
    //public int hashCode() {
    //    return Objects.hash(tipo, representacion, jugador, posicion);
    //}
}
