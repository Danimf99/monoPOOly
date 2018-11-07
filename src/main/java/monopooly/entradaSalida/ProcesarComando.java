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

        /* El caso de ve a la carcel */
        if (posJugadorActual.esIrCarcel()) {//Si sucede esto y luego el jugador lanza dados cobra 200 por pasar de la casilla de salida
            posJugadorActual.irCarcel();
            casillaActual.getAvatares().remove(jActual.getAvatar());
            tablero.getCasilla(new Posicion(Posiciones.CARCEL)).insertarAvatar(jActual.getAvatar());
            jActual.setEstarEnCarcel(true);
            System.out.println(prompt.getTablero().toString());
            return; // Return porque no hay nada que hacer
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
                if(args.length!=3){
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                if(prompt.getTablero().getJugador(args[2])==null){
                    Mensajes.error("No existe ese jugador");
                    return;
                }
                System.out.println(prompt.getTablero().getJugador(args[2]).toString());//TODO Revisar errores
                break;
            case "avatar":
                if(args.length!=3){
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                Collection<Jugador> jugadores = prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadores) {
                    if (args[2].charAt(0)==(jugador.getAvatar().getRepresentacion())) {
                        System.out.println(jugador.getAvatar());
                        return;
                    }
                }
                break;
            case "casilla":
                if(args.length!=3 && args.length!=4){
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                if(args.length==4){
                    args[2]=args[2].concat(" "+args[3]);
                }
                Inmueble estar=prompt.getTablero().getCalle(args[2]);
                if(estar==null){
                    Mensajes.error("No existe esa casilla");
                    return;
                }
                System.out.println(prompt.getTablero().getCalle(args[2]).toString()); // SI metes describir y luego cualquier cosa peta hay que comprobar errores
                break;
            default:
                Mensajes.error("Comando incorrecto");
                prompt.setHelp(true);
        }
    }

    public static void listar(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if(args.length!=2){
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
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
                    System.out.println(jugador.getAvatar().toString());
                }
                break;
            case "enventa":
                //Mostrar tablero por pantalla
                System.out.println(prompt.getTablero().toString());
                Set<Inmueble> enventa=prompt.getTablero().getBanca().getPropiedades();
                for(Inmueble i: enventa){
                    switch(i.getGrupoColor().getTipo()){
                        case none:
                        case impuesto:
                        case suerte:
                        case parking:
                        case caja_comunidad:
                            break;
                        default:
                            System.out.println(i.toString());
                            break;
                    }
                }
                break;
            default:
                Mensajes.error("Comando incorrecto");
                prompt.setHelp(true);
                break;
        }
    }

    public static void comprar(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if(args.length!=2 && args.length!=3){
            Mensajes.error("Comando incorrecto");
            return;
        }
        if(args.length==3){
            args[1]=args[1].concat(" "+args[2]);
        }
        switch(prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().getGrupoColor().getTipo()){
            case parking:
            case caja_comunidad:
            case suerte:
            case impuesto:
            case none:
                Mensajes.error("No se puede comprar la casilla "+args[1]);
                return;

        }
        if(prompt.getTablero().getCalle(args[1])==null){
            Mensajes.error("No existe esa casilla");
            return;
        }
        if(prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle()!=prompt.getTablero().getCalle(args[1])){
           Mensajes.info("No estás en esta casilla");
           return;
        }
        if(prompt.getTablero().getCalle(args[1]).getPropietario().getNombre()!="Banca"){
            Mensajes.info("La casilla ya está comprada por un jugador");
            return;
        }
        if(prompt.getJugador().getDinero()>prompt.getTablero().getCalle(args[1]).getPrecio()){
            Mensajes.info("El jugador "+prompt.getJugador().getNombre()+" compró la propiedad "+prompt.getTablero().getCalle(args[1]).getNombre()
                    +" por el precio de "+prompt.getTablero().getCalle(args[1]).getPrecio());
            prompt.getTablero().getCalle(args[1]).compra(prompt.getJugador());
            prompt.setModificacionPasta(-prompt.getTablero().getCalle(args[1]).getPrecio(),"Compra del inmueble");
        }
    }

    //Solo para ver que jugador tiene turno
    public static void infoJugador(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if (args.length != 1) {
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        Mensajes.info("{\n"+
                            "    Nombre: "+prompt.getJugador().getNombre()+"\n" +
                            "    Avatar: "+prompt.getJugador().getAvatar().getRepresentacion()+
                            "\n}");
    }

    public static void salirCarcel(String[] args/* Argumentos a mayores que se necesiten */,Prompt prompt) {
        if(args.length!=2){
            Mensajes.error("Error en el comando");
            prompt.setHelp(true);
            return;
        }
        if(args[1].equals("Carcel") && args[1].equals("carcel")){
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        if (prompt.getJugador().getEstarEnCarcel()) {
            prompt.getJugador().quitarDinero(Precios.SALIR_CARCEL);
            prompt.setModificacionPasta(-Precios.SALIR_CARCEL,"Salir de la carcel");
            prompt.getJugador().setEstarEnCarcel(false);
            Mensajes.info("Ya puede volver a tirar, no está en la carcel");
        }
        else{
            Mensajes.info("No estás en la carcel");
        }
    }

    public static boolean acabarTurno(String[] args/* Argumentos que se necesiten */) {
        // Probablemente no haga falta pero bueno, puede ser un booleano
        // que diga si el usuario puede pasar turno
        return true;
    }

    public static void verTablero(String[] args, Prompt prompt) {
        if (args[1].toLowerCase().equals("tablero")) {
            Mensajes.error("Error en el comando introducido");
            prompt.setHelp(true);
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
