package monopooly.Estadisticas;

import monopooly.colocacion.Tablero;

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

    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Inmueble más rentable: "+inmuebleMasRentable+"\n");
        stats.append("Grupo mas rentable: "+grupoMasRentable+"\n");
        stats.append("Inmueble mas frecuentado: "+inmuebleMasFrecuentado+"\n");
        stats.append("Jugador con mas vueltas: ");
        return stats.toString();

    }
}
