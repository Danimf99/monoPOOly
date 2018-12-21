package monopooly.estadisticas;

import monopooly.configuracion.Precios;
import monopooly.entradaSalida.PintadoAscii;

public class EstadisticasJugador {

    private int dineroInvertido;
    private int pagoDeAlquileres;
    private int cobroDeAlquileres;
    private int dineroPasoPorSalida;
    private int inversionesBote;
    private int vecesCarcel;
    private int pagoTasas;

    public EstadisticasJugador(){
        dineroInvertido=0;
        pagoDeAlquileres=0;
        cobroDeAlquileres=0;
        dineroPasoPorSalida=0;
        inversionesBote=0;
        pagoTasas=0;
        vecesCarcel=0;
    }

    public int getPagoTasas() {
        return pagoTasas;
    }

    public int getDineroInvertido() {
        return dineroInvertido;
    }

    public int getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }

    public int getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }

    public int getDineroPasoPorSalida() {
        return dineroPasoPorSalida;
    }

    public int getInversionesBote() {
        return inversionesBote;
    }

    public int getVecesCarcel() {
        return vecesCarcel;
    }

    public void sumarInvertido(int cantidad){
        this.dineroInvertido+=cantidad;
    }

    public void sumarPagoAlquileres(int cantidad){
        this.pagoDeAlquileres+=cantidad;
    }

    public void sumarCobroAlquileres(int cantidad){
        this.cobroDeAlquileres+=cantidad;
    }

    public void sumarDineroSalida(int cantidad){
        this.dineroPasoPorSalida+=cantidad;
    }

    public void sumarInversionesBote(int cantidad){
        this.inversionesBote+=cantidad;
    }
    public void sumarVecesCarcel(int cantidad){
        this.vecesCarcel+=cantidad;
    }
    public void sumarTasas(int cantidad){
        this.pagoTasas+=cantidad;
    }

    @Override
    public String toString(){
        StringBuilder stats=new StringBuilder();
        stats.append("Dinero invertido: "+dineroInvertido+ Precios.MONEDA+"\n");
        stats.append("Dinero pagado por alquileres: "+pagoDeAlquileres+Precios.MONEDA+"\n");
        stats.append("Dinero cobrado por alquileres: "+cobroDeAlquileres+Precios.MONEDA+"\n");
        stats.append("Dinero cobrado de haber pasado por salida: "+dineroPasoPorSalida+Precios.MONEDA+"\n");
        stats.append("Veces que estuvo en la carcel: "+vecesCarcel+Precios.MONEDA+"\n");
        stats.append("Dinero obtenido por inversiones o por el bote: "+inversionesBote+Precios.MONEDA+"\n");
        stats.append("Dinero pagado por tasas e impuestos: "+pagoTasas+Precios.MONEDA+"\n");

        return PintadoAscii.encuadrar(stats.toString());
    }

}
