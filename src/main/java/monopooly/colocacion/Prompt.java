package monopooly.colocacion;

import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Dados;
import monopooly.player.Jugador;

import java.util.ArrayList;

public class Prompt {
    private Jugador jugador;
    private int modDinero;
    private String motivoPago;
    private boolean help;
    private boolean compro;
    private ArrayList<Posicion> posicionesTurno;


    void reset() {
        this.jugador = Tablero.getTablero().getJugadorTurno();
        this.modDinero = 0;
        this.motivoPago = "";
        this.help = false;
        this.compro = false;
        this.posicionesTurno = new ArrayList<>();
    }

    protected Prompt() {
        this.reset();
    }

    public void setModDinero(int modDinero) {
        this.modDinero = modDinero;
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
        this.modDinero = modDinero;
        this.motivoPago = motivoPago;
    }

    public boolean isCompro() {
        return compro;
    }

    public ArrayList<Posicion> getPosicionesTurno() {
        return posicionesTurno;
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

    public void anhadirPosicion(Posicion posicion){
        this.posicionesTurno.add(posicion);
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
                + "tipo");

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


    @Override
    public String toString() {
        ArrayList<String> elementos = this.mandatoryElems();



         return this.genPrompt(elementos);
    }
}
