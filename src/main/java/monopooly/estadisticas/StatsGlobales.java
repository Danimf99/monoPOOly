package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;

import java.util.HashMap;

public class StatsGlobales implements Observador {
    private Subject subject;
    private HashMap<Casilla, StatsCasillas> statsCasillas = new HashMap<>();


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

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }

    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registrar(this);
    }
}
