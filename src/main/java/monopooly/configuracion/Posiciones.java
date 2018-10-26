package monopooly.configuracion;

import monopooly.colocacion.Posicion;

import java.util.ArrayList;

public class Posiciones {
    public static int TOTAL = 40;
    public static int SALIDA = 0;
    public static int CARCEL = 10;
    public static int PARKING = 20;
    public static int VE_A_LA_CARCEL = 30;

    /* Metodos estaticos */


    /**
     * Devuelve las posiciones del tablero por orden.
     * @return ArrayList ordenado de las 40 posiciones del tablero
     */
    public static ArrayList<Posicion> posicionesPosibles(){
        ArrayList<Posicion> conjunto = new ArrayList<>();
        for (int i = 0; i < Posiciones.TOTAL; i++) {
            conjunto.add(new Posicion(i));
        }
        return conjunto;
    }

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


    public static ArrayList<Posicion> posicionesEstaciones() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            conjunto.add(new Posicion(5 + 10 * i));
        }
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesCajaComunidad() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(2));
        conjunto.add(new Posicion(17));
        conjunto.add(new Posicion(33));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesSuerte() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(7));
        conjunto.add(new Posicion(22));
        conjunto.add(new Posicion(36));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesServicios() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(12));
        conjunto.add(new Posicion(28));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesImpuestos() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(4));
        conjunto.add(new Posicion(38));
        return conjunto;
    }


    public static ArrayList<Posicion> posicionesMarron() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(1));
        conjunto.add(new Posicion(3));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesAzulClaro() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(6));
        conjunto.add(new Posicion(8));
        conjunto.add(new Posicion(9));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesVioleta() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(11));
        conjunto.add(new Posicion(13));
        conjunto.add(new Posicion(14));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesNaranja() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(16));
        conjunto.add(new Posicion(18));
        conjunto.add(new Posicion(19));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesRojo() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(21));
        conjunto.add(new Posicion(23));
        conjunto.add(new Posicion(24));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesAmarillo() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(26));
        conjunto.add(new Posicion(27));
        conjunto.add(new Posicion(29));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesVerde() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(31));
        conjunto.add(new Posicion(32));
        conjunto.add(new Posicion(34));
        return conjunto;
    }

    public static ArrayList<Posicion> posicionesAzulMarino() {
        ArrayList<Posicion> conjunto = new ArrayList<>();
        conjunto.add(new Posicion(37));
        conjunto.add(new Posicion(39));
        return conjunto;
    }


}
