package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;

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
        this.representacion= ReprASCII.AVATARES.get(0);
        ReprASCII.AVATARES.remove(0);
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
    public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof Avatar)) return false;
        Avatar avatar=(Avatar) o;
        return this.tipo==avatar.tipo
                && this.representacion==avatar.representacion;
    }
}
