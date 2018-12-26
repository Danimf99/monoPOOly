package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;

import java.util.HashMap;

public class StatsGlobales implements Observador {

    private Subject subject;
    private HashMap<Casilla, StatsCasillas> statsCasillas = new HashMap<>();

    private String jugadorMasVueltas;
    private String jugadorDados;
    private String jugadorEnCabeza;

    public StatsGlobales(Subject subject) {
        this.setSubject(subject);
        Tablero.getTablero().getCasillas()
                .forEach(casilla -> this.statsCasillas.put(
                        casilla,
                        casilla instanceof Propiedad ?
                                new StatsPropiedades(subject, (Propiedad) casilla) :
                                new StatsCasillas(subject, casilla)));
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

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        calcularEnCabeza();
        calcularJugadorMasVueltas();
        calcularJugadorVecesDados();
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registrar(this);
    }

    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Inmueble más rentable: "+"\n");
        stats.append("Grupo mas rentable: "+"\n");
        stats.append("Casilla mas frecuentada: "+"\n");
        stats.append("Jugador con mas vueltas: "+jugadorMasVueltas+"\n");
        stats.append("Jugador que lanzo mas veces: "+jugadorDados+"\n");
        stats.append("Jugador que va en cabeza: "+jugadorEnCabeza+"\n");
        return PintadoAscii.encuadrar(stats.toString());

    }
}
