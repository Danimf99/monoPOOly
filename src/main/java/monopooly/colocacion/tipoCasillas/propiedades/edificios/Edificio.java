package monopooly.colocacion.tipoCasillas.propiedades.edificios;


import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;

public abstract class Edificio {
    private String nombre;
    private Solar solar;
    private int precio;
    public enum TIPO {
        casa,
        hotel,
        deporte,
        piscina
    }
    public Edificio(int id, Solar solar,int precio) {
        this.nombre = this.getClass().getSimpleName() + '-' + id;
        this.solar = solar;
        this.precio=precio;
        solar.edificar(this);
    }

    public int getPrecio() {
        return precio;
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
