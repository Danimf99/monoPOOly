package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;

import java.util.List;
import java.util.Random;

public class Avatar {
    private TipoAvatar tipo;
    private char representacion;
    private Jugador jugador;
    private Posicion posicion;
    private int vueltasTablero;
    private boolean nitroso;

    /**
     * Inicializa avatar pasandole la representacion ASCII, el TipoAvatar que va a ser y el Jugador asociado a el
     * @param tipo TipoAvatar para saber balon,esfinge...
     * @param jugador Jugador poseedor del avatar
     */
    public Avatar(TipoAvatar tipo, Jugador jugador){
        if(tipo==null || jugador==null){
            Mensajes.error("Error al crear avatar, atributos nulos");
            return;
        }
        this.representacion= sorteoAvatar(ReprASCII.AVATARES);
        this.tipo=tipo;
        this.jugador=jugador;
        posicion=new Posicion();
        vueltasTablero = 0;
        nitroso=false;
    }
    private char sorteoAvatar(List<Character> AVATARES){
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
    public boolean getNitroso(){
        return nitroso;
    }
    public void setNitroso(boolean nitroso){
        this.nitroso=nitroso;
    }
    public TipoAvatar getTipo(){
        return tipo;
    }
    public Jugador getJugador(){
        return jugador;
    }
    public Posicion getPosicion(){
        return posicion;
    }
    public void setPosicion(int nuevaPosicion){
        if(nuevaPosicion<0){
            Mensajes.error("Error en la nueva posicion");
            return;
        }
        this.posicion.setX(nuevaPosicion);
    }

    public void sumarVuelta() {
        vueltasTablero++;
    }

    public int getVueltasTablero() {
        return vueltasTablero;
    }

    //Con setPosicion creo que llega, no hace falta hacer un set a los demas atributos
    @Override
    public String toString(){
        return PintadoASCII.encuadrar("Avatar{\n"+
                "   Tipo: "+tipo+
                "\n   Representación: "+representacion+
                "\n   Jugador: "+jugador.getNombre()+
                "\n   Posicion: "+posicion.getX()
                +"\n}");
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
