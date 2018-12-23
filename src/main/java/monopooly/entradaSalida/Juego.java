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

    @Override
    public void deshipotecar(Jugador jugador, Casilla casilla) {
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
    public void comprar(Jugador jugador, Casilla casilla) {

        if (!jugador.puedeComprar(casilla)) {
            Juego.consola.error("No puedes comprar esa propiedad.");
            return;
        }
        if(!(((Propiedad) casilla).getPropietario().getNombre().equals("Banca"))){
            Juego.consola.error("La propiedad "+casilla.getNombre()+" ya pertenece a otro jugador");
            return;
        }
        if (jugador.getDinero() > ((Propiedad) casilla).getPrecio()) {
            ((Propiedad)casilla).compra(jugador);
            Tablero.getPrompt().setModDinero(-((Propiedad)casilla).getPrecio());
            Tablero.getPrompt().setMotivoPago("Compra de la propiedad "+casilla.getNombre());
            jugador.getEstadisticas().sumarInvertido(((Propiedad)casilla).getPrecio());
            if (!jugador.getAvatar().isNitroso() || !(jugador.getAvatar() instanceof Pelota)) {
                Tablero.getPrompt().setCompro(true);
            }
        }else{
            Juego.consola.info("No tiene suficiente dinero para comprar "+casilla.getNombre());
        }
    }
    @Override
    public void lanzar(Jugador jugador){

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
        Tablero.getPrompt().setModDinero(((Propiedad) casilla).getMonopolio().getPrecio()/2);
        Tablero.getPrompt().setMotivoPago("Hipoteca de la propiedad "+casilla.getNombre());
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

    public Casilla casillaCorrecta(String casilla){
        Casilla casillaComprar;

        casillaComprar=Tablero.getTablero().getCasilla(casilla);
        if(casillaComprar==null){
            return null;
        }
        return casillaComprar;
    }
}
