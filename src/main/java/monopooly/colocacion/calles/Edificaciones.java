package monopooly.colocacion.calles;

public class Edificaciones {

    private TipoEdificio tipo;
    private int precio;
    private Casilla casilla;

    public Edificaciones(TipoEdificio tipo,int precio,Casilla casilla){
        this.tipo=tipo;
        this.precio=precio;
        this.casilla=casilla;
    }

    public TipoEdificio getTipo(){
        return tipo;
    }

    public int getPrecio(){
        return precio;
    }
}
