package monopooly.colocacion.calles;

public class Edificaciones {

    private TipoEdificio tipo;
    private int precio;
    private Inmueble inmueble;

    public Edificaciones(TipoEdificio tipo,int precio,Inmueble inmueble){
        this.tipo=tipo;
        this.precio=precio;
        this.inmueble=inmueble;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public TipoEdificio getTipo(){
        return tipo;
    }

    public int getPrecio(){
        return precio;
    }
}
