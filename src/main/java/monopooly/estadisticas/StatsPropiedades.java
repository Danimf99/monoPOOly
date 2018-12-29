package monopooly.estadisticas;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Alquiler;

public class StatsPropiedades extends StatsCasillas {

    private int propiedadRentable;

    public StatsPropiedades(Subject subject, Propiedad propiedad) {
        super(subject, propiedad);
        this.propiedadRentable=0;
    }

    public int getPropiedadRentable() {
        return propiedadRentable;
    }

    @Override
    public void update() {
        super.update();
        Suceso suceso = (Suceso) this.getSubject().getUpdate(this);
        if (suceso == null) {
            return;
        }

        if(suceso instanceof Alquiler && ((Alquiler) suceso).getPropiedad().equals(super.getCasilla())){
            propiedadRentable+=((Alquiler) suceso).getCantidad();
        }
    }

    @Override
    public void setSubject(Subject subject) {
        super.setSubject(subject);
    }
}
