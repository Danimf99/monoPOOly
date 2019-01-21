package monopooly.colocacion;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import monopooly.Partida;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.player.Dados;
import monopooly.player.Jugador;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Prompt implements Observador {
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;
    private boolean compro;
    private boolean usoMovEspecial;
    private int lanzamientosDados;
    private ArrayList<Posicion> posicionesTurno;
    private Subject subject;
    private ArrayList<Suceso> sucesosTurno;

    private StringProperty nombreJugadorProperty = new SimpleStringProperty(this, "nombreJugador", "");
    private StringProperty dineroProperty = new SimpleStringProperty(this, "dineroJugador", "0 $");
    private StringProperty modDIneroProperty = new SimpleStringProperty(this, "ultimoPago", "0 $");
    private StringProperty tipoAvatarProperty = new SimpleStringProperty(this, "tipoAavatar", "nil");

    public String getTipoAvatarProperty() {
        return tipoAvatarProperty.get();
    }

    public StringProperty tipoAvatarPropertyProperty() {
        return tipoAvatarProperty;
    }

    public void setTipoAvatarProperty(String tipoAvatarProperty) {
        this.tipoAvatarProperty.set(tipoAvatarProperty);
    }

    public String getDineroProperty() {
        return dineroProperty.get();
    }

    public StringProperty dineroPropertyProperty() {
        return dineroProperty;
    }

    public void setDineroProperty(String dineroProperty) {
        this.dineroProperty.set(dineroProperty);
    }


    void reset() {
        this.jugador = Tablero.getTablero().getJugadorTurno();
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.compro = false;
        this.posicionesTurno = new ArrayList<>();
        this.sucesosTurno = new ArrayList<>();
        this.usoMovEspecial = false;
        this.lanzamientosDados = 0;
        this.nombreJugadorProperty.setValue(Tablero.getTablero().getJugadorTurno().getNombre());
        this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
        this.dineroProperty.set(Tablero.getTablero().getJugadorTurno().getDinero() + " " + Precios.MONEDA);
        this.tipoAvatarProperty.setValue(Tablero.getTablero().getJugadorTurno().getAvatar().getTipo().toString());
    }

    protected Prompt() {
        this.reset();
        this.subject = Partida.interprete;
        this.subject.registrar(this);
        this.subject.registrar(jugador.getAvatar());
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
        this.dineroProperty.setValue(jugador.getDinero() + " " + Precios.MONEDA);
        this.modDIneroProperty.setValue(modDinero + " " + Precios.MONEDA);
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setCompro(boolean compro) {
        this.compro = compro;
    }

    public String getMotivoPago() {
        return motivoPago;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public void setMotivoPago(String motivoPago) {
        this.motivoPago = motivoPago;
    }

    public void setModDinero(int modDinero, String motivoPago) {
        this.setModDinero(modDinero);
        this.motivoPago = motivoPago;
    }

    public void intentaCompraEspecial(Casilla casilla) throws ExcepcionAccionInvalida {
        if (!pisoCasilla(casilla)) {
            throw new ExcepcionAccionInvalida(
                    "No pasaste por la casilla '" + casilla.getNombre() + "'\n" +
                            "este turno."
            );
        }
    }

    public boolean isCompro() {
        return compro;
    }

    public ArrayList<Posicion> getPosicionesTurno() {
        return posicionesTurno;
    }

    public ArrayList<Suceso> getSucesosTurno() {
        return new ArrayList<>(sucesosTurno);
    }

    public void clearSucesos() {
        this.sucesosTurno.clear();
    }

    public void setSucesosTurno(ArrayList<Suceso> sucesosTurno) {
        this.sucesosTurno = sucesosTurno;
    }

    public boolean isHelp() {
        return help;
    }

    public void setPosicionesTurno(ArrayList<Posicion> posicionesTurno) {
        this.posicionesTurno = posicionesTurno;
    }

    public int getModDinero() {
        return modDinero;
    }

    public int getTiradasEspeciales() {
        return this.posicionesTurno.size();
    }

    public void anhadirPosicion(Posicion posicion){
        this.posicionesTurno.add(posicion);
    }

    public boolean isUsoMovEspecial() {
        return usoMovEspecial;
    }

    public void setUsoMovEspecial(boolean usoMovEspecial) {
        this.usoMovEspecial = usoMovEspecial;
    }

    public int getLanzamientosDados() {
        return lanzamientosDados;
    }

    public void setLanzamientosDados(int lanzamientosDados) {
        this.lanzamientosDados = lanzamientosDados;
    }

    public void aumentarLanzamientosDados() {
        this.lanzamientosDados++;
    }

    /**
     * Dada una lista de elementos que debe presentar genera el String de la prompt
     *
     * @param elementos Elementos que se presentan
     * @return Representacion de la prompt
     */
    private String genPrompt(ArrayList<String> elementos) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append('\n' + ReprASCII.PROMPT_TOP_OPENER);
        for (String elemento : elementos) {
            sBuilder.append(ReprASCII.PROMPT_ELM_SEPARATOR);
            sBuilder.append(ReprASCII.PROMPT_ELM_LEFT);
            sBuilder.append(elemento);
            sBuilder.append(ReprASCII.PROMPT_ELM_RIGHT);
        }
        sBuilder.append("\n");
        sBuilder.append(ReprASCII.PROMPT_BOT_OPENER);
        return sBuilder.toString();
    }

    /**
     * Prepara el string del dinero. Con cantidad = 0, el motivo y la variacion no se muestran.
     * @return String con la representacion del dinero para el prompt
     */
    private String cambioDinero() {
        String color = "";
        String reprMotivo = "";
        String separador = ReprASCII.PROMPT_ELM_OUTTER_SEP;
        String modificador = "-";
        String salida = "";

        if (modDinero == 0) {
            separador = motivoPago.length() > 1 ? " " : "";
        } else if (modDinero < 0) {
            color = ReprASCII.ANSI_RED;
            modificador = ReprASCII.PROMPT_LOG_DINERO_DOWN;

            reprMotivo += color + " " + this.modDinero + Precios.MONEDA;

        } else {
            color = ReprASCII.ANSI_GREEN;
            modificador = ReprASCII.PROMPT_LOG_DINERO_UP;

            reprMotivo += color + " +" + this.modDinero + Precios.MONEDA;
        }
        salida += color;
        salida += jugador.getDinero();
        salida += ReprASCII.ANSI_RESET;
        salida += " " + modificador;
        salida += separador;
        salida += motivoPago;
        salida += reprMotivo;
        salida += ReprASCII.ANSI_RESET;
        return salida;
    }


    /**
     * Elementos del prompt que siempre estarán presentes
     * @return Arraylist de los elementos minimos
     */
    private ArrayList<String> mandatoryElems() {
        ArrayList<String> elementos = new ArrayList<>();

        // Indica el avatar actual
        elementos.add(""
                + this.jugador.getAvatar().getRepresentacion()
                + " - "
                + ReprASCII.ANSI_BLUE_BOLD
                + "Tipo"
                + ReprASCII.ANSI_RESET
                + ": "
                + this.jugador.getAvatar().getTipo());

        // Nombre jugador
        elementos.add(this.jugador.getNombre());

        // Nombre casilla actual
        Posicion posJugador = this.jugador.getAvatar().getPosicion();
        Casilla casilla = Tablero.getTablero().getCasilla(posJugador);
        String nombreCasilla = ReprASCII.colorMonopolio(casilla.getTipo())
                + " "
                + casilla.getNombre()
                + " "
                + ReprASCII.ANSI_RESET;
        elementos.add(nombreCasilla);


        // Dados
        Dados dadosPlayer = jugador.getDados();

        if (dadosPlayer.getDado1() * dadosPlayer.getDado2() != 0){
            String salida = "";
            if (jugador.getCooldown() > 0) {
                salida += ReprASCII.ANSI_BLUE_BOLD + "Cooldown: " + ReprASCII.ANSI_RESET;
                salida += jugador.getCooldown() + "  ";
            }
            if (dadosPlayer.sonDobles()) {
                salida += ReprASCII.ANSI_BLACK_BOLD;
            }

            elementos.add(salida
                    + ReprASCII.PROMPT_DADOS
                    + dadosPlayer.tirada()
                    + " ("
                    + dadosPlayer.getDado1()
                    + "-"
                    + dadosPlayer.getDado2()
                    + ")"
                    + ReprASCII.ANSI_RESET
            );
        }



        // Informacion sobre el dinero
        elementos.add(this.cambioDinero());


        // Movimiento especial activado ?
        elementos.add(movmientoEspecial());

        // CaerCarcel
        if (jugador.isEstarEnCarcel()) {
            elementos.add(ReprASCII.PROMPT_CARCEL);
        }

        // Logo Monopooly
        elementos.add(ReprASCII.PROMPT_LOGO);

        if (help) {
            elementos.add(ReprASCII.PROMPT_LOG_HELP);
        }



        // Devolver las partes de la prompt
        return elementos;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }

    private String movmientoEspecial() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ANSI_PURPLE_BOLD);
        sBuilder.append("Movimiento especial");
        sBuilder.append(ReprASCII.ANSI_RESET);
        sBuilder.append(": ");
        if (this.jugador.getAvatar().isNitroso()) {
            sBuilder.append(ReprASCII.ON);
        } else {
            sBuilder.append(ReprASCII.OFF);
        }
        return sBuilder.toString();
    }

    /**
     * Determina si un jugador piso una casilla concreta este turno
     *
     * @param casilla Casilla que se quiere saber si piso
     * @return Boolean, true si la piso falso si no
     */
    public boolean pisoCasilla(Casilla casilla) {
        return this.sucesosTurno.stream()
                .filter(suceso -> suceso instanceof Caer)
                .anyMatch(suceso -> ((Caer) suceso).getPosicion().equals(casilla.getPosicion()));
    }


    @Override
    public String toString() {
        ArrayList<String> elementos = this.mandatoryElems();
         return this.genPrompt(elementos);
    }

    @Override
    public void update() {
        Suceso suceso = (Suceso) this.subject.getUpdate(this);
        if (suceso == null) {
            return;
        }

        if (suceso.getDeshacer()) {
            this.sucesosTurno.remove(suceso);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.tipoAvatarProperty.setValue(this.jugador.getAvatar().getTipo().toString());
            return;
        }

        this.sucesosTurno.add(suceso);

        /* Modificaciones de dinero */

        if (suceso instanceof Comprar) {  // Si fue una compra
            Object compra = ((Comprar) suceso).getObjetoComprado();
            Comprar sucesoCompra = (Comprar) suceso;
            this.modDinero = -sucesoCompra.getPrecioPagado();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);

            if (compra instanceof Propiedad) {
                this.motivoPago = "Compra de la propiedad " +
                        ((Propiedad) compra).getNombre();
            }

            if (compra instanceof Edificio) {
                this.motivoPago = "Edificacion de " +
                        ((Edificio) compra).getNombre();
            }
            return;
        }

        if (suceso instanceof AccionCarta) {
            this.modDinero = ((AccionCarta) suceso).getCarta().modDinero();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = "Accion de carta especial";
            return;
        }

        if (suceso instanceof PagoImpuesto) {
            this.modDinero = -((PagoImpuesto) suceso).getCantidad();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = "Pagaste un impuesto";
            return;
        }

        if (suceso instanceof ConseguirBote) {
            this.modDinero = ((ConseguirBote) suceso).getCantidadBote();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = "Conseguiste el bote del parking";
            return;
        }

        if (suceso instanceof PasoSalida) {
            this.modDinero = Precios.SALIDA;
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.motivoPago = "Pasaste por la Salida";
            return;
        }

        if (suceso instanceof PagoBanca) {
            this.modDinero = ((PagoBanca) suceso).getCantidad();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = ((PagoBanca) suceso).getExplicacion();
            return;
        }

        if (suceso instanceof Alquiler) {
            this.modDinero = -((Alquiler) suceso).getCantidad();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = "Alquiler en " + ((Alquiler) suceso).getPropiedad().getNombre();
        }

        if (suceso instanceof HipotecarPropiedad) {
            this.modDinero = ((HipotecarPropiedad) suceso).getDinero();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
            this.motivoPago = "Hipoteca propiedad " + ((HipotecarPropiedad) suceso).getPropiedad().getNombre();
        }

        if(suceso instanceof VenderEdificios){
            this.motivoPago="Venta de "+((VenderEdificios) suceso).getEdificioVendido().toString();
            this.modDinero=((VenderEdificios) suceso).getDineroGanado();
            this.modDIneroProperty.setValue(this.modDinero + " " + Precios.MONEDA);
            this.dineroProperty.set(jugador.getDinero() + " " + Precios.MONEDA);
        }
    }

    public String listarAccionesTurno() {
        return this.sucesosTurno.stream().map(suceso -> suceso.toString() + "\n").collect(Collectors.joining());
    }

    @Override
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public String getNombreJugadorProperty() {
        return nombreJugadorProperty.get();
    }

    public StringProperty nombreJugadorPropertyProperty() {
        return nombreJugadorProperty;
    }

    public void setNombreJugadorProperty(String nombreJugadorProperty) {
        this.nombreJugadorProperty.set(nombreJugadorProperty);
    }

    public String getModDIneroProperty() {
        return modDIneroProperty.get();
    }

    public StringProperty modDIneroPropertyProperty() {
        return modDIneroProperty;
    }

    public void setModDIneroProperty(String modDIneroProperty) {
        this.modDIneroProperty.set(modDIneroProperty);
    }
}
