package monopooly.Estadisticas;

import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.entradaSalida.PintadoASCII;

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

    public String getInmuebleMasRentable() {
        return inmuebleMasRentable;
    }

    public String getGrupoMasRentable() {
        return grupoMasRentable;
    }

    public void calcularEstadisticas(){
        calcularGrupoMasRentable();
        calcularInmuebleFrecuentado();
        calcularInmuebleMasRentable();
        calcularEnCabeza();
        calcularJugadorMasVueltas();
        calcularJugadorVecesDados();
    }
    private void calcularInmuebleMasRentable(){
        int rentable=0;

        for(Inmueble i: tablero.getCalles().values()){
            if(rentable<i.getPagoDeAlquileres()){
                rentable=i.getPagoDeAlquileres();
                inmuebleMasRentable=new String(i.getNombre());
            }
        }
    }

    private void calcularGrupoMasRentable(){
        int rentable=0;
        int auxiliar=0;

        for(Inmueble i:tablero.getCalles().values()){
            auxiliar=i.getGrupoColor().alquileresTotales();
            if(rentable<auxiliar){
                rentable=auxiliar;
                grupoMasRentable=new String(i.getGrupoColor().getTipo().toString());
            }
        }
    }

    private void calcularInmuebleFrecuentado(){
        int freq=0;

        for(Inmueble i: tablero.getCalles().values()){
            if(freq<i.getVecesFrecuentado()){
                freq=i.getVecesFrecuentado();
                inmuebleMasFrecuentado=new String(i.getNombre());
            }
        }
    }

    private void calcularJugadorMasVueltas(){

    }

    private void calcularJugadorVecesDados(){

    }

    private void calcularEnCabeza(){

    }

    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Inmueble más rentable: "+inmuebleMasRentable+"\n");
        stats.append("Grupo mas rentable: "+grupoMasRentable+"\n");
        stats.append("Inmueble mas frecuentado: "+inmuebleMasFrecuentado+"\n");
        stats.append("Jugador con mas vueltas: "+jugadorMasVueltas+"\n");
        stats.append("Jugador que lanzo mas veces: "+jugadorDados+"\n");
        stats.append("Jugador que va en cabeza: "+jugadorEnCabeza+"\n");
        return PintadoASCII.encuadrar(stats.toString());

    }
}
