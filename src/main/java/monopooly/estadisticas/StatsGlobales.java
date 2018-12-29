package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;

import java.util.HashMap;

public class StatsGlobales implements Observador {

    private Subject subject;
    private HashMap<Casilla, StatsCasillas> statsCasillas = new HashMap<>();

    private String inmuebleMasRentable;
    private String jugadorMasVueltas;
    private String jugadorDados;
    private String jugadorEnCabeza;
    private String inmuebleMasFrecuentado;

    public StatsGlobales(Subject subject) {
        this.setSubject(subject);
        Tablero.getTablero().getCasillas()
                .forEach(casilla -> this.statsCasillas.put(
                        casilla,
                        casilla instanceof Propiedad ?
                                new StatsPropiedades(subject, (Propiedad) casilla) :
                                new StatsCasillas(subject, casilla)));
        inmuebleMasFrecuentado="Ninguno";
        inmuebleMasRentable="Ninguno";
        jugadorDados="Ninguno";
        jugadorEnCabeza="Ninguno";
        jugadorMasVueltas="Ninguno";
    }

    public StatsCasillas getStatsCasilla(Casilla casilla) {
        return this.statsCasillas.get(casilla);
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

    private void calcularInmuebleFrecuentado(){
        int freq=0;

        for(StatsCasillas i: this.statsCasillas.values()){
            if(freq<i.getFrecuenciaVisita()){
                freq=i.getFrecuenciaVisita();
                inmuebleMasFrecuentado=new String(i.getCasilla().getNombre());
            }
        }
    }

    private void calcularInmuebleMasRentable(){
        int rentable=0;

        for(StatsCasillas stat: this.statsCasillas.values()){
            if(stat instanceof StatsPropiedades)
            if(rentable<((StatsPropiedades) stat).getPropiedadRentable()){
                rentable=((StatsPropiedades) stat).getPropiedadRentable();
                inmuebleMasRentable=new String(stat.getCasilla().getNombre());
            }
        }
    }

    @Override
    public void update() {
        calcularEnCabeza();
        calcularJugadorMasVueltas();
        calcularJugadorVecesDados();
        calcularInmuebleFrecuentado();
        calcularInmuebleMasRentable();
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registrar(this);
    }

    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Propiedad más rentable: "+ inmuebleMasRentable+"\n");
        stats.append("Grupo mas rentable: "+"\n");
        stats.append("Casilla más frecuentada: "+ inmuebleMasFrecuentado+"\n");
        stats.append("Jugador con más vueltas: "+jugadorMasVueltas+"\n");
        stats.append("Jugador que lanzo más veces: "+jugadorDados+"\n");
        stats.append("Jugador que va en cabeza: "+jugadorEnCabeza+"\n");
        return PintadoAscii.encuadrar(stats.toString());

    }
}
