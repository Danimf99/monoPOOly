package monopooly.player;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.Visitante;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionMonopooly;

import java.util.List;
import java.util.Random;

public abstract class Avatar {

    private char representacion;
    private Jugador jugador;
    private Posicion posicion;
    private boolean nitroso;
    private MementoAvatar mementoAvatar;

    public static enum TIPO {
        sombrero,
        esfinge,
        coche,
        pelota
    }


    /*-------------------------*/
    /*CONSTRUCTOR AVATAR*/
    /*-------------------------*/

    public Avatar(Jugador jugador){
        this.representacion=sorteoAvatar(ReprASCII.AVATARES);
        this.jugador=jugador;
        this.posicion=new Posicion();
        this.nitroso=false;
        this.mementoAvatar = null;
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

    public abstract Avatar.TIPO getTipo();

    /*-------------------------*/
    /* METODOS PARA VATAR */
    /*-------------------------*/

    public void pasarTurno() {
        MementoAvatar old = mementoAvatar;
        mementoAvatar = new MementoAvatar(this, old);
    }


    private char sorteoAvatar(List<Character> AVATARES){
        Random avatar=new Random();
        int n=avatar.nextInt(AVATARES.size());
        char ascii;

        ascii= ReprASCII.AVATARES.get(n);
        ReprASCII.AVATARES.remove(n);
        return ascii;
    }

    /* AQUI HABRIA QUE PONER EL MOVIMIENTO EN VEZ DE EN JUGADOR Y SE EMPLEMENTARIA EL BASICO*/
    public void moverAvatar( int desplazamiento) throws ExcepcionMonopooly {
        if (desplazamiento == 0) {
            return;
        }
        Posicion posicion=new Posicion();
        posicion.mover(desplazamiento+getPosicion().getX());
        Tablero.getTablero().recolocar(this,posicion);
        this.getPosicion().mover(desplazamiento);
        Tablero.getPrompt().anhadirPosicion(this.getPosicion());
        Visitante visitante=new Visitante(getJugador());
    }

    public void moverAvatar(Posicion posicion) throws ExcepcionMonopooly {
        Tablero.getTablero().recolocar(this,posicion);
        this.getPosicion().setX(posicion.getX());
        Tablero.getPrompt().anhadirPosicion(this.getPosicion());
        Visitante visitante = new Visitante(getJugador());
    }

    public void moverBasico() throws ExcepcionMonopooly {
        if (this.getJugador().getCooldown() > 0) {
            this.getJugador().setCooldown(this.getJugador().getCooldown()-1);
            return;
        }

        if(this.getJugador().getTurnosEnCarcel()==3){
            Juego.consola.info("Ya pasaste 3 turnos en la cárcel, pagas automaticamente para salir.");
            if(this.getJugador().getDinero()< Precios.SALIR_CARCEL){
                Juego.consola.info("Debes declararte en bancarrota, no tienes dinero suficiente para salir de la carcel");
            }
            this.getJugador().quitarDinero(Precios.SALIR_CARCEL);
            this.getJugador().setEstarEnCarcel(false);
            this.getJugador().setTurnosEnCarcel(0);
        }
        this.getJugador().getDados().lanzar();
        this.getJugador().aumentarVecesDados();
        getJugador().checkCarcel();
        if(getJugador().isEstarEnCarcel()){
            return;
        }
        Tablero.getPrompt().getPosicionesTurno().clear();
        Tablero.getPrompt().setCompro(false);
        moverAvatar(getJugador().getDados().tirada());
        getJugador().pagoSalida();
    }

    /* QUE LO IMPLEMENTE CADA SUBCLASE */
    public abstract void moverAvanzado() throws ExcepcionMonopooly;
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
