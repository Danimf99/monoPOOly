package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;

public class StatsCasillas implements Observador {
    private Subject subject;
    private Casilla casilla;

    public StatsCasillas(Subject subject, Casilla casilla) {
        this.setSubject(subject);
        this.casilla = casilla;
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

    protected Subject getSubject() {
        return this.subject;
    }
}
