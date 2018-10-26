package monopooly.colocacion;

import monopooly.colocacion.calles.*;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.entradaSalida.Mensajes;
import monopooly.player.Jugador;

import java.util.ArrayList;
import java.util.HashMap;

public class Tablero {
    private HashMap<Posicion, Casilla> casillas;
    private HashMap<String, Inmueble> calles;
    private HashMap<String, Jugador> jugadores;


    /* Constructores */

    public Tablero(ArrayList<Jugador> jugadores) {
        if (jugadores == null) {
            Mensajes.error("Lista de jugadores NULL");
            return;
        }
        this.casillas = new HashMap<>();
        this.calles = new HashMap<>();
        this.jugadores = new HashMap<>();

        Monopolio none = new Monopolio(TipoMonopolio.none);
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
        inmuAux = new Inmueble(nombreAux,0, none);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Carcel
        posAux = new Posicion(Posiciones.CARCEL);
        nombreAux = Nombres.CALLES[Posiciones.CARCEL];
        inmuAux = new Inmueble(nombreAux,0, none);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Parking
        posAux = new Posicion(Posiciones.PARKING);
        nombreAux = Nombres.CALLES[Posiciones.PARKING];
        inmuAux = new Inmueble(nombreAux,0, parking);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Ve a la carcel
        posAux = new Posicion(Posiciones.VE_A_LA_CARCEL);
        nombreAux = Nombres.CALLES[Posiciones.VE_A_LA_CARCEL];
        inmuAux = new Inmueble(nombreAux,0, none);
        calles.put(nombreAux, inmuAux);
        casillas.put(posAux, new Casilla(inmuAux));

        // Estaciones
        for (Posicion lugar : Posiciones.posicionesEstaciones()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.ESTACION, estacion);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Suerte
        for (Posicion lugar : Posiciones.posicionesSuerte()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, 0, suerte);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Caja de comunidad
        for (Posicion lugar : Posiciones.posicionesCajaComunidad()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, 0, caja_comunidad);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Servicios
        for (Posicion lugar : Posiciones.posicionesServicios()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.SERVICIOS, servicio);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Impuestos
        for (Posicion lugar : Posiciones.posicionesImpuestos()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.IMPUESTOS, impuestos);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Marrones
        for (Posicion lugar : Posiciones.posicionesMarron()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, marron);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Azul claro
        for (Posicion lugar : Posiciones.posicionesAzulClaro()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, azul_claro);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Violeta
        for (Posicion lugar : Posiciones.posicionesVioleta()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, violeta);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Naranja
        for (Posicion lugar : Posiciones.posicionesNaranja()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, naranja);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Rojo
        for (Posicion lugar : Posiciones.posicionesRojo()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, rojo);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Amarillo
        for (Posicion lugar : Posiciones.posicionesAmarillo()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, amarillo);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Verde
        for (Posicion lugar : Posiciones.posicionesVerde()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, verde);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        // Azul Marino
        for (Posicion lugar : Posiciones.posicionesAzulMarino()) {
            nombreAux = Nombres.CALLES[lugar.getX()];
            inmuAux = new Inmueble(nombreAux, Precios.FOO, azul_marino);
            calles.put(nombreAux, inmuAux);
            casillas.put(lugar, new Casilla(inmuAux));
        }

        for (Jugador player: jugadores) {
            this.jugadores.put(player.getNombre(), player);
            // TODO añadir insertarAvatar. No se puede insertar no hay constructor aun
//            this.casillas.get(new Posicion(0)).insertarAvatar(player.getAvatar());
        }

//        TODO Implementar constructor del tablero
    }


    /* Getters && Setters */

    /**
     * Getter de casillas. Su objetivo es dar info a las clases de pintado ASCII
     * @return HashMap de posicion->casilla
     */
    public HashMap<Posicion, Casilla> getCasillas() {
//        TODO comprobaciones de errores en el getter de casillas
        return casillas;
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
     * Dado el nombre de una calle se devuelve informacion correspondiente
     * @param nombre Nombre de la calle
     * @return instancia del Inmueble correspondiente
     */
    public Inmueble getCalle(String nombre) {
//        TODO Comprobacion de errores en getCalle
        return calles.get(nombre);
    }


    /**
     * Dado el nombre de un jugador se devuelve informacion correspondiente
     * @param nombre Nombre del jugador
     * @return instancia del Jugador correspondiente
     */
    public Jugador getJugador(String nombre) {
//        TODO Comprobacion de errores en getJugador
        return jugadores.get(nombre);
    }

    @Override
    public String toString() {
        return "Tablero{" +
                "casillas=" + casillas +
                ", calles=" + calles +
                ", jugadores=" + jugadores +
                '}';
    }
}
