package monopooly.player;

import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;

import java.util.Objects;

public class ControladorAlquileres {
    private Propiedad propiedadNoPagasAlquiler;
    private int numeroTurnosSinPagar;

    public ControladorAlquileres(Propiedad propiedadNoPagasAlquiler,int numeroTurnosSinPagar)throws ExcepcionMonopooly {
        if(propiedadNoPagasAlquiler==null){
            throw new ExcepcionParametrosInvalidos("Propiedad en la que no vas a pagar el alquiler es null");
        }
        if(numeroTurnosSinPagar<0){
            throw new ExcepcionParametrosInvalidos("El numero de turnos que no vas a pagar tiene que ser mayor que 0");
        }
        this.numeroTurnosSinPagar=numeroTurnosSinPagar;
        this.propiedadNoPagasAlquiler=propiedadNoPagasAlquiler;
    }

    public Propiedad getPropiedadNoPagasAlquiler() {
        return propiedadNoPagasAlquiler;
    }

    public int getNumeroTurnosSinPagar() {
        return numeroTurnosSinPagar;
    }

    public void disminuirTurnos(){
        if(numeroTurnosSinPagar>0) {
            numeroTurnosSinPagar--;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ControladorAlquileres controlador = (ControladorAlquileres) o;
        return Objects.equals(propiedadNoPagasAlquiler, ((ControladorAlquileres) o).getPropiedadNoPagasAlquiler());
    }
}
