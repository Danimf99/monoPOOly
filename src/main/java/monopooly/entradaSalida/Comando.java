package monopooly.entradaSalida;

import monopooly.colocacion.Casilla;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.colocacion.tipoCasillas.propiedades.TipoMonopolio;
import monopooly.colocacion.tipoCasillas.propiedades.edificios.Edificio;
import monopooly.excepciones.ExcepcionAccionInvalida;
import monopooly.excepciones.ExcepcionMonopooly;
import monopooly.player.Avatar;
import monopooly.player.Jugador;

public interface Comando {
    /**
     * Permite a un jugador comprar una casilla. Se hacen las comprobaciones de si puede comprarla
     * @param jugador Jugador que comprar
     * @param casilla Casilla que se quiere comprar
     */
    void comprar(Jugador jugador, Casilla casilla) throws ExcepcionMonopooly;

    void lanzar(Jugador jugador) throws ExcepcionMonopooly;

    void hipotecar(Jugador jugador,Casilla casilla);

    void vender(Casilla casilla,int numeroEdificios,Edificio.TIPO tipo)throws ExcepcionMonopooly;

    void estadisticasJugador(Jugador jugador);

    void estadistiscasGlobales();

    void Hacertrato1(Jugador originador,Jugador receptor,Propiedad propiedadO,Propiedad propiedadR);

    void verTratos(Jugador jugador) throws ExcepcionMonopooly;

    void listarJugadores();

    void listarAvatares();

    void listarEnVenta();

    void listarEdificios();

    void listarEdificiosGrupo(TipoMonopolio tipo);

    void edificar(Propiedad casilla, Edificio.TIPO tipo)throws ExcepcionMonopooly;

    void deshipotecar(Jugador jugador,Casilla casilla) throws ExcepcionMonopooly;

    void info(Jugador jugador);

    void describirJugador(Jugador jugador);

    void describirCasilla(Casilla casilla);

    void describirAvatar(Avatar avatar);

    void describirFinanzas(Avatar avatar);

    void salirCarcel(Jugador jugador) throws ExcepcionMonopooly;

    void verTablero();

    void cambiarModo(Avatar avatar) throws ExcepcionAccionInvalida;

    void bancarrota(Jugador jugador) throws ExcepcionMonopooly;
}

