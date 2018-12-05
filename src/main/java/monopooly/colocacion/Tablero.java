package monopooly.colocacion;

import monopooly.Estadisticas.EstadisticasGlobales;
import monopooly.cartas.CajaComunidad;
import monopooly.cartas.Suerte;
import monopooly.colocacion.calles.*;
import monopooly.configuracion.Carta;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import monopooly.entradaSalida.PintadoASCII;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Tablero {
    private HashMap<Posicion, Casilla> casillas;
    private HashMap<String, Inmueble> calles;
    private HashMap<String, Jugador> jugadores;
    private ArrayList<Jugador> jugadoresTurno;
    private Jugador banca;
    private int bote;
    private int incrementosVueltas;
    private ArrayList<Suerte> cartasSuerte;
    private ArrayList<CajaComunidad> cartasCajaComunidad;
    private EstadisticasGlobales estadisticas;


    /* Constructores */

    public Tablero(ArrayList<Jugador> jugadores) {
        if (jugadores == null) {
            Mensajes.error("Lista de jugadores NULL");
            return;
        }
        this.incrementosVueltas = 0;
        this.casillas = new HashMap<>();
        this.calles = new HashMap<>();
        this.jugadores = new HashMap<>();
        this.jugadoresTurno = new ArrayList<>();
        this.bote = 0;
        this.estadisticas=new EstadisticasGlobales(this);
        HashSet<Inmueble> propiedadesBanca = new HashSet<>();
        this.banca = new Jugador(propiedadesBanca); // Lleva aliasing para irselas metiendo ;)

        Monopolio none = new Monopolio();
        Monopolio estacion = new Monopolio(TipoMonopolio.estacion);
        Monopolio servicio = new Monopolio(TipoMonopolio.servicio);
        Monopolio parking = new Monopolio(TipoMonopolio.parking);
        Monopolio marron = new Monopolio(TipoMonopolio.marron);
        Monopolio azul_claro = new Monopolio(TipoMonopolio.azul_claro);
        Monopolio violeta = new Monopolio(TipoMonopolio.violeta);
        Monopolio naranja = new Monopolio(TipoMonopolio.naranja);
        Monopolio rojo = new Monopolio(TipoMonopolio.rojo);
        Monopolio amarillo = new Monopolio(TipoMonopolio.amarillo);
        Monopolio verde = new Monopolio(TipoMonopolio.verde);
        Monopolio azul_marino = new Monopolio(TipoMonopolio.azul_marino);
        Monopolio suerte = new Monopolio(TipoMonopolio.suerte);
        Monopolio caja_comunidad = new Monopolio(TipoMonopolio.caja_comunidad);
        Monopolio impuestos = new Monopolio(TipoMonopolio.impuesto);

        int precio;
        Posicion posAux;
        Inmueble inmuAux;
        String nombreAux;

        // Salida
        posAux = new Posicion(Posiciones.SALIDA);
        nombreAux = Nombres.CALLES[Posiciones.SALIDA];
        inmuAux = new Inmueble(banca, nombreAux,0, none);
        propiedadesBanca.add(inmuAux);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Carcel
        posAux = new Posicion(Posiciones.CARCEL);
        nombreAux = Nombres.CALLES[Posiciones.CARCEL];
        inmuAux = new Inmueble(banca, nombreAux,0, none);
        propiedadesBanca.add(inmuAux);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Parking
        posAux = new Posicion(Posiciones.PARKING);
        nombreAux = Nombres.CALLES[Posiciones.PARKING];
        inmuAux = new Inmueble(banca, nombreAux,0, parking);
        propiedadesBanca.add(inmuAux);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Ve a la carcel
        posAux = new Posicion(Posiciones.VE_A_LA_CARCEL);
        nombreAux = Nombres.CALLES[Posiciones.VE_A_LA_CARCEL];
        inmuAux = new Inmueble(banca, nombreAux,0, none);
        propiedadesBanca.add(inmuAux);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Estaciones
        for (Posicion lugar : Posiciones.posicionesEstaciones()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.ESTACION, estacion);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Suerte
        for (Posicion lugar : Posiciones.posicionesSuerte()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, 0, suerte);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Caja de comunidad
        for (Posicion lugar : Posiciones.posicionesCajaComunidad()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, 0, caja_comunidad);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Servicios
        for (Posicion lugar : Posiciones.posicionesServicios()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.SERVICIOS, servicio);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Impuestos
        for (Posicion lugar : Posiciones.posicionesImpuestos()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.IMPUESTOS, impuestos);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Marrones
        for (Posicion lugar : Posiciones.posicionesMarron()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_MARRON, marron);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Azul claro
        for (Posicion lugar : Posiciones.posicionesAzulClaro()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_AZUL_CLARO, azul_claro);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Violeta
        for (Posicion lugar : Posiciones.posicionesVioleta()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_VIOLETA, violeta);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Naranja
        for (Posicion lugar : Posiciones.posicionesNaranja()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_NARANJA, naranja);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Rojo
        for (Posicion lugar : Posiciones.posicionesRojo()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_ROJO, rojo);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Amarillo
        for (Posicion lugar : Posiciones.posicionesAmarillo()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_AMARILLO, amarillo);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Verde
        for (Posicion lugar : Posiciones.posicionesVerde()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_VERDE, verde);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Azul Marino
        for (Posicion lugar : Posiciones.posicionesAzulMarino()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(banca, nombreAux, Precios.PRECIO_AZUL_MARINO, azul_marino);
            propiedadesBanca.add(inmuAux);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        for (Jugador player: jugadores) {
            this.jugadores.put(player.getNombre(), player);
            this.jugadoresTurno.add(player);
            // TODO añadir insertarAvatar. No se puede insertar no hay constructor aun
            this.casillas.get(new Posicion(0)).insertarAvatar(player.getAvatar());
        }

        /* Cartas */
        this.cartasCajaComunidad = new ArrayList<>();
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta1.MENSAJE,
                CajaComunidad.NumeracionCartas.carta1));
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta2.MENSAJE,
                CajaComunidad.NumeracionCartas.carta2));
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta3.MENSAJE,
                CajaComunidad.NumeracionCartas.carta3));
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta4.MENSAJE,
                CajaComunidad.NumeracionCartas.carta4));
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta5.MENSAJE,
                CajaComunidad.NumeracionCartas.carta5));
        //
        this.cartasCajaComunidad.add(new CajaComunidad(
                Carta.Comunidad.Carta8.MENSAJE,
                CajaComunidad.NumeracionCartas.carta8));


        this.cartasSuerte = new ArrayList<>();
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta1.MENSAJE,
                Suerte.NumeracionCartas.carta1));
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta2.MENSAJE,
                Suerte.NumeracionCartas.carta2));
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta3.MENSAJE,
                Suerte.NumeracionCartas.carta3));
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta4.MENSAJE,
                Suerte.NumeracionCartas.carta4));
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta8.MENSAJE,
                Suerte.NumeracionCartas.carta8));
        //
        this.cartasSuerte.add(new Suerte(
                Carta.Suerte.Carta10.MENSAJE,
                Suerte.NumeracionCartas.carta10));
    }


    /* Getters && Setters */

    public int getBote() {
        return bote;
    }

    public EstadisticasGlobales getEstadisticas() {
        return estadisticas;
    }

    public void setBote(int bote) {
        this.bote = bote;
        this.casillas.get(new Posicion(Posiciones.PARKING)).getCalle().setPrecio_inicial(bote);
    }

    /**
     * Otorga el bote a un jugador. Vacia el conjunto del bote
     * @param jugador jugador al que se le añade el dinero
     * @return cantidad que se le añadio al jugador
     */
    public int devolverBote(Jugador jugador) {
        jugador.anhadirDinero(bote);
        int cantidad = bote;
        setBote(0);
        return cantidad;
    }

    /**
     * Añade una cantidad de dinero al bote
     * @param cantidad dinero que se añade al bote
     */
    public void meterEnBote(int cantidad) {
        if (cantidad < 0) {
            Mensajes.error("No se puede añadir una cantidad negativa al bote.");
            return;
        }
        setBote(bote + cantidad);
    }


    public HashMap<String, Inmueble> getCalles() {
        return calles;
    }

    /**
     * Getter de casillas. Su objetivo es dar info a las clases de pintado ASCII
     * @return HashMap de posicion->casilla
     */
    public HashMap<Posicion, Casilla> getCasillas() {
//        TODO comprobaciones de errores en el getter de casillas
        return casillas;
    }

    public ArrayList<Jugador> getJugadoresTurno() {
        return jugadoresTurno;
    }

    public Jugador getBanca() {
        return banca;
    }

    /* Metodos sobre la instancia */

    /**
     * Dada una posicion el tablero devuelve su casilla correspondiente.
     * @param pos Posicion que se precisa
     * @return Casilla correspondiente a esa posicion
     */
    public Casilla getCasilla(Posicion pos) {
//        TODO Comprobacion de errores en getCasilla
        return casillas.get(pos);
    }

    /**
     * Que nos devuelva el hasmap de jugadores para iterar sobre el, ayuda para describir avatar dada una representacion
     * @return hashMap de todos los jugadores en partida
     */
    public HashMap<String, Jugador> getJugadores() {
        return jugadores;
    }

    /**
     * Dado el nombre de una calle se devuelve informacion correspondiente
     * @param nombre Nombre de la calle
     * @return instancia del Inmueble correspondiente
     */
    public Inmueble getCalle(String nombre) {
        String primeraMinuscula = nombre.toLowerCase();
        String primeraMayuscula = nombre.substring(0, 1).toUpperCase() + nombre.substring(1);
        Inmueble salida = calles.get(primeraMinuscula);
        if (salida == null) {
            salida = calles.get(primeraMayuscula);
        }
        return salida;
    }


    /**
     * Dado el nombre de un jugador se devuelve informacion correspondiente
     * @param nombre Nombre del jugador
     * @return instancia del Jugador correspondiente
     */
    public Jugador getJugador(String nombre) {
        return jugadores.get(nombre);
    }

    /**
     * Devuelve el jugador que actualmente tiene el turno
     * @return Jugador que tiene el turno
     */
    public Jugador getJugadorTurno() {
        return this.jugadoresTurno.get(0);
    }

    /**
     * Actualiza el array de turnos
     */
    public void pasarTurno() {
        this.jugadoresTurno.add(this.getJugadorTurno());
        this.jugadoresTurno.remove(0);
        // Se comprueba si es necesario aumentar el precio inicial de las casillas
        int minimo = this.getJugadorTurno().getAvatar().getVueltasTablero();
        int auxVueltas;
        for (Jugador player :
                jugadoresTurno) {
            auxVueltas = player.getAvatar().getVueltasTablero();
            if (auxVueltas < minimo) {
                minimo = auxVueltas;
            }
        }
        if (minimo / this.jugadoresTurno.size() > this.incrementosVueltas) {
            for (Inmueble propiedad :
                    this.calles.values()) {
                propiedad.incrementarPrecio();
            }
            this.incrementosVueltas++;
        }
    }

    public CajaComunidad cartaComunidad(int eleccion) {
        Collections.shuffle(this.cartasCajaComunidad);
        return this.cartasCajaComunidad.get(eleccion - 1);
    }

    public Suerte cartaSuerte(int eleccion) {
        Collections.shuffle(this.cartasSuerte);
        return this.cartasSuerte.get(eleccion - 1);
    }

    @Override
    public String toString() {
        return "\n" + PintadoASCII.genTablero(this) + "\n";
    }
}
