package monopooly.entradaSalida;

import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Casilla;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
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
        if (prompt.getJugador().getEstarEnCarcel()) {
            Mensajes.error("Estás en la cárcel!!! No puedes tirar los dados");
            return;
        }

        prompt.getJugador().moverJugador(prompt.getTablero());
        //Repintado tablero
        System.out.println(prompt.getTablero().toString());
        // Hay aliasing y para que sea mas facil se pueden renombrar cachos para no tener que enlazar todo
        // al enlazar tantas funciones te puede dar stack overflow
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();
        Casilla casillaActual = tablero.getCasilla(jActual.getAvatar().getPosicion());
        Inmueble inmuebleActual = casillaActual.getCalle();

        /* Se debe cobrar siempre al caer si la propiedad es de otra persona distinta de la banca */

        /* Si el jugador acaba con dinero negativo da igual, esto deberia comprobarlo la funcion que
        * le permite pasar turno */


        /* Primero hay que mirar si paso por la casilla de salida */

        if (posJugadorActual.pasoPorSalida()) {
            // Podemos poner un mensaje por si hay un pago y se sobreescribe el mensaje del prompt
            Mensajes.info("Se pagan " + Precios.SALIDA + Precios.MONEDA + "por pasar de la salida.");
            jActual.anhadirDinero(Precios.SALIDA);
            prompt.setModificacionPasta(Precios.SALIDA, "El jugador paso por la salida");
        }

        /* Si es un impuesto fasil, pagas y ya */
        if (inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.impuesto)) {
            int dineroExtraido = inmuebleActual.getPrecio_inicial();
            jActual.quitarDinero(dineroExtraido);
            prompt.setModificacionPasta(- dineroExtraido, "Impuesto en " + inmuebleActual.getNombre());
            return; // Nada mas que hacer un return y via
        }

        /* Si la calle pertenece a la banca en esta funcion no hay que hacer nada, ergo con un return en ese caso
        *  arreglamos */

        if (inmuebleActual.getPropietario().equals(tablero.getBanca())) {
            // Se puede meter un mensaje de esto esta sin comprar puede comprarla
            return;
        }

        /* Si el jugador actual posee la propiedad no hay que cobrarle alquiler, se puede salir igual que antes */

        if (jActual.getPropiedades().contains(inmuebleActual)) {
            return;
        }

        /* El caso de ve a la carcel */
        if (posJugadorActual.esIrCarcel()) {
            posJugadorActual.irCarcel();
            casillaActual.getAvatares().remove(jActual.getAvatar());
            tablero.getCasilla(new Posicion(Posiciones.CARCEL)).insertarAvatar(jActual.getAvatar());
            return; // Return porque no hay nada que hacer
        }

        /* Despues de todos los casos */
        int alquiler = inmuebleActual.calcularAlquiler(jActual);
        inmuebleActual.pago(jActual);
        prompt.setModificacionPasta(-alquiler, "Alquiler por caer en: " + inmuebleActual.getNombre());

        /* El caso de que sacara dobles y pueda volver a tirar lo debe manejar probablemente la clase dados
        * Mañana lo comentamos*/

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
                break;
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
