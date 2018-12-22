package monopooly.entradaSalida;


import monopooly.colocacion.Casilla;
import monopooly.colocacion.Tablero;
import monopooly.colocacion.tipoCasillas.propiedades.Propiedad;
import monopooly.player.Jugador;
import monopooly.player.tiposAvatar.Pelota;

/**
 * Esta clase implementa los comandos de la partida. A esta clase se le
 * deberian pasar las cosas bien masticaditas.
 */
public class Juego implements Comando{
    public static final Consola consola = new ConsolaNormal();


    public Juego() {
    }

    public void comprar(Jugador jugador, Casilla casilla) {

        if (jugador.getDinero() > ((Propiedad) casilla).getPrecio()) {
            ((Propiedad)casilla).compra(jugador);
            Tablero.getPrompt().setModDinero(-((Propiedad)casilla).getPrecio());
            Tablero.getPrompt().setMotivoPago("Compra de la propiedad "+casilla.getNombre());
            jugador.getEstadisticas().sumarInvertido(((Propiedad)casilla).getPrecio());
            if (!jugador.getAvatar().isNitroso() || !(jugador.getAvatar() instanceof Pelota)) {
                Tablero.getPrompt().isCompro();
            }
        }else{
            Juego.consola.info("No tiene suficiente dinero para comprar "+casilla.getNombre());
        }
    }

    public void lanzar(Jugador jugador){

    }

    public void hipotecar(Jugador jugador,Casilla casilla){

    }

    public void vender(Jugador jugador){

    }

    public Casilla casillaCorrecta(String casilla){
        Casilla casillaComprar;

        casillaComprar=Tablero.getTablero().getCasilla(casilla);
        if(casillaComprar==null){
            return null;
        }
        return casillaComprar;
    }


}
