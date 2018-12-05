package monopooly.player;

import monopooly.Estadisticas.EstadisticasJugador;
import monopooly.cartas.CajaComunidad;
import monopooly.cartas.Suerte;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Casilla;
import monopooly.colocacion.calles.Edificaciones;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.configuracion.Posiciones;
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
        this.dinero += cantidad;
    }

    public void moverJugador(Prompt prompt, int desplazamiento) {
        if (desplazamiento == 0) {
            return;
        }
        Tablero tablero = prompt.getTablero();
        tablero.getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        avatar.getPosicion().mover(desplazamiento);
        pisarCasilla(prompt, avatar.getPosicion());
        tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
    }


    public void moverJugador(Prompt prompt, Posicion posicion) {
        prompt.getTablero().getCasilla(this.avatar.getPosicion()).getAvatares().remove(this.avatar);
        avatar.getPosicion().setX(posicion.getX());
        pisarCasilla(prompt, avatar.getPosicion());
        prompt.getTablero().getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
    }


    public void pisarCasilla(Prompt prompt, Posicion posicion) {
        prompt.anhadirPosicion(posicion);
        Tablero tablero = prompt.getTablero();
        Posicion posJugadorActual = this.getAvatar().getPosicion();
        Casilla casillaActual = tablero.getCasilla(posicion);
        Inmueble inmuebleActual = casillaActual.getCalle();
        inmuebleActual.aumentarVecesFrecuentado();

        if (this.getAvatar().getPosicion().getX() == Posiciones.PARKING) {
            int dineroParking = prompt.getTablero().devolverBote(this);
            prompt.setModificacionPasta(dineroParking, "Bote del parking");
            this.getEstadisticas().sumarInversionesBote(dineroParking);
            return;
        }
        /*Si caes en una casilla de suerte*/
        if(inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.suerte)){
            int eleccion= Mensajes.elegirCarta();
            if (eleccion < 0) {
                return;
            }
            Suerte carta=tablero.cartaSuerte(eleccion);
            carta.ejecutarCarta(prompt);
        }

        /*Si caes en una casilla de caja de comunidad*/
        if(inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.caja_comunidad)){
            int eleccion= Mensajes.elegirCarta();
            if (eleccion < 0) {
                return;
            }
            CajaComunidad carta=tablero.cartaComunidad(eleccion);
            carta.ejecutarCarta(prompt);
        }

        /* Si es un impuesto fasil, pagas y ya */
        if (inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.impuesto)) {
            int dineroExtraido = inmuebleActual.getPrecio_inicial();
            this.quitarDinero(dineroExtraido);
            tablero.meterEnBote(dineroExtraido);
            this.getEstadisticas().sumarTasas(dineroExtraido);
            prompt.setModificacionPasta(-dineroExtraido, "Impuesto en " + inmuebleActual.getNombre() + " para el bote del parking.");
            return; // Nada mas que hacer un return y via
        }

        /* El caso de ve a la carcel */
        if (posJugadorActual.esIrCarcel()) {//Si sucede esto y luego el jugador lanza dados cobra 200 por pasar de la casilla de salida
            posJugadorActual.irCarcel();
            casillaActual.getAvatares().remove(this.getAvatar());
            tablero.getCasilla(new Posicion(Posiciones.CARCEL)).insertarAvatar(this.getAvatar());
            this.setEstarEnCarcel(true);
            this.getEstadisticas().sumarVecesCarcel(1);
            System.out.println(prompt.getTablero().toString());
            return; // Return porque no hay nada que hacer
        }
        /* Si la calle pertenece a la banca en esta funcion no hay que hacer nada, ergo con un return en ese caso
         *  arreglamos */

        if (inmuebleActual.getPropietario().equals(tablero.getBanca())) {
            // Se puede meter un mensaje de esto esta sin comprar puede comprarla
            return;
        }

        /* Si el jugador actual posee la propiedad no hay que cobrarle alquiler, se puede salir igual que antes */

        if (this.getPropiedades().contains(inmuebleActual) || this.getHipotecas().contains(inmuebleActual)) {
            return;
        }

        /* Despues de todos los casos */
        if(inmuebleActual.getHipotecado()){
            Mensajes.info("La propiedad está hipotecada, por lo que no tienes que pagar alquiler.");
            return;
        }
        int alquiler = inmuebleActual.calcularAlquiler(this);
        if (alquiler > this.getDinero()) {
            Mensajes.info("No tienes dinero para pagar el alquiler. Debes declararte en bancarrota o hipotecar tus propiedades");
        }
        this.getEstadisticas().sumarPagoAlquileres(alquiler);
        inmuebleActual.sumarPagoAlquileres(alquiler);
        casillaActual.getCalle().getPropietario().getEstadisticas().sumarCobroAlquileres(alquiler); //actualizamos estadisticas en el dueño de la propiedad
        inmuebleActual.pago(this);
        prompt.setModificacionPasta(-alquiler, "Alquiler por caer en: " + inmuebleActual.getNombre());
    }


    public void cocheHandler(Prompt prompt) {
        int turnos_especiales = 4;
        if (prompt.getTiradasEspeciales() >= turnos_especiales -1 ) {
            this.dados.setContador(1);
            Mensajes.error("Lanzaste demasaiadas veces este turno", "No puedes lanzar los dados");
            return;
        } else {
            this.dados.setContador(0);
        }
        if (this.dados.tirada() > 4) {
            this.moverJugador(prompt, this.dados.tirada());
            pagoSalida(prompt);
        } else {
            this.moverJugador(prompt, -this.dados.tirada());
            this.cooldown = 3; // Turnos antes de volver a tirar
            this.dados.setContador(1);
        }
        if (prompt.getTiradasEspeciales() >= turnos_especiales - 1) {
            this.dados.setContador(1);
        }
    }


    public void pelotaHandler(Prompt prompt) {
        prompt.getPosicionesTurno().clear();
        prompt.setCompro(false);
        if (this.dados.tirada() > 4) {
            this.moverJugador(prompt, 5);
            if (this.avatar.getPosicion().getX() == Posiciones.VE_A_LA_CARCEL) {
                return;
            }
            for (int i = 7; i <= this.dados.tirada(); i += 2) {
                if (this.avatar.getPosicion().getX() == Posiciones.VE_A_LA_CARCEL) {
                    return;
                }
                this.moverJugador(prompt, 2);
                pagoSalida(prompt);
            }
            if (this.dados.tirada() % 2 == 0){
                this.moverJugador(prompt, 1);
                pagoSalida(prompt);
            }

        } else {
            this.moverJugador(prompt, -1);
            for (int i = 3; i < this.dados.tirada() ; i += 2) {
                if (this.avatar.getPosicion().getX() == Posiciones.VE_A_LA_CARCEL) {
                    return;
                }
                this.moverJugador(prompt, -2);
            }
            if (this.dados.tirada() % 2 == 0){
                this.moverJugador(prompt, -1);
            }
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
        return !prompt.isCompro();
    }


    private void checkCarcel(Tablero tablero) {
        if(this.estarEnCarcel && dados.sonDobles()) {
            Mensajes.info("Sacaste dobles, sales de la carcel");
            this.estarEnCarcel = false;
        }

        if(this.estarEnCarcel && this.turnosEnCarcel!=3){
            Mensajes.info("Estás en la cárcel, no puedes moverte.\n" +
                    "Paga "+Precios.SALIR_CARCEL+" para salir de la carcel");
            this.turnosEnCarcel++;
            return;
        }

        if(this.dados.getDobles()==3) {
            Mensajes.info("No puede seguir tirando, 3 dobles seguidos, vas a la carcel");
            this.estadisticas.sumarVecesCarcel(1);
            this.estarEnCarcel = true;
            avatar.getPosicion().irCarcel();
            tablero.getCasilla(this.avatar.getPosicion()).insertarAvatar(this.avatar);
            return;
        }
    }

    private void pagoSalida(Prompt prompt) {
        Jugador jActual = prompt.getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();
        if (posJugadorActual.pasoPorSalida() && !jActual.getEstarEnCarcel()) {
            jActual.getEstadisticas().sumarDineroSalida(Precios.SALIDA);
            jActual.anhadirDinero(Precios.SALIDA);
            jActual.aumentarVueltas();
            prompt.setModificacionPasta(Precios.SALIDA, "El jugador paso por la salida");
            jActual.getAvatar().sumarVuelta();
        }
    }


    /**
     * Mueve al jugador dependiendo de muchos factores
     * @param prompt tablero donde se mueve al jugador
     */
    public void moverJugador(Prompt prompt){
        Tablero tablero = prompt.getTablero();
        if (this.cooldown > 0) {
            this.cooldown--;
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
        dados.lanzar();
        aumentarVecesDados();
        checkCarcel(tablero);
        if (this.estarEnCarcel) {
            return;
        }
        if (this.avatar.getNitroso()) {
            switch (this.avatar.getTipo()) {
                case coche:
                    cocheHandler(prompt);
                    break;
                case pelota:
                    pelotaHandler(prompt);
                    break;
            }
            return;
        }
        prompt.getPosicionesTurno().clear();
        prompt.setCompro(false);
        this.moverJugador(prompt, dados.tirada());
        pagoSalida(prompt);
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
