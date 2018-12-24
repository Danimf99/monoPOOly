package monopooly.estadisticas;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;

public class StatsPropiedades extends StatsCasillas {


    public StatsPropiedades(Subject subject, Propiedad propiedad) {
        super(subject, propiedad);
    }

    @Override
    public void update() {
        super.update();
        Suceso suceso = (Suceso) this.getSubject().getUpdate(this);
        if (suceso == null) {
            return;
        }
    }

    @Override
    public void setSubject(Subject subject) {
        super.setSubject(subject);
    }
}
