package monopooly.player;

import monopooly.Estadisticas.EstadisticasJugador;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Edificaciones;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.entradaSalida.Prompt;

import java.util.HashSet;
import java.util.Objects;

public class Jugador {


    private String nombre;
    private int dinero;
    private Avatar avatar;
    private HashSet<Inmueble> propiedades;
    private Dados dados;
    private HashSet<Inmueble> hipotecas;
    private boolean estarEnCarcel;
    private int turnosEnCarcel;
    private EstadisticasJugador estadisticas;
    private int vueltas;
    private int vecesDados;
    private int cooldown;
    /**
     * Inicizaliza jugador pasandole el nombre, TipoAvatar y Dados, dinero y propiedades siempre se van a inicizalizar al mismo valor
     *
     * @param nombre string con el nombre del jugador
     * @param avatar TipoAvatar para saber si es sombrero,balon...
     */
    public Jugador(String nombre, TipoAvatar avatar) {
        if (avatar != null && nombre != null) {
            this.nombre=nombre;
            this.dados=new Dados();
            dinero= Precios.DINERO_INICIAL;
            propiedades=new HashSet<>();
            this.avatar=new Avatar(avatar,this);
            this.hipotecas=new HashSet<>();
            this.estarEnCarcel=false;
            this.turnosEnCarcel=0;
            estadisticas=new EstadisticasJugador();
            this.vueltas = 0;
            this.cooldown = 0;
            this.vecesDados=0;
        }
    }

    /**
     * Constructor para el jugador Banca
     * @param propiedades todas las propiedades del tablero
     */
    public Jugador(HashSet<Inmueble> propiedades){
        avatar=new Avatar(TipoAvatar.sombrero,this);
        dados=new Dados();
        this.propiedades=propiedades;
        this.nombre="Banca";
        this.dinero=0;
        this.estarEnCarcel=false;
        this.hipotecas = new HashSet<>();
        this.estarEnCarcel = false;
        this.turnosEnCarcel=0;
        this.vueltas=0;
        this.vecesDados=0;
        estadisticas=new EstadisticasJugador();
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }

    public void reducirCooldown() {
        this.cooldown--;
    }

    public int getVueltas() {
        return vueltas;
    }

    public int getVecesDados() {
        return vecesDados;
    }

    public int getDinero() {
        return dinero;
    }

    public int getTurnosEnCarcel(){return turnosEnCarcel;}

    public EstadisticasJugador getEstadisticas() {
        return estadisticas;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public HashSet<Inmueble> getHipotecas(){
        return hipotecas;
    }

    public String getNombre() {
        return nombre;
    }
    //Con el get ya comprobamos si esta en la carcel o no
    public boolean getEstarEnCarcel(){
        return estarEnCarcel;
    }
    public void setEstarEnCarcel(boolean carcel){
        this.estarEnCarcel=carcel;
    }

    public HashSet<Inmueble> getPropiedades() {
        return propiedades;
    }
    public Dados getDados(){
        return dados;
    }
    //LOS SETTERS PARA PROPIEDADES Y DINERO NO HACEN FALTA, YA QUE TENEMOS METODOS PARA IR AÑADIENDO Y QUITANDO TANTO UNO COMO OTRO
    /**
     * Al jugador se le añade una propiedad ya sea porque la compra, la intercambia por otro jugador...
     * @param propiedad propiedad nueva que se le introduce al jugador
     */
    public void anhadirPropiedad(Inmueble propiedad){
        if(propiedad==null){
            Mensajes.error("La propiedad que se quiere añadir es nula");
            return;
        }
        this.propiedades.add(propiedad);
    }

    /**
     * Al jugador se le retira una propiedad de su lista de propiedades ya sea porque la vende, la intercambia....
     * @param propiedad propiedad que se quiere retirar
     */
    public void quitarPropiedad(Inmueble propiedad){
        if(propiedad==null){
            Mensajes.error("La propiedad que se quiere retirar es nula");
            return;
        }
        this.propiedades.remove(propiedad);
    }

    public void hipotecar(Inmueble inmueble){
        if(inmueble==null){
            Mensajes.error("Inmueble null, no se puede hipotecar");
            return;
        }
        hipotecas.add(inmueble);
        anhadirDinero(inmueble.getPrecio_inicial()/2);
        quitarPropiedad(inmueble);
        inmueble.setHipotecado(true);
    }

    public void aumentarVecesDados(){
        this.vecesDados++;
    }
    /**
     * Al jguador se le quitar una cantidad de dinero
     *
     * @param cantidad cantidad que se desea quitar al jugador
     */
    public void quitarDinero(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero -= cantidad;
    }

    /**
     * AL jugador se le suma una cantidad de dinero
     *
     * @param cantidad cantidad que se desea sumar al jugador
     */
    public void anhadirDinero(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("Error en la cantidad que quiere quitar, tiene que ser positiva");
            return;
        }
        this.dinero += cantidad;
    }

    public void moverJugador(Tablero tablero, int desplazamiento) {
        tablero.getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        avatar.getPosicion().mover(desplazamiento);
        tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
    }

    public void moverJugador(Tablero tablero, int desplazamiento, Prompt prompt) {
        moverJugador(tablero, desplazamiento);
        prompt.anhadirPosicion(this.avatar.getPosicion());
    }

    public void moverJugador(Tablero tablero, Posicion posicion) {
        tablero.getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        avatar.getPosicion().setX(posicion.getX());
        tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
    }


    public void cocheHandler(Prompt prompt) {
        int turnos_especiales = 4;
        this.dados.lanzar();
        if (prompt.getTiradasEspeciales() >= turnos_especiales) {
            this.dados.setContador(1);
        } else {
            this.dados.setContador(0);
        }
        if (this.dados.tirada() > 4) {
            this.moverJugador(prompt.getTablero(), this.dados.tirada());
            prompt.anhadirPosicion(this.avatar.getPosicion());
        } else {
            this.moverJugador(prompt.getTablero(), -this.dados.tirada());
            this.cooldown = 3; // Turnos antes de volver a tirar
            this.dados.setContador(1);
        }
    }

    public boolean puedeComprar(Inmueble propiedad, Prompt prompt) {
        if (prompt.isCompro()) {
            return false;
        }

        for (Posicion posicion : prompt.getPosicionesTurno()) {
            if (propiedad.equals(prompt.getTablero().getCasilla(posicion).getCalle())) {
                return true;
            }
        }
        return prompt.isCompro();
    }


    /**
     * Mueve al jugador dependiendo de muchos factores
     * @param tablero tablero donde se mueve al jugador
     */
    public void moverJugador(Tablero tablero){
        if(tablero==null){
            Mensajes.error("Tablero nulo, no se puede mover al jugador");
            return;
        }

        if(this.estarEnCarcel && this.turnosEnCarcel!=3){
            Mensajes.info("Estás en la cárcel, no puedes moverte.\n" +
                    "Paga "+Precios.SALIR_CARCEL+" para salir de la carcel");
            this.turnosEnCarcel++;
            return;
        }
        if(this.turnosEnCarcel==3){
            Mensajes.info("Ya pasaste 3 turnos en la cárcel, pagas automaticamente para salir.");
            if(this.getDinero()<Precios.SALIR_CARCEL){
                Mensajes.info("Debes declararte en bancarrota, no tienes dinero suficiente para salir de la carcel");
            }
            this.quitarDinero(Precios.SALIR_CARCEL);
            this.estarEnCarcel=false;
            this.turnosEnCarcel=0;
        }
        if (this.avatar.getNitroso()) {
            // TODO : llamar al mov especial
            return;
        }
        dados.lanzar();
        aumentarVecesDados();
        if(dados.sonDobles()){
            Mensajes.info("Puede tirar otra vez, dados dobles");

        }
        if(this.estarEnCarcel && dados.sonDobles()) {
            Mensajes.info("Sacaste dobles, sales de la carcel");
            this.estarEnCarcel = false;
        }
        if(this.dados.getDobles()==3) {
            Mensajes.info("No puede seguir tirando, 3 dobles seguidos, vas a la carcel");
            this.estadisticas.sumarVecesCarcel(1);
            this.estarEnCarcel = true;
            avatar.getPosicion().irCarcel();
            tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
            return;
        }
        this.moverJugador(tablero, dados.tirada());
    }

    public void aumentarVueltas(){
        this.vueltas++;
    }

    public int calcularFortunaTotal(){
        int fortuna=this.dinero;

        for(Inmueble i: propiedades){
            fortuna+=i.getPrecio_inicial();
            for(Edificaciones e:i.getEdificios()){
                fortuna+=e.getPrecio();
            }
        }

        return fortuna;
    }
    @Override
    public String toString() {
        int j=0;
        StringBuilder imprimirJugador = new StringBuilder();
        imprimirJugador.append("Jugador{\n" +
                "   Nombre: " + nombre +
                "\n   Fortuna: " + dinero +
                "\n   Avatar: " + getAvatar().getRepresentacion()+"\n   Propiedades: [");
        for(Inmueble i:propiedades){
            if(propiedades.size()-1==j){
                imprimirJugador.append(i.getNombre());
            }
            else {
                imprimirJugador.append(i.getNombre()+",");
            }
            j++;
        }
        imprimirJugador.append("]\n   Hipotecadas: [");
        j=0;
        for(Inmueble h:hipotecas){
            if(hipotecas.size()-1==j) {
                imprimirJugador.append(h.getNombre());
            }
            else{
                imprimirJugador.append(h.getNombre()+",");
            }
            j++;
        }
        imprimirJugador.append("]\n");
        imprimirJugador.append("   Edificios: [");
        for(Inmueble h:propiedades){
            j=0;
           if(h.getEdificios().size()!=0){
               for(Edificaciones e:h.getEdificios()){
                   if(h.getEdificios().size()-1==j) {
                       imprimirJugador.append(e.getId());
                   }
                   else{
                       imprimirJugador.append(e.getId()+",");
                   }
                   j++;
               }
           }
        }
        imprimirJugador.append("]\n}");
        return  PintadoASCII.encuadrar(imprimirJugador.toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Jugador jugador = (Jugador) o;
        return dinero == jugador.dinero &&
                Objects.equals(nombre, jugador.nombre);

    }
//
    //@Override
    //public int hashCode() {
    //    return Objects.hash(nombre, dinero, avatar, propiedades, dados);
    //}

}
