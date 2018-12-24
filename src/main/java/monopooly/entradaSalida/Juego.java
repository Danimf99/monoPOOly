package monopooly.entradaSalida;


import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.configuracion.Precios;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;
import monopooly.player.tiposAvatar.Pelota;
import monopooly.sucesos.Observador;
import monopooly.sucesos.Subject;
import monopooly.sucesos.Suceso;
import monopooly.sucesos.tipoSucesos.Comprar;

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
        Tablero.getTablero().pasarTurno();
    }

    @Override
    public void verTablero() {
        Juego.consola.imprimirln(Tablero.getTablero().toString());
    }

    @Override
    public void cambiarModo(Avatar avatar) {
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
        Tablero.getPrompt().setMotivoPago("Deshipotecación de la propiedad "+casilla.getNombre());
        Tablero.getPrompt().setModDinero(-(int)(((Propiedad) casilla).getMonopolio().getPrecio()*1.1));
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
        if (jugador.getDados().getContador() == 1) {
            Juego.consola.info("Ya lanzaste este turno, no puedes volver a tirar");
            return;
        }

        if (jugador.getCooldown() > 0) {
            Juego.consola.info("Tienes que esperar " + jugador.getCooldown() + " turnos\n para volver a tirar.");
            jugador.getDados().setContador(1);
            return;
        }
        jugador.getAvatar().moverBasico();
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
    }
    @Override
    public void vender(Jugador jugador){

    }

    @Override
    public void info(Jugador jugador) {
        Juego.consola.info("{\n" +
                "    Nombre: " + jugador.getNombre() + "\n" +
                "    Avatar: " + jugador.getAvatar().getRepresentacion() +
                "\n}");
    }

    public Casilla casillaCorrecta(String casilla) throws ExcepcionAccionInvalida {
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
