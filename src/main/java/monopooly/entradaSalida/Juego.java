package monopooly.entradaSalida;


import monopooly.Partida;
import monopooly.colocacion.Casilla;
import monopooly.colocacion.Posicion;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.*;
import monopooly.colocacion.tipoCasillas.propiedades.tiposPropiedad.Solar;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.excepciones.ExcepcionParametrosInvalidos;
import monopooly.excepciones.ExcepcionRecursosInsuficientes;
import monopooly.player.Avatar;
import monopooly.player.ControladorAlquileres;
import monopooly.player.Jugador;
import monopooly.player.Tratos.ClasesTratos.*;
import monopooly.player.Tratos.Trato;
import monopooly.player.tiposAvatar.Pelota;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.*;

import java.util.HashSet;

/**
 * Esta clase implementa los comandos de la partida. A esta clase se le
 * deberian pasar las cosas bien masticaditas.
 */
public class Juego implements Comando, Subject {
    public static final Consola consola = new ConsolaNormal();
    private Suceso ultimoSuceso;
    private HashSet<Observador> observadores;
    private boolean cambio;


    public Juego() {
        ultimoSuceso = null;
        observadores = new HashSet<>();
        cambio = false;
    }

    @Override
    public void verTratos(Jugador jugador) throws ExcepcionMonopooly{
        if(jugador.getTratos().size()==0){
            throw new ExcepcionAccionInvalida("No se te ha propuesto ningún trato");
        }
        for(Trato trato:jugador.getTratos()){
            Juego.consola.info(trato.toString());
        }
    }

    @Override
    public void hacerTrato4(Jugador originador, Jugador receptor,Propiedad propiedadO, int cantidadDineroO, Propiedad propiedadR) throws ExcepcionMonopooly {

        if(propiedadO.getPropietario().equals(originador) && propiedadR.getPropietario().equals(receptor)) {
            Trato4 trato=new Trato4(originador,receptor,propiedadO,propiedadR,cantidadDineroO);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        else if(!propiedadO.getPropietario().equals(originador) ) {
            throw new ExcepcionAccionInvalida("No eres el propietario de " + propiedadO.getNombre());
        }
        throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadR.getNombre());
    }

    @Override
    public void hacerTrato5(Jugador originador, Jugador receptor, Propiedad propiedadO, int cantidadDineroReceptor, Propiedad propiedadR) throws ExcepcionMonopooly {
        if(propiedadO.getPropietario().equals(originador)
                && propiedadR.getPropietario().equals(receptor)) {
            Trato5 trato=new Trato5(originador,receptor,propiedadO,propiedadR,cantidadDineroReceptor);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        else if(!propiedadO.getPropietario().equals(originador) ) {
            throw new ExcepcionAccionInvalida("No eres el propietario de " + propiedadO.getNombre());
        }
        throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadR.getNombre());
    }

    @Override
    public void hacerTrato6(Jugador originador, Jugador receptor, Propiedad propiedadOrigina, Propiedad propiedadReceptor, Propiedad propiedadNoAlquiler, int turnosNoAlquiler) throws ExcepcionMonopooly {
        if(propiedadOrigina.getPropietario().equals(originador) && propiedadNoAlquiler.getPropietario().equals(receptor)
                && propiedadReceptor.getPropietario().equals(receptor)) {
            Trato6 trato=new Trato6(originador,receptor,propiedadOrigina,propiedadReceptor,propiedadNoAlquiler,turnosNoAlquiler);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        else if(!propiedadOrigina.getPropietario().equals(originador) ) {
            throw new ExcepcionAccionInvalida("No eres el propietario de " + propiedadOrigina.getNombre());
        }
        else if(!propiedadNoAlquiler.getPropietario().equals(receptor)){
            throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadNoAlquiler.getNombre());
        }
        throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadReceptor.getNombre());
    }

    @Override
    public void vender(Casilla casilla,int numeroEdificios,Edificio.TIPO tipo) throws ExcepcionMonopooly{
        Solar solar=(Solar)casilla;
        Jugador jActual = Tablero.getPrompt().getJugador();
        int dinero=0;
        switch (tipo){
            case casa:
                if(solar.calcularNumCasas()==0){
                    throw new ExcepcionAccionInvalida("No hay casas en ese solar");
                }
                if(solar.calcularNumCasas()<numeroEdificios){
                    dinero=(int)(solar.calcularNumCasas()*solar.getPrecio()*Precios.VALOR_CASA/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<solar.calcularNumCasas();i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                else{
                    dinero=(int)(numeroEdificios*solar.getPrecio()*Precios.VALOR_CASA/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<numeroEdificios;i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                break;
            case hotel:
                if(solar.calcularNumHoteles()==0){
                    throw new ExcepcionAccionInvalida("No hay hoteles en ese solar");
                }
                if(solar.calcularNumHoteles()<numeroEdificios){
                    dinero=(int)(solar.calcularNumHoteles()*solar.getPrecio()*Precios.VALOR_HOTEL/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<solar.calcularNumHoteles();i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                else{
                    dinero=(int)(numeroEdificios*solar.getPrecio()*Precios.VALOR_HOTEL/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<numeroEdificios;i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                break;
            case deporte:
                if(solar.calcularNumDeportes()==0){
                    throw new ExcepcionAccionInvalida("No hay pistas de deporte en ese solar");
                }
                if(solar.calcularNumDeportes()<numeroEdificios){
                    dinero=(int)(solar.calcularNumDeportes()*solar.getPrecio()*Precios.VALOR_DEPORTE/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<solar.calcularNumDeportes();i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                else{
                    dinero=(int)(numeroEdificios*solar.getPrecio()*Precios.VALOR_DEPORTE/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<numeroEdificios;i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                break;
            case piscina:
                if(solar.calcularNumPiscinas()==0){
                    throw new ExcepcionAccionInvalida("No hay piscinas en ese solar");
                }
                if(solar.calcularNumPiscinas()<numeroEdificios){
                    dinero=(int)(solar.calcularNumPiscinas()*solar.getPrecio()*Precios.VALOR_PISCINA/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<solar.calcularNumPiscinas();i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                else{
                    dinero=(int)(numeroEdificios*solar.getPrecio()*Precios.VALOR_PISCINA/2);
                    jActual.anhadirDinero(dinero);
                    for(int i=0;i<numeroEdificios;i++){
                        solar.quitarEdificio(tipo);
                    }
                }
                break;
        }
        Partida.interprete.enviarSuceso(new VenderEdificios(jActual,solar,dinero,tipo));
    }

    @Override
    public void listarJugadores() {
        for (Jugador jugador : Tablero.getTablero().getJugadores()) {
            System.out.println(jugador.toString());
        }
    }

    @Override
    public void listarAvatares() {
        for (Jugador jugador : Tablero.getTablero().getJugadores()) {
            System.out.println(jugador.getAvatar().toString());
        }
    }

    @Override
    public void listarEnVenta() {
        for(Casilla i:Tablero.getTablero().getCasillas()){
            if ( (i instanceof Propiedad) && ((Propiedad) i).getPropietario().getNombre().equals("Banca")) {
                Juego.consola.imprimir(i.toString());
            }
        }
    }

    @Override
    public void listarEdificios() {
        for(Casilla i:Tablero.getTablero().getCasillas()){
            if( (i) instanceof Solar){
                if(((Solar) i).getEdificios().size()>0){
                    Juego.consola.imprimir(((Solar) i).listarEdificaciones());
                }
            }
        }
    }

    @Override
    public void listarEdificiosGrupo(TipoMonopolio tipo) {

       Juego.consola.imprimir(Tablero.getTablero().getTipoGrupo(tipo).listaEdificaciones());

    }

    @Override
    public void edificarRapido(Propiedad casilla, Edificio.TIPO tipo) throws ExcepcionMonopooly {
        Jugador jActual = Tablero.getPrompt().getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();

        if (!(casilla).getPropietario().getNombre().equals(jActual.getNombre())) {
            throw new ExcepcionAccionInvalida("La casilla no te pertenece");
        }
        int numeroVeces = Tablero.getPrompt().getJugador().getAvatar().getPosicion().contarRepeticiones(posJugadorActual);
        if (!(casilla).getMonopolio().esCompleto() && numeroVeces < 2) {
            throw new ExcepcionAccionInvalida("No posees todos los solares del monopolio!!");
        }
        if((casilla).getHipotecado()){
            throw new ExcepcionAccionInvalida("No se puede edificar en propiedades hipotecadas.");
        }

        switch (tipo){
            case casa:
                if (((Solar)casilla).calcularNumHoteles() == casilla.getMonopolio().sizeMonopolio()
                        && ((Solar)casilla).calcularNumCasas() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más casas en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_CASA)*4) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir 4 casas en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() == 4) {
                    throw new ExcepcionAccionInvalida("No se pueden construir más casas en este solar");
                }
                for(int i=0;i<4;i++) {
                    Edificio edificio = new Casa(((Solar) casilla));
                    Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificio,edificio.getPrecio()));
                    Tablero.getPrompt().getJugador().quitarDinero(edificio.getPrecio());
                }

                break;
            case hotel:
                if (((Solar)casilla).calcularNumHoteles() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más hoteles en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_HOTEL)*3) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir 3 hoteles en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() <4) {
                    throw new ExcepcionAccionInvalida("Necesitas 4 casas para construir un hotel");
                }
                for (int i=0;i<4;i++) {
                    ((Solar)casilla).quitarEdificio(Edificio.TIPO.casa);
                }
                for(int i=0;i<3;i++) {
                    Edificio edificioH = new Hotel(((Solar) casilla));
                    Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioH,edificioH.getPrecio()));
                    Tablero.getPrompt().getJugador().quitarDinero(edificioH.getPrecio());
                }

                break;
            case piscina:
                if (((Solar)casilla).calcularNumPiscinas() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más piscinas en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_PISCINA)*3) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir 3 piscinas en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() <2 && ((Solar)casilla).calcularNumHoteles()<1) {
                    throw new ExcepcionAccionInvalida("Necesitas al menos 2 casas y 1 hotel para construir una piscina");
                }
                for(int i=0;i<3;i++) {
                    Edificio edificioP = new Piscina(((Solar) casilla));
                    Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioP,edificioP.getPrecio()));
                    Tablero.getPrompt().getJugador().quitarDinero(edificioP.getPrecio());
                }

                break;
            case deporte:
                if (((Solar)casilla).calcularNumDeportes() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más pistas de deporte en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_DEPORTE)*3) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir 3 pistas de deporte en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumHoteles()<2) {
                    throw new ExcepcionAccionInvalida("Necesitas al menos 2 hoteles ");
                }
                for(int i=0;i<3;i++) {
                    Edificio edificioD = new PistaDeporte(((Solar) casilla));
                    Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioD,edificioD.getPrecio()));
                    Tablero.getPrompt().getJugador().quitarDinero(edificioD.getPrecio());
                }
                break;


        }
    }

    @Override
    public void edificar(Casilla casilla1, Edificio.TIPO tipo) throws ExcepcionMonopooly{
        Jugador jActual = Tablero.getPrompt().getJugador();
        Posicion posJugadorActual = jActual.getAvatar().getPosicion();

        if(!(casilla1 instanceof Solar)){
            throw new ExcepcionAccionInvalida("La casilla en la que estás no es un solar");
        }
        Solar casilla=(Solar)casilla1;
        if (!(casilla).getPropietario().getNombre().equals(jActual.getNombre())) {
            throw new ExcepcionAccionInvalida("La casilla no te pertenece");
        }
        int numeroVeces = Tablero.getPrompt().getJugador().getAvatar().getPosicion().contarRepeticiones(posJugadorActual);
        if (!(casilla).getMonopolio().esCompleto() && numeroVeces < 2) {
            throw new ExcepcionAccionInvalida("No posees todos los solares del monopolio!!");
        }
        if((casilla).getHipotecado()){
            throw new ExcepcionAccionInvalida("No se puede edificar en propiedades hipotecadas.");
        }

        switch (tipo){
            case casa:
                if (((Solar)casilla).calcularNumHoteles() == casilla.getMonopolio().sizeMonopolio()
                        && ((Solar)casilla).calcularNumCasas() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más casas en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_CASA)) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir una casa en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() == 4) {
                    throw new ExcepcionAccionInvalida("No se pueden construir más casas en este solar");
                }
                Edificio edificio=new Casa(((Solar)casilla));
                Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificio,edificio.getPrecio()));
                Tablero.getPrompt().getJugador().quitarDinero(edificio.getPrecio());
                break;
            case hotel:
                if (((Solar)casilla).calcularNumHoteles() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más hoteles en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_HOTEL)) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir un hotel en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() <4) {
                    throw new ExcepcionAccionInvalida("Necesitas 4 casas para construir un hotel");
                }
                for (int i=0;i<4;i++) {
                    ((Solar)casilla).quitarEdificio(Edificio.TIPO.casa);
                }
                Edificio edificioH=new Hotel(((Solar)casilla));
                Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioH,edificioH.getPrecio()));
                Tablero.getPrompt().getJugador().quitarDinero(edificioH.getPrecio());
                break;
            case piscina:
                if (((Solar)casilla).calcularNumPiscinas() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más piscinas en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_PISCINA)) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir una piscina en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumCasas() <2 && ((Solar)casilla).calcularNumHoteles()<1) {
                    throw new ExcepcionAccionInvalida("Necesitas al menos 2 casas y 1 hotel para construir una piscina");
                }
                Edificio edificioP=new Piscina(((Solar)casilla));
                Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioP,edificioP.getPrecio()));
                Tablero.getPrompt().getJugador().quitarDinero(edificioP.getPrecio());
                break;
            case deporte:
                if (((Solar)casilla).calcularNumDeportes() == casilla.getMonopolio().sizeMonopolio()) {
                    throw new ExcepcionAccionInvalida("No se pueden edificar más pistas de deporte en esta casilla, tienes el máximo(" + casilla.getMonopolio().sizeMonopolio() + ")");

                }
                if (jActual.getDinero() < (int)((casilla).getPrecio()*Precios.VALOR_DEPORTE)) {
                    throw new ExcepcionRecursosInsuficientes("No tienes suficiente dinero para construir una pista de deporte en la casilla " + casilla.getNombre());
                }
                if (((Solar)casilla).calcularNumHoteles()<2) {
                    throw new ExcepcionAccionInvalida("Necesitas al menos 2 hoteles ");
                }
                Edificio edificioD=new PistaDeporte(((Solar)casilla));
                Partida.interprete.enviarSuceso(new Comprar(Tablero.getPrompt().getJugador(),edificioD,edificioD.getPrecio()));
                Tablero.getPrompt().getJugador().quitarDinero(edificioD.getPrecio());
                break;


        }
    }

    @Override
    public void bancarrota(Jugador jugador) throws ExcepcionMonopooly {

        Tablero.getTablero().eliminarJugador(jugador);

        if(jugador.getHipotecas().size()>0){
            for(Propiedad p:jugador.getHipotecas()){
                jugador.deshipotecar(p);
            }
        }
        if(jugador.getPropiedades().size()>0){
            for(Propiedad p:jugador.getPropiedades()){
                jugador.quitarPropiedad(p);
                Tablero.BANCA.anhadirPropiedad(p);
                p.setPropietario(Tablero.BANCA);
            }
        }
    }

    @Override
    public void verTablero() {
        Juego.consola.imprimirln(Tablero.getTablero().toString());
    }

    @Override
    public void cambiarModo(Avatar avatar) throws ExcepcionAccionInvalida {
        // Vamos a caparlo de otra manera cuz es mas facil.
        // La logica es que si ya te moviste este turno no puedas combiar de modo
        if (Tablero.getPrompt().isUsoMovEspecial()) {
            throw new ExcepcionAccionInvalida("No puedes cambiar el modo si ya has tirado este turno");
        }

        avatar.setNitroso(!avatar.isNitroso());
        if(Tablero.getPrompt().getTiradasEspeciales()>0){
            avatar.getJugador().getDados().setContador(1);
        }
    }

    @Override
    public void salirCarcel(Jugador jugador) throws ExcepcionMonopooly {
        if(!jugador.isEstarEnCarcel()){
            Juego.consola.info("No estás en la cárcel");
            return;
        }
        jugador.quitarDinero(Precios.SALIR_CARCEL);
        Tablero.getPrompt().setModDinero(-Precios.SALIR_CARCEL,"Salida de la cárcel");
        jugador.setEstarEnCarcel(false);
        Juego.consola.info("Ya saliste de la cárcel, puedes volver a tirar");
    }


    @Override
    public void describirJugador(Jugador jugador) {
        Juego.consola.imprimir(jugador.toString());
    }

    @Override
    public void describirCasilla(Casilla casilla) {
        Juego.consola.imprimir(casilla.toString());
    }

    @Override
    public void describirAvatar(Avatar avatar) {
        Juego.consola.imprimir(avatar.toString());
    }

    @Override
    public void describirFinanzas(Avatar avatar) {
        boolean evitar = true;
        StringBuilder salida = new StringBuilder();
        for (Suceso suceso : Tablero.getPrompt().getSucesosTurno()) {
            evitar = suceso instanceof Caer ||
                    suceso instanceof CaerCarcel ||
                    (suceso instanceof AccionCarta && ((AccionCarta) suceso).getCarta().modDinero() == 0) ||
                    suceso instanceof Guardado;
            if (!evitar) {
                salida.append(suceso).append("\n\n");
            }
        }
        Juego.consola.imprimirln(PintadoAscii.encuadrar(salida.toString()));
    }

    @Override
    public void aceptarTrato(Trato trato) throws ExcepcionMonopooly{

        if(trato instanceof TratoPropiedad){
            Jugador creadorTrato=trato.getOriginador();
            trato.getReceptor().quitarPropiedad(((TratoPropiedad) trato).getPropiedadReceptor());
            ((TratoPropiedad) trato).getPropiedadReceptor().setPropietario(creadorTrato);
            creadorTrato.anhadirPropiedad(((TratoPropiedad) trato).getPropiedadReceptor());
            creadorTrato.quitarPropiedad(((TratoPropiedad) trato).getPropiedadOrigina());
            ((TratoPropiedad) trato).getPropiedadOrigina().setPropietario(trato.getReceptor());
            trato.getReceptor().anhadirPropiedad(((TratoPropiedad) trato).getPropiedadOrigina());

            Juego.consola.info("Consigues la propiedad "+((TratoPropiedad) trato).getPropiedadOrigina().getNombre()
                                +" a cambio de "+((TratoPropiedad) trato).getPropiedadReceptor().getNombre());
            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }

        if(trato instanceof TratoDinero){
            Jugador creadorTrato=trato.getOriginador();
            try {
                trato.getReceptor().quitarDinero(((TratoDinero) trato).getDinero());
            }
            catch(ExcepcionRecursosInsuficientes e){
                e.imprimeError();
                return;
            }
            catch(ExcepcionParametrosInvalidos e){
                e.imprimeError();
                return;
            }
            creadorTrato.anhadirDinero(((TratoDinero) trato).getDinero());

            creadorTrato.quitarPropiedad(((TratoDinero) trato).getPropiedadO());
            ((TratoDinero) trato).getPropiedadO().setPropietario(trato.getReceptor());
            trato.getReceptor().anhadirPropiedad(((TratoDinero) trato).getPropiedadO());
            Juego.consola.info("Consigues la propiedad "+((TratoDinero) trato).getPropiedadO().getNombre()+" a cambio de "
                    +((TratoDinero) trato).getDinero()+" "+Precios.MONEDA);
            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }

        if(trato instanceof Trato3){
            Jugador creadorTrato=trato.getOriginador();
            try{
                creadorTrato.quitarDinero(((Trato3) trato).getDinero());
            }
            catch(ExcepcionRecursosInsuficientes e){
                e.imprimeError();
            }
            catch(ExcepcionParametrosInvalidos e){
                e.imprimeError();
            }

            trato.getReceptor().anhadirDinero(((Trato3) trato).getDinero());

            trato.getReceptor().quitarPropiedad(((Trato3) trato).getPropiedadR());
            ((Trato3) trato).getPropiedadR().setPropietario(creadorTrato);
            creadorTrato.anhadirPropiedad(((Trato3) trato).getPropiedadR());
            Juego.consola.info("Consigues "+((Trato3) trato).getDinero()+" "+Precios.MONEDA
                                +" a cambio de "+((Trato3) trato).getPropiedadR().getNombre());
            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }

        if(trato instanceof Trato4){

            try{
                trato.getOriginador().quitarDinero(((Trato4) trato).getDineroO());
            }
            catch(ExcepcionRecursosInsuficientes e){
                e.imprimeError();
            }
            catch(ExcepcionParametrosInvalidos e){
                e.imprimeError();
            }
            trato.getReceptor().anhadirDinero(((Trato4) trato).getDineroO());

            trato.getReceptor().quitarPropiedad(((Trato4) trato).getPropiedadReceptor());
            ((Trato4) trato).getPropiedadReceptor().setPropietario(trato.getOriginador());
            trato.getOriginador().anhadirPropiedad(((Trato4) trato).getPropiedadReceptor());

            trato.getOriginador().quitarPropiedad(((Trato4) trato).getPropiedadO());
            ((Trato4) trato).getPropiedadO().setPropietario(trato.getReceptor());
            trato.getReceptor().anhadirPropiedad(((Trato4) trato).getPropiedadO());

            Juego.consola.info("Consigues "+((Trato4) trato).getPropiedadO().getNombre()+" y "+
                    ((Trato4) trato).getDineroO()+" "+Precios.MONEDA+
                    " a cambio de "+((Trato4) trato).getPropiedadReceptor().getNombre());
            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }

        if(trato instanceof Trato5){

            try{
                trato.getReceptor().quitarDinero(((Trato5) trato).getDineroReceptor());
            }
            catch(ExcepcionRecursosInsuficientes e){
                e.imprimeError();
            }
            catch(ExcepcionParametrosInvalidos e){
                e.imprimeError();
            }
            trato.getOriginador().anhadirDinero(((Trato5) trato).getDineroReceptor());

            trato.getReceptor().quitarPropiedad(((Trato5) trato).getPropiedadReceptor());
            ((Trato5) trato).getPropiedadReceptor().setPropietario(trato.getOriginador());
            trato.getOriginador().anhadirPropiedad(((Trato5) trato).getPropiedadReceptor());

            trato.getOriginador().quitarPropiedad(((Trato5) trato).getPropiedadO());
            ((Trato5) trato).getPropiedadO().setPropietario(trato.getReceptor());
            trato.getReceptor().anhadirPropiedad(((Trato5) trato).getPropiedadO());
            Juego.consola.info("Consigues "+((Trato5) trato).getPropiedadO().getNombre()+" a cambio de "+
                    ((Trato5) trato).getPropiedadReceptor().getNombre()+" y "+((Trato5) trato).getDineroReceptor()+" "+Precios.MONEDA);

            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }

        if(trato instanceof Trato6){
            ControladorAlquileres controlador=new ControladorAlquileres(((Trato6) trato).getPropiedadNoAlquiler(),((Trato6) trato).getTurnosNoAlquiler());


            if(Tablero.getPrompt().getJugador().perteneceControladorAlquiler(controlador)){
                throw new ExcepcionAccionInvalida("Ya tienes un trato activo de no pagar alquiler en esa propiedad por lo que no puedes aceptar el trato");
            }

            Jugador creadorTrato=trato.getOriginador();
            trato.getReceptor().quitarPropiedad(((Trato6) trato).getPropiedadReceptor());
            ((Trato6) trato).getPropiedadReceptor().setPropietario(creadorTrato);
            creadorTrato.anhadirPropiedad(((Trato6) trato).getPropiedadReceptor());

            creadorTrato.quitarPropiedad(((Trato6) trato).getPropiedadOrigina());
            ((Trato6) trato).getPropiedadOrigina().setPropietario(trato.getReceptor());
            trato.getReceptor().anhadirPropiedad(((Trato6) trato).getPropiedadOrigina());


            trato.getOriginador().anhadirNoAlquiler(controlador);

            Juego.consola.info("Consigues la propiedad "+((Trato6) trato).getPropiedadOrigina().getNombre()
                    +" a cambio de "+((Trato6) trato).getPropiedadReceptor().getNombre()+" y el jugador "
                    +trato.getOriginador().getNombre()+" no paga alquiler en "+((Trato6) trato).getPropiedadNoAlquiler().getNombre());
            Tablero.getPrompt().getJugador().getTratos().remove(trato);
        }
    }

    @Override
    public void eliminarTrato(Jugador jugador,Trato trato) throws ExcepcionMonopooly{
        if(Tablero.getPrompt().getJugador().equals(trato.getOriginador())){
            jugador.getTratos().remove(trato);
        }
        throw new ExcepcionAccionInvalida("No eres el que realizó la petición del trato "+trato.getId());
    }

    @Override
    public void Hacertrato3(Jugador originador, Jugador receptor, int cantidadDinero, Propiedad propiedadR) throws ExcepcionMonopooly {
        if(propiedadR.getPropietario().equals(receptor)) {
            Trato3 trato=new Trato3(originador, receptor, cantidadDinero, propiedadR);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadR.getNombre());

    }

    @Override
    public void Hacertrato2(Jugador originador, Jugador receptor, Propiedad propiedadO, int cantidadDinero) throws ExcepcionMonopooly {
        if(propiedadO.getPropietario().equals(originador)) {
            TratoDinero trato=new TratoDinero(originador, receptor, propiedadO, cantidadDinero);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        throw new ExcepcionAccionInvalida("No eres el propietario de " + propiedadO.getNombre());
    }

    @Override
    public void Hacertrato1(Jugador originador, Jugador receptor,Propiedad propiedadO,Propiedad propiedadR) throws ExcepcionMonopooly{
        if(propiedadO.getPropietario().equals(originador) && propiedadR.getPropietario().equals(receptor)) {
            TratoPropiedad trato=new TratoPropiedad(originador, receptor, propiedadO, propiedadR);
            receptor.getTratos().add(trato);
            Juego.consola.info(trato.toString());
            return;
        }
        else if(!propiedadO.getPropietario().equals(originador) ) {
            throw new ExcepcionAccionInvalida("No eres el propietario de " + propiedadO.getNombre());
        }
        throw new ExcepcionAccionInvalida("El jugador "+receptor.getNombre()+" no es el dueño de "+propiedadR.getNombre());
    }

    @Override
    public void deshipotecar(Jugador jugador, Casilla casilla) throws ExcepcionMonopooly {
        if(!((Propiedad)casilla).getPropietario().equals(jugador)){
            Juego.consola.info("No eres el dueño de la propiedad "+casilla.getNombre());
            return;
        }
        if(!((Propiedad) casilla).getHipotecado()){
            Juego.consola.info("La propiedad "+casilla.getNombre()+" no está hipotecada");
            return;
        }
        jugador.deshipotecar(((Propiedad)casilla));
        Partida.interprete.enviarSuceso(new HipotecarPropiedad(jugador,(Propiedad)casilla,false));
    }
    @Override
    public void comprar(Jugador jugador, Casilla casilla) throws ExcepcionMonopooly {

        if (!jugador.puedeComprar(casilla)) throw new ExcepcionAccionInvalida("No puedes comprar esa propiedad.");


        if(!(((Propiedad) casilla).getPropietario().getNombre().equals("Banca")))
            throw new ExcepcionAccionInvalida("La propiedad " + casilla.getNombre() + " ya pertenece a otro jugador");

        ((Propiedad)casilla).comprar(jugador);

        if (!jugador.getAvatar().isNitroso() || !(jugador.getAvatar() instanceof Pelota)) {
            Tablero.getPrompt().setCompro(true);
        }
    }
    @Override
    public void lanzar(Jugador jugador) throws ExcepcionMonopooly {
        jugador.getAvatar().lanzarDados();
    }

    @Override
    public void estadisticasJugador(Jugador jugador) {
        Juego.consola.imprimirln(jugador.getEstadisticas().toString());
    }

    @Override
    public void estadistiscasGlobales() {
        Juego.consola.imprimirln(Tablero.getStatsGlobales().toString());
    }

    @Override
    public void hipotecar(Jugador jugador,Casilla casilla){
        if(!((Propiedad) casilla).getPropietario().equals(jugador)){
            Juego.consola.error("Esa propiedad pertenece a otro jugador");
            return;
        }
        if(((Propiedad) casilla).getHipotecado()){
            Juego.consola.info("La propiedad "+casilla.getNombre()+" ya está hipotecada.");
            return;
        }
        jugador.hipotecar(((Propiedad) casilla));
        Partida.interprete.enviarSuceso(new HipotecarPropiedad(jugador,(Propiedad)casilla,true));
    }


    @Override
    public void info(Jugador jugador) {
        Juego.consola.info("{\n" +
                "    Nombre: " + jugador.getNombre() + "\n" +
                "    Avatar: " + jugador.getAvatar().getRepresentacion() +
                "\n}");
    }

    public Casilla casillaCorrecta(String casilla) throws ExcepcionMonopooly {
        Casilla casillaComprar;

        casillaComprar=Tablero.getTablero().getCasilla(casilla);
        if(casillaComprar==null){
            throw new ExcepcionAccionInvalida("La casilla no existe");
        }
        return casillaComprar;
    }

    /**
     * Informa sobre el acontecimiento de un suceso. Esto se guardara en el
     * historial de cada jugador y se tendrá en cuenta en las estadísticas
     *
     * @param suceso Suceso acontecido
     */
    public void enviarSuceso(Suceso suceso) {
        if (suceso == null) {
            return;
        }
        this.cambio = true;
        this.ultimoSuceso = suceso;
        this.notificarObservadores();
    }

    @Override
    public void registrar(Observador observador) {
        if (observador != null) {
            observadores.add(observador);
        }
    }

    @Override
    public void eliminar(Observador observador) {
        if (observador != null) {
            observadores.remove(observador);
        }
    }

    @Override
    public void notificarObservadores() {
        if (!cambio) {
            return;
        }
        observadores.forEach(Observador::update);
        cambio = false;
    }

    @Override
    public Object getUpdate(Observador observador) {
        return this.cambio ? this.ultimoSuceso : null;
    }
}
