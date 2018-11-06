package monopooly.entradaSalida;

import monopooly.colocacion.calles.Inmueble;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

public class ProcesarComando {
    /*
     *
     * Aqui van los metodos para cada comando. Lo suyo es pasarle a estos metodos
     * el prompt. El promt tiene el tablero y el jugador actual. A mayores pues
     * el string de los comandos
     *
     * */

    public static void lanzarDados(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        if (!args[1].equals("dados")) {
            Mensajes.error("Comando incorrecto");
            return;
        }
        if (prompt.getJugador().estaEnCarcel()) {
            Mensajes.error("Estás en la cárcel!!! No puedes tirar los dados");
            return;
        }

        prompt.getJugador().moverJugador(prompt.getTablero());
        //TODO poner de que casilla a que casilla se mueve el jugador pero bonito, por eso te lo dejo a ti Saul
        //Repintado tablero
        System.out.println(prompt.getTablero().toString());
        if (prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().getPropietario().getNombre().equals("banca")
                && prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().getPropietario().getNombre().equals(prompt.getJugador().getNombre())) {

            Mensajes.info("Cayó en la casilla de un jugador, por lo que tiene que pagar de alquiler: " + prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().calcularAlquiler(prompt.getJugador()));
            //Comprobar que tiene suficiente dinero
            if (prompt.getJugador().getDinero()
                    < prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().calcularAlquiler(prompt.getJugador())) {
                Mensajes.info("No tiene suficiente dinero para pagar la deuda");
                //TODO Preguntar para hipotecar etc....
            }
            prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().pago(prompt.getJugador());
        }
        //TODO Comprobar si la casilla es irCarcel
        if (prompt.getJugador().getDados().sonDobles()) {
            Mensajes.info("El jugador " + prompt.getJugador().getNombre() + "sacó dados dobles, por lo que puede tirar otra vez.");
        }

    }

    public static void describir(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        switch (args[1].toLowerCase()) {
            case "jugador":
                System.out.println(prompt.getTablero().getJugador(args[2]).toString());
                break;
            case "avatar":
                Collection<Jugador> jugadores = prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadores) {
                    if (args[2].equals(jugador.getAvatar().getRepresentacion())) {
                        System.out.print(jugador.getAvatar().toString());
                    }
                }
            default: //Para describir casillas
                System.out.println(prompt.getTablero().getCalle(args[2]).toString());
                break;
        }
    }

    public static void listar(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if(args.length!=2){
            Mensajes.error("Comando incorrecto");
            return;
        }
        switch(args[1].toLowerCase()){
            case "jugadores":
                Collection<Jugador> jugadores= prompt.getTablero().getJugadores().values();
                for(Jugador jugador: jugadores){
                    System.out.println(jugador.toString());
                }
                break;
            case "avatares":
                Collection<Jugador> jugadors= prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadors) {
                    System.out.print(jugador.getAvatar().toString());
                }
                break;
            case "enventa":
                //Mostrar tablero por pantalla
                System.out.println(prompt.getTablero().toString());
                Set<Inmueble> enventa=prompt.getTablero().getBanca().getPropiedades();
                for(Inmueble i: enventa){
                    System.out.println(i.toString());
                }
                break;
        }
    }

    public static void comprar(String[] args/* Argumentos a mayores que se necesiten */) {

    }

    //Solo para ver que jugador tiene turno
    public static void infoJugador(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if (args.length != 1) {
            Mensajes.error("Comando incorrecto");
            return;
        }
        Mensajes.info("{\n"+
                            "    Nombre: "+prompt.getJugador().getNombre()+"\n" +
                            "    Avatar: "+prompt.getJugador().getAvatar().getRepresentacion()+
                            "\n}");
    }

    public static void salirCarcel(String[] args/* Argumentos a mayores que se necesiten */) {

    }

    public static boolean acabarTurno(String[] args/* Argumentos que se necesiten */) {
        // Probablemente no haga falta pero bueno, puede ser un booleano
        // que diga si el usuario puede pasar turno
        return true;
    }

    public static void verTablero(String[] args, Prompt prompt) {
        if (args[1].toLowerCase().equals("tablero")) {
            Mensajes.error("Error en el comando introducido");
            return;
        }
        System.out.print(prompt.getTablero().toString());
    }

    /**
     * Pide al jugador por pantalla un nombre y un tipo de avatar y lo crea con esos datos
     * @return nuevo jugador
     */
    public static Jugador crearJugador() {
        Scanner scanner = new Scanner(System.in);
        System.out.print(ReprASCII.PROMPT_NOMBRE_JUGADOR);
        String nombre = scanner.nextLine();
        TipoAvatar tipoAvatar = TipoAvatar.sombrero; // Valor por defecto en caso de que algo salga mal
        boolean tipoCorrecto = false;
        while (!tipoCorrecto) {
            tipoCorrecto = true;
            System.out.println(ReprASCII.PROMT_AYUDA_TIPO_AVATAR);
            System.out.print(ReprASCII.PROMPT_TIPO_AVATAR);
            String avatar = scanner.nextLine();
            switch (avatar.toLowerCase()) {
                case "sombrero":
                    tipoAvatar = TipoAvatar.sombrero;
                    break;
                case "pelota":
                    tipoAvatar = TipoAvatar.pelota;
                    break;
                case "esfinge":
                    tipoAvatar = TipoAvatar.esfinge;
                    break;
                case "coche":
                    tipoAvatar = TipoAvatar.coche;
                    break;
                    default:
                        tipoCorrecto = false;
            }
        }
        return new Jugador(nombre, tipoAvatar);
    }

}
