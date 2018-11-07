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
    private Dados dadosInicio;
    private Tablero tablero;
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;

    public Prompt(Tablero tablero, Jugador jugador) {

        this.tablero = tablero;
        this.jugador = jugador;
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.dadosInicio = new Dados(jugador.getDados().getDado1(), jugador.getDados().getDado2()); // TODO mejorar esto
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

    private String genPrompt(ArrayList<String> elementos) {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append(ReprASCII.PROMPT_TOP_OPENER);
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
        elementos.add("" + this.jugador.getAvatar().getRepresentacion());
        elementos.add(this.jugador.getNombre());
        // Nombre casilla actual
        Posicion posJugador = this.jugador.getAvatar().getPosicion();
        Inmueble inmuebleActual = tablero.getCasilla(posJugador).getCalle();
        String nombreCasilla = ReprASCII.colorMonopolio(inmuebleActual.getGrupoColor().getTipo()) +
                " " + inmuebleActual.getNombre() + " " +
                ReprASCII.ANSI_RESET;
        elementos.add(nombreCasilla);
        return elementos;
    }

    private String cambioDados() {
        Dados dadosPlayer = jugador.getDados();

        return ReprASCII.PROMPT_DADOS +
                dadosPlayer.tirada() +
                " (" +
                dadosPlayer.getDado1() +
                "-" +
                dadosPlayer.getDado2() +
                ")";
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

    /**
     * Representacion del prompt que se usará al crear la partida
     * @return string a imprimir antes de leer lo del usuario
     */
    public static String promptComienzo() {
        return ReprASCII.PROMPT_NUEVA_PARTIDA;
    }

    @Override
    public String toString() {
        ArrayList<String> elementos = madatoryElems();
        elementos.add(cambioDinero());
        if (jugador.getDados().getContador() > 0) {
            elementos.add(cambioDados());
        }

        if (jugador.getEstarEnCarcel()) {
            elementos.add(ReprASCII.PROMPT_CARCEL);
        }

        elementos.add(ReprASCII.PROMPT_LOGO);

        if (help) {
            elementos.add(ReprASCII.PROMPT_LOG_HELP);
        }
        return genPrompt(elementos);
    }

}
