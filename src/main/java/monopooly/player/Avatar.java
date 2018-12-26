package monopooly.player;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.configuracion.General;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.entradaSalida.Juego;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionAcontecimiento;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.tiposAvatar.Biografo;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Guardado;
import monopooly.sucesos.tipoSucesos.PagoBanca;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * <h1>Clase Avatar</h1>
 *
 * <p>Permite instanciar las "fichas" que se mueven por el tablero, guardan
 * informacion sobre la posicion actual y el jugador al que pertenecen.</p>
 *
 * <p>EL nitroso cuando vale true activa el movimiento especial del Avatar.
 * Cada subclase determina su funcionamiento. Ecaso de estar desactivado se
 * usa el movimiento báscio típico en el Monopoly.</p>
 * <h2> Tipos de avatares: </h2>
 * <ul>
 *     <li>Coche: {@link monopooly.player.tiposAvatar.Coche}</li>
 *     <li>Pelota: {@link monopooly.player.tiposAvatar.Pelota}</li>
 *     <li>Esfinge: {@link monopooly.player.tiposAvatar.Esfinge}</li>
 *     <li>Sombrero: {@link monopooly.player.tiposAvatar.Sombrero}</li>
 * </ul>
 *
 * @author luastan
 * @author Danimf99
 */
public abstract class Avatar implements Observador {

    private char representacion;
    private Jugador jugador;
    private Posicion posicion;
    private Posicion oldPosicion;
    protected boolean nitroso;
    private Biografo biografo;

    private Subject subject;
    private ArrayList<Suceso> sucesos;

    public enum TIPO {
        sombrero,
        esfinge,
        coche,
        pelota
    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null || suceso instanceof Guardado) return;
        if (!suceso.getDeshacer()) sucesos.add(suceso);
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /*-------------------------*/
    /*CONSTRUCTOR AVATAR*/
    /*-------------------------*/


    /**
     * Constructor Base. Este constructor debe llamarse unicamente desde el
     * constructor de Jugador {@link Jugador#Jugador(java.lang.String, monopooly.player.Avatar.TIPO)}
     *
     * @param jugador Jugador que tendrá el avatar
     */
    public Avatar(Jugador jugador){
        this.representacion=sorteoAvatar(ReprASCII.AVATARES);
        this.jugador=jugador;
        this.posicion=new Posicion();
        this.oldPosicion=new Posicion();
        this.nitroso=false;
        this.sucesos = new ArrayList<>();
        this.setSubject(Partida.interprete);
        this.biografo = new Biografo();
    }

    /*-------------------------*/
    /*SETTERS Y GETTERS*/
    /*-------------------------*/


    public ArrayList<Suceso> getSucesos() {
        return sucesos;
    }

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

    public Posicion getOldPosicion() {
        return oldPosicion;
    }

    public void setOldPosicion(Posicion oldPosicion) {
        this.oldPosicion = new Posicion(oldPosicion);
    }

    public Subject getSubject() {
        return subject;
    }

    /**
     * Activa o desactiva el movimiento especial. Una vez se tire en el
     * turno actual no se podrá activar o desactivar el movimiento especial
     *
     * @param nitroso True para activarlo, false para desactivarlo
     * @throws ExcepcionAccionInvalida En caso de no estar permitido el cambio,
     * lanza esta excepción.
     */
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
     * Comprueba si puede pasarse turno y elimina al avatar de la lista de
     * observadores, los eventos que sucedan fuera de su turno no le interesan.
     * Se lanza una excepción en caso de que no le esté permitido al jugador
     * pasar turno en estos momentos. Este comportamiento varía dependiendo
     * del tipo de avatar y de si está el movimiento especial activado. Ver:
     * <ul>
     *     <li>{@link monopooly.player.tiposAvatar.Coche#pasarTurno()}</li>
     *     <li>{@link monopooly.player.tiposAvatar.Esfinge#pasarTurno()}</li>
     *     <li>{@link monopooly.player.tiposAvatar.Pelota#pasarTurno()}</li>
     *     <li>{@link monopooly.player.tiposAvatar.Sombrero#pasarTurno()}</li>
     * </ul>
     *
     * <p>En este metodo solo se implementan las comprobaciones para el caso
     * del movimiento básico.</p>
     *
     * @throws ExcepcionMonopooly En caso de que no se pueda pasar turno.
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
            throw new ExcepcionAccionInvalida("Ya compraste este turno.\n" +
                    "No puedes volver a comprar hasta el siguiente.");
        }
    }

    /**
     * Comprueba si se puede edificar en una casilla. Si el avatar no se
     * encuentra en ella lanza una excepcion de accion inválida.
     *
     * @param casilla Casilla donde se pretende edificar
     * @throws ExcepcionMonopooly Excepcion en caso de el avatar no se
     * encuentre en esa casilla.
     */
    public void intentarEdificar(Casilla casilla) throws ExcepcionMonopooly {
        if (!casilla.getAvatares().contains(this)) {
            throw new ExcepcionAccionInvalida(
                    "No puedes edificar en la casilla '" +
                            casilla.getNombre() + "'.\n" +
                            "Porque actualmente te encuentras en '" +
                            Tablero.getTablero().getCasilla(this.getPosicion()).getNombre() +
                            "'."
            );
        }
    }

    /**
     * Elige aleatoriamente una representación para el Avatar
     *
     * @param avatares Lista de avatares disponibles
     * @return caracter que representa el avatar
     */
    private char sorteoAvatar(List<Character> avatares){
        Random avatar=new Random();
        int n=avatar.nextInt(avatares.size());
        char ascii;

        ascii= ReprASCII.AVATARES.get(n);
        ReprASCII.AVATARES.remove(n);
        return ascii;
    }

    /* AQUI HABRIA QUE PONER EL MOVIMIENTO EN VEZ DE EN JUGADOR Y SE EMPLEMENTARIA EL BASICO*/

    /**
     * Dado un desplazamiento el avatar se mueve consecuentemente respecto a su
     * {@link Posicion} actual. Automaticamente se "pisa" la casilla en la que se
     * cae realizandose las acciones corresponidentes como pagar el alquiler, ir
     * a la carcel, cobrar el bote, la salida...
     *
     * @param desplazamiento Número de casillas que se desplazará el avatar
     * @throws ExcepcionMonopooly Excepcion en caso de movimientos invalidos o
     * {@link ExcepcionAcontecimiento} para casos como el de ir a la carcel o
     * activar una carta de suerte o Caja de Comunidad
     */
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

    /**
     * Funcionamiento idéntico a {@link Avatar#moverAvatar(int)}. Pero en este
     * caso se indica directamente cual será la nueva posicion del avatar en
     * lugar del numero de casillas que se desplaza
     *
     * @param posicion Nueva posición que tendrá el avatar
     * @throws ExcepcionMonopooly Excepcion en caso de movimientos invalidos o
     * {@link ExcepcionAcontecimiento} para casos como el de ir a la carcel o
     * activar una carta de suerte o Caja de Comunidad
     */
    public void moverAvatar(Posicion posicion) throws ExcepcionMonopooly {
        Tablero.getTablero().recolocar(this,posicion);
    }


    /**
     * Metodo común a todo tipo de lanzamientos. Realiza los preparativos para
     * realizarlos. Comprueba si el jugador está en la carcel o no, si sale
     * porque saca dobles y guarda el estado del Avatar para poder recuperarlo
     * más adelante.
     *
     * @throws ExcepcionMonopooly En caso de que se produzca una accion inválida
     */
    protected void preLanzamiento() throws ExcepcionMonopooly {
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
        this.backup();
    }


    /**
     * Movimiento básico del avatar
     *
     * @throws ExcepcionMonopooly Excepcion para movimientos invalidos o
     * acontecimientos como el de ir a la carcel
     */
    public void moverBasico() throws ExcepcionMonopooly {
        if (Tablero.getPrompt().getLanzamientosDados() == 0) {
            preLanzamiento();
            this.setOldPosicion(this.getPosicion());
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

    /**
     * Desencadena todas las acciones pertinentes. Lanza los dados mueve el
     * avatar y lo hace "caer" en la o las casillas correspondientes.
     *
     * @throws ExcepcionMonopooly Para movimientos inválidos o acontecimientos
     * como ir a la carcel
     */
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

    /**
     * Pide al Avatar que guarde su estado actual.
     *
     * <p>Si el si el avatar no realizo acciones este turno no se guarda info.
     * Esto permite evitar que veces en las que se pase turno se guarde todo
     * vacío; lo cual sería un funcionamiento correcto, porque tecnicamente
     * ha tenido turno, simplemente no ha sucedido nada, pero no es
     * interesante deshacer el vacío, por ello si no se producen cambios,
     * no se guarda el estado del avatar.
     *
     */
    public void backup() {
        if (this.sucesos.size() == 0) {
            return;
        }
        this.biografo.guardar(this);
    }

    /**
     * Recupera la versión inmediatamente anterior del avatar
     *
     */
    public void restore() throws ExcepcionMonopooly {
        this.biografo.deshacer(this);
    }

    /**
     * Guarda el estado actual del avatar
     *
     * @return Object con los datos newcesarios para reconstruir el estado actual del Avatar
     */
    public Snapshot guardar() {
        Partida.interprete.enviarSuceso(new Guardado(this.getJugador()));
        return new Snapshot(sucesos, oldPosicion);
    }

    /**
     * Recupera una version anterior del avatar
     *
     * @param anteriorVersion Snapshot del estado anterior del avatar
     */
    public void recuperar(Object anteriorVersion) throws ExcepcionMonopooly {
        Snapshot snapshot = (Snapshot) anteriorVersion;
        Tablero.getPrompt().setModDinero(0, "Viajaste en el tiempo");

        if (General.MODO_ABURRIDO) {
            for (Suceso suceso : snapshot.sucesosTirada) {
                Suceso.enviarSoloBeneficios(suceso);
            }
        } else {
            Tablero.getTablero().recolocacionSimple(this, snapshot.posicion);
            for (Suceso suceso : snapshot.sucesosTirada) {
                suceso.deshacer();
                Partida.interprete.enviarSuceso(suceso);
            }
        }
    }


    /**
     * Clase pivada donde se guarda el estado del Avatar. Sirve para
     * recuperarlo cuando se vuelve atrás en el tiempo
     */
    private class Snapshot {
        private ArrayList<Suceso> sucesosTirada;
        private Posicion posicion;

        Snapshot(ArrayList<Suceso> sucesosTirada, Posicion pos) {
            this.sucesosTirada = new ArrayList<>(sucesosTirada);
            this.posicion = new Posicion(pos);
            sucesosTirada.clear();
        }
    }
}
