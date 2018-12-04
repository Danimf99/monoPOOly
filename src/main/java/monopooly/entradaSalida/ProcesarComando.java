package monopooly.entradaSalida;

import monopooly.cartas.CajaComunidad;
import monopooly.cartas.Suerte;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.calles.Casilla;
import monopooly.colocacion.calles.Inmueble;
import monopooly.colocacion.calles.TipoEdificio;
import monopooly.colocacion.calles.TipoMonopolio;
import monopooly.configuracion.Nombres;
import monopooly.configuracion.Posiciones;
import monopooly.configuracion.Precios;
import monopooly.configuracion.ReprASCII;
import monopooly.player.Jugador;
import monopooly.player.TipoAvatar;

import java.util.Collection;
import java.util.Scanner;
import java.util.Set;

import static monopooly.colocacion.calles.TipoEdificio.*;

public class ProcesarComando {
    /*
     *
     * Aqui van los metodos para cada comando. Lo suyo es pasarle a estos metodos
     * el prompt. El promt tiene el tablero y el jugador actual. A mayores pues
     * el string de los comandos
     *
     * */
    public static void bancarrota(String[] args, Prompt prompt) {
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();
        Casilla casillaActual = tablero.getCasilla(jActual.getAvatar().getPosicion());
        Inmueble inmuebleActual = casillaActual.getCalle();

        tablero.getJugadoresTurno().remove(0);
        tablero.getJugadores().remove(jActual.getNombre());
        if (inmuebleActual.getPropietario().getNombre().equals("Banca") || inmuebleActual.getPropietario().getNombre().equals(jActual.getNombre())) {
            for (Inmueble i : jActual.getPropiedades()) {
                jActual.quitarPropiedad(i);
                tablero.getBanca().anhadirPropiedad(i);
            }
        } else {
            for (Inmueble i : jActual.getPropiedades()) {
                jActual.quitarPropiedad(i);
                inmuebleActual.getPropietario().anhadirPropiedad(i);
            }
        }
    }

    public static void lanzarDados(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        if (!args[1].equals("dados")) {
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        // Mensaje de ayuda
        if (args.length > 2 && args[2].equals("-h")) {
            PintadoASCII.genAyuda(
                    "lanzar dados - Permite lanzar los dados",
                    "lanzar dados",
                    "Permite lanzar los dados al jugador actual.\n" +
                            "Los alquileres se cobrarán automaticamente."
            );
            return; // Iportante salir cuando se muestra la ayuda
        }
        // Fin Mensaje de ayuda
        if (prompt.getJugador().getDados().getContador() == 1) {
            Mensajes.info("Ya lanzaste este turno, no puedes volver a tirar");
            return;
        }

        if (prompt.getJugador().getCooldown() > 0) {
            Mensajes.info("Tienes que esperar " + prompt.getJugador().getCooldown() + " turnos\n para volver a tirar.");
            prompt.getJugador().getDados().setContador(1);
            return;
        }

        prompt.getJugador().moverJugador(prompt);
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

        inmuebleActual.aumentarVecesFrecuentado();
        /* Primero hay que mirar si paso por la casilla de salida */

        if (posJugadorActual.pasoPorSalida() && !jActual.getEstarEnCarcel()) {
            // Podemos poner un mensaje por si hay un pago y se sobreescribe el mensaje del prompt
            Mensajes.info("Se pagan " + Precios.SALIDA + Precios.MONEDA + "por pasar de la salida.");
            jActual.getEstadisticas().sumarDineroSalida(Precios.SALIDA);
            jActual.anhadirDinero(Precios.SALIDA);
            jActual.aumentarVueltas();
            prompt.setModificacionPasta(Precios.SALIDA, "El jugador paso por la salida");
            jActual.getAvatar().sumarVuelta();
        }
        if (posJugadorActual.getX() == Posiciones.PARKING) {
            int dineroParking = tablero.devolverBote(jActual);
            prompt.setModificacionPasta(dineroParking, "Bote del parking");
            jActual.getEstadisticas().sumarInversionesBote(dineroParking);
            return;
        }
        /*Si caes en una casilla de suerte*/
        if(inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.suerte)){
            int eleccion=0;
            Scanner reader=new Scanner(System.in);
            Suerte carta;

            Mensajes.imprimir("Elige la carta: ");
            eleccion= reader.nextInt();
            carta=tablero.cartaSuerte(eleccion);
            carta.ejecutarCarta(prompt);
        }

        /*Si caes en una casilla de caja de comunidad*/
        if(inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.caja_comunidad)){
            int eleccion=0;
            Scanner reader=new Scanner(System.in);
            CajaComunidad carta;

            Mensajes.imprimir("Elige la carta que quieres coger: ");
            eleccion= reader.nextInt();
            carta=tablero.cartaComunidad(eleccion);
            carta.ejecutarCarta(prompt);
        }

        /* Si es un impuesto fasil, pagas y ya */
        if (inmuebleActual.getGrupoColor().getTipo().equals(TipoMonopolio.impuesto)) {
            int dineroExtraido = inmuebleActual.getPrecio_inicial();
            jActual.quitarDinero(dineroExtraido);
            tablero.meterEnBote(dineroExtraido);
            jActual.getEstadisticas().sumarTasas(dineroExtraido);
            prompt.setModificacionPasta(-dineroExtraido, "Impuesto en " + inmuebleActual.getNombre() + " para el bote del parking.");
            return; // Nada mas que hacer un return y via
        }

        /* El caso de ve a la carcel */
        if (posJugadorActual.esIrCarcel()) {//Si sucede esto y luego el jugador lanza dados cobra 200 por pasar de la casilla de salida
            posJugadorActual.irCarcel();
            casillaActual.getAvatares().remove(jActual.getAvatar());
            tablero.getCasilla(new Posicion(Posiciones.CARCEL)).insertarAvatar(jActual.getAvatar());
            jActual.setEstarEnCarcel(true);
            jActual.getEstadisticas().sumarVecesCarcel(1);
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

        if (jActual.getPropiedades().contains(inmuebleActual) || jActual.getHipotecas().contains(inmuebleActual)) {
            return;
        }

        /* Despues de todos los casos */
        if(inmuebleActual.getHipotecado()==true){
            Mensajes.info("La propiedad está hipotecada, por lo que no tienes que pagar alquiler.");
            return;
        }
        int alquiler = inmuebleActual.calcularAlquiler(jActual);
        if (alquiler > jActual.getDinero()) {
            Mensajes.info("No tienes dinero para pagar el alquiler. Debes declararte en bancarrota o hipotecar tus propiedades");
        }
        jActual.getEstadisticas().sumarPagoAlquileres(alquiler);
        inmuebleActual.sumarPagoAlquileres(alquiler);
        casillaActual.getCalle().getPropietario().getEstadisticas().sumarCobroAlquileres(alquiler); //actualizamos estadisticas en el dueño de la propiedad
        inmuebleActual.pago(jActual);
        prompt.setModificacionPasta(-alquiler, "Alquiler por caer en: " + inmuebleActual.getNombre());

        /* El caso de que sacara dobles y pueda volver a tirar lo debe manejar probablemente la clase dados
         * Mañana lo comentamos*/

    }

    public static void describir(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {

        switch (args[1].toLowerCase()) {
            case "jugador":
                if (args.length != 3) {
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                if (prompt.getTablero().getJugador(args[2]) == null) {
                    Mensajes.error("No existe ese jugador");
                    return;
                }
                Mensajes.imprimir(prompt.getTablero().getJugador(args[2]).toString());//TODO Revisar errores
                break;
            case "avatar":
                if (args.length != 3) {
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                Collection<Jugador> jugadores = prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadores) {
                    if (args[2].charAt(0) == (jugador.getAvatar().getRepresentacion())) {
                        Mensajes.imprimir(jugador.getAvatar().toString());
                        return;
                    }
                }
                break;
            default:
                if (args.length != 2 && args.length != 3) {
                    Mensajes.error("Error en el comando");
                    prompt.setHelp(true);
                    return;
                }
                if (args.length == 3) {
                    args[1] = args[1].concat(" " + args[2]);
                }
                Inmueble estar = prompt.getTablero().getCalle(args[1]);
                if (estar == null) {
                    Mensajes.error("No existe esa casilla");
                    return;
                }
                Mensajes.imprimir(prompt.getTablero().getCalle(args[1]).toString());
                break;
        }
    }

    public static void listar(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {

        switch (args[1].toLowerCase()) {
            case "jugadores":
                Collection<Jugador> jugadores = prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadores) {
                    System.out.println(jugador.toString());
                }
                break;
            case "avatares":
                Collection<Jugador> jugadors = prompt.getTablero().getJugadores().values();
                for (Jugador jugador : jugadors) {
                    System.out.println(jugador.getAvatar().toString());
                }
                break;
            case "enventa":
                //Mostrar tablero por pantalla
                System.out.println(prompt.getTablero().toString());
                Set<Inmueble> enventa = prompt.getTablero().getBanca().getPropiedades();
                for (Inmueble i : enventa) {
                    switch (i.getGrupoColor().getTipo()) {
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
            case "edificios":
                if(args.length==2){
                    Tablero tablero=prompt.getTablero();
                    Collection<Inmueble> i_values=tablero.getCalles().values();
                    for(Inmueble i:i_values){
                        if(i.getEdificios().size()!=0){
                            Mensajes.imprimir(i.listarEdificaciones());
                        }
                    }
                }
                else{
                    if(args.length>3){
                        args[2]=args[2].concat(" "+args[3]);
                    }
                    int casas=0;
                    int hoteles=0;
                    int piscinas=0;
                    int deportes=0;
                    int size=0;
                    switch (args[2]){
                        case "marron":
                        case "marrón":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Murcia").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Murcia").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Murcia").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Murcia").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Murcia").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Murcia").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                           break;
                        case  "azul claro":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Plasencia").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Plasencia").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Plasencia").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Plasencia").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Plasencia").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Plasencia").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case "violeta":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Toledo").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Toledo").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Toledo").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Toledo").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Toledo").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Toledo").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case "naranja":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Valencia").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Valencia").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Valencia").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Valencia").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Valencia").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Valencia").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case"rojo":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Sevilla").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Sevilla").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Sevilla").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Sevilla").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Sevilla").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Sevilla").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case "amarillo":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Lugo").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Lugo").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Lugo").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Lugo").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Lugo").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Lugo").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case "verde":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Madrid").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Madrid").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Madrid").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Madrid").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Madrid").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Madrid").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        case "azul marino":
                            Mensajes.imprimir(prompt.getTablero().getCalle("Ibiza").getGrupoColor().listaEdificaciones());
                            size=prompt.getTablero().getCalle("Ibiza").getGrupoColor().sizeMonopolio();
                            casas=prompt.getTablero().getCalle("Ibiza").getGrupoColor().calcularEdificios(casa);
                            hoteles=prompt.getTablero().getCalle("Ibiza").getGrupoColor().calcularEdificios(hotel);
                            piscinas=prompt.getTablero().getCalle("Ibiza").getGrupoColor().calcularEdificios(piscina);
                            deportes=prompt.getTablero().getCalle("Ibiza").getGrupoColor().calcularEdificios(deporte);
                            Mensajes.imprimir(PintadoASCII.encuadrar("Se pueden construir: "+(size-casas)+" casas, "+(size-hoteles)+" hoteles, "+(size-piscinas)+
                                    " piscinas, "+(size-deportes)+" deportes."));
                            break;
                        default:
                            Mensajes.info("En ese monopolio no puede haber edificios");
                            return;
                    }
                }
                break;
            default:
                Mensajes.error("Comando incorrecto");
                prompt.setHelp(true);
                break;
        }
    }


    public static void comprar(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        if (args.length != 2 && args.length != 3) {
            Mensajes.error("Comando incorrecto");
            return;
        }
        if (args.length == 3) {
            args[1] = args[1].concat(" " + args[2]);
        }
        switch (prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle().getGrupoColor().getTipo()) {
            case parking:
            case caja_comunidad:
            case suerte:
            case impuesto:
            case none:
                Mensajes.error("No se puede comprar la casilla " + args[1]);
                return;

        }
        if (prompt.getTablero().getCalle(args[1]) == null) {
            Mensajes.error("No existe esa casilla");
            return;
        }
        if (prompt.getTablero().getCasilla(prompt.getJugador().getAvatar().getPosicion()).getCalle() != prompt.getTablero().getCalle(args[1])) {
            Mensajes.info("No estás en esta casilla");
            return;
        }
        if (!prompt.getTablero().getCalle(args[1]).getPropietario().getNombre().equals("Banca")) {
            Mensajes.info("La casilla ya está comprada por un jugador");
            return;
        }
        if (prompt.getJugador().getDinero() > prompt.getTablero().getCalle(args[1]).getPrecio()) {
            prompt.getTablero().getCalle(args[1]).compra(prompt.getJugador());
            prompt.setModificacionPasta(-prompt.getTablero().getCalle(args[1]).getPrecio(), "Compra del inmueble " + prompt.getTablero().getCalle(args[1]).getNombre());
            prompt.getJugador().getEstadisticas().sumarInvertido(prompt.getTablero().getCalle(args[1]).getPrecio());

        } else {
            Mensajes.info("No tiene suficiente dinero para comprar " + prompt.getTablero().getCalle(args[1]).getNombre());
        }
    }

    //Solo para ver que jugador tiene turno
    public static void infoJugador(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        if (args.length != 1) {
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        Mensajes.info("{\n" +
                "    Nombre: " + prompt.getJugador().getNombre() + "\n" +
                "    Avatar: " + prompt.getJugador().getAvatar().getRepresentacion() +
                "\n}");
    }

    public static void estadisticas(String[] args,Prompt prompt){
        if(args.length==2 && prompt.getTablero().getJugadores().containsKey(args[1]) ){
            for(Jugador j: prompt.getTablero().getJugadores().values())
                if(j.getNombre().equals(args[1])){
                    Mensajes.imprimir(j.getEstadisticas().toString());
                }
            return;
        }
        if(args.length==1){
            Mensajes.imprimir(prompt.getTablero().getEstadisticas().toString());
            return;
        }
        else{
            Mensajes.error("Comando incorrecto");
            return;
        }
    }

    public static void salirCarcel(String[] args/* Argumentos a mayores que se necesiten */, Prompt prompt) {
        if (args.length != 2) {
            Mensajes.error("Error en el comando");
            prompt.setHelp(true);
            return;
        }
        if (args[1].equals("Carcel") || args[1].equals("carcel")) {
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        if (prompt.getJugador().getEstarEnCarcel()) {
            prompt.getJugador().quitarDinero(Precios.SALIR_CARCEL);
            prompt.setModificacionPasta(-Precios.SALIR_CARCEL, "Salir de la carcel");
            prompt.getJugador().setEstarEnCarcel(false);
            Mensajes.info("Ya puede volver a tirar, no está en la carcel");
        } else {
            Mensajes.info("No estás en la carcel");
        }
    }

    public static void edificar(String[] args, Prompt prompt) {
        if (args.length != 2) {
            Mensajes.error("Error en el comando");
            prompt.setHelp(true);
            return;
        }
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();
        Casilla casillaActual = tablero.getCasilla(jActual.getAvatar().getPosicion());
        Inmueble inmuebleActual = casillaActual.getCalle();

        switch (casillaActual.getCalle().getGrupoColor().getTipo()) {
            case caja_comunidad:
            case parking:
            case suerte:
            case impuesto:
            case none:
            case estacion:
            case servicio:
                Mensajes.info("No se puede edificar en esta casilla");
                return;
            default:
                break;
        }
        if (!inmuebleActual.getPropietario().getNombre().equals(jActual.getNombre())) {
            Mensajes.info("No puedes edificar en esta casilla, no eres el dueño!");
            return;
        }
        int numeroVeces = posJugadorActual.contarRepeticiones(posJugadorActual);
        if (!inmuebleActual.getGrupoColor().esCompleto() && numeroVeces < 2) {
            Mensajes.info("No posees todos los solares del monopolio!!");
            return;
        }
        if(inmuebleActual.getHipotecado()==true){
            Mensajes.info("No se puede edificar en propiedades hipotecadas.");
            return;
        }
        switch (args[1].toLowerCase()) {
            case "casa":
                if (inmuebleActual.calcularNumHoteles() == inmuebleActual.getGrupoColor().sizeMonopolio()
                        && inmuebleActual.calcularNumCasas() == inmuebleActual.getGrupoColor().sizeMonopolio()) {
                    Mensajes.info("No se pueden edificar más casa en esta casilla, tienes el máximo(" + inmuebleActual.getGrupoColor().sizeMonopolio() + ")");
                    return;
                }
                if (jActual.getDinero() < inmuebleActual.precioEdificio(casa)) {
                    Mensajes.info("No tienes suficiente dinero para construir una casa en la casilla " + inmuebleActual.getNombre());
                    return;
                }
                if (inmuebleActual.calcularNumCasas() == 4) {
                    Mensajes.info("No se pueden construir más casas en este solar");
                    return;
                }
                inmuebleActual.anhadirEdificio(casa);
                prompt.setModificacionPasta(-inmuebleActual.precioEdificio(casa), "Compra de una casa");
                jActual.getEstadisticas().sumarInvertido(inmuebleActual.precioEdificio(casa));
                jActual.quitarDinero(inmuebleActual.precioEdificio(casa));
                break;
            case "hotel":
                if (inmuebleActual.calcularNumHoteles() == inmuebleActual.getGrupoColor().sizeMonopolio()) {
                    Mensajes.info("No se pueden edificar más piscinas en esta casilla, tienes el máximo(" + inmuebleActual.getGrupoColor().sizeMonopolio() + ")");
                    return;
                }
                if (jActual.getDinero() < inmuebleActual.precioEdificio(TipoEdificio.hotel)) {
                    Mensajes.info("No tienes suficiente dinero para construir un hotel en la casilla " + inmuebleActual.getNombre());
                    return;
                }
                if (inmuebleActual.calcularNumCasas() < 4) {
                    Mensajes.info("Necesitas 4 casas para construir un hotel!!");
                    return;
                }
                for (int i=0;i<4;i++) {
                    inmuebleActual.quitarEdificio(casa);
                }
                inmuebleActual.anhadirEdificio(TipoEdificio.hotel);
                prompt.setModificacionPasta(-inmuebleActual.precioEdificio(hotel), "Compra de un hotel");
                jActual.getEstadisticas().sumarInvertido(inmuebleActual.precioEdificio(hotel));
                jActual.quitarDinero(inmuebleActual.precioEdificio(hotel));
                break;
            case "piscina":
                if (inmuebleActual.calcularNumPiscinas() == inmuebleActual.getGrupoColor().sizeMonopolio()) {
                    Mensajes.info("No se pueden edificar más piscinas en esta casilla, tienes el máximo(" + inmuebleActual.getGrupoColor().sizeMonopolio() + ")");
                    return;
                }
                if (jActual.getDinero() < inmuebleActual.precioEdificio(TipoEdificio.piscina)) {
                    Mensajes.info("No tienes suficiente dinero para construir una piscina en esta casilla " + inmuebleActual.getNombre());
                    return;
                }
                if (inmuebleActual.calcularNumCasas() < 2 || inmuebleActual.calcularNumHoteles() < 1) {
                    Mensajes.info("No hay construidas las suficientes casas/edificios para edificar una piscna");
                    return;
                }
                prompt.setModificacionPasta(-inmuebleActual.precioEdificio(TipoEdificio.piscina), "Compra de una piscina");
                inmuebleActual.anhadirEdificio(TipoEdificio.piscina);
                jActual.getEstadisticas().sumarInvertido(inmuebleActual.precioEdificio(piscina));
                jActual.quitarDinero(inmuebleActual.precioEdificio(piscina));
                break;
            case "deporte":
                if (inmuebleActual.calcularNumDeportes() == inmuebleActual.getGrupoColor().sizeMonopolio()) {
                    Mensajes.info("No se pueden edificar más pistas de deportes en esta casilla, tienes el máximo(" + inmuebleActual.getGrupoColor().sizeMonopolio() + ")");
                    return;
                }
                if (jActual.getDinero() < inmuebleActual.precioEdificio(TipoEdificio.deporte)) {
                    Mensajes.info("No tienes suficiente dinero para construir una pista de deportes en la casilla " + inmuebleActual.getNombre());
                    return;
                }
                if (inmuebleActual.calcularNumHoteles() < 2) {
                    Mensajes.info("Tienes menos de dos hoteles en esta casilla, no puedes construir una pista de deporte");
                    return;
                }
                prompt.setModificacionPasta(-inmuebleActual.precioEdificio(TipoEdificio.deporte), "Compra de una pista de deporte");
                inmuebleActual.anhadirEdificio(TipoEdificio.deporte);
                jActual.getEstadisticas().sumarInvertido(inmuebleActual.precioEdificio(deporte));
                jActual.quitarDinero(inmuebleActual.precioEdificio(deporte));
                break;
            default:
                Mensajes.error("Ese edificio existe. No se puede construir");
                return;
        }
    }

    public static void venderEdificios(String[] args, Prompt prompt) {

        if (args.length < 4) {
            Mensajes.error("Comando incorrecto");
            prompt.setHelp(true);
            return;
        }
        int num=0;
        if (args.length > 4) {
            args[2]=args[2].concat(" "+ args[3]);
            num=Integer.parseInt(args[4]);
        }
        else{
            num=Integer.parseInt(args[3]);
        }
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Inmueble inmuebleVender = tablero.getCalle(args[2]);
        if(!inmuebleVender.getPropietario().equals(jActual)){
            Mensajes.info("No puedes vender "+args[1]+" en "+args[2]+" esta propiedad no te pertenece!");
            return;
        }
        if(inmuebleVender.getEdificios().size()==0){
            Mensajes.info("La propiedad "+inmuebleVender.getNombre()+" no tiene edificios construidos");
            return;
        }
        switch(args[1]){
            case "casas":
                if(inmuebleVender.calcularNumCasas()==0){
                    Mensajes.info("No hay casas construidas en la propiedad "+args[2]);
                    return;
                }
                if(inmuebleVender.calcularNumCasas()<num){
                    jActual.anhadirDinero((int)(inmuebleVender.calcularNumCasas()*inmuebleVender.getEdificio(casa).getPrecio()/2));
                    prompt.setModificacionPasta((int)(inmuebleVender.calcularNumCasas()*inmuebleVender.getEdificio(casa).getPrecio()/2),"Venta de "+inmuebleVender.calcularNumCasas()+" "+args[1]);
                    for(int i=0;i<inmuebleVender.calcularNumCasas();i++){
                        inmuebleVender.quitarEdificio(casa);
                    }
                }
                else{
                    jActual.anhadirDinero((int)(num*inmuebleVender.getEdificio(casa).getPrecio()/2));
                    prompt.setModificacionPasta((int)(num*inmuebleVender.getEdificio(casa).getPrecio()/2),"Venta de "+num+args[1]);
                    for(int i=0;i<num;i++){
                        inmuebleVender.quitarEdificio(casa);
                    }
                }
                break;
            case "hoteles":
                if(inmuebleVender.calcularNumHoteles()==0){
                    Mensajes.info("No hay hoteles construidos en la propiedad "+args[2]);
                    return;
                }
                if(inmuebleVender.calcularNumHoteles()<num){
                    jActual.anhadirDinero((int)(inmuebleVender.calcularNumHoteles()*inmuebleVender.getEdificio(hotel).getPrecio()/2));
                    prompt.setModificacionPasta((int)(inmuebleVender.calcularNumHoteles()*inmuebleVender.getEdificio(hotel).getPrecio()/2),"Venta de "+inmuebleVender.calcularNumHoteles()+" "+args[1]);
                    for(int i=0;i<inmuebleVender.calcularNumHoteles();i++){
                        inmuebleVender.quitarEdificio(hotel);
                    }
                }
                else{
                    jActual.anhadirDinero((int)(num*inmuebleVender.getEdificio(hotel).getPrecio()/2));
                    prompt.setModificacionPasta((int)(num*inmuebleVender.getEdificio(hotel).getPrecio()/2),"Venta de "+num+" "+args[1]);
                    for(int i=0;i<num;i++){
                        inmuebleVender.quitarEdificio(hotel);
                    }
                }
                break;
            case "piscinas":
                if(inmuebleVender.calcularNumPiscinas()==0){
                    Mensajes.info("No hay piscinas construidas en la propiedad "+args[2]);
                    return;
                }
                if(inmuebleVender.calcularNumPiscinas()<num){
                    jActual.anhadirDinero((int)(inmuebleVender.calcularNumPiscinas()*inmuebleVender.getEdificio(piscina).getPrecio()/2));
                    prompt.setModificacionPasta((int)(inmuebleVender.calcularNumPiscinas()*inmuebleVender.getEdificio(piscina).getPrecio()/2),"Venta de "+inmuebleVender.calcularNumCasas()+" "+args[1]);
                    for(int i=0;i<inmuebleVender.calcularNumPiscinas();i++){
                        inmuebleVender.quitarEdificio(piscina);
                    }
                }
                else{
                    jActual.anhadirDinero((int)(num*inmuebleVender.getEdificio(piscina).getPrecio()/2));
                    prompt.setModificacionPasta((int)(num*inmuebleVender.getEdificio(piscina).getPrecio()/2),"Venta de "+num+" "+args[1]);
                    for(int i=0;i<num;i++){
                        inmuebleVender.quitarEdificio(piscina);
                    }
                }
                break;
            case "deportes":
                if(inmuebleVender.calcularNumDeportes()==0){
                    Mensajes.info("No hay pistas de deporte construidas en la propiedad "+args[2]);
                    return;
                }
                if(inmuebleVender.calcularNumDeportes()<num){
                    jActual.anhadirDinero((int)(inmuebleVender.calcularNumDeportes()*inmuebleVender.getEdificio(deporte).getPrecio()/2));
                    prompt.setModificacionPasta((int)(inmuebleVender.calcularNumDeportes()*inmuebleVender.getEdificio(deporte).getPrecio()/2),"Venta de "+inmuebleVender.calcularNumCasas()+" "+args[1]);
                    for(int i=0;i<inmuebleVender.calcularNumDeportes();i++){
                        inmuebleVender.quitarEdificio(deporte);
                    }
                }
                else{
                    jActual.anhadirDinero((int)(num*inmuebleVender.getEdificio(deporte).getPrecio()/2));
                    prompt.setModificacionPasta((int)(num*inmuebleVender.getEdificio(deporte).getPrecio()/2),"Venta de "+num+" "+args[1]);
                    for(int i=0;i<num;i++){
                        inmuebleVender.quitarEdificio(deporte);
                    }
                }
                break;
            default:
                Mensajes.info("No existe ese tipo de edificio");
                return;
        }

    }

    public static void hipotecar(String[] args,Prompt prompt){
        if(args.length!=3 && args.length!=2){
            Mensajes.info("Error en el comando.");
            prompt.setHelp(true);
            return;
        }
        //SI la propiedad tiene 2 cadenas ej:A Coruña, los concantena en args[1]
        if(args.length==3){
            args[1]=args[1].concat(" "+args[2]);
        }
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Inmueble inmuebleHipotecar = tablero.getCalle(args[1]);

        if(!inmuebleHipotecar.getPropietario().equals(jActual)){
            Mensajes.info("No eres el dueño de la propiedad "+args[1]+".");
            return;
        }
        if(inmuebleHipotecar.getHipotecado()==true){
            Mensajes.info("La proiedad "+args[1]+" ya está hipotecada.");
            return;
        }
        if(inmuebleHipotecar.getEdificios().size()>0){
            Mensajes.info("Tienes que vender todos los edificios de "+args[1]+" antes de hipotecar.");
            return;
        }
        int dinero=inmuebleHipotecar.getPrecio_inicial()/2;
        prompt.setModificacionPasta(dinero,"Hipoteca de "+args[1]);
        jActual.hipotecar(inmuebleHipotecar);


    }

    public static void cambiarModo(String[] args, Prompt prompt){
        if(!args[1].equals("modo")){
            Mensajes.info("Error en el comando");
            prompt.setHelp(true);
            return;
        }
        prompt.getJugador().getAvatar().setNitroso(
                !prompt.getJugador().getAvatar().getNitroso()
        );
        if (prompt.getTiradasEspeciales() > 0) {
            prompt.getJugador().getDados().setContador(1);
        }
    }

    public static void deshipotecar(String[] args,Prompt prompt){
        if(args.length!=2 && args.length!=3){
            Mensajes.info("Error en el comando");
            prompt.setHelp(true);
            return;
        }
        //SI la propiedad tiene 2 cadenas ej:A Coruña, los concantena en args[1]
        if(args.length==3){
            args[1]=args[1].concat(" "+args[2]);
        }
        Tablero tablero = prompt.getTablero();
        Jugador jActual = prompt.getJugador();
        Inmueble inmuebleDesHipotecar = tablero.getCalle(args[1]);
        if(!inmuebleDesHipotecar.getPropietario().equals(jActual)){
            Mensajes.info("No eres el dueño de la propiedad "+args[1]+".");
            return;
        }
        if(inmuebleDesHipotecar.getHipotecado()==false){
            Mensajes.info("La proiedad "+args[1]+" no está hipotecada.");
            return;
        }
        int dinero=(int)(1.1*(inmuebleDesHipotecar.getPrecio_inicial()/2));
        jActual.getHipotecas().remove(inmuebleDesHipotecar);
        jActual.anhadirPropiedad(inmuebleDesHipotecar);
        jActual.quitarDinero(dinero);
        prompt.setModificacionPasta(-dinero,"Deshipotecación de la propiedad "+args[1]);
        inmuebleDesHipotecar.setHipotecado(false);
    }

    public static boolean acabarTurno(String[] args/* Argumentos que se necesiten */) {
        // Probablemente no haga falta pero bueno, puede ser un booleano
        // que diga si el usuario puede pasar turno
        return true;
    }

    public static void verTablero(String[] args, Prompt prompt) {
        if (!args[1].toLowerCase().equals("tablero")) {
            Mensajes.error("Error en el comando introducido");
            prompt.setHelp(true);
            return;
        }
        System.out.print(prompt.getTablero().toString());
    }

    /**
     * Pide al jugador por pantalla un nombre y un tipo de avatar y lo crea con esos datos
     *
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

    public static void ayuda() {
        StringBuilder sBuilder = new StringBuilder();
        sBuilder.append("Comandos disponibles:").append("\n");
        for (String comando :
                Nombres.LISTA_COMANDOS) {
            sBuilder.append("   ").append(comando).append("\n");
        }
        Mensajes.detalles(sBuilder.toString());

/*        Mensajes.info("" +
                "Añade -h como segundo argumento a cualquier\n" +
                "comando para mostrar informacion de uso.\n");*/

    }

}
