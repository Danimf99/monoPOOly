package monopooly.player;

import monopooly.colocacion.Posicion;

public class Avatar {
    private tipoAvatar tipo;
    private char representacion;
    private Jugador jugador;
    private Posicion posicion;

    /**
     * Inicializa avatar pasandole la representacion ASCII, el tipoAvatar que va a ser y el Jugador asociado a el
     * @param representacion
     * @param tipo
     * @param jugador
     */
    public Avatar(char representacion,tipoAvatar tipo,Jugador jugador){
        if(representacion!='\0' && tipo!=null && jugador!=null){
            this.representacion=representacion;
            //TODO Inicializar resto de atributos
        }
    }

    public char getRepresentacion() {
        return representacion;
    }
}
