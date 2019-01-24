package monopooly.estadisticas;

import monopooly.configuracion.Precios;
import monopooly.entradaSalida.PintadoAscii;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.*;

public class StatsJugador implements Observador {

    // Base
    private Subject subject;
    private Jugador jugador;

    // Stats
    private int dineroInvertido;
    private int pagoDeAlquileres;
    private int cobroDeAlquileres;
    private int dineroPasoPorSalida;
    private int inversionesBote;
    private int vecesCarcel;
    private int pagoTasas;
    private int vueltas;

    public StatsJugador(Subject subject, Jugador jugador) {
        this.jugador = jugador;
        this.setSubject(subject);
        this.subject = subject;
        dineroInvertido=0;
        pagoDeAlquileres=0;
        cobroDeAlquileres=0;
        dineroPasoPorSalida=0;
        inversionesBote=0;
        pagoTasas=0;
        vecesCarcel=0;
        vueltas=0;
    }


    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }

        if (suceso instanceof Alquiler && ((Alquiler) suceso).getPropietario().equals(this.jugador))
            this.cobroDeAlquileres += ((Alquiler) suceso).getCantidad();
        if(suceso instanceof Alquiler && !((Alquiler) suceso).getPropietario().equals(this.jugador)){
            this.pagoDeAlquileres +=((Alquiler)suceso).getCantidad();
        }
        if (!suceso.getJugadorOriginador().equals(this.jugador)) return;

        if (suceso instanceof Comprar) {  // Si fue una compra
            Comprar compra = (Comprar) suceso;
            dineroInvertido += suceso.getDeshacer() ? dineroInvertido -compra.getPrecioPagado() : compra.getPrecioPagado();
            return;
        }

        if (suceso instanceof PagoImpuesto) {
            PagoImpuesto pagoImpuesto = (PagoImpuesto) suceso;
            pagoTasas += suceso.getDeshacer() ? -pagoImpuesto.getCantidad() : pagoImpuesto.getCantidad();
            return;
        }

        if (suceso instanceof ConseguirBote) {
            ConseguirBote bote = (ConseguirBote) suceso;
            inversionesBote += suceso.getDeshacer() ? -bote.getCantidadBote() : bote.getCantidadBote();
            return;
        }

        if (suceso instanceof PasoSalida) {
            dineroPasoPorSalida += suceso.getDeshacer() ? -Precios.SALIDA : Precios.SALIDA;
            vueltas += suceso.getDeshacer() ? -1 : 1;
            return;
        }

        if (suceso instanceof PagoBanca) {
            PagoBanca pagoBanca = (PagoBanca) suceso;
            this.pagoTasas += suceso.getDeshacer() ? -pagoBanca.getCantidad() : pagoBanca.getCantidad();
            return;
        }

        if (suceso instanceof CaerCarcel) {
            if (((CaerCarcel) suceso).entraEnCarcel())
                vecesCarcel += suceso.getDeshacer() ? -1 : 1;
        }
    }

    public int getDineroInvertido() {
        return dineroInvertido;
    }

    public void setDineroInvertido(int dineroInvertido) {
        this.dineroInvertido = dineroInvertido;
    }

    public int getPagoDeAlquileres() {
        return pagoDeAlquileres;
    }

    public void setPagoDeAlquileres(int pagoDeAlquileres) {
        this.pagoDeAlquileres = pagoDeAlquileres;
    }

    public int getCobroDeAlquileres() {
        return cobroDeAlquileres;
    }

    public void setCobroDeAlquileres(int cobroDeAlquileres) {
        this.cobroDeAlquileres = cobroDeAlquileres;
    }

    public int getDineroPasoPorSalida() {
        return dineroPasoPorSalida;
    }

    public void setDineroPasoPorSalida(int dineroPasoPorSalida) {
        this.dineroPasoPorSalida = dineroPasoPorSalida;
    }

    public int getInversionesBote() {
        return inversionesBote;
    }

    public void setInversionesBote(int inversionesBote) {
        this.inversionesBote = inversionesBote;
    }

    public int getVecesCarcel() {
        return vecesCarcel;
    }

    public void setVecesCarcel(int vecesCarcel) {
        this.vecesCarcel = vecesCarcel;
    }

    public int getPagoTasas() {
        return pagoTasas;
    }

    public void setPagoTasas(int pagoTasas) {
        this.pagoTasas = pagoTasas;
    }

    public int getVueltas() {
        return vueltas;
    }

    public void setVueltas(int vueltas) {
        this.vueltas = vueltas;
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
        this.subject.registrar(this);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    @Override
    public String toString(){

        String stats = ("Dinero invertido: " + dineroInvertido + Precios.MONEDA + "\n") +
                "Dinero pagado por alquileres: " + pagoDeAlquileres + Precios.MONEDA + "\n" +
                "Dinero cobrado por alquileres: " + cobroDeAlquileres + Precios.MONEDA + "\n" +
                "Dinero cobrado de haber pasado por salida: " + dineroPasoPorSalida + Precios.MONEDA + "\n" +
                "Veces que estuvo en la carcel: " + vecesCarcel + "\n" +
                "Dinero obtenido por inversiones o por el bote: " + inversionesBote + Precios.MONEDA + "\n" +
                "Dinero pagado por tasas e impuestos: " + pagoTasas + Precios.MONEDA + "\n";
        return PintadoAscii.encuadrar(stats);
    }

    public String toStringGUI(){

        String stats = ("Dinero invertido: " + dineroInvertido + Precios.MONEDA + "\n") +
                "Dinero pagado por alquileres: " + pagoDeAlquileres + Precios.MONEDA + "\n" +
                "Dinero cobrado por alquileres: " + cobroDeAlquileres + Precios.MONEDA + "\n" +
                "Dinero cobrado de haber pasado por salida: " + dineroPasoPorSalida + Precios.MONEDA + "\n" +
                "Veces que estuvo en la carcel: " + vecesCarcel + "\n" +
                "Dinero obtenido por inversiones o por el bote: " + inversionesBote + Precios.MONEDA + "\n" +
                "Dinero pagado por tasas e impuestos: " + pagoTasas + Precios.MONEDA + "\n";
        return stats;
    }
}
