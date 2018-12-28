package monopooly.player;

import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Juego;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.estadisticas.StatsJugador;
import monopooly.excepciones.*;
import monopooly.player.Tratos.Trato;
import monopooly.player.tiposAvatar.Coche;
import monopooly.player.tiposAvatar.Esfinge;
import monopooly.player.tiposAvatar.Pelota;
import monopooly.player.tiposAvatar.Sombrero;
import monopooly.sucesos.tipoSucesos.HipotecarPropiedad;
import monopooly.sucesos.tipoSucesos.PasoSalida;

import java.util.HashSet;
import java.util.Objects;

public class Jugador {

    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Propiedad> propiedades;
    private Dados dados;
    private HashSet<Propiedad> hipotecas;
    private boolean estarEnCarcel;
    private int turnosEnCarcel;
    private StatsJugador estadisticas;
    private int vecesDados;
    private int cooldown;
    private HashSet<Trato> tratos;


    /*-------------------------*/
    /* CONSTRUCTORES PARA JUGADOR */
    /*-------------------------*/

    public Jugador(String nombre,Avatar.TIPO avatar) {
        this.nombre = nombre;
        this.dinero= Precios.DINERO_INICIAL;
        this.dados=new Dados();
        this.propiedades=new HashSet<>();
        this.hipotecas=new HashSet<>();
        this.estarEnCarcel=false;
        this.turnosEnCarcel=0;
        this.estadisticas=new StatsJugador(Partida.interprete, this);
        this.vecesDados=0;
        this.cooldown=0;
        this.tratos=new HashSet<>();
        switch(avatar){
            case coche:
                this.avatar=new Coche(this);
                break;
            case esfinge:
                this.avatar=new Esfinge(this);
                break;
            case sombrero:
                this.avatar=new Sombrero(this);
                break;
            case pelota:
                this.avatar=new Pelota(this);
                break;
        }
    }

    public Jugador() {
        avatar=new Coche(this);
        dados=new Dados();
        this.propiedades=new HashSet<>();
        this.nombre="Banca";
        this.dinero=0;
        this.estarEnCarcel=false;
        this.hipotecas = new HashSet<>();
        this.estarEnCarcel = false;
        this.turnosEnCarcel=0;
        this.vecesDados=0;
    }

    /*-------------------------*/
    /* SETTERS Y GETTERS*/
    /*-------------------------*/

    public void setEstadisticas(StatsJugador estadisticas) {
        this.estadisticas = estadisticas;
    }

    public HashSet<Trato> getTratos() {
        return tratos;
    }

    public void setTratos(HashSet<Trato> tratos) {
        this.tratos = tratos;
    }

    public StatsJugador getEstadisticas() {
        return estadisticas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getDinero(){
        return dinero;
    }

    public void setDinero(int dinero){
        this.dinero=dinero;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public HashSet<Propiedad> getPropiedades() {
        return new HashSet<>(propiedades);
    }

    public void setPropiedades(HashSet<Propiedad> propiedades) {
        this.propiedades = propiedades;
    }

    public Dados getDados() {
        return dados;
    }

    public void setDados(Dados dados) {
        this.dados = dados;
    }

    public HashSet<Propiedad> getHipotecas() {
        return hipotecas;
    }

    public void setHipotecas(HashSet<Propiedad> hipotecas) {
        this.hipotecas = hipotecas;
    }

    public boolean isEstarEnCarcel() {
        return estarEnCarcel;
    }

    public void setEstarEnCarcel(boolean estarEnCarcel) throws ExcepcionAccionInvalida {
        if (estarEnCarcel) {
            Tablero.getPrompt().setLanzamientosDados(100);
        }
        this.estarEnCarcel = estarEnCarcel;
        if (avatar instanceof Esfinge && estarEnCarcel) {
            avatar.setNitroso(false);
        }
    }

    public int getTurnosEnCarcel() {
        return turnosEnCarcel;
    }

    public void setTurnosEnCarcel(int turnosEnCarcel) {
        this.turnosEnCarcel = turnosEnCarcel;
    }

    public int getVueltas() {
        return this.estadisticas.getVueltas();
    }

    public void setVueltas(int vueltas) {
        this.estadisticas.setVueltas(vueltas);
    }

    public int getVecesDados() {
        return vecesDados;
    }

    public void setVecesDados(int vecesDados) {
        this.vecesDados = vecesDados;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
    /*-------------------------*/
    /* METODOS PARA JUGADOR */
    /*-------------------------*/
    public boolean puedeComprar(Casilla casilla) throws ExcepcionMonopooly {
        /*
        if (Tablero.getPrompt().isCompro()) {
            throw new ExcepcionAccionInvalida("El jugador ya comrpró este turno");
        }
        if(Tablero.getPrompt().getPosicionesTurno().size()==0){
            if(!casilla.getPosicion().equals(Tablero.getPrompt().getJugador().getAvatar().getPosicion())){
                return false;
            }
        }
        for (Posicion posicion : Tablero.getPrompt().getPosicionesTurno()) {
            if (casilla.equals(Tablero.getTablero().getCasilla(posicion))) {
                return true;
            }
        }
        return !Tablero.getPrompt().isCompro();
        */

        // La comprobacion de la compra es distinta para cada avatar y tambien varia dependiendo de si está
        // activado el movimiento especial o no. Es mas facil meter las comprobaciones en cada tipo de avatar

        this.avatar.intentarComprar(casilla);
        return true; // Permite usarla de la misma manera que antes
    }


    public void pasarTurno() throws ExcepcionMonopooly {
        this.avatar.pasarTurno();
        if (dinero < 0) {
            throw new ExcepcionRecursosInsuficientes("Tienes dinero negativo -> " + dinero + " " + Precios.MONEDA+ " ,debes declararte en bancarrota");
        }
        // TODO comprobaciones de paso de turno aqui con sus excepciones

    }

    public void aumentarVecesDados(){
        this.vecesDados++;
    }

    public void checkCarcel() throws ExcepcionMonopooly {
        if(this.estarEnCarcel && dados.sonDobles()) {
            Juego.consola.info("Sacaste dobles, sales de la carcel");
            this.estarEnCarcel = false;
            return;
        }

        if(this.estarEnCarcel && this.turnosEnCarcel!=3){
            this.turnosEnCarcel++;
            throw new ExcepcionAccionInvalida("Estás en la cárcel, no puedes moverte.\n" +
                    "Paga "+Precios.SALIR_CARCEL+" para salir de la carcel,\n" +
                    "saca dobles en alguna de tus tiradas disponibles o \n" +
                    "espera " + ( 4 - this.turnosEnCarcel ) + " turnos más.");
        }

        if(this.dados.getDobles()==3) {
            this.estarEnCarcel = true;
            Posicion posicion=new Posicion();
            posicion.setX(Posiciones.CARCEL);
            Tablero.getTablero().recolocar(avatar,posicion);
            throw new ExcepcionAcontecimiento("No puede seguir tirando, 3 dobles seguidos, vas a la carcel");
        }
    }

    public void pagoSalida() {
        Jugador jActual = Tablero.getPrompt().getJugador();
        jActual.anhadirDinero(Precios.SALIDA);
        Partida.interprete.enviarSuceso(new PasoSalida(jActual));

    }

    public void hipotecar(Propiedad inmueble){
        if(inmueble==null){
            Juego.consola.error("Inmueble null, no se puede hipotecar");
            return;
        }
        hipotecas.add(inmueble);
        anhadirDinero(inmueble.getMonopolio().getPrecio()/2);
        quitarPropiedad(inmueble);
        inmueble.setHipotecado(true);
        Partida.interprete.enviarSuceso(new HipotecarPropiedad(this, inmueble, true));
    }
    public void deshipotecar(Propiedad inmueble) throws ExcepcionMonopooly {
        if(inmueble==null){
            Juego.consola.error("Inmueble null, no se puedes deshipotecar");
            return;
        }
        this.anhadirPropiedad(inmueble);
        inmueble.setHipotecado(false);
        this.hipotecas.remove(inmueble);
        this.quitarDinero((int)(inmueble.getMonopolio().getPrecio()*1.1));
        Partida.interprete.enviarSuceso(new HipotecarPropiedad(this, inmueble, false));
    }
    /**
     * AL jugador se le suma una cantidad de dinero
     *
     * @param cantidad cantidad que se desea sumar al jugador
     */
    public void anhadirDinero(int cantidad) {
        this.dinero += cantidad;
    }
    public void quitarDinero(int cantidad) throws ExcepcionMonopooly {
        if (cantidad < 0) {
            throw new ExcepcionParametrosInvalidos("Error en la cantidad que quiere quitar, tiene que ser positiva");
        }
        if(cantidad>this.dinero){
            throw new ExcepcionRecursosInsuficientes("La cantidad que quiere quitar es mayor que la fortuna actual");
        }
        this.dinero -= cantidad;
    }

    /**
     * Al jugador se le añade una propiedad ya sea porque la comprar, la intercambia por otro jugador...
     * @param propiedad propiedad nueva que se le introduce al jugador
     */
    public void anhadirPropiedad(Propiedad propiedad){
        if(propiedad==null){
            Juego.consola.error("La propiedad que se quiere añadir es nula");
            return;
        }
        this.propiedades.add(propiedad);
    }
    public int calcularFortunaTotal(){
        int fortuna=this.dinero;

        for(Propiedad i: propiedades){
            fortuna+=i.getPrecio();
            if(i instanceof Solar){
                for(Edificio e: ((Solar) i).getEdificios()){
                    fortuna+=e.getPrecio();
                }
            }
        }

        return fortuna;
    }
    /**
     * Al jugador se le retira una propiedad de su lista de propiedades ya sea porque la vende, la intercambia....
     * @param propiedad propiedad que se quiere retirar
     */
    public void quitarPropiedad(Propiedad propiedad){
        if(propiedad==null){
            Juego.consola.error("La propiedad que se quiere retirar es nula");
            return;
        }
        this.propiedades.remove(propiedad);
    }
    @Override
    public String toString() {
        int j=0;
        StringBuilder imprimirJugador = new StringBuilder();
        imprimirJugador.append("Jugador{\n" +
                "   Nombre: " + nombre +
                "\n   Fortuna: " + dinero +
                "\n   Avatar: " + getAvatar().getRepresentacion()+"\n   Propiedades: [");
        meterPropiedades(j, imprimirJugador, propiedades);
        imprimirJugador.append("]\n   Hipotecadas: [");
        j=0;
        meterPropiedades(j, imprimirJugador, hipotecas);
        imprimirJugador.append("]\n}");
        return  PintadoAscii.encuadrar(imprimirJugador.toString());
    }

    private void meterPropiedades(int j, StringBuilder imprimirJugador, HashSet<Propiedad> propiedades) {
        for(Propiedad i: propiedades){
            if(propiedades.size()-1==j){
                imprimirJugador.append(i.getNombre());
            }
            else {
                imprimirJugador.append(i.getNombre()+",");
            }
            j++;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return Objects.equals(nombre, jugador.nombre);

    }
}
