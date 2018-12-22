package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.configuracion.ReprASCII;

import java.util.List;
import java.util.Random;

public abstract class Avatar {

    private char representacion;
    private Jugador jugador;
    private Posicion posicion;
    private boolean nitroso;

    /*-------------------------*/
    /*CONSTRUCTOR AVATAR*/
    /*-------------------------*/

    public Avatar(Jugador jugador){
        this.representacion=sorteoAvatar(ReprASCII.AVATARES);
        this.jugador=jugador;
        this.posicion=new Posicion();
        this.nitroso=false;
    }

    /*-------------------------*/
    /*SETTERS Y GETTERS*/
    /*-------------------------*/

    public char getRepresentacion() {
        return representacion;
    }

    public void setRepresentacion(char representacion) {
        this.representacion = representacion;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public Posicion getPosicion() {
        return posicion;
    }

    public void setPosicion(Posicion posicion) {
        this.posicion = posicion;
    }

    public boolean isNitroso() {
        return nitroso;
    }

    public void setNitroso(boolean nitroso) {
        this.nitroso = nitroso;
    }

    /*-------------------------*/
    /* METODOS PARA VATAR */
    /*-------------------------*/

    private char sorteoAvatar(List<Character> AVATARES){
        Random avatar=new Random();
        int n=avatar.nextInt(AVATARES.size());
        char ascii;

        ascii= ReprASCII.AVATARES.get(n);
        ReprASCII.AVATARES.remove(n);
        return ascii;
    }

    /* AQUI HABRIA QUE PONER EL MOVIMIENTO EN VEZ DE EN JUGADOR Y SE EMPLEMENTARIA EL BASICO*/

    public void moverBasico(){

    }
    /* QUE LO IMPLEMENTE CADA SUBCLASE */
   // public abstract void moverAvanzado();
    @Override
    public String toString(){
        return "\n   Representación: "+representacion+
                "\n   Jugador: "+jugador.getNombre()+
                "\n   Posicion: "+posicion.getX();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Avatar avatar = (Avatar) o;
        return representacion == avatar.representacion;
    }
}
