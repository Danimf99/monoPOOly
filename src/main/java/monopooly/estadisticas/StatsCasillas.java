package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Caer;

public class StatsCasillas implements Observador {
    private Subject subject;
    private Casilla casilla;

    private int frecuenciaVisita;

    public StatsCasillas(Subject subject, Casilla casilla) {
        this.setSubject(subject);
        this.casilla = casilla;
        this.frecuenciaVisita=0;
    }

    public int getFrecuenciaVisita() {
        return frecuenciaVisita;
    }

    public Casilla getCasilla() {
        return casilla;
    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }
        if(suceso instanceof Caer && Tablero.getTablero().getCasilla(((Caer) suceso).getPosicion()).equals(this.casilla)){
            frecuenciaVisita++;
        }
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registrar(this);
    }

    protected Subject getSubject() {
        return this.subject;
    }
}
