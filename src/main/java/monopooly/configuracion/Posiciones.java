package monopooly.configuracion;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;

import java.util.ArrayList;

public class Posiciones {
    public static int TOTAL = 40;
    public static int SALIDA = 0;
    public static int CARCEL = 10;
    public static int PARKING = 20;
    public static int VE_A_LA_CARCEL = 30;

    /* Metodos estaticos */



    public static final TipoMonopolio[] ORDENACION_POR_DEFECTO = {
            TipoMonopolio.salida,
            TipoMonopolio.marron,
            TipoMonopolio.caja_comunidad,
            TipoMonopolio.marron,
            TipoMonopolio.impuesto,
            TipoMonopolio.estacion,
            TipoMonopolio.azul_claro,
            TipoMonopolio.suerte,
            TipoMonopolio.azul_claro,
            TipoMonopolio.azul_claro,
            TipoMonopolio.carcel,
            TipoMonopolio.violeta,
            TipoMonopolio.servicio,
            TipoMonopolio.violeta,
            TipoMonopolio.violeta,
            TipoMonopolio.estacion,
            TipoMonopolio.naranja,
            TipoMonopolio.caja_comunidad,
            TipoMonopolio.naranja,
            TipoMonopolio.naranja,
            TipoMonopolio.parking,
            TipoMonopolio.rojo,
            TipoMonopolio.suerte,
            TipoMonopolio.rojo,
            TipoMonopolio.rojo,
            TipoMonopolio.estacion,
            TipoMonopolio.amarillo,
            TipoMonopolio.amarillo,
            TipoMonopolio.servicio,
            TipoMonopolio.amarillo,
            TipoMonopolio.ir_carcel,
            TipoMonopolio.verde,
            TipoMonopolio.verde,
            TipoMonopolio.caja_comunidad,
            TipoMonopolio.verde,
            TipoMonopolio.estacion,
            TipoMonopolio.suerte,
            TipoMonopolio.azul_marino,
            TipoMonopolio.impuesto,
            TipoMonopolio.azul_marino
    };

    public static final TipoMonopolio[] coloresValidos = {
            TipoMonopolio.marron,
            TipoMonopolio.azul_claro,
            TipoMonopolio.violeta,
            TipoMonopolio.naranja,
            TipoMonopolio.rojo,
            TipoMonopolio.amarillo,
            TipoMonopolio.verde,
            TipoMonopolio.azul_marino,
            TipoMonopolio.servicio,
            TipoMonopolio.estacion
    };


    /**
     * Devuelve las posiciones de la parte norte del tablero por orden.
     * @return Arraylist con las posiciones
     */
    public static ArrayList<Posicion> posicionesNorteIzqDer(){
        ArrayList<Posicion> conjunto = new ArrayList<>();
        for (int i = 20; i <= Posiciones.VE_A_LA_CARCEL; i++) {
            conjunto.add(new Posicion(i));
        }
        return conjunto;
    }


    /**
     * Devuelve las posiciones de la parte norte del tablero por orden.
     * @return Arraylist con las posiciones
     */
    public static ArrayList<Posicion> posicionesSurIzqDer(){
        ArrayList<Posicion> conjunto = new ArrayList<>();
        for (int i = Posiciones.CARCEL; i >= Posiciones.SALIDA; i--) {
            conjunto.add(new Posicion(i));
        }
        return conjunto;
    }

    public static ArrayList<ArrayList<Posicion>> posicionesEsteOeste() {
        ArrayList<ArrayList<Posicion>> conjunto = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            ArrayList<Posicion> pareja = new ArrayList<>();
            pareja.add(new Posicion(Posiciones.PARKING - i));
            pareja.add(new Posicion(Posiciones.VE_A_LA_CARCEL + i));
            conjunto.add(pareja);
        }

        return conjunto;
    }

}
