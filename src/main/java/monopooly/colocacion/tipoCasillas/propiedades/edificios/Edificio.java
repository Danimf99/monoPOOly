package monopooly.colocacion.tipoCasillas.propiedades.edificios;


import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;

public abstract class Edificio {
    private String nombre;
    private Solar solar;

    public Edificio(int id, Solar solar) {
        this.nombre = this.getClass().getName() + '-' + id;
        this.solar = solar;
        solar.edificar(this);
    }

    public String getNombre() {
        return nombre;
    }

    public Solar getSolar() {
        return solar;
    }

    public void setSolar(Solar solar) {
        this.solar = solar;
    }
}
