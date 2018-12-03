package monopooly.entradaSalida;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Dados;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Prompt {
    private Tablero tablero;
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;
    private boolean compro;
    private ArrayList<Posicion> posicionesTurno;

    public Prompt(Tablero tablero, Jugador jugador) {

        this.tablero = tablero;
        this.jugador = jugador;
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.compro = false;
        this.posicionesTurno = new ArrayList<>();
    }

    public Prompt() {
        /*  Prompt vacio para inicializar la partida  */
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    public int getModDinero() {
        return modDinero;
    }

    public String getMotivoPago() {
        return motivoPago;
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
    }

    public boolean isCompro() {
        return compro;
    }

    public void setCompro(boolean compro) {
        this.compro = compro;
    }

    public void setMotivoPago(String motivoPago) {
        this.motivoPago = motivoPago;
    }

    public void setModificacionPasta(int modDinero, String motivoPago) {
        this.modDinero = modDinero;
        this.motivoPago = motivoPago;
    }

    public void setHelp(boolean help) {
        this.help = help;
    }


    public ArrayList<Posicion> getPosicionesTurno() {
        return posicionesTurno;
    }

    public void setPosicionesTurno(ArrayList<Posicion> posicionesTurno) {
        this.posicionesTurno = posicionesTurno;
    }

    public int getTiradasEspeciales() {
        return this.posicionesTurno.size();
    }

    public void anhadirPosicion(Posicion posicion) {
        this.posicionesTurno.add(posicion);
    }

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
     * Elementos del prompt que siempre estarán presentes
     * @return Arraylist de los elementos minimos
     */
    private ArrayList<String> madatoryElems() {
        ArrayList<String> elementos = new ArrayList<>();

        // Indica el avatar actual
        elementos.add(""
                + this.jugador.getAvatar().getRepresentacion()
                + " - "
                + ReprASCII.ANSI_BLUE_BOLD
                + "Tipo"
                + ReprASCII.ANSI_RESET
                + ": "
                + this.jugador.getAvatar().getTipo().toString());

        // Nombre jugador
        elementos.add(this.jugador.getNombre());

        // Nombre casilla actual
        Posicion posJugador = this.jugador.getAvatar().getPosicion();
        Inmueble inmuebleActual = tablero.getCasilla(posJugador).getCalle();
        String nombreCasilla = ReprASCII.colorMonopolio(inmuebleActual.getGrupoColor().getTipo())
                + " "
                + inmuebleActual.getNombre()
                + " "
                + ReprASCII.ANSI_RESET;
        elementos.add(nombreCasilla);

        return elementos;
    }

    private String cambioDados() {
        Dados dadosPlayer = jugador.getDados();
        String salida = "";
        if (dadosPlayer.sonDobles()) {
            salida += ReprASCII.ANSI_BLACK_BOLD;
        }

        return salida
                + ReprASCII.PROMPT_DADOS
                + dadosPlayer.tirada()
                + " ("
                + dadosPlayer.getDado1()
                + "-"
                + dadosPlayer.getDado2()
                + ")"
                + ReprASCII.ANSI_RESET;
    }

    /**
     * Prepara el string del dinero. Con cantidad = 0, el motivo y la variacion no se muestran.
     * @return String con la representacion del dinero para el prompt
     */
    private String cambioDinero() {
        String color = "";
        String reprMotivo = "";
        String separador = ReprASCII.PROMPT_ELM_OUTTER_SEP;
        String dinero = ReprASCII.PROMPT_LOG_DINERO + jugador.getDinero() + "" + Precios.MONEDA;
        String modificador = "-";
        String salida = "";

        if (modDinero == 0) {
            separador = "";
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


    private String movmientoEspecial() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.ANSI_PURPLE_BOLD);
        sBuilder.append("Movimiento especial");
        sBuilder.append(ReprASCII.ANSI_RESET);
        sBuilder.append(": ");
        if (this.jugador.getAvatar().getNitroso()) {
            sBuilder.append(ReprASCII.ON);
        } else {
            sBuilder.append(ReprASCII.OFF);
        }
        return sBuilder.toString();
    }


    /**
     * Representacion del prompt que se usará al crear la partida
     * @return string a imprimir antes de leer lo del usuario
     */
    public static String promptComienzo() {
        return new Prompt().toString();
    }

    @Override
    public String toString() {
        if (this.jugador == null) {
            ArrayList<String> elementos = new ArrayList<>();
            elementos.add("Nueva partida");
            elementos.add(ReprASCII.PROMPT_LOGO);
            elementos.add("Introduca el numero de jugadores");
            return genPrompt(elementos);
        }
        ArrayList<String> elementos = madatoryElems();
        elementos.add(cambioDinero());
        if (jugador.getDados().getContador() > 0 || jugador.getDados().getDobles() > 0) {
            elementos.add(cambioDados());
        }

        if (jugador.getEstarEnCarcel()) {
            elementos.add(ReprASCII.PROMPT_CARCEL);
        }

        elementos.add(this.movmientoEspecial());

        elementos.add(ReprASCII.PROMPT_LOGO);

        if (help) {
            elementos.add(ReprASCII.PROMPT_LOG_HELP);
        }
        return genPrompt(elementos);
    }

}
