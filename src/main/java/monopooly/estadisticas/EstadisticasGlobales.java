package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;

public class EstadisticasGlobales {
    private String inmuebleMasRentable;
    private String grupoMasRentable;
    private String inmuebleMasFrecuentado;
    private String jugadorMasVueltas;
    private String jugadorDados;
    private String jugadorEnCabeza;
    private Tablero tablero;


    public EstadisticasGlobales(Tablero tablero){
        this.tablero=tablero;
        inmuebleMasFrecuentado="Ninguno";
        inmuebleMasRentable="Ninguno";
        grupoMasRentable="Ninguno";
        jugadorDados="Ninguno";
        jugadorEnCabeza="Ninguno";
        jugadorMasVueltas="Ninguno";
    }
    //TODO acabar estadisticas cuando estean edificios y casillas completamente implementados
    public String getInmuebleMasRentable() {
        return inmuebleMasRentable;
    }

    public String getGrupoMasRentable() {
        return grupoMasRentable;
    }

    public void calcularEstadisticas(){
       // calcularGrupoMasRentable();
        calcularInmuebleFrecuentado();
       // calcularInmuebleMasRentable();
        calcularEnCabeza();
        calcularJugadorMasVueltas();
        calcularJugadorVecesDados();
    }
    //private void calcularInmuebleMasRentable(){
    //    int rentable=0;
//
    //    for(Casilla i: Tablero.getTablero().ge){
    //        if(rentable<i.getPagoDeAlquileres()){
    //            rentable=i.getPagoDeAlquileres();
    //            inmuebleMasRentable=new String(i.getNombre());
    //        }
    //    }
    //}
//
    //private void calcularGrupoMasRentable(){
    //    int rentable=0;
    //    int auxiliar=0;
//
    //    for(Inmueble i:tablero.getCalles().values()){
    //        auxiliar=i.getGrupoColor().alquileresTotales();
    //        if(rentable<auxiliar){
    //            rentable=auxiliar;
    //            grupoMasRentable=new String(i.getGrupoColor().getTipo().toString());
    //        }
    //    }
    //}
//
    private void calcularInmuebleFrecuentado(){
        int freq=0;

        for(Casilla i: tablero.getCasillas()){
            if(freq<i.frecuenciaVisita()){
                freq=i.frecuenciaVisita();
                inmuebleMasFrecuentado=new String(i.getNombre());
            }
        }
    }

    private void calcularJugadorMasVueltas(){
        int max=0;
        for(Jugador j: Tablero.getTablero().getJugadores()){
            if(max<j.getVueltas()){
                max=j.getVueltas();
                jugadorMasVueltas=new String(j.getNombre());
            }
        }
    }

    private void calcularJugadorVecesDados(){
        int max=0;
        for(Jugador j: Tablero.getTablero().getJugadores()){
            if(max<j.getVecesDados()){
                max=j.getVecesDados();
                jugadorDados=new String(j.getNombre());
            }
        }
    }

    private void calcularEnCabeza(){
        int maxDinero=0;

        for(Jugador j:Tablero.getTablero().getJugadores()){
            if(maxDinero<j.calcularFortunaTotal()){
                maxDinero=j.calcularFortunaTotal();
                jugadorEnCabeza=new String(j.getNombre());
            }
        }
    }

    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Inmueble más rentable: "+inmuebleMasRentable+"\n");
        stats.append("Grupo mas rentable: "+grupoMasRentable+"\n");
        stats.append("Casilla mas frecuentada: "+inmuebleMasFrecuentado+"\n");
        stats.append("Jugador con mas vueltas: "+jugadorMasVueltas+"\n");
        stats.append("Jugador que lanzo mas veces: "+jugadorDados+"\n");
        stats.append("Jugador que va en cabeza: "+jugadorEnCabeza+"\n");
        return PintadoAscii.encuadrar(stats.toString());

    }
}
