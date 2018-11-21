package monopooly.colocacion.calles;

public class Edificaciones {

    private TipoEdificio tipo;
    private int precio;

    public Edificaciones(TipoEdificio tipo,int precio){
        this.tipo=tipo;
        this.precio=precio;
    }

    public TipoEdificio getTipo(){
        return tipo;
    }

    public int getPrecio(){
        return precio;
    }
}
