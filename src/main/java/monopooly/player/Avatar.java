package monopooly.player;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Avatar implements Observador {

    private char representacion;
    private Jugador jugador;
    private Posicion posicion;
    private Posicion oldPosicion;
    private boolean nitroso;

    private Subject subject;
    private ArrayList<Suceso> sucesos;

    public static enum TIPO {
        sombrero,
        esfinge,
        coche,
        pelota
    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        if (!suceso.getDeshacer()) {
            sucesos.add(suceso);
        }
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /*-------------------------*/
    /*CONSTRUCTOR AVATAR*/
    /*-------------------------*/



    public Avatar(Jugador jugador){
        this.representacion=sorteoAvatar(ReprASCII.AVATARES);
        this.jugador=jugador;
        this.posicion=new Posicion();
        this.oldPosicion=new Posicion();
        this.nitroso=false;
        this.sucesos = new ArrayList<>();
        this.setSubject(Partida.interprete);
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

    public void setNitroso(boolean nitroso) throws ExcepcionAccionInvalida {
        if (Tablero.getPrompt().getLanzamientosDados() > 0 || Tablero.getPrompt().isUsoMovEspecial()) {
            throw new ExcepcionAccionInvalida("No puedes cambiar de modo despues de tirar.\n" +
                    "Inténtalo el próximo turno");
        }
        this.nitroso = nitroso;
    }

    public abstract Avatar.TIPO getTipo();

    /*-------------------------*/
    /* METODOS PARA AVATAR */
    /*-------------------------*/

    /**
     * Comprueba si puede pasarse turno y guarda info del turno actual
     */
    public void pasarTurno() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().getLanzamientosDados() == 0 ||
                Tablero.getPrompt().getLanzamientosDados() < 3 && jugador.getDados().sonDobles()) {
            throw new ExcepcionAccionInvalida("Aun no tiraste todas las veces permitidas");
        }
        this.subject.eliminar(this);
    }




    /**
     * Comprobacion clasica de la compra. Si está en la casilla y no ha comprado este turno puede comprar
     *
     * @throws ExcepcionMonopooly Excepcion en caso de que no se pueda comprar
     */
    public void intentarComprar(Casilla casilla) throws ExcepcionMonopooly {
        if (!casilla.getPosicion().equals(this.posicion)) {
            throw new ExcepcionAccionInvalida(
                "No puedes comprar la casilla '" +
                casilla.getNombre() + "'.\n" +
                "Porque actualmente te encuentras en '" +
                Tablero.getTablero().getCasilla(this.getPosicion()).getNombre() +
                "'."
            );
        }

        if (Tablero.getPrompt().isCompro()) {
            // Se usará como fallback en caso de que no esté el movimiento especial activo
            throw new ExcepcionAccionInvalida("Ya compraste este turno.\n" +
                    "No puedes volver a comprar hasta el siguiente.");
        }
    }

    public void intentarEdificar(Casilla casilla) throws ExcepcionMonopooly {
        if (!casilla.getPosicion().equals(this.posicion)) {
            throw new ExcepcionAccionInvalida(
                    "No puedes edificar en la casilla '" +
                            casilla.getNombre() + "'.\n" +
                            "Porque actualmente te encuentras en '" +
                            Tablero.getTablero().getCasilla(this.getPosicion()).getNombre() +
                            "'."
            );
        }
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
        if (posicion.getX() - desplazamiento < 0) {
            this.getJugador().pagoSalida();
        }
    }

    public void moverAvatar(Posicion posicion) throws ExcepcionMonopooly {
        Tablero.getTablero().recolocar(this,posicion);
    }


    protected void preLanzamiento() throws ExcepcionMonopooly {
        this.oldPosicion = new Posicion(this.posicion);
        Tablero.getPrompt().aumentarLanzamientosDados();
        this.getJugador().getDados().lanzar();
        getJugador().checkCarcel();
        if(this.getJugador().getTurnosEnCarcel()==3){
            Juego.consola.info("Ya pasaste 3 turnos en la cárcel, pagas automaticamente para salir.");
            if(this.getJugador().getDinero()< Precios.SALIR_CARCEL){
                Juego.consola.info("Debes declararte en bancarrota, no tienes dinero suficiente para salir de la carcel");
            }
            this.getJugador().quitarDinero(Precios.SALIR_CARCEL);
            this.getJugador().setEstarEnCarcel(false);
            this.getJugador().setTurnosEnCarcel(0);
            Partida.interprete.enviarSuceso(new PagoBanca(this.getJugador(), -Precios.SALIR_CARCEL, "Pago por salir de la carcel"));
        }
        if(getJugador().isEstarEnCarcel()){
            throw new ExcepcionAccionInvalida("Estas en la carcel; no puedes moverte");
        }
    }

    public void moverBasico() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().getLanzamientosDados() == 0) {
            preLanzamiento();
            moverAvatar(getJugador().getDados().tirada());
        } else if (Tablero.getPrompt().getLanzamientosDados() < 3 && this.getJugador().getDados().sonDobles()) {
            if(getJugador().isEstarEnCarcel()){
                throw new ExcepcionAccionInvalida("Estas en la carcel; no puedes moverte");
            }
            preLanzamiento();
            moverAvatar(getJugador().getDados().tirada());
        } else {
            throw new ExcepcionAccionInvalida("Ya tiraste este turno");
        }
    }

    public void lanzarDados() throws ExcepcionMonopooly {
        if (nitroso) this.moverAvanzado();
        else this.moverBasico();
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

    /*

        MEMENTO

     */

    public Memento guardar() {
        Partida.interprete.enviarSuceso(new Guardado(this.getJugador()));
        return new Memento(sucesos, oldPosicion);
    }

    public void deshacer(Object object) throws ExcepcionMonopooly {
        Memento memento = (Memento) object;
        Tablero.getTablero().recolocacionSimple(this, memento.posicion);
        this.posicion = memento.posicion;
        Tablero.getPrompt().setModDinero(0, "Viajaste en el tiempo");
        for (Suceso suceso : memento.sucesosTirada) {
            suceso.deshacer();
            Partida.interprete.enviarSuceso(suceso);
        }
    }


    private class Memento {
        private ArrayList<Suceso> sucesosTirada;
        private Posicion posicion;

        Memento(ArrayList<Suceso> sucesosTirada, Posicion posicion) {
            this.sucesosTirada = new ArrayList<>(sucesosTirada);
            this.posicion = new Posicion(posicion);
            sucesosTirada.clear();
        }
    }
}
